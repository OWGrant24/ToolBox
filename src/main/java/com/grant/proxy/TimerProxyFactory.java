package com.grant.proxy;

import com.grant.util.WorkingHours;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TimerProxyFactory implements InvocationHandler {
    private final Object target;
    private final WorkingHours workingHours;

    public TimerProxyFactory(Object target) {
        this.target = target;
        this.workingHours = new WorkingHours();
    }

    public Object createProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        workingHours.startTime();
        Object invoke = method.invoke(target, args);
        workingHours.stopTime();
        workingHours.durationTime();
        return invoke;
    }
}

// CGLIB
//public class TimerProxyFactory implements MethodInterceptor {
//    private Object target;
//    private WorkingHours workingHours;
//
//    public TimerProxyFactory(Object target) {
//        this.target = target;
//        this.workingHours = new WorkingHours();
//    }
//
//    public Object createProxy() {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(target.getClass());
//        enhancer.setCallback(new TimerProxyFactory(target));
//        return enhancer.create();
//    }
//
//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        workingHours.startTime();
//        Object invoke = method.invoke(target,args);
//        workingHours.stopTime();
//        workingHours.durationTime();
//        return invoke;
//    }
//}
