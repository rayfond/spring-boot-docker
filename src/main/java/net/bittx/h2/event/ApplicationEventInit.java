package net.bittx.h2.event;

import net.bittx.h2.dao.mapper.OrderDetailMapper;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Application event to be handle.
 *
 * @author Asin Liu
 * @version 1.0.0
 * @since 1.0.0
 */

@Component
public class ApplicationEventInit {

    @Resource
    OrderDetailMapper mapper;

    @EventListener(ApplicationStartedEvent.class)
    public void run() {
        //log.info(">>> ApplicationStartedEvent fired!");
        try {
            Map<String,Object> m1 = new HashMap<String,Object>(){{
                put("id","1");
                put("name","tv");
                put("sku","ele");
            }};

            Map<String,Object> m2 = new HashMap<String,Object>(){{
                put("id",2);
                put("name","pvc");
                put("sku","cons");
            }};

            List<Map<String,Object>> lst = new ArrayList<>();
            lst.add(m1);
            lst.add(m2);
            mapper.insertOrderDetails(lst, LocalDateTime.now());

            List list = mapper.queryOrders();
            System.out.println(list.size());

            list = mapper.queryOrders();
            System.out.println(list.size());
        } catch (Exception e) {
            e.printStackTrace();
            //log.error(e.getMessage(),e);
        }
        //log.info(">>> ApplicationStartedEvent ended. ");
    }
}
