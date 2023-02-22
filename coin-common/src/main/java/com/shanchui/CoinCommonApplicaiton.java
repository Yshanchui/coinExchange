package com.shanchui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import javax.swing.*;

@SpringBootApplication
@EnableDiscoveryClient
public class CoinCommonApplicaiton {
    public static void main(String[] args) {
        SpringApplication.run(CoinCommonApplicaiton.class, args);
    }
}
