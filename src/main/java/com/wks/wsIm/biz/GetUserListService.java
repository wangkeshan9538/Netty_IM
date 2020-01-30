package com.wks.wsIm.biz;


import com.wks.wsIm.domain.resp.UserResp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.wks.wsIm.domain.Commands.GET_USER_LIST;

@Command(GET_USER_LIST)
public class GetUserListService extends BaseService<Void, List> {
    @Override
    List<UserResp> process(MsgContext context, Void aVoid) {

        List<UserResp> users = new ArrayList<>();
        Map<String, UserInfo> user = UserService.getUserPool();
        user.forEach((key, value) -> {
            UserResp u=new UserResp(value.  getUserId(),value.getUserName());
            users.add(u);
        });
        return users;
    }
}
