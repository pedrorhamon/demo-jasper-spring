package com.mballem.curso.jasper.spring.controller;

import java.sql.Connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@Autowired
	private Connection connection;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/conn")
    public String myConn(Model model) {
    	model.addAttribute("conn", connection != null ? "Conexão ok" : "Ops... erro");
        return "index";
    }
}
