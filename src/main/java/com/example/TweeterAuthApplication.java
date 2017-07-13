package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @see https://github.com/Pivotal-Japan/spring-security-oauth-workshop/blob/master/authorization-server.md
 */
@SpringBootApplication
@EnableAuthorizationServer
public class TweeterAuthApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(TweeterAuthApplication.class, args);
  }
}
