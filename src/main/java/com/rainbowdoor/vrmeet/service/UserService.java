package com.rainbowdoor.vrmeet.service;

import com.rainbowdoor.vrmeet.entity.User;
import com.rainbowdoor.vrmeet.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User login(String name, String password)
    {
        List<User> users = userMapper.getUser(name, password);
        if(users.size() == 0) return null;
        return users.get(0);
    }

    public boolean register(String name, String password, int rid)
    {
        Integer state = 0;
        userMapper.registerUser(name, password, rid, state);
        if(state == 0) return false;
        return true;
    }
}
