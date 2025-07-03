package com.demo;

import com.demo.dao.UserMasterMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMasterMapper userMasterMapper;

    @Test
    void contextLoads() {

    }
}
