package net.bittx.h2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("profile")
public class PlaceHolderController {


    @Value("${profile-test-controller}")
    private String profile;

    @RequestMapping("test")
    public String getProfile(){
        return profile;
    }


}
