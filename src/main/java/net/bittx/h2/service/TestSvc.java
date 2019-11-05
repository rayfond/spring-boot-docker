package net.bittx.h2.service;

import net.bittx.h2.dao.mapper.ChildMapper;
import net.bittx.h2.dao.mapper.OrderDetailMapper;
import net.bittx.h2.dao.mapper.ParentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestSvc {

    @Resource
    OrderDetailMapper orderDetailMapper;

    @Resource
    ParentMapper parentMapper;

    @Resource
    ChildMapper childMapper;


    @Transactional
    public int testAspect(){

        parentMapper.insertParent();

        try {
            childMapper.insetChild();
            int x = 0;
            int y = 1/x;
            System.out.println(">>>>");
        } catch (Exception e) {
            // rollback the entire transaction, so the bellow method call:
            // orderDetailMapper.insertOrderDetails will be canceled
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            System.out.println(e.getMessage());
        }


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
        orderDetailMapper.insertOrderDetails(lst, LocalDateTime.now());

        return 1;
    }
}
