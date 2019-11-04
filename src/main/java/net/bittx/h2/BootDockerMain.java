package net.bittx.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BootDockerMain {
    private static final Logger logger = LoggerFactory.getLogger(BootDockerMain.class);
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() ->{
                    System.out.println("执行钩子线程>>>> sout");
                    logger.error("执行钩子线程>>> logger");
                } ));
        SpringApplication.run(BootDockerMain.class, args);
    }

}
