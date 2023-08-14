package com.teruterubozu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;

import com.google.gson.Gson;
import com.teruterubozu.domain.Latlon;
import com.teruterubozu.domain.Result;
import com.teruterubozu.domain.Weather;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class weatherService {
  public Latlon computeGrid(double lat, double lon) {
    Latlon gpt = new Latlon();

    double RE = 6371.00877; // 지구 반경(km)
    double GRID = 5.0; // 격자 간격(km)
    double SLAT1 = 30.0; // 투영 위도1(degree)
    double SLAT2 = 60.0; // 투영 위도2(degree)
    double OLON = 126.0; // 기준점 경도(degree)
    double OLAT = 38.0; // 기준점 위도(degree)
    double XO = 43; // 기준점 X좌표(GRID)
    double YO = 136; // 기1준점 Y좌표(GRID)

    double DEGRAD = Math.PI / 180.0;

    double re = RE / GRID;
    double slat1 = SLAT1 * DEGRAD;
    double slat2 = SLAT2 * DEGRAD;
    double olon = OLON * DEGRAD;
    double olat = OLAT * DEGRAD;

    double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
    double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
    sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
    double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
    ro = re * sf / Math.pow(ro, sn);

    double ra = Math.tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5);
    ra = re * sf / Math.pow(ra, sn);
    double theta = lon * DEGRAD - olon;
    if (theta > Math.PI)
      theta -= 2.0 * Math.PI;
    if (theta < -Math.PI)
      theta += 2.0 * Math.PI;
    theta *= sn;

    gpt.setLat(Math.floor(ra * Math.sin(theta) + XO + 0.5));
    gpt.setLon(Math.floor(ro - ra * Math.cos(theta) + YO + 0.5));

    return gpt;
  }

  public Result getWeather(Latlon grid) {
    Date tmp = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    String base_date = format.format(tmp);
    format = new SimpleDateFormat("HHmm");
    String base_time = format.format(tmp);

    Result result = new Result();

    base_time = String.valueOf(Integer.parseInt(base_time.substring(0, 2)) - 1) + "00";

    try {
      StringBuilder urlBuilder = new StringBuilder(
          "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /* URL */
      urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "="
          + "eM2GI%2FKiuCJfMm9yc1w%2FSDnIXO204g2EwTn2ZHYbJIIW364iAYg56QvjLWIhpqrsgv9iUPr15YK6SQQP9Wbimg%3D%3D"); /*
                                                                                                                  * Service
                                                                                                                  * Key
                                                                                                                  */
      urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*
                                                                                                              * 페이지번호
                                                                                                              */
      urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "="
          + URLEncoder.encode("1000", "UTF-8")); /* 한 페이지 결과 수 */
      urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "="
          + URLEncoder.encode("JSON", "UTF-8")); /* 요청자료형식(XML/JSON) Default: XML */
      urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "="
          + URLEncoder.encode(base_date, "UTF-8")); /* ‘21년 6월 28일 발표 */
      urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "="
          + URLEncoder.encode(base_time, "UTF-8")); /* 06시30분 발표(30분 단위) */
      urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "="
          + URLEncoder.encode(String.valueOf((int) grid.getLat()), "UTF-8")); /* 예보지점 X 좌표값 */
      urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "="
          + URLEncoder.encode(String.valueOf((int) grid.getLon()), "UTF-8")); /* 예보지점 Y 좌표값 */

      URL url = new URL(urlBuilder.toString());
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Content-type", "application/json");
      BufferedReader rd;
      if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      } else {
        rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
      }
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = rd.readLine()) != null) {
        sb.append(line);
      }
      rd.close();
      conn.disconnect();

      JSONParser jsonParser = new JSONParser();
      JSONObject jsonObject = (JSONObject) jsonParser.parse(sb.toString());

      JSONObject response = (JSONObject) jsonObject.get("response");
      JSONObject header = (JSONObject) response.get("header");

      if (!header.get("resultCode").equals("00")) {
        result.setResultCode((String) header.get("resultCode"));
        result.setResultMsg((String) header.get("resultMsg"));
        return result;
      }

      result.setResultCode((String) header.get("resultCode"));
      result.setResultMsg((String) header.get("resultMsg"));

      JSONObject body = (JSONObject) response.get("body");
      JSONObject items = (JSONObject) body.get("items");
      JSONArray item = (JSONArray) items.get("item");

      List<Weather> weatherList = new ArrayList<Weather>();

      Gson gson = new Gson();

      for (Object object : item) {
        Weather tmpObject = gson.fromJson(object.toString(), Weather.class);
        if (tmpObject.getCategory().equals("T1H")
            || tmpObject.getCategory().equals("SKY") || tmpObject.getCategory().equals("PTY")
            || tmpObject.getCategory().equals("WSD")) {
          weatherList.add(tmpObject);
        }
      }

      result.setData(weatherList);

      return result;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return result;
  }
}
