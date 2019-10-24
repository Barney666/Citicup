package com.citicup;

import com.citicup.bean.Account;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Map;

@SpringBootApplication
public class CitiCupApplication {

    public static ArrayList<Map> tokenList=new ArrayList<>();

    public static Account find(String token){
        for(Map<String,Account> temp:tokenList){
            for(String string:temp.keySet()){
                if(string.equals(token)){
                    return temp.get(string);
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SpringApplication.run(CitiCupApplication.class, args);
    }

}
