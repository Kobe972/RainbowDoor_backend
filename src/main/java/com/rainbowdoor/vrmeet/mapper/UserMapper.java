package com.rainbowdoor.vrmeet.mapper;

import com.rainbowdoor.vrmeet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.data.jpa.repository.query.Procedure;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT u.uid, u.name, u.rid, r.rname FROM User u, Role r Where u.rid = r.rid And u.name = #{name} And u.password = #{password}")
    List<User> getUser(@Param("name") String name, @Param("password") String password);

    @Options(useGeneratedKeys = true, keyProperty = "state", keyColumn = "state", statementType = StatementType.CALLABLE)
    @Select("CALL register(#{name}, #{password}, #{rid}, #{state, mode=OUT, jdbcType=INTEGER})")
    void registerUser(String name, String password, int rid, Integer state);
}
