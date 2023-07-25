package com.rainbowdoor.vrmeet.mapper;

import com.rainbowdoor.vrmeet.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT u.uid, u.name, u.rid, r.rname FROM User u, Role r Where u.rid = r.rid And u.name = #{name} And u.password = #{password}")
    List<User> getUser(@Param("name") String name, @Param("password") String password);

    @Options(statementType = StatementType.CALLABLE)
    @Select("CALL register(#{name}, #{password}, #{rid}, #{state, mode=OUT, jdbcType=INTEGER})")
    void registerUser(@Param("name") String name,
                      @Param("password") String password,
                      @Param("rid") int rid,
                      @Param("state") Integer state);
}
