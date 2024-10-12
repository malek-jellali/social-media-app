package com.jellali.fullstacksocialmedia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.jellali.Entities.User , com.jellali.Entities.Post"  )
public class FullstackSocialMediaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FullstackSocialMediaApplication.class, args);
    }

}
