package com.example.modelmapper.module.user.mapper;

import com.example.modelmapper.module.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper // Mapper용으로 쓸 Repository라고 선언하는 어노테이션
public interface UserMapperRepository {

// 회원가입
    int saveUser(User user);
    User findById(int id);
}
