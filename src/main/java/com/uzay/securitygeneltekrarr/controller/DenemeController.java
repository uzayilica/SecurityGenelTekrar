package com.uzay.securitygeneltekrarr.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DenemeController {


    @GetMapping("/acik")
    public String Acik() {
       return "acik";
    }





    @GetMapping("/deneme")
    public ModelAndView DENEME() {
        ModelAndView modelAndView = new ModelAndView("home"); // home.html dosyasını döndürüyoruz
        modelAndView.addObject("message", "Merhaba, Thymeleaf!");
        return modelAndView;
    }
    @GetMapping("/customloginurl")
    public ModelAndView customloginUrl() {
        ModelAndView modelAndView = new ModelAndView("customloginurl"); // customloginUrl.html dosyasını döndürüyoruz
        return modelAndView;
    }
}



