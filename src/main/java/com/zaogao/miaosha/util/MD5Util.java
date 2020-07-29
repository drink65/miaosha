package com.zaogao.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public  static  String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static  final  String salt ="1a2bbb22";

    public static  String inputPassFormPass(String inputPass){

        String str =""+salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return  md5(str);
    }

    public static  String formPassToDBPass(String formPass,String salt){
        String str =""+salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return  md5(str);
    }

    public static String inputPassToDbPass(String input, String saltDB){
        String formPass = inputPassFormPass(input);
        String dbPass =formPassToDBPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args){
        System.out.println(inputPassFormPass("123456"));  //89116a0f08ede718a29c8b9b9affa2bf
        System.out.println(formPassToDBPass(inputPassFormPass("123456"),"1a2bbb22"));  //a59a7bb4a84164edddfe3e1f3373567f
        System.out.println(inputPassToDbPass("123456","1a2bbb22"));
    }
}
