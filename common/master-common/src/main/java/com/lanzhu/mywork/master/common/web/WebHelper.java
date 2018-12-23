package com.lanzhu.mywork.master.common.web;

import com.lanzhu.mywork.master.constant.Constant;
import com.lanzhu.mywork.master.constant.Language;
import com.lanzhu.mywork.master.constant.Terminal;
import com.lanzhu.mywork.master.model.ApiRequestHeader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * description: 获取一些请求信息中的内容
 *
 * @author lanzhu259X
 * @date 2018-12-08
 */
public class WebHelper {

    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    public static final String X_ORIGIN_FORWARD_FOR = "X-Origin-Forwarded-For";
    public static final String X_FORWARD_FOR = "X-Forwarded-For";
    public static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    public static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
    public static final String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
    public static final String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    public static final String UNKNOWN = "unknown";

    private static final String ANYHOST = "0.0.0.0";
    private static final String LOCALHOST = "127.0.0.1";

    private static ApplicationContext applicationContext;

    /**
     * 设置 spring 上下文
     * @param applicationContext
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebHelper.applicationContext = applicationContext;
    }

    /**
     * 获取 spring 上下文
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        int max = 50;
        while (applicationContext == null && max > 0) {
            max-- ;
            try {
                Thread.sleep(1000);
            }catch (Exception e) {
                // DO NOTHING
            }
        }
        return applicationContext;
    }

    /**
     * 获取 HttpServletRequest
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取 HttpServletResponse
     * @return
     */
    public static HttpServletResponse getHttpSerlvetReponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return null;
        }
        return requestAttributes.getResponse();
    }

    /**
     * 获取请求头的值
     * @param name
     * @return
     */
    public static String getHeader(String name) {
        HttpServletRequest request = getHttpServletRequest();
        return getHeader(request, name);
    }

    /**
     * 获取请求头的值
     * @param request
     * @param name
     * @return
     */
    public static String getHeader(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        return request.getHeader(name);
    }

    /**
     * 获取请求参数值
     * @param name
     * @return
     */
    public static String getParameter(String name) {
        HttpServletRequest request = getHttpServletRequest();
        return getParameter(request, name);
    }

    /**
     * 获取请求参数值
     * @param request
     * @param name
     * @return
     */
    public static String getParameter(HttpServletRequest request, String name) {
        if (request == null) {
            return null;
        }
        return request.getParameter(name);
    }

    /**
     * 根据cookie key 获取 cookie的值
     * @param key
     * @return
     */
    public static String getCookieValue(String key) {
        HttpServletRequest request = getHttpServletRequest();
        return getCookieValue(request, key);
    }

    /**
     * 根据 httpServletRequest 和 cookie key 获取cookie 的值
     * @param request
     * @param key
     * @return
     */
    public static String getCookieValue(HttpServletRequest request, String key) {
        if (request == null) {
            return null;
        }
        Cookie cookie = WebUtils.getCookie(request, key);
        return cookie == null ? null : cookie.getValue();
    }

    /**
     * 获取请求的语言信息, 默认返回的是 Language.CH_ZH;
     * @return
     */
    public static Language getLanguage() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return Language.CH_ZH;
        }
        String lang = getHeader(request, Constant.LANG);
        if (StringUtils.isBlank(lang)) {
            lang = getParameter(request, Constant.LANG);
        }
        if (StringUtils.isBlank(lang)) {
            lang = getCookieValue(request, Constant.LANG);
        }
        Language language = Language.findByCode(lang);
        return language == null ? Language.CH_ZH : language;
    }

    /**
     * 获取请求终端信息, 默认返回的是 Terminal.OTHER
     * @return
     */
    public static Terminal getTerinal() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return Terminal.OTHER;
        }
        String clientType = getHeader(request, Constant.CLIENT_TYPE);
        if (StringUtils.isBlank(clientType)) {
            clientType = getParameter(request, Constant.CLIENT_TYPE);
        }
        if (StringUtils.isBlank(clientType)) {
            clientType = getCookieValue(request, Constant.CLIENT_TYPE);
        }
        Terminal terminal = Terminal.findByCode(clientType);
        return terminal == null ? Terminal.OTHER : terminal;
    }

    /**
     * 获取请求的域名 (注意当没有对应的Http请求时返回的是null)
     * @return
     */
    public static String getBaseDomain() {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }
        String contextPath = request.getContextPath();
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        if (StringUtils.isBlank(contextPath) || StringUtils.equalsIgnoreCase(contextPath, "/")) {
            contextPath = "/";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(scheme);
        builder.append("://");
        builder.append(serverName);
        builder.append(port);
        builder.append(contextPath);
        return builder.toString();
    }

    /**
     * 获取请求的IP
     * @return
     */
    public static String getRequestIp() {
        HttpServletRequest request = getHttpServletRequest();
        return getRequestIp(request);
    }

    /**
     * 获取请求IP
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ipAddress = request.getHeader(X_ORIGIN_FORWARD_FOR);
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(X_FORWARD_FOR);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HTTP_CLIENT_IP);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(HTTP_X_FORWARDED_FOR);
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }

            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割,  "***.***.***.***".length()
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.contains(",")) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
            throw new IllegalArgumentException("获取不到客户端IP请检查网络配置");
        }
        return ipAddress;
    }

    public static ApiRequestHeader getApiRequestHeader() {
        return (ApiRequestHeader) getHttpServletRequest().getAttribute(Constant.API_REQUEST_HEADER);
    }

}
