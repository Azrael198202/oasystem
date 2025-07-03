package com.demo;

import com.demo.listener.PropertiesListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/*
 * Author  : baiye
   Time    : 2021/06/30
   Function: 项目启动类
*/
@Configuration
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(DemoApplication.class);

        // 添加配置文件监听器
        application.addListeners(new PropertiesListener("mail-templates.properties"));

        application.run(args);
        }

}
