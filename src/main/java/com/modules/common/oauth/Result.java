package com.modules.common.oauth;

import lombok.Data;

/**
 * 移动端api接口返回的数据模型
 * @author chunqiu
 *
 */
@Data
public class Result {
	private int code;
    private String msg;
    private Object data;

	public Result(ResultStatusCode resultStatusCode, Object data){
		this.code = resultStatusCode.getCode();
		this.msg = resultStatusCode.getMsg();
		this.data = data;
	}

    public Result(int code, String msg, Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(int code, String msg){
    	this(code, msg, null);
	}
	public Result(ResultStatusCode resultStatusCode){
    	this(resultStatusCode, null);
	}

	public static Result success(Object data) {
		return new Result(ResultStatusCode.OK.getCode(),ResultStatusCode.OK.getMsg(),data);
	}

	public static Result fail(String errorMsg) {
		return new Result(1,errorMsg,null);
	}

	public static Result fail(int code,String errorMsg) {
		return new Result(code,errorMsg,null);
	}


	public static Result fail(ResultStatusCode resultStatusCode) {
		return new Result(resultStatusCode.getCode(),resultStatusCode.getMsg(),null);
	}



}
