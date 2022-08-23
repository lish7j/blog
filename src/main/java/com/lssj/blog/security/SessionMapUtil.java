package com.lssj.blog.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionMapUtil {

    private static Map<String, String> sessions = new ConcurrentHashMap<>();
    private static final String LOGIN = "LOGIN";


    public static String getSession(String userId) {
        return sessions.get(userId);
    }

    public static void putSession(String userId) {
        sessions.put(userId, LOGIN);
    }

    public static boolean hasLogin(String userId) {
        return LOGIN.equals(sessions.get(userId));
    }

}
