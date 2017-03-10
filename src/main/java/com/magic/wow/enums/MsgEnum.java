package com.magic.wow.enums;

public enum MsgEnum {
    ERROR(999, "服务器异常!"),
    UN_AUTH_ERROR(998, "您没有该功能权限,请联系管理员!"),
    ILLEGAL_ARGUMENT_ERROR(997, "[%s] 非法参数!"),
    LOGIN_ERROR(996, "用户名或密码错误！"),
    FORCE_OUT_ERROR(995, "此用户帐号已被封!"),
    EMPTY_ERROR(994, "[%s] 不能为空!"),

    OPERATION_SUCCESS(0, "操作成功！"),
    OPERATION_ERROR(1, "操作失败！"),
    SUCCESS(0, "服务器正常!");


    private int code;
    private String msg;


    MsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
