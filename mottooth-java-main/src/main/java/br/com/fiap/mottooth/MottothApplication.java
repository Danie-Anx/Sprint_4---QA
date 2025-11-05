package br.com.fiap.mottooth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MottothApplication {

    public static void main(String[] args) {
        SpringApplication.run(MottothApplication.class, args);
    }
}
