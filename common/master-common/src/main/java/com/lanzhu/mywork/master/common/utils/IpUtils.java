package com.lanzhu.mywork.master.common.utils;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
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
    private static InetAddress inetAddress;

    private static synchronized InetAddress getFirstValidAddress() {
        if (inetAddress != null) {
            return inetAddress;
        }
        InetAddress result = null;
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
            inetAddress = result;
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
