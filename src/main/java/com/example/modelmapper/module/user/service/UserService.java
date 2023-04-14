package com.example.modelmapper.module.user.service;

import com.example.modelmapper.module.user.entity.User;
import com.example.modelmapper.module.user.mapper.UserMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserMapperRepository userMapperRepository;

    private final ModelMapper mapper;

    @Transactional
    public User save(User user){
        int a= userMapperRepository.saveUser(user);
        log.info(String.valueOf(a));
        return mapper.map(userMapperRepository.findById(a), User.class);
    }
}
