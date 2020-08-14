package com.cntytz.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableAdminServer
@SpringBootApplication
public class CntytzMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CntytzMonitorApplication.class, args);
        log.info("------------cntytz-monitor started successfully-------------");
    }

}
