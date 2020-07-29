package com.zaogao.miaosha.result;
public class Result<T> {   //定义封装一个集合Result<> 存放 code msg data 属性，  其中data为泛型，其类型暂定义限制
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时候的调用
     * */
    public static <T> Result<T> success(T data){
        return new  Result<T>(data);
    }

    /**
     * 失败时候的调用
     * */
    public static <T> Result<T> error(CodeMsg cm){
        return new  Result<T>(cm);
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if(cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public T getData() {
        return data;
    }
}

