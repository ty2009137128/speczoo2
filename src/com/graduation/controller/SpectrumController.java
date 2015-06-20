package com.graduation.controller;


import com.graduation.model.SpectrumElement;
import com.graduation.service.ISpectrumService;
import com.graduation.service.IUserService;


import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping({"/spectrum"})
public class SpectrumController
{

  @SuppressWarnings("unused")
@Inject
  private ISpectrumService spectrumService;
  @SuppressWarnings("unused")
private IUserService userService;

 

  @RequestMapping(value={"/specview"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public String spectrumSave(@RequestParam("z") String redShiftvalue, @RequestParam("showName") String showName, @RequestParam("info") String info,@RequestParam("userName") String userName, HttpServletRequest request, Model model)
  {
    Object temp = request.getSession().getAttribute("loginUser");

    String fitsName = null;
    String template_name = null;
    String template_nameChanged = null;
    if (temp != null)
    {
      String[] showNames = showName.split(";");
      if (showNames[0].length() > showNames[1].length()) {
        fitsName = showNames[0];
        template_name = showNames[1];
      }
      else {
        fitsName = showNames[1];
        template_name = showNames[0];
      }
      System.out.println("fitsName" + fitsName);
      System.out.println("template_nameChanged" + template_name);
      template_nameChanged = template_name.substring(1, template_name.length());
    }

    SpectrumElement spectrum = new SpectrumElement();
    spectrum.setUserName(userName);
    spectrum.setFitsName(fitsName);
    spectrum.setRedShift(Double.parseDouble(redShiftvalue));
    spectrum.setTemplateName(template_nameChanged);
    spectrum.setNote(info);
    System.out.println("spectrum从哪里开始？？");

    this.spectrumService.insertSpectrum(spectrum);
    model.addAttribute("saved", "1");
    return "./result";
  }

/*
  @RequestMapping(value={"/specview"}, method = RequestMethod.POST)
  public void spectrumSave(HttpServletRequest request, HttpServletResponse response)
  {
	  String z=request.getParameter("z"); 
	  System.out.print("接收到了Z了吗？"+z);
  }
  */

}
