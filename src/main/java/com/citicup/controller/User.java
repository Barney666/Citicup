package com.citicup.controller;


import com.alibaba.fastjson.JSONObject;

import com.citicup.CitiCupApplication;
import com.citicup.bean.Account;
import com.citicup.bean.BackData;
import com.citicup.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class User {

    @Autowired
    private AccountDao accountDao;

//    private List<Account> list=accountDao.findAll();

    @RequestMapping("/password")
    public JSONObject changePassword(@RequestBody Map<String,String> value){
        String token=value.get("token");
        Account account= CitiCupApplication.find(token);
        String username=account.getUsername();
        String oldPassword=value.get("oldpassword");
        String newPassword=value.get("newpassword");

        if(accountDao.findByNameAndPassword(username,oldPassword)!=null){
            accountDao.changePassword(username,newPassword);
            System.out.println("成功修改密码为"+newPassword);
            return BackData.json("0",new JSONObject());
        }
        else {
            System.out.println("用户名不正确或密码错误");
            return BackData.json("1",new JSONObject());  //有错误
        }
    }

    @RequestMapping(value = "/setmark")
    public JSONObject changeMark(@RequestBody Map<String,String> value){ //改分数
        String token=value.get("token");
        Account account= CitiCupApplication.find(token);
        String username=account.getUsername();
        String mark=value.get("mark");
        System.out.println("要将"+username+"的成绩修改为"+mark);
        if(accountDao.findByName(username)!=null){
            accountDao.changeMark(username,mark);
            System.out.println("成功修改成绩为"+mark);
            return BackData.json("0",new JSONObject());
        }
        else {
            System.out.println("未查找到该用户名，无法修改成绩");
            return BackData.json("1",new JSONObject());  //有错误
        }
    }

    @RequestMapping(value = "/getmark")
    public JSONObject getMark(@RequestBody Map<String,String> value){ //获取分数
        String token=value.get("token");
        Account account= CitiCupApplication.find(token);
        String username=account.getUsername();
        Account tempAccount=accountDao.findByName(username);
        Map map=new HashMap<>();
        if(tempAccount!=null) {
            map.put("mark",tempAccount.getMark());
            System.out.println(tempAccount.getUsername()+"的分数是"+tempAccount.getMark());
            return BackData.json("0",map);
        }
        else  {
            map.put("mark","-1");
            return BackData.json("1",new JSONObject());
        }
    }

    @RequestMapping("/footprint")
    public JSONObject footPrint(@RequestBody Map<String,String> value){
        String token=value.get("token");
        Account account= CitiCupApplication.find(token);
        String username=account.getUsername();
        String foot=accountDao.findFoot(username);
        ArrayList<String> arrayList=new ArrayList<>();
        for(String temp:foot.split("&")){
            arrayList.add(temp);
        }
        Map map=new HashMap();
        map.put("footprints",arrayList);
        System.out.println(arrayList);
        return BackData.json("0",map);
    }

}
