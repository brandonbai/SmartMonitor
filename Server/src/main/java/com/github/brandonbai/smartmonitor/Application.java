package com.github.brandonbai.smartmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author brandonbai
 * @since 2018/05/31
 */
@SpringBootApplication
@tk.mybatis.spring.annotation.MapperScan("com.github.brandonbai.smartmonitor.mapper")
@ImportResource(value = {"classpath:spring/applicationContext-transaction.xml"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}