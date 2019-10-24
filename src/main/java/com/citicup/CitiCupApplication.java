package com.citicup;

import com.citicup.bean.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CitiCupApplication {

    public static Map<String,Account> tokenMap=new HashMap();

    public static Account find(String token){
        for(String string:tokenMap.keySet()){
            if(string.equals(token)){
                return tokenMap.get(string);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(CitiCupApplication.class, args);
    }

}
