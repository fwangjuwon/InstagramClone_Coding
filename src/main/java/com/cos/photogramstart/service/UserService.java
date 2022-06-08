package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User 회원수정(int id, User user) {

        //1. 영속화
        User userEntity = userRepository.findById(id).orElseThrow(()->{
                return new CustomValidationApiException("찾을 수 없는 id입니다");
        });

        //2. 영속화된 object 수정하기 - 더티체킹(업데이트 완료)
        userEntity.setName(user.getName());

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        userEntity.setPassword(encPassword);

        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
//더티체킹 일어나서 수정 완료     
    }
}
