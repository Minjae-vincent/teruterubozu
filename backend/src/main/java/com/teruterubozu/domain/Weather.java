package com.teruterubozu.domain;

import lombok.Data;

@Data
public class Weather {
    private String baseDate;
    private String baseTime;
    private String category;
    private String fcstDate;
    private String fcstTime;
    private String fcstValue;
    private String nx;
    private String ny;
}
