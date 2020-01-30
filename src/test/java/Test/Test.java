package Test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

class User {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", age=" + age + "]";
    }
};

class UserGroup {
    private String name;
    private List users = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List  getUsers() {
        return users;
    }

    public void setUsers(List  users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserGroup [name=" + name + ", users=" + users + "]";
    }
}

class FastJsonTest {
    public static void main(String[] args) {
        // 构建用户geust
        User guestUser = new User();
        guestUser.setName("guest");
        guestUser.setAge(28);
        // 构建用户root
        User rootUser = new User();
        rootUser.setName("root");
        guestUser.setAge(35);
        // 构建用户组对象
        UserGroup group = new UserGroup();
        group.setName("admin");
        group.getUsers().add(guestUser);
        group.getUsers().add(rootUser);
        // 用户组对象转JSON串
        String jsonString = JSON.toJSONString(group);
        System.out.println("jsonString:" + jsonString);
        // JSON串转用户组对象
        UserGroup group2 = JSON.parseObject(jsonString, UserGroup.class);
        System.out.println("group2:" + group2);

        System.out.println( JSON.parseObject(jsonString,UserGroup.class));

     /*   // 构建用户对象数组
        Object[] users = new Object[2];
        users[0] = guestUser;
        users[1] = rootUser;
        // 用户对象数组转JSON串
        String jsonString2 = JSON.toJSONString(users);
        System.out.println("jsonString2:" + jsonString2);
        // JSON串转用户对象列表
        List<User> users2 = JSON.parseArray(jsonString2, User.class);
        System.out.println("users2:" + users2);*/
    }
}