package com.citicup.bean;

import com.alibaba.fastjson.JSONObject;

public class BackData {
    public static JSONObject json(String code, Object data) {
        JSONObject object = new JSONObject();
        object.put("code", Integer.parseInt(code));
        object.put("data", data);
        return object;
    }
}