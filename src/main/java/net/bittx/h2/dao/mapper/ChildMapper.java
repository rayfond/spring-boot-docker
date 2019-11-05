package net.bittx.h2.dao.mapper;


import org.apache.ibatis.annotations.Insert;

public interface ChildMapper {
    @Insert("<script>" +
            "INSERT INTO child (childName,parentId)" +
            " VALUES ('chils', 5432 )" +
            "</script>")
    void insetChild();
}
