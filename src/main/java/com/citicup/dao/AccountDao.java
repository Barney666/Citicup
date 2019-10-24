package com.citicup.dao;

import com.citicup.bean.Account;
import org.apache.ibatis.annotations.*;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface AccountDao {

    @Select("select * from account")
    List<Account> findAll();   //查询所有用户

    @Select("select * from account where username=#{username}")
    Account findByName(String username);  //看是否存在这个用户名

    @Select("select * from account where username=#{username} and password=#{password}")
    Account findByNameAndPassword(String username,String password);   //根据用户名和密码查询

    @Select("select footprint from account where username=#{username}")
    String findFoot(String username);

    @Insert("insert into account(username,password,mark) values(#{username},#{password},#{mark})")
    void addAccount(Account account);   //增加一个用户

    @Insert("insert into login_account(username) values(#{username})")
    void addLoginAccount(Account account);  //增加一个登陆状态的用户

    @Delete("delete from login_account where username=#{username}")
    void deleteLoginAccount(String username);   //删除一个登陆状态的用户

    @Update("update account set password=#{password} where username=#{username}")
    void changePassword(String username,String password);   //修改密码

    @Update("update account set mark=#{mark} where username=#{username}")
    void changeMark(String username,String mark);   //修改成绩

    @Update("update account set footprint=#{footprint} where username=#{username}")
    void changeFoot(String footprint,String username);   //增加足迹（在后面更新）
}
