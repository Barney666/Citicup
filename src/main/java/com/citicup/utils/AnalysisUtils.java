package com.citicup.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AnalysisUtils {

	private static String pyDir = "C:\\huaqibei\\python\\";

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
                BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream()));

                line=in.readLine();
                System.out.println(line);    //输出名字 这块中文乱码 先不管了
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
                BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream()));

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


    public static String result(String code){
        //调用1.py

        String line=null;
        StringBuilder result=new StringBuilder();
        try {
            String[] arg=new String[] {"python",
                    pyDir + "model.py",
                    code,
                    pyDir + "15.xlsx",
                    pyDir + "1",
                    pyDir + "2",
                    pyDir + "3",
                    pyDir + "4",
                    pyDir + "Result_of_Whether.csv"
            };
            Process process=Runtime.getRuntime().exec(arg);
            BufferedReader in= new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line=in.readLine())!=null){
                System.out.println(line);   //输出代码 假设是把输出的都.append变成一个String变成line
				if (!line.isEmpty()) result.append(line);
            }
            in.close();
            process.waitFor();

        }catch (IOException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return result.toString().trim();
    }


}
