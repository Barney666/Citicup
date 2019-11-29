package com.citicup.controller;

import com.alibaba.fastjson.JSONObject;
import com.citicup.bean.BackData;
import com.citicup.utils.AnalysisUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Emotion {

    @PostMapping("/emotion")
    public JSONObject emtion(@RequestBody Map<String,String> value){
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
            String result=AnalysisUtils.snow(stockcode);
            JSONObject object = JSONObject.parseObject(result);
            return BackData.json("0",object);
        }catch (Exception e){
            e.printStackTrace();
            return BackData.json("1",e);
        }

    }
}
