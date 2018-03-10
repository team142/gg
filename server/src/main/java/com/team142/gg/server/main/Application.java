package com.team142.gg.server.main;

import com.team142.gg.server.controller.runnable.TickerTimeseries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        new Thread(new TickerTimeseries()).start();

    }
}
