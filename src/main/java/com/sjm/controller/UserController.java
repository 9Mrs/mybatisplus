package com.sjm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sjm.pojo.User;
import com.sjm.service.UserService;
import com.sjm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/selectAll")
    @ResponseBody
//    查询所有
    public List<User> selectALLUser() {
        return userService.selectAll();
    }

    //    根据Id查询
    @RequestMapping("/selectById")
    @ResponseBody
    public User selectById(Long id) {
        return userService.selectById(id);
    }

    //    根据多个Id查询
    @RequestMapping("/selectByMoreId")
    @ResponseBody
//    请求方式：http://localhost:8080/selectByMoreId?ids=1&ids=3
//SELECT id,name,age,email FROM user WHERE id IN ( ? , ? )
//    查询结果：
//    {"msg":"查询成功！",
//    "status":200,
//    "data":[{"id":1,"name":"Jone","age":18,"email":"test1@baomidou.com"},
//    {"id":3,"name":"Tom","age":28,"email":"test3@baomidou.com"}]}
    public ResultUtil selectByMoreId(Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        List<User> userList = userService.selectByIds(list);
        return ResultUtil.success("查询成功！", userList);
    }


    //    插入
    @RequestMapping("/insertOne")
    @ResponseBody
//    @RequestBody用于接收json格式的数据{"id":7,"name":"sjm","age":23,"email":"test4@baomidou.com"}
    public ResultUtil insertOne(@RequestBody User user) {
        if (userService.selectById(user.getId()) == null) {
            userService.insertOne(user);
            return ResultUtil.success("成功新增一条记录");
        } else {

            return ResultUtil.fail(201, "新增失败");
        }
    }

    //    删除
    @RequestMapping("/deleteById")
    @ResponseBody
    public ResultUtil DeleteById(Long id) {

        if (id != null && userService.deleteById(id) != 0) {
            return ResultUtil.success("成功删除一条记录");
        } else {
            return ResultUtil.fail(201, "删除失败");
        }
    }

    //    根据Map键值来删除
    @RequestMapping("/deleteByMap")
    @ResponseBody
//    {"id":8,"name":"liHua","age":23,"email":"test4@baomidou.com"}
    public ResultUtil DeleteByMap(@RequestBody User user) {

        Map<String, Object> map = new HashMap<>();

        if (user.toString() != null) {
//            设置删除的条件
            map.put("name", user.getName());
            map.put("age", user.getAge());
            int result = userService.deleteByMap(map);
            return ResultUtil.success("成功删除" + result + "条记录");
        } else {
            return ResultUtil.fail(201, "用户信息不能为空");
        }
    }

    //    根据id来批量删除
    @RequestMapping("/deleteByIdList")
    @ResponseBody
//    请求方式：http://localhost:8080/deleteByIdList?IdList=5&IdList=7
    public ResultUtil deleteByIdList(String IdList) {
        if (IdList != null) {
            String[] lists = IdList.split(",");
            List<Long> bids = new ArrayList<>();
            for (String list : lists) {
                bids.add(Long.parseLong(list));
            }
//            DELETE FROM user WHERE id IN ( ? , ? )
            int result = userService.deleteByList(bids);
            if (result > 0) {
                return ResultUtil.success("成功删除了" + result + "条信息");
            } else {
                return ResultUtil.fail(201, "删除失败！");
            }
        } else {
            return ResultUtil.fail(201, "请至少选择一条信息！");
        }
    }

    //    修改
    @RequestMapping("/updateById")
    @ResponseBody
    public ResultUtil UpdateUser(@RequestBody User user) {
        if (user != null && user.getId() != null) {
            int result = userService.updateUser(user);
            if (result > 0) {
                return ResultUtil.success("成功修改了" + result + "条记录");
            } else {
                return ResultUtil.fail(201, "修改失败！");
            }
        } else {
            return ResultUtil.fail(202, "修改失败！参数不能为空！");
        }

    }

    //    使用条件构造器
//        查询用户名包含a，年龄在20到23之间，邮箱信息不为null的用户信息
    @RequestMapping("/TestWrapper")
    @ResponseBody
    public ResultUtil testWrapper(Integer i) {
        if (i == 1) {
            List<User> list = userService.TestWrapper("a", 20, 30, "email");

            return ResultUtil.success("查询成功", list);
        } else {
            return ResultUtil.fail(201, "查询失败");
        }
    }

    //排序
    @RequestMapping("/TestWrapperAsc")
    @ResponseBody
    public ResultUtil TestWrapperAsc() {
        if (true) {
            List<User> list = userService.TestWrapperAsc();
            return ResultUtil.success("查询成功", list);
        } else {
            return ResultUtil.fail(201, "查询失败");
        }
    }

    //条件构造器删除
    @RequestMapping("/TestWrapperDelete")
    @ResponseBody
    public ResultUtil TestWrapperDelete() {
        int result = userService.deleteIsNullEmail();
        System.out.println(result);
        if (result > 0) {
            return ResultUtil.success("成功删除" + result + "条信息");
        } else {
            return ResultUtil.fail(201, "删除失败");
        }
    }

    //条件构造器修改
    @RequestMapping("/TestWrapperUpdate")
    @ResponseBody
//    @RequestBody User user,Integer age,String name,String email
    public ResultUtil TestWrapperUpdate(@RequestBody User user) {
        int result = userService.WrapperUpdate(user, 20, "a", "email");
        System.out.println(result);
        if (result > 0) {
            return ResultUtil.success("成功修改" + result + "条信息");
        } else {
            return ResultUtil.fail(201, "修改失败");
        }
    }

    //    条件优先级
    @RequestMapping("/TestWrapperCondition")
    @ResponseBody
    public ResultUtil TestWrapperCondition(@RequestBody User user) {
//        将用户名中含有a并且年龄大于20或邮箱为null的用户信息修改
//        lambda中的条件优先
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        int result = userService.WrapperCondition(user, queryWrapper);

        if (result > 0) {
            return ResultUtil.success("成功修改" + result + "条信息");
        } else {
            return ResultUtil.fail(201, "修改失败");
        }
    }

    //多条件查询
