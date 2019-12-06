package net.bittx.h2.controller;

import net.bittx.h2.anno.Evt;
import org.apache.ibatis.annotations.Param;
import org.aspectj.lang.annotation.RequiredTypes;
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


    @Evt(code = "code",value = "33")
    @RequestMapping("c")
    public String anno(){
        return "Test @Evt!";
    }

    @Evt("33")
    @RequestMapping("d")
    public String anno2(){
        return "Test @Evt!";
    }

    @Evt
    @RequestMapping("f")
    public String f(String code, String value,int index){
        return "Test @Evt with params!";
    }
}
