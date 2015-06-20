package com.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: wenfe_000
 * Date: 5/14/14
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String enterHome() {

        return "home";
    }
}
