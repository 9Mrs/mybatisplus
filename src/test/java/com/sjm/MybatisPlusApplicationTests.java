package com.sjm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjm.enums.SexEnum;
import com.sjm.mapper.ProductMapper;
import com.sjm.mapper.UserMapper;
import com.sjm.pojo.Product;
import com.sjm.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    void contextLoads() {
    }


    @Test
    void testSelect() {
//        通过条件构造器查询一个list集合，若没有条件，则可以设置null为参数
//        List<User> userList = userMapper.selectList(null);
        userMapper.selectList(null).forEach(System.out::println);

    }

    @Test
    void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email"); //条件构造器也可以构建删除语句的条件
        int result = userMapper.delete(queryWrapper);
//        System.out.println("---------------"+result+"---------------------");
    }

    //    模拟数据冲突
    @Test
    public void testConcurrentUpdate() {
        //1、小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());
        //2、小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        if (result2 == 0) {
//            操作失败，重试
            Product productNow = productMapper.selectById(1);
            productNow.setPrice(productNow.getPrice() - 30);
            productMapper.updateById(productNow);

        }
        System.out.println("小王修改结果：" + result2);
        //最后的结果
        Product p3 = productMapper.selectById(1L);
        //价格覆盖，最后的结果： 70
        System.out.println("最后的结果：" + p3.getPrice());
    }

    @Test
    public void testSexEnum() {
        User user = new User();
        user.setId(10L);
        user.setName("Enum");
        user.setAge(22);
        //设置性别信息为枚举项，会将@EnumValue注解所标识的属性值存储到数据库
        user.setSex(SexEnum.FEMALE );
        //INSERT INTO t_user ( username, age, sex ) VALUES ( ?, ?, ? )
        // Parameters: Enum(String), 20(Integer), 1(Integer)
        userMapper.insert(user);
    }


}