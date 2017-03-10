package com.magic.wow.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.magic.wow.enums.MsgEnum;
import com.magic.wow.model.Msg;

/**
 * Created by Phil on 2015/12/19.
 */
public class ResultUtils {

    private ResultUtils() {
    }

    /**
     * 供DataTables  列表使用
     *
     * @param data
     * @return
     */
    public static Object list(Object data) {
        JSONObject jo = new JSONObject();
        jo.put("data", new Msg(MsgEnum.SUCCESS.getCode(), null, data));
        return jo;
    }

    private static Object toJSON(int code, Object data, String msg) {
        return JSON.toJSON(new Msg(code, msg, data));
    }

    public static Object success() {
        return success(MsgEnum.SUCCESS.getMsg());
    }

    public static Object success(String msg) {
        return success(msg, null);
    }

    public static Object success(Object data) {
        return success(null, data);
    }

    public static Object success(String msg, Object data) {
        return success(MsgEnum.SUCCESS.getCode(), msg, data);
    }

    public static Object success(int code, String msg, Object data) {
        return toJSON(code, data, msg);
    }

    public static Object error() {
        return error(MsgEnum.ERROR.getMsg());
    }

    public static Object error(String msg) {
        return error(msg, null);
    }

    public static Object error(int code, String msg) {
        return error(code, msg, null);
    }

    public static Object error(String msg, Object data) {
        return error(MsgEnum.ERROR.getCode(), msg, data);
    }

    public static Object error(int code, String msg, Object data) {
        return toJSON(code, data, msg);
    }

    /**
     * 操作成功
     *
     * @return
     */
    public static Object operationSuccess() {
        return success(MsgEnum.OPERATION_SUCCESS.getMsg());
    }

    public static Object operationError(Object data) {
        return error(MsgEnum.OPERATION_ERROR.getMsg(), data);
    }

    /**
     * 登陆错误
     *
     * @return
     */
    public static Object loginError() {
        return error(MsgEnum.LOGIN_ERROR.getCode(), MsgEnum.LOGIN_ERROR.getMsg());
    }

    /**
     * 参数校验
     *
     * @param field
     * @return
     */
    public static Object emptyError(String field) {
        return error(MsgEnum.EMPTY_ERROR.getCode(), String.format(MsgEnum.EMPTY_ERROR.getMsg(), field));
    }

    /**
     * 非法参数
     *
     * @param field
     * @return
     */
    public static Object illegalArgumentError(String field) {
        return error(MsgEnum.ILLEGAL_ARGUMENT_ERROR.getCode(), String.format(MsgEnum.ILLEGAL_ARGUMENT_ERROR.getMsg(), field));
    }

    /**
     * 未授权异常
     *
     * @return
     */
    public static Object UnAuthError() {
        return JSON.toJSON(new Msg(MsgEnum.UN_AUTH_ERROR.getCode(),  MsgEnum.UN_AUTH_ERROR.getMsg(),null));
    }
}
