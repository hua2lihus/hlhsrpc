package com.hlhs.examplespringbootconsumer;

import com.hlhs.examplespringbootconsumer.service.ExampleServicerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ExampleSpringbootConsumerApplicationTests {


    @Resource
    private ExampleServicerImpl servicer;

    @Test
    void contextLoads() {
        servicer.test();
    }

}
