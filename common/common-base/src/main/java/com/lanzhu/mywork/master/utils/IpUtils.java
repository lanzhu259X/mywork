package com.lanzhu.mywork.master.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.bind.PropertySourcesPropertyValues;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-10
 */
@Log4j2
public class IpUtils {

    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");
    private static final String ANYHOST = "0.0.0.0";
    private static final String LOCALHOST = "127.0.0.1";
    private static volatile InetAddress LOCAL_ADDRESS = null;
    private static InetUtils inetUtils;

    private static synchronized InetUtils getInetUtils() {
        if (inetUtils == null) {
            final Map<String, String> envs = System.getenv();
            final Properties properties = new Properties();
            for (Map.Entry<String, String> env : envs.entrySet()) {
                if (StringUtils.startsWith(env.getKey(), InetUtilsProperties.PREFIX)) {
                    properties.put(env.getKey(), env.getValue());
                }
            }
            final Properties temp = System.getProperties();
            for (Map.Entry<Object, Object> enu : temp.entrySet()) {
                if (StringUtils.startsWith(enu.getKey().toString(), InetUtilsProperties.PREFIX)) {
                    properties.put(enu.getKey().toString(), enu.getValue());
                }
            }
            System.out.println("InetUtils Params:" + JSON.toJSONString(properties));
            final MutablePropertySources propertySources = new MutablePropertySources();
            propertySources.addLast(new PropertiesPropertySource("sys-init", properties));
            final InetUtilsProperties target = new InetUtilsProperties();
            final RelaxedDataBinder binder = new RelaxedDataBinder(target, InetUtilsProperties.PREFIX);
            binder.bind(new PropertySourcesPropertyValues(propertySources));
            inetUtils = new InetUtils(target);
        }
        return inetUtils;
    }

    private static InetAddress getFirstValidAddress() {
        InetAddress result = null;
        InetUtils inetUtils = getInetUtils();
        if (inetUtils != null) {
            return inetUtils.findFirstNonLoopbackAddress();
        }
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces(); nics
                    .hasMoreElements();) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    log.info("Testing interface: " + ifc.getDisplayName());
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }
                    // @formatter:off
                    for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements();) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address && !address.isLoopbackAddress()) {
                            log.info("Found non-loopback interface: " + ifc.getDisplayName());
                            result = address;
                        }
                    }
                    // @formatter:on
                }
            }
        } catch (IOException ex) {
            log.error("Cannot get first non-loopback address", ex);
        }
        if (result != null) {
            return result;
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.warn("Unable to retrieve localhost");
        }
        return null;
    }

    public static InetAddress getAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getFirstValidAddress();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    public static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return isValidAddress(name);
    }

    public static boolean isValidAddress(String address) {
        return (address != null && !ANYHOST.equals(address) && !LOCALHOST.equals(address)
                && IP_PATTERN.matcher(address).matches());
    }

    /**
     * 获取当前网络IP
     * @return
     */
    public static String getIp() {
        InetAddress address = getAddress();
        if (address == null) {
            return null;
        }
        return address.getHostAddress();
    }
}
