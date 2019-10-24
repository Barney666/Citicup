package com.citicup.controller;

import com.alibaba.fastjson.JSONObject;

import com.citicup.CitiCupApplication;
import com.citicup.bean.Account;
import com.citicup.bean.BackData;
import com.citicup.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/session")
public class Session {

    @Autowired
    private AccountDao accountDao;


    @PostMapping("/register")
    public JSONObject register(@RequestBody Account account){   //查询数据 没有返回的是null
        System.out.println("一个用户进行注册操作");
        if(accountDao.findByName(account.getUsername())==null){
            accountDao.addAccount(account);
            System.out.println("成功创建账户"+account);
            return BackData.json("0",new JSONObject());
        }
        System.out.println("用户名被占用");
        return BackData.json("1",new JSONObject());
    }

    @PostMapping("/login")
    public JSONObject login(@RequestBody Account account){
        System.out.println("一个用户进行登录操作");

        //这里可以加一个已经登陆的用户重复登录的提示 后端可以判断出他是否登陆
        Account tempAccount=accountDao.findByNameAndPassword(account.getUsername(),account.getPassword());
        if(tempAccount!=null){
            System.out.println("成功登陆，欢迎宁"+tempAccount);
            Map map=new HashMap();
            map.put("username",tempAccount.getUsername());
            if(tempAccount.getMark()==null){  //未测试
                map.put("mark","-1");
                System.out.println("宁还没填写问卷");
            }
            else{
                map.put("mark",tempAccount.getMark());
                System.out.println("宁的问卷分数为"+tempAccount.getMark());
            }

            String string= UUID.randomUUID().toString().replace("-","");  //32位随机String
            map.put("token",string);  //登录时传回去的map加一个

            System.out.println(string);

            Map<String,Account> token=new HashMap();   //后端保存token
            token.put(string,tempAccount);
            CitiCupApplication.tokenList.add(token);

            return BackData.json("0", map);
        }
        else {
            System.out.println("用户名不正确或密码错误");
            return BackData.json("1", new JSONObject());
        }
    }

    @PostMapping("/logout")
    public JSONObject logout(@RequestBody JSONObject value){
        String token=(String) value.get("token");
        Account account=CitiCupApplication.find(token);
        String name=account.getUsername();

        Map map=null;
        if(accountDao.findByName(name)!=null){
            for(Map<String,Account> temp:CitiCupApplication.tokenList){
                for(String string:temp.keySet()){
                    if(string.equals(token)){
                        map=temp;
                    }
                }
            }
            CitiCupApplication.tokenList.remove(map);
            System.out.println(name+"已下线");
            return BackData.json("0", new JSONObject());
        }
        else return BackData.json("1", new JSONObject());
    }
}

