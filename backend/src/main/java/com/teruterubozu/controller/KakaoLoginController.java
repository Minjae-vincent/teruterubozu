package com.teruterubozu.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/oauth2/kakao")
@Slf4j
public class KakaoLoginController {
    @GetMapping("/authenticate")
    public void authenticate(@RequestParam(name = "code") String code) throws MalformedURLException {
        log.info(code);

    }
}
