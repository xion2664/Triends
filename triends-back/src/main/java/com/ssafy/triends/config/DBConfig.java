package com.ssafy.triends.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.ssafy.triends.*.model.mapper")
public class DBConfig {

}
