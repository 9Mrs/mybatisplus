package com.sjm.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjm.dao.UserDao;
import com.sjm.mapper.UserMapper;
import com.sjm.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserDao userDao;

    public List<User> selectAll() {
        return userMapper.selectList(null);
    }

    public User selectById(Long id) {
        return userDao.selectByOne(id);
    }

    public List<User> selectByIds(List<Long> ids) {

        return userMapper.selectBatchIds(ids);
    }

    public Integer insertOne(User user) {
        return userMapper.insert(user);
    }

    public Integer deleteById(Long id) {
        return userMapper.deleteById(id);
    }

    public Integer deleteByMap(Map<String, Object> map) {
        return userMapper.deleteByMap(map);
    }

    public Integer deleteByList(List<Long> ids) {
        return userMapper.deleteBatchIds(ids);
    }

    public Integer updateUser(User user) {
        return userMapper.updateById(user);
    }


    public List<User> TestWrapper(String name, Integer minAge, Integer maxAge, String email) {
//        查询用户名包含a，年龄在20到23之间，邮箱信息不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name)
                .between("age", minAge, maxAge)
                .isNotNull(email);
        return userMapper.selectList(queryWrapper);
    }

    public List<User> TestWrapperAsc() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        先根据年龄降序排列，如果年龄想相同再根据id升序排列
        queryWrapper.orderByDesc("age")
                .orderByAsc("id");
        return userMapper.selectList(queryWrapper);
    }


    //    组装删除
    public int deleteIsNullEmail() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email"); //条件构造器也可以构建删除语句的条件
        int result = userMapper.delete(queryWrapper);
        return result;
    }

    public int WrapperUpdate(User user, Integer age, String name, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", age).like("name", name).or().isNull(email);
        return userMapper.update(user, queryWrapper);
    }


    public int WrapperCondition(User user, QueryWrapper<User> queryWrapper) {
        return userMapper.update(user, queryWrapper);
    }

    public List<Map<String, Object>> WrapperSelectMoreCondition(String name, String age, String email) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(name, age, email);
        return userMapper.selectMaps(queryWrapper);
    }

    public List<User> WrapperSonSelect(Long id) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from t_user where id <= "+id);
        return userMapper.selectList(queryWrapper);
    }

    public int TestUpdateWrapper(String oldName,String name, Integer age, String OldEmail,String email) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("name", oldName)
                .and(i -> i.gt("age", age).or().isNull(OldEmail));
        updateWrapper.set("name",name).set("email",email);
        return userMapper.update(null, updateWrapper);
    }

    public List<User> ConditionAdd(QueryWrapper<User> queryWrapper) {
        return  userMapper.selectList(queryWrapper);
    }

    public List<User> LambdaWrapper(LambdaQueryWrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }

    public int updateWrapper(LambdaUpdateWrapper<User> updateWrapper) {
        return userMapper.update(null,updateWrapper);
    }

    public Page<User> selectPage(Page<User> page) {
        return userMapper.selectPage(page,null);
    }

    public Page<User> MySelfPage(Page<User> page, Integer age) {
        return userDao.selectPageVo(page,age);
    }
}
