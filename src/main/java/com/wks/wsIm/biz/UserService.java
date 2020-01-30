package com.wks.wsIm.biz;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final static Map<String, UserInfo> USER_POOL = new ConcurrentHashMap<>();


    public static Map<String, UserInfo> getUserPool() {
        return USER_POOL;
    }

    public static boolean addFriend(@NonNull String userId, @NonNull String addId) {
        USER_POOL.get(userId).getFriends().add(USER_POOL.get(addId));
        USER_POOL.get(addId).getFriends().add(USER_POOL.get(userId));
        return true;
    }

    public static UserInfo getUserInfo(String userId) {
        return USER_POOL.get(userId);
    }

    public static boolean addUserInfo(String userId, UserInfo userInfo) {
        USER_POOL.put(userId, userInfo);
        return true;
    }

    public static boolean removeUser(String userId) {
        USER_POOL.remove(userId);
        return true;
    }
}
