package com.example.Intellihealth.controller;


import com.example.Intellihealth.Service.SPARQLService;
import com.example.Intellihealth.model.HealthDataDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Query_controller {
    @Autowired
    private SPARQLService sparqlService;

    @GetMapping("/")
    public ModelAndView home() {
        System.out.println("Home Controller called");
        return new ModelAndView("index");
    }

    @PostMapping("/saveHealthData")
    public ResponseEntity<Map<String, Object>> executeCustomQuery(@RequestBody HealthDataDTO healthData) {
        Map<String, Object> response = new HashMap<>();
        System.out.println(healthData.getSmoke());
        System.out.println(healthData.getBloodPressure());
        System.out.println(healthData.getAge());
        List<Integer> queryResults = sparqlService.buildCustomQuery(healthData);
//        model.addAttribute("copdResults", queryResults.get(0));
//        model.addAttribute("covidResults", queryResults.get(1));
//        model.addAttribute("cardioResults", queryResults.get(2));
//        model.addAttribute("genericMaleResults", queryResults.get(3));
//        model.addAttribute("genericFemaleResults", queryResults.get(4));
        response.put("copdResults", queryResults.get(0));
        response.put("covidResults", queryResults.get(1));
        response.put("cardioResults", queryResults.get(2));
        response.put("genericMaleResults", queryResults.get(3));
        response.put("genericFemaleResults", queryResults.get(4));
        System.out.println(queryResults.get(0));
        System.out.println(queryResults.get(1));
        System.out.println(queryResults.get(2));
        System.out.println(queryResults.get(3));
        System.out.println(queryResults.get(4));
        // return new ResponseEntity<>(Integer.toString(0), HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/displayData")
    public ModelAndView displayData() {
        System.out.println("displayData Controller called");
        return new ModelAndView("displayData");
    }
}
