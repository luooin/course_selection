package com.example.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenBrowser implements CommandLineRunner {
    //项目启动之前，预先加载数据。
    private static final Logger logger = LoggerFactory.getLogger(OpenBrowser.class);

    @Value("${open.browser.url}")
    private String url;

    @Override
    public void run(String... args) throws Exception {
        logger.info("项目启动成功……");
        try {
            //指定浏览器的路径
            Runtime.getRuntime().exec("cmd   /c   start   " + url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}