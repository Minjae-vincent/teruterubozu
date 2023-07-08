<template>
  <div>
    <h2>카카오 맵 보기</h2>
    <div id="map"></div>
    <br />
    <v-btn @click="getWeather">날씨 가져오기</v-btn>
    <br />
  </div>
  {{ weatherMonitor.temp }}
  <br />
  {{ weatherMonitor.sky }}
  <br />
  {{ weatherMonitor.precipitation }}
</template>
<style scoped>
#map {
  width: 50%;
  height: 400px;
  margin: 0 auto;
  text-align: center;
}
</style>
<script>
import axios from 'axios';

export default {
  name: 'KakaoMap',
  props: {
    lat: Number,
    lon: Number,
  },
  data() {
    return {
      map: null,
      weather: {
        temp: [],
        sky: [],
        precipitation: [],
      },
    };
  },
  computed: {
    weatherMonitor() {
      return this.weather;
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.loadMap();
    } else {
      this.loadScript();
    }
  },
  methods: {
    loadScript() {
      const script = document.createElement('script');
      script.src =
        '//dapi.kakao.com/v2/maps/sdk.js?appkey=f4629327dac89bf72282190258ee9716&autoload=false';
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    loadMap() {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(this.lat, this.lon),
        level: 1,
      };

      this.map = new window.kakao.maps.Map(container, options);
      this.loadMaker();
    },
    loadMaker() {
      const markerPosition = new window.kakao.maps.LatLng(this.lat, this.lon);

      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
      });

      marker.setMap(this.map);
    },
    getWeather() {
      axios
        .post('http://localhost:9000/api/weather', {
          lat: this.lat,
          lon: this.lon,
        })
        .then((res) => {
          this.weather = {
            temp: [],
            sky: [],
            precipitation: [],
          };
          res.data.data.forEach((element) => {
            if (element.category === 'T1H') {
              this.weather.temp.push(element);
            } else if (element.category === 'SKY') {
              this.weather.sky.push(element);
            } else if (element.category === 'RN1') {
              this.weather.precipitation.push(element);
            }
          });
        })
        .then(() => {
          console.log('asdf');
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
