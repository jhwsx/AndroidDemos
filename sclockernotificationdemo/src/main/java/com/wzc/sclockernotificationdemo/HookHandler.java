package com.wzc.sclockernotificationdemo;

import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wzc on 2017/7/19.
 */

public class HookHandler implements InvocationHandler {
    private Object mBase;

    public HookHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("apply".equals(method.getName())) {
            for (int i = 0; i < args.length; i++) {
                if ((args[i] instanceof View && !(args[i] instanceof ViewGroup))) {
                    View root = (View) args[i];
                    break;
                }
            }
        }
        return method.invoke(mBase,args);
    }
}
