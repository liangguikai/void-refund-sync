package com.remoc.sync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VoidRefundSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoidRefundSyncApplication.class, args);
    }

}
