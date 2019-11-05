package net.bittx.h2.dao.mapper;


import org.apache.ibatis.annotations.Insert;

public interface ParentMapper {

    @Insert("<script>" +
            "INSERT INTO parent (parentName)" +
            " VALUES ('Ray')" +
            "</script>")
    void insertParent();
}
