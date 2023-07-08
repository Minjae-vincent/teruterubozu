package com.teruterubozu.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.teruterubozu.domain.Latlon;
import com.teruterubozu.domain.Result;
import com.teruterubozu.service.weatherService;


@RestController
public class WeatherController {
    @Autowired
    private weatherService weatherService;    
    @PostMapping("/api/weather")
    public Result queryWeather(@RequestBody Latlon weatherDTO) {
        Latlon grid = weatherService.computeGrid(weatherDTO.getLat(), weatherDTO.getLon());
        Result result = weatherService.getWeather(grid);
        
        return result;
    }

}
