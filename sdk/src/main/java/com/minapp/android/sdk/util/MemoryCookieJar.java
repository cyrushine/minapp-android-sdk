package com.minapp.android.sdk.util;

import android.text.TextUtils;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.util.*;

public class MemoryCookieJar implements CookieJar {

    public static final String TAG = "MemoryCookieJar";

    private final Map<String, List<Cookie>> store = new HashMap<>(10);

    @Override
    synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        for (Cookie c : cookies) {
            List<Cookie> domainCookies = getCookie(c.domain());
            addCookie(domainCookies, c);
        }
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> stored = new LinkedList<>();
        String host = url.host();
        for (String domain : store.keySet()) {
            if (Util.domainMatch(host, domain)) {

                List<Cookie> cookies = getCookie(domain);
                for (Cookie cookie : cookies) {
                    if (cookie.matches(url)) {
                        stored.add(cookie);
                    }
                }
            }
        }
        return stored;
    }

    synchronized public void clear() {
        store.clear();
    }

    private List<Cookie> getCookie(String domain) {
        List<Cookie> cookies = store.get(domain);
        if (cookies == null) {
            cookies = new LinkedList<>();
            store.put(domain, cookies);
        }
        return cookies;
    }

    private static void addCookie(List<Cookie> cookies, Cookie cookie) {
        Iterator<Cookie> iterator = cookies.iterator();
        while (iterator.hasNext()) {
            Cookie item = iterator.next();
            if (TextUtils.equals(item.name(), cookie.name())) {
                iterator.remove();
            }
        }
        cookies.add(cookie);
    }

}

