package net.bittx.h2;

import net.bittx.h2.util.Str;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EnvironmentPrinter implements CommandLineRunner, EnvironmentAware {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentPrinter.class);

    int padding = 32;

    @Override
    public void run(String... args) throws Exception{

        logger.info("\n================== ARGUMENTS ==================\n");

        if(args != null && args.length > 0){
            for(String arg  : args){
                logger.info(arg);
            }
        }
    }

    @Override
    public void setEnvironment(Environment environment){
        ConfigurableEnvironment confEnv = (ConfigurableEnvironment) environment;

        logger.info("\n================== SYSTEM PROPERTIES ==================\n");
        printMap(confEnv.getSystemProperties());

        logger.info("\n================== SYSTEM ENVIRONMENTS ==================\n");
        printMap(confEnv.getSystemEnvironment());
    }

    private void printMap(Map<String,Object> map){

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(o-> System.out.println(Str.rightPad(o.getKey(), padding," ")
                        + "： "
                        + o.getValue().toString()
                ));
    }
}