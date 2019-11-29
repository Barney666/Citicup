package com.citicup.controller;

import com.alibaba.fastjson.JSONObject;
import com.citicup.bean.BackData;
import com.citicup.dao.AccountDao;
import com.citicup.utils.AnalysisUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class Information {

    @PostMapping("/information")
    public JSONObject information(@RequestBody Map<String,String> value){
        String stockcode;
        String stockname;
        try {
            String stock = value.get("stock");
            System.out.println(stock);
            if (stock.matches("[0-9]+")) {  //是代码
                stockcode = stock;
                stockname = AnalysisUtils.find(stock, true);
            } else {  ///是名字
                stockcode = AnalysisUtils.find(stock, false);
                stockname = stock;
            }
            String number=value.get("number");

            String[][] innerData=AnalysisUtils.readData(stockcode,number);

            Map map=new HashMap();
            map.put("stockname",stockname);
            map.put("stockcode",stockcode);
            map.put("data",innerData);
            return BackData.json("0",map);

        }catch (Exception e){
            return BackData.json("1",new Object());
        }
    }

}
