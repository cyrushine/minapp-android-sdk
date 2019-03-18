package com.minapp.android.sdk.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import static okhttp3.internal.Util.verifyAsIpAddress;

public abstract class Util {

    public static <R, T> PagedListResponse<R> transform(PagedListResponse<T> response, Function<T, R> func) {
        PagedListResponse<R> data = new PagedListResponse<>();
        data.setMeta(response.getMeta());
        List<T> objects = response.getObjects();
        if (objects != null && func != null) {
            data.setObjects(new ArrayList<R>(objects.size()));
            for (T item : objects) {
                data.getObjects().add(func.on(item));
            }
        }
        return data;
    }

    public static @Nullable String readString(InputStream input) {
        return readString(input, "utf-8");
    }

    public static @Nullable String readString(InputStream input, String charset) {
        try {
            byte[] bytes = new byte[input.available()];
            input.read(bytes);
            return new String(bytes, charset);
        } catch (Exception e) {
            try {
                input.close();
            } catch (Exception ignored) {}
            return null;
        }
    }

    public static void assetNotNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    /**
     * copy from okhttp3.internal.Util
     * Quick and dirty pattern to differentiate IP addresses from hostnames. This is an approximation
     * of Android's private InetAddress#isNumeric API.
     *
     * <p>This matches IPv6 addresses as a hex string containing at least one colon, and possibly
     * including dots after the first colon. It matches IPv4 addresses as strings containing only
     * decimal digits and dots. This pattern matches strings like "a:.23" and "54" that are neither IP
     * addresses nor hostnames; they will be verified as IP addresses (which is a more strict
     * verification).
     */
    private static final Pattern VERIFY_AS_IP_ADDRESS = Pattern.compile(
            "([0-9a-fA-F]*:[0-9a-fA-F:.]*)|([\\d.]+)");

    /**
     * copy from okhttp3.internal.Util
     * Returns true if {@code host} is not a host name and might be an IP address.
     * */
    private static boolean verifyAsIpAddress(String host) {
        return VERIFY_AS_IP_ADDRESS.matcher(host).matches();
    }


    /**
     * copy from okhttp3.Cookie
     */
    public static boolean domainMatch(String urlHost, String domain) {
        if (urlHost.equals(domain)) {
            return true; // As in 'example.com' matching 'example.com'.
        }

        if (urlHost.endsWith(domain)
                && urlHost.charAt(urlHost.length() - domain.length() - 1) == '.'
                && !verifyAsIpAddress(urlHost)) {
            return true; // As in 'example.com' matching 'www.example.com'.
        }

        return false;
    }



    public static @NonNull String join(Collection data, String separator) {
        if (data != null && !data.isEmpty()) {
            if (separator == null) {
                separator = ",";
            }
            StringBuilder sb = new StringBuilder();
            Iterator iterator = data.iterator();
            Object item = null;
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item != null) {
                    sb.append(item.toString()).append(separator);
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        return "";
    }
}
