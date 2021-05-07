package com.example.codefellowship.controllers;

import com.example.codefellowship.models.ApplicationUser;
import com.example.codefellowship.models.UserPost;
import com.example.codefellowship.repositories.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class PostController {
    @Autowired
    UserPostRepository userPostRepository;

    @GetMapping("/posts")
    public String posts(Model m, Principal p){
        if (((UsernamePasswordAuthenticationToken) p) != null ){
            UserDetails userDetails = (UserDetails) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
            m.addAttribute("username",userDetails.getUsername());
        }
        ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();

//        UserPost postDetails = (UserPost) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
        m.addAttribute("posts",userPostRepository.findAll());
//        m.addAttribute("posts",userPostRepository.findById(userDetails.getId()));

//        System.out.println(userPostRepository.findAll());
        return "posts";
    }

    @PostMapping("/addPost")
    public RedirectView addPost(@RequestParam(value = "body") String body , Principal p){
        ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
        UserPost newPost = new UserPost(body,userDetails);

        userPostRepository.save(newPost);
        System.out.println(newPost);
        return new RedirectView("/myprofile");

    }
}
