package com.mzaxd.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author root
 */
@Component
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) {
        System.out.println("程序初始化");
    }
}