package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class GeneralController {

    @GetMapping
    public String getHomePage(Model m, Principal p){

        if (((UsernamePasswordAuthenticationToken) p )!= null){
            ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p) .getPrincipal();
            m.addAttribute("isAllow",true);
            m.addAttribute("loggedInUser",userDetails);
        }else {
            m.addAttribute("isAllow",false);

        }
        return "homepage.html";
    }
}
