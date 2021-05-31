package com.grant.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static com.grant.OutputWindow.consoleStringBuilder;

public class TimerProxyFactory implements InvocationHandler {
    private final Object target;

    public TimerProxyFactory(Object target) {
        this.target = target;
    }

    public Object createProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object invoke = method.invoke(target, args);
        long duration = (System.nanoTime()- startTime);
        if (duration < 1_000_000_000) {
            consoleStringBuilder.append("Процесс обработки занял: ").append(duration / 1_000_000).append(" мс.\n");
        } else {
            consoleStringBuilder.append("Процесс обработки занял: ").append(duration / 1_000_000_000).append(" с.\n");
        }
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
