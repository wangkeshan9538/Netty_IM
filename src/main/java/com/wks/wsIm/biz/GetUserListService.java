package com.wks.wsIm.biz;


import com.wks.wsIm.domain.resp.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wks.wsIm.domain.Commands.GET_USER_LIST;

@Command(GET_USER_LIST)
public class GetUserListService extends BaseService<Void, List> {
    @Override
    List<User> process(MsgContext context, Void aVoid) {

        List<User> users = new ArrayList<>();
        Map<User, ?> user = LoginService.getUserPool();
        user.forEach((key, value) -> {
            users.add(key);
        });
        return users;
    }
}
