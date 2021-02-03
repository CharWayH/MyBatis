package com.lagou.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class,       // 拦截哪个类
                method = "prepare",                     // 拦截哪个方法
                args = {Connection.class, Integer.class}    // 拦截方法的入参。防止方法重载
        )
})
public class MyPlugin implements Interceptor {


    /**
     * 拦截方法:只要被拦截目标对象的目标方法被执行时，就会执行intercept方法
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法进行增强");
        return invocation.proceed();  // 原方法执行
    }

    /**
     * 把当前拦截器生成代理存到拦截器链中
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    /**
     * 获取配置文件参数
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("获取到配置文件的参数是:" + properties);
    }
}
