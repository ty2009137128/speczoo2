package com.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/tool")
public class ToolsController {

		
	    @RequestMapping(value="/tools",method = RequestMethod.GET)
	    public String showtools() {

	        return "tool/tools";
	    }
}