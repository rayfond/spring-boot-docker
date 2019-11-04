package net.bittx.h2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("a")
public class A {

    @RequestMapping("b")
    public String hello(){
        return "Hey Guys!";
    }

    public String test(){
        return "Hey Guys!";
    }
}
