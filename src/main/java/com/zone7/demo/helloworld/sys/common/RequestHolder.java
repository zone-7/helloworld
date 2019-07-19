package com.zone7.demo.helloworld.sys.common;

import com.zone7.demo.helloworld.sys.vo.SysUserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestHolder
 * 请求信息存储类
 *
 * @author: zone7
 * @time: 2019.02.19
 */
public class RequestHolder {

    // 当前进程ID -> SysUser
    private static final ThreadLocal<SysUserVo> USER_HOLDER = new ThreadLocal<>();

    // 当前进程ID -> HttpServletRequest
    private static final ThreadLocal<HttpServletRequest> REQUEST_HOLDER = new ThreadLocal<>();

    public static void add(SysUserVo userVo) {
        USER_HOLDER.set(userVo);
    }

    public static void add(HttpServletRequest request) {
        REQUEST_HOLDER.set(request);
    }

    public static SysUserVo getCurrentUser() {
        return USER_HOLDER.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return REQUEST_HOLDER.get();
    }

    public static void remove() {
        USER_HOLDER.remove();
        REQUEST_HOLDER.remove();
    }

}
