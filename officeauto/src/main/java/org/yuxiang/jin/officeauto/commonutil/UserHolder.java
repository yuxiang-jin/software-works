package org.yuxiang.jin.officeauto.commonutil;

import org.yuxiang.jin.officeauto.domain.User;

/**
 * @author 靳玉祥
 * @Email yuxiang.jin@outlook.com
 * @QQ 1409387550
 * @Tel 17313215836
 */
public class UserHolder {
    //定义一个ThreadLocal，存储当前某个请求线程对应的登录用户
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public static void addCurrentUser(User user) {
        if (users.get() == null) {
            users.set(user);
        }
    }

    public static User getCurrentUser() {
        return users.get();
    }

    public static void removeCurrentUser() {
        users.remove();
    }
}
