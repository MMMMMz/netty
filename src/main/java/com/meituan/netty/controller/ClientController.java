package com.meituan.netty.controller;

import com.meituan.netty.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mazhe
 * @date 2023/8/25 20:17
 */
@RestController
@Slf4j
public class ClientController {
    @GetMapping("/helloNetty")
    public String helloNetty(@RequestParam String msg) {
        return NettyClient.helloNetty(msg);
    }
}
