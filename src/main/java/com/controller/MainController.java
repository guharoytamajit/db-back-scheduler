package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.job.DataBackup;


@Controller
@RequestMapping(value = "/")
public class MainController {

    @RequestMapping(value = "/info.htm", method = RequestMethod.GET)
    public String name() {
        return "home";
    }
}