package com.example.cctvforchating;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;


@SpringBootApplication
@Controller
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) // db 설정 없이 스프링 실행 시키기 위한 코드이다.
public class CctvForChatingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CctvForChatingApplication.class, args);
    }

}
