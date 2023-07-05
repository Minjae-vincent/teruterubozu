package com.teruterubozu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teruterubozu.domain.weatherDTO;
import com.teruterubozu.service.weatherService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class WeatherController {
    @Autowired
    private weatherService weatherService;    
    @PostMapping("/api/weather")
    public void queryWeather(@RequestBody weatherDTO weatherDTO) {
        weatherDTO grid = weatherService.computeGrid(weatherDTO.getLat(), weatherDTO.getLon());
        Map<String, Object> weather = weatherService.getWeather(grid);
        
    }

}
