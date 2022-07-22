package com.sjm.controller;

import com.sjm.pojo.User;
import com.sjm.service.UsersService;
import com.sjm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
@RequestMapping("/IService")
public class IServiceController {

    @Autowired
    UsersService usersService;

    @RequestMapping("/testIservice")
    @ResponseBody
    public ResultUtil testIservice() {

        return ResultUtil.success("统计成功",usersService.count());
    }

//    批量添加   使用usersService.saveBatch(list)，参数为集合，放回结果为boolean类型
    @RequestMapping("/InsertMore")
    @ResponseBody
//    接收json格式的数据，需要用到@RequestBody注解，
//    接收格式：[{"id":5,"name":"李华","age":23,"email":"test4@baomidou.com"},
//    {"id":7,"name":"李华","age":23,"email":"test4@baomidou.com"}]
//
    public ResultUtil testInsertMore(@RequestBody ArrayList<User> list){
//        输出接收到的数据
        System.out.println(list.toArray());
        if (list.toArray().length!=0&&usersService.saveBatch(list)) {
            return ResultUtil.success("添加成功！");
        }else
        {
            return ResultUtil.fail(201,"添加失败！");
        }
    }
}
