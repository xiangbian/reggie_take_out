package com.xiangbian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiangbian.reggie.entity.User;
import com.xiangbian.reggie.mapper.UserMapper;
import com.xiangbian.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
