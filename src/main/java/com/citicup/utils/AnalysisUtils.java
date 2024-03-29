package com.citicup.utils;


import com.alibaba.fastjson.JSONObject;
import com.citicup.bean.BackData;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalysisUtils {

	private static String pyDir = "F:\\github\\HuaQiBei__PythonProject\\";
//	private static String pyDir = "C:\\huaqibei\\python\\";

    //负责寻找Code与Name的对应
    public static String find(String stock,boolean whether){  //whether代表看是不是Code
        //调用findName.py
        if(whether==true){
            String line=null;
            try {
                String[] arg=new String[] {"python",
                        pyDir + "findName.py",
                        stock,
                        pyDir + "Name_Code2.csv"};
                Process process=Runtime.getRuntime().exec(arg);
                BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("gbk")));

                line=in.readLine();
                System.out.println(line);
                in.close();
                process.waitFor();

            }catch (IOException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return line;
        }
        //调用findCode.py
        else {
            String line=null;
            try {
                String[] arg=new String[] {"python",
                        pyDir + "findCode.py",
                        stock,
                        pyDir + "Name_Code2.csv"};
                Process process=Runtime.getRuntime().exec(arg);
                BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("gbk")));

                line=in.readLine();
				while (line.length() < 6) line = "0" + line;
                System.out.println(line);
                in.close();
                process.waitFor();

            }catch (IOException e){
                e.printStackTrace();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return line;
        }
    }


    public static String result(String code,String mark){
        //调用model.py
        String line=null;
        StringBuilder result1=new StringBuilder();
        StringBuilder result2=new StringBuilder();
        try {
            String[] arg=new String[] {pyDir+"venv\\Scripts\\python.exe",
                    pyDir + "model_before.py", code};
            Process process=Runtime.getRuntime().exec(arg);
            BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("gbk")));

            while ((line=in.readLine())!=null){
                System.out.println(line);   //输出代码 假设是把输出的都.append变成一个String变成line
				if (!line.isEmpty()) result1.append(line);
            }
            in.close();
            process.waitFor();

            //NEXT

            arg=new String[] {pyDir+"venv\\Scripts\\python.exe",
                                pyDir + "model.py",
                                code
            };
            process=Runtime.getRuntime().exec(arg);
            in= new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("gbk")));

            while ((line=in.readLine())!=null){
                System.out.println(line);   //输出代码 假设是把输出的都.append变成一个String变成line
				if (!line.isEmpty()) result2.append(line);
            }
            in.close();
            process.waitFor();

            JSONObject jsonObject1=JSONObject.parseObject(result1.toString().trim());
            JSONObject jsonObject2=JSONObject.parseObject(result2.toString().trim());
            JSONObject result=new JSONObject();
            result.putAll(jsonObject1);
            result.putAll(jsonObject2);

            return result.toString().trim();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String snow(String stock){
        String line;
        StringBuilder result=new StringBuilder();
        try {
            String[] arg=new String[] {pyDir+"venv\\Scripts\\python.exe",
                    pyDir + "trynewxqw.py",
                    stock,
            };
            Process process=Runtime.getRuntime().exec(arg);
            BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("gbk")));

            while ((line=in.readLine())!=null){
                System.out.println(line);   //输出代码 假设是把输出的都.append变成一个String变成line
                if (!line.isEmpty()) result.append(line);
            }
            in.close();
            process.waitFor();
            return result.toString().trim();
        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }


    public static String[][] readData(String code,String number){   //返回四张表和成长能力
        // 读取csv文件的内容
        String filepath=pyDir+number+"/"+code+".csv";

        File csv = new File(filepath); // CSV文件路径
        csv.setReadable(true);//设置可读

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"GBK"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        ArrayList<String> allString = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) // 读取到的内容给line变量
            {
                everyLine = line;
                System.out.println(everyLine);
                allString.add(everyLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] result=new String[allString.size()][];
        int index=0;
        for(int i=0;i<allString.size();i++){
            char[] temp=allString.get(i).toCharArray();
            String str="";
            List<String> arrayList=new ArrayList();
            for(int j=0;j<temp.length;j++){
                if(temp[j]!=',')   str+=temp[j];
                else{
                    arrayList.add(str);
                    str="";
                }
            }
            String[] arr=new String[arrayList.size()];
            for(int k=0;k<arr.length;k++){
                arr[k]=arrayList.get(k);
            }
            result[index]=arr;
            index++;
        }
        return result;
    }
}
