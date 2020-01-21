package com.wks.wsIm.biz;

/**
 * 两个参数不能再有泛型，不然在路由层不好锁定接收返回类型
 * @param <T> req
 * @param <I> resp
 */
public abstract class BaseService <T,I>{

    abstract I process(MsgContext context,T t);
}