//    http://localhost:8080/TestWrapperSelectMoreCondition?name=name&age=age&email=email
    @RequestMapping("/TestWrapperSelectMoreCondition")
    @ResponseBody
    public ResultUtil TestWrapperSelectMoreCondition(String name, String age, String email) {
        List<Map<String, Object>> maps = userService.WrapperSelectMoreCondition(name, age, email);
        if (!maps.isEmpty()) {
            return ResultUtil.success("查询成功", maps);
        } else {
            return ResultUtil.fail(201, "查询失败");
        }
    }

    //    子查询
    @RequestMapping("/TestWrapperSonSelect")
    @ResponseBody
    public ResultUtil TestWrapperSonSelect(Long id) {
        List<User> list = userService.WrapperSonSelect(id);
        if (!list.isEmpty()) {
            return ResultUtil.success("查询成功", list);
        } else {
            return ResultUtil.fail(201, "查询失败");
        }
    }
//  条件修改
    @RequestMapping("/TestUpdateWrapper")
    @ResponseBody
//    请求方式：http://localhost:8080/TestUpdateWrapper?
//    oldName=a&name=苏建铭&age=20&OldEmail=email&email=sjm@qq.com
//    返回结果：{
//    "msg": "修改成功",
//    "status": 200,
//    "data": null
//   }
    public ResultUtil TestUpdateWrapper(String oldName, String name, Integer age, String OldEmail, String email) {
        int result = userService.TestUpdateWrapper(oldName, name, age, OldEmail, email);
        if (result > 0) {
            return ResultUtil.success("修改成功");
        } else {
            return ResultUtil.fail(201, "修改失败");
        }
    }


    //      拼接条件
//    请求方式：http://localhost:8080/TestConditionAdd?name=苏&beginAge=20&endAge=23
//    结果：{"msg":"查询成功","status":200,"data":[{"id":4,"name":"苏建铭","age":23,"email":"sjm@qq.com"}]}
    @RequestMapping("/TestConditionAdd")
    @ResponseBody
    public ResultUtil TestConditionAdd(String name, Integer beginAge, Integer endAge) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotBlank(name) ) {
//            queryWrapper.like("name",name);
//
//        }if (beginAge != null){
//            queryWrapper.ge("age",beginAge);
//        }
//        if (endAge != null){
//            queryWrapper.le("age",endAge);
//        }
//        此方式可替代if语句
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .ge(beginAge != null, "age", beginAge)
                .le(endAge != null, "age", endAge);
        List<User> list = userService.ConditionAdd(queryWrapper);
        if (!list.isEmpty()) {
            return ResultUtil.success("查询成功", list);
        } else {
            return ResultUtil.fail(201, "查询失败");
        }

//
    }

    //    LambdaQueryWrapper
//    请求方式：http://localhost:8080/LambdaQueryWrapper?name=J&beginAge=18&endAge=25
//    返回结果；{
//    "msg": "success",
//    "status": 200,
//    "data": [
//        {
//            "id": 1,
//            "name": "Jone",
//            "age": 18,
//            "email": "test1@baomidou.com"
//        },
//        {
//            "id": 2,
//            "name": "Jack",
//            "age": 20,
//            "email": "test2@baomidou.com"
//        }
//    ]
//}
    @RequestMapping("/LambdaQueryWrapper")
    @ResponseBody
    public ResultUtil LambdaQueryWrapper(String name, Integer beginAge, Integer endAge) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), User::getName, name)
                .ge(beginAge != null, User::getAge, beginAge)
                .le(endAge != null, User::getAge, endAge);
        return ResultUtil.success("success", userService.LambdaWrapper(queryWrapper));
    }

    //LambdaUpdateWrapper
    @RequestMapping("/LambdaUpdateWrapper")
    @ResponseBody
    public ResultUtil LambdaUpdateWrapper(String name, Integer age, Integer endAge) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(User::getAge, 18).
                set(User::getEmail, "user@atguigu.com")
                .like(User::getName, "a")
                .and(i -> i.lt(User::getAge, 24)
                        .or().isNull(User::getEmail)); //lambda 表达式内的逻辑优先运算
        return ResultUtil.success("success", userService.updateWrapper(updateWrapper));
    }

    //    分页实现
    @RequestMapping("/splitPage")
    @ResponseBody
    public ResultUtil splitPage(Integer current, Integer size) {
        Page<User> page = new Page<>(current, size);
        userService.selectPage(page);
        return ResultUtil.success("success", page.getRecords());
    }

    //    自定义分页查询
//    请求方式：http://localhost:8080/splitMySelfPage?current=1&size=2&age=19
//    返回结果：{
//    "msg": "success",
//    "status": 200,
//    "data": [
//        {
//            "id": 3,
//            "name": "Tom",
//            "age": 28,
//            "email": "test3@baomidou.com"
//        },
//        {
//            "id": 4,
//            "name": "XXX",
//            "age": 23,
//            "email": "sjm@qq.com"
//        }
//    ]
//}
    @RequestMapping("/splitMySelfPage")
    @ResponseBody
    public ResultUtil splitMySelfPage(Integer current, Integer size,Integer age) {
        Page<User> page = new Page<>(current, size);
        userService.MySelfPage(page,age);
        return ResultUtil.success("success", page.getRecords());
    }


}
