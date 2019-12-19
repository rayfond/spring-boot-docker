package net.bittx.h2.controller;

import net.bittx.h2.anno.LoginUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("bind")
public class BindingController {


    // 如果使用postman调用 http://localhost:8080/bind/2019-12-10 12:00:00
    // 将会抛出类型转换异常： “Failed to convert value of type 'java.lang.String'
    // to required type 'java.time.LocalDatetime'; nested exception is
    // org.springframework.core.convert.ConversionFailedException ...
    // 因为spring默认不支持将String类型的请求参数转换为LocalDateTime类型，所以我们需要
    // 自定义converter [转换器] 完成这个过程


    /**
     * 测试绑定到spring mvc 未实现的java类型
     *
     * @param dateTime
     */
    @GetMapping("{dt}")
    public Object getSpecificDateInfo(@PathVariable("dt") LocalDateTime dateTime) {
        System.out.println(dateTime.toString());
        return dateTime.toString();
    }


    @GetMapping("id")
    public Object getLoginUserInfo(@LoginUser LoginUserVo loginUserVo) {
        System.out.println(loginUserVo.toString());
        return loginUserVo;
    }
}
