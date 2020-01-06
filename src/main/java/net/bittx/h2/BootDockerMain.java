package net.bittx.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class BootDockerMain {
    private static final Logger logger = LoggerFactory.getLogger(BootDockerMain.class);
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    System.out.println("执行钩子线程>>>> sout");
                    logger.error("执行钩子线程>>> logger");
                }));
        ConfigurableApplicationContext context = SpringApplication.run(BootDockerMain.class, args);
        ApplicationArguments arguments = context.getBean(ApplicationArguments.class);
        System.out.println("name = " + arguments.getOptionNames());
        System.out.println("values = " + arguments.getOptionValues("developer.name"));
        System.out.println("Server started up.");
    }
}
