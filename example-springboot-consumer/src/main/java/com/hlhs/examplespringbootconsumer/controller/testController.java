package com.hlhs.examplespringbootconsumer.controller;

import com.hlhs.examplespringbootconsumer.service.ExampleServicerImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController()
@RequestMapping("/rpc")
public class testController {
    @Resource
    private ExampleServicerImpl servicer;
    @GetMapping("/test")
    public String test(){
        String userName = servicer.test();
        return userName;
    }
}
