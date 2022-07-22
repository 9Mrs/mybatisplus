package com.sjm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjm.mapper.UserMapper;
import com.sjm.pojo.User;
import com.sjm.service.UsersService;
import org.springframework.stereotype.Service;

//使用IService需要继承并实现IServiceImpl 的方法

@Service
public class UsersServiceImpl extends ServiceImpl<UserMapper, User> implements UsersService {

}
