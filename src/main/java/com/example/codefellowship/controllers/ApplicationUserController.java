package com.example.codefellowship.controllers;

import com.example.codefellowship.repositories.ApplicationUserRepository;
import com.example.codefellowship.repositories.UserPostRepository;
import com.example.codefellowship.models.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserPostRepository userPostRepository;

    @GetMapping("/signup")
    public String getSignUpPage(){
        return "signup.html";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestParam(value="username") String username, @RequestParam(value="password") String password, @RequestParam(value="firstName") String firstName, @RequestParam(value="lastName") String lastName, @RequestParam(value="dateOfBirth") String dateOfBirth, @RequestParam(value="bio") String bio){
        ApplicationUser newUser = new ApplicationUser(bCryptPasswordEncoder.encode(password),username,firstName,lastName,dateOfBirth,bio);

        newUser = applicationUserRepository.save(newUser);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/login");
    }



    @GetMapping("/myprofile")
    public String profile(Model m, Principal p){
        ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p).getPrincipal();
        m.addAttribute("user", applicationUserRepository.findById (userDetails.getId()).get());
//        m.addAttribute("user2",userPostRepository.findById(userDetails.getId()).get());
        Integer id =  userDetails.getId();
        m.addAttribute("user2",userPostRepository.findByUserId(id));
        System.out.println(userPostRepository.findByUserId(id));
        m.addAttribute("isAllow",true);
//        ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p) .getPrincipal();
        m.addAttribute("loggedInUser",userDetails);
        m.addAttribute("isAllowNav",true);

        return "profile";
    }

    @GetMapping("/users/{id}")
    public String getUserProfilePage(@PathVariable(value = "id") Integer id, Principal p, Model m){
        if (((UsernamePasswordAuthenticationToken) p) != null ){
            ApplicationUser userDetails =  applicationUserRepository.findById(id).get();
            m.addAttribute("user",userDetails.getUsername());
        }
        ApplicationUser applicationRequiredUser =  applicationUserRepository.findById(id).get();
//        ApplicationUser user=  applicationUserRepository.findById(id).get();
        if (applicationRequiredUser != null){
            if (((UsernamePasswordAuthenticationToken) p) != null){
                ApplicationUser userDetails = (ApplicationUser) ((UsernamePasswordAuthenticationToken) p) .getPrincipal();
                m.addAttribute("loggedInUser",userDetails);
                m.addAttribute("isAllowNav",true);
                System.out.println("userId"+userDetails.getId()+" pathVraiable Id: "+ id);
                if (((int)id == userDetails.getId())){
                    System.out.println("Inside if true");
                    m.addAttribute("user2",userPostRepository.findByUserId(id));
                    m.addAttribute("isAllow",true);
//                    m.addAttribute("user",user);
                }else {
                    System.out.println("Inside if false");
                    m.addAttribute("user2",userPostRepository.findByUserId(id));
                    m.addAttribute("isAllow",false);
                }
            }
            m.addAttribute("user", applicationRequiredUser);
            return "profile";
        }
        //            String massage = "The user does not exist";
            m.addAttribute("message","Id%is%not%used");
            return "error.html";
        }

}