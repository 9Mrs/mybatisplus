package com.sjm.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjm.pojo.User;
import org.springframework.stereotype.Repository;

//@Mapper
//Repository将接口标记为实体
@Repository
public interface UserMapper extends BaseMapper<User> {
//    此处可以自定义查询

    int insertSelective(User user);
}
