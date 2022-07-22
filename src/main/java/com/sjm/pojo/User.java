package com.sjm.pojo;

import com.sjm.enums.SexEnum;
import lombok.Data;

//无参构造
//@NoArgsConstructor
//有参构造
//@AllArgsConstructor
//getter
//setter
//@Getter
//@Setter
//@EqualsAndHashCode
@Data
//@TableName("t_user")，此处可以使用全局配置来代替
public class User {
    //    @TableId注解的value属性用于指定主键的字段
    /*Id默认的策略是雪花算法，可自动生成ID
若实体类和表中表示主键的不是id，而是其他字段，例如uid，MyBatis-Plus会自动识别uid为主键列吗？
我们实体类中的属性id改为uid，将表中的字段id也改为uid，
测试添加功能程序抛出异常，Field 'uid' doesn't have a default value，说明MyBatis-Plus没有将uid作为主键赋值
在实体类中uid属性上通过@TableId将其标识为主键，即可成功执行SQL语句*/

    //   type属性用来定义主键生成策略
//    注意：
    /*
    * IdType.ASSIGN_ID（默
        认） 基于雪花算法的策略生成数据id，与数据库id是否设置自增无关
        IdType.AUTO 使用数据库的自增策略，注意，该类型请确保数据库设置了id自增，
        否则无效
    * */
//    @TableId(value = "指定的主键名", type = IdType.AUTO)
    private Long id;

    private String name;
//@TableField("指定属性对应的字段名")的作用
    /*
    * 若实体类中的属性使用的是驼峰命名风格，而表中的字段使用的是下划线命名风格
    * 例如实体类属性userName，表中字段user_name
    * 此时MyBatis-Plus会自动将下划线命名风格转化为驼峰命名风格相当于在MyBatis中配置
    *
    * 若实体类中的属性和表中的字段不满足情况1
    * 例如实体类属性name，表中字段username
    * 此时需要在实体类属性上使用@TableField("username")设置属性所对应的字段名
    * */
    private Integer age;

    private String email;
    private SexEnum sex;

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }
}
