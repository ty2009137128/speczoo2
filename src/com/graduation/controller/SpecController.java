package com.graduation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpecController
{
  @RequestMapping(value={"./result"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public String specviewapplet()
  {
    return "./result";
  }
}