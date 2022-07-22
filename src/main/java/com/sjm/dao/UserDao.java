package com.sjm.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjm.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User selectByOne(Long id);

    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
}
