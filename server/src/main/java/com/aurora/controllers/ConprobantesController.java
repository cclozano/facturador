package com.aurora.controllers;

import com.aurora.pos.repositorios.FacturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ConprobantesController {

    @Autowired
    private FacturaRepositorio facturaRepositorio;


    @RequestMapping(value = "/respuesta", method = RequestMethod.GET)
    private ModelAndView respuesta(String id)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("comprobantes");

        modelAndView.addObject("message", "Hello World..!!! I'm live!!");
        return modelAndView;
    }



    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    private ModelAndView greeting()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/xxx.html");
        modelAndView.addObject("message", "Hello World..!!! I'm live!!");
        return modelAndView;
    }

}
