package com.citicup.controller;


import com.alibaba.fastjson.JSONObject;

import com.citicup.bean.BackData;
import com.citicup.dao.AccountDao;
import com.citicup.utils.AnalysisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class Analysis {

    @Autowired
    private AccountDao accountDao;

    @RequestMapping("/test")
    public void test(){
        String foot=accountDao.findFoot("barney");
        if(foot==null) foot="600100";
        else foot+="&"+"600100";
        accountDao.changeFoot(foot,"barney");    //添加足迹
    }

	@RequestMapping("/analysis")
    public JSONObject analysis(@RequestBody Map<String,String> value){
       String stockcode = "";
       String stockname = "";
       try {
           String stock=(String) value.get("stock");
           System.out.println(stock);
           if (stock.matches("[0-9]+")) {  //是代码
               stockcode=stock;
               stockname= AnalysisUtils.find(stock,true);
           }
           else {  ///是名字
               stockcode=AnalysisUtils.find(stock,false);
               stockname=stock;
           }

           String foot=accountDao.findFoot(value.get("username"));
           if(foot==null) foot=stockcode;
           else foot+="&"+stockcode;
           accountDao.changeFoot(foot,value.get("username"));    //添加足迹

           String result=AnalysisUtils.result(stockcode);

		   JSONObject object = JSONObject.parseObject(result);
		   object.put("stockname", stockname);
		   object.put("stockcode", stockcode);
           return BackData.json("0", object);
       }catch (Exception e){
           return BackData.json("1", new JSONObject());
       }

    }


}
