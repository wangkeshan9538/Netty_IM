package com.wks.wsIm.biz;

import com.wks.wsIm.Util.GodChannel;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserService {

    private final static Map<String, UserInfo> USER_POOL = new ConcurrentHashMap<>();

    public final static String GOD_ID="123456789";
    //放入我自己
    static {
        USER_POOL.put(GOD_ID,new UserInfo(GOD_ID,"王柯杉",new GodChannel(),"1"));
    }


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

    public static UserInfo removeUser(String userId) {

        return USER_POOL.remove(userId);
    }

    public static List<UserInfo> getAllUsers(){
        List<UserInfo> userInfos=new ArrayList<>();
        USER_POOL.forEach((key, value) -> {
            userInfos.add(value);
        });
        return userInfos;
    }
}
