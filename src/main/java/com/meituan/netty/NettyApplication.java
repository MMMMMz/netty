package com.meituan.netty;

import com.meituan.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@Slf4j
public class NettyApplication  extends SpringBootServletInitializer {
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NettyApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
		//开启Netty服务
		NettyServer nettyServer = new NettyServer();
		nettyServer.start();
		log.info("======服务已经启动========");
	}
}
