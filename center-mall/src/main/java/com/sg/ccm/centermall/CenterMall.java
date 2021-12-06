package com.sg.ccm.centermall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sg.ccm.centermall"})
@EnableJpaRepositories(basePackages = "com.sg.ccm.centermall.repository")
@EntityScan(basePackages = "com.sg.ccm.centermall.model")
class CenterMallApplication {

  public static void main(String[] args) {
    SpringApplication.run(CenterMallApplication.class, args);
  }
}
