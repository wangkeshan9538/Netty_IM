package com.wks.wsIm.biz;

import com.wks.wsIm.Util.ClassUtil;
import com.wks.wsIm.domain.Packet;
import com.wks.wsIm.domain.resp.ErrorResp;
import com.wks.wsIm.serializer.JSONSerializer;
import com.wks.wsIm.serializer.Serializer;
import lombok.extern.slf4j.Slf4j;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 根据 packet来路由到对应的service
 */
@Slf4j
public class Router {


    public final static Map<String, Class> mapping = new HashMap<>();

    JSONSerializer serializer = new JSONSerializer();

    static {
        //TODO 可以再加一个Order 注解 来表示一个命令的 处理 pipeline
        Set<Class<?>> services = ClassUtil.scanPackage("com.wks.wsIm.biz", (c) ->
                c.getAnnotation(Command.class) != null &&
                        c.getSuperclass().equals(BaseService.class) &&
                        c.getAnnotation(Deprecated.class) == null);
        services.forEach((c) -> {
            mapping.put(c.getAnnotation(Command.class).value(), c);
        });
    }

    public Packet router(MsgContext context,Packet p) throws ClassNotFoundException {
        Class clezz = mapping.get(p.getCommand());

        Object result = null;

        Class reqType = Class.forName(((ParameterizedType) clezz.getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
        Class resptype = Class.forName(((ParameterizedType) clezz.getGenericSuperclass()).getActualTypeArguments()[1].getTypeName());


        //填充req
        // TODO 这块对于入参出参的处理 明显不够好，入参出参带个泛型，就没办法处理了，我觉得用泛型来标志入参出参并不是一个好的方式，应该需要看下springmvc对入参出参怎么处理的
        Object req =null;
        if (!reqType.equals(Void.class)) {
            req=serializer.desData(p, reqType);
        }

        //process req
        try {
            result = clezz.getDeclaredMethod("process",MsgContext.class, Object.class).invoke(clezz.newInstance(), context,req);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            log.error("方法invoeke 失败", e);
            return new Packet(p.getTraceId(), new ErrorResp());
        }


        //处理result
        if (!resptype.equals(Void.class)) {
            return new Packet(p.getCommand(),p.getTraceId(), result);
        }
        return null;
    }


}
