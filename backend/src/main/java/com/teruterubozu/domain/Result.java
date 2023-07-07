package com.teruterubozu.domain;

import java.util.List;

import lombok.Data;

@Data
public class Result {
    private String resultCode;
    private String resultMsg;
    private List<Weather> data;
}
