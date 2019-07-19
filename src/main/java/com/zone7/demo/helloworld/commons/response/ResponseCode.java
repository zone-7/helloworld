package com.zone7.demo.helloworld.commons.response;

/**
 * ResponseCode
 * 响应编码
 *
 * @author: zone7
 * @time: 2018.08.28
 */
public enum ResponseCode {

    //响应成功编码
    SUCCESS(200, "请求成功"),


    //响应错误编码
    ERROR(40000, "请求错误"),
    ERROR_PARAM_ILLGAL(40001, "请求参数不合法"),
    ERROR_DATA_DUPLICATION(40002, "新增数据已存在"),
    ERROR_GET_NO_RESULT(40003, "查询结果为空"),
    ERROR_ADD_OP(40004, "新增操作异常"),
    ERROR_UPDATE_OP(40005, "更新操作异常"),
    ERROR_DELETE_OP(40006, "删除操作异常"),
    ERROR_RECOVER_OP(40007, "恢复操作异常"),
    ERROR_MODIFY_PASSWORD(40008, "修改密码操作异常"),
    ERROR_CONFIG(40009, "系统配置异常"),

    ERROR_LOGIN_NOAUTH(50000, "用户未登录，请登录后继续访问"),
    ERROR_LOGIN_NOAUTH_ACL(50001, "用户没有该功能权限");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
