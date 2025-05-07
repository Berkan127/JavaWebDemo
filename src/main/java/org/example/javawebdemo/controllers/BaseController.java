package org.example.javawebdemo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class BaseController {
    public String getLoggedInEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth != null){
            DefaultOAuth2User user = (DefaultOAuth2User) auth.getPrincipal();
            if(user != null){
                return user.getAttribute("email").toString();
            }

        }
        return null;
    }
}
