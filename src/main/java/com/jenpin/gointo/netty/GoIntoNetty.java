package com.jenpin.gointo.netty;/**
 * Created by Administrator on 2018/10/25.
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: Jenpin
 * @date: 2018/10/25 22:47
 * @email: yuan_268311@163.com
 * @description:
 **/
@Slf4j
@SpringBootApplication
public class GoIntoNetty {
    public static void  main(String[] args){
        SpringApplication.run(GoIntoNetty.class,args);
        log.info("====================启动成功!====================");
    }
}
