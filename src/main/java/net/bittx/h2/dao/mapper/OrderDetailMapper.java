package net.bittx.h2.dao.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * CacheNamespace use to level 2 cache.
 * Need set cacheEnabled to true in ibatis.xml

@CacheNamespace
*/

public interface OrderDetailMapper {
    @Insert("<script>" +
            "INSERT INTO order_detail (id,name,sku,c_time)" +
            " VALUES" +
            " <foreach collection='list' separator=',' item='i'>" +
            " (#{i.id,jdbcType=BIGINT}," +
            "  #{i.name,jdbcType=VARCHAR}," +
            "  #{i.sku,jdbcType=VARCHAR}," +
            "  #{cTime})" +
            "</foreach>" +
            "</script>")
    int insertOrderDetails(@Param("list") List<Map<String, Object>> lst,
                           @Param("cTime") LocalDateTime ldt);


    @Select("SELECT * FROM order_detail")
    List<Map<String,Object>> queryOrders();
}
