package com.yousset.rentcar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ybelandria
 */
@SpringBootApplication
@EnableTransactionManagement
@Slf4j
public class RentCarStartApplication {

    public static final String DEFAULT_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DEFAULT_FORMAT_DATE_GRAPH = "yyyy-MM-dd' 'HH:mm:ss.SSS";

    public static void main(String[] args) {
        SpringApplication.run(RentCarStartApplication.class, args);
    }
}
