package com.magic.wow.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * 扩展 WebUtils 工具类
 * Created by Phil on 2016/3/29.
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

    /**
     * Is ajax boolean.
     *
     * @param request the request
     * @return the boolean
     */
    public static boolean isAjax(ServletRequest request) {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        return "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("X-Requested-With"));
    }

//    public static void writeJson(ServletResponse response) throws IOException {
////        HttpServletResponse httpResponse = (HttpServletResponse) response;
////        httpResponse.setCharacterEncoding("UTF-8");
////        httpResponse.setContentType("application/json; charset=utf-8");
////        JSONObject jo = new JSONObject();
////        jo.put("code", 1);
////        jo.put("msg", "您没有权限操作,请联系管理员!");
////        PrintWriter out = null;
////        try {
////            out = httpResponse.getWriter();
////            out.write(jo.toJSONString());
////        } catch (IOException e) {
////            e.printStackTrace();
////        } finally {
////            if (out != null) {
////                out.close();
////            }
////        }
//    }
}
