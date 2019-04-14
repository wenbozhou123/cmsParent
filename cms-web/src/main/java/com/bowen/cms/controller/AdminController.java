package com.bowen.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("adminController")
public class AdminController {

    @RequestMapping("/admin")
    public String index(){
        return "admin/index";
    }
}
