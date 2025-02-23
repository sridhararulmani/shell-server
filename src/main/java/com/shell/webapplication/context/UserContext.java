package com.shell.webapplication.context;

public abstract class UserContext {

    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();

    public static Long getLoggedInUserId() {
        return threadLocal.get();
    }

    public static void setLoggedInUserId(Long userId) {
        threadLocal.set(userId);
    }

    public static void removeLoggedInUserId() {
        threadLocal.remove();
    }

}
