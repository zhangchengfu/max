package com.laozhang.max_interview;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

public class DbPool {
    private static int link_count = 10;//连接数
    private static LinkedList<Connection> pool = new LinkedList<Connection>();// 连接池 （存放所有的初始化连接）

    // 初始化连接放入连接池
    static {
        for (int i = 0; i < link_count; i++) {
            // 创建原始的连接对象
            Connection connection = createConnection();
            // 把连接加入连接池
            pool.addLast(connection);
        }
    }

    // 创建一个新的连接的方法
    private static Connection createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            // 原始的目标对象
            final Connection con = DriverManager.getConnection("jdbc:mysql:///jdbc_demo", "root", "root");

            // 对con创建其代理对象
            Connection proxy = (Connection) Proxy.newProxyInstance(
                    con.getClass().getClassLoader(),     // 类加载器
                    //con.getClass().getInterfaces(),   // 当目标对象是一个具体的类的时候,但是这里con是一个接口
                    new Class[]{Connection.class},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // 方法返回值
                            Object result = null;
                            // 当前执行的方法的方法名
                            String methodName = method.getName();
                            // 判断当执行了close方法的时候，把连接放入连接池
                            if ("close".equals(methodName)) {
                                // 连接放入连接池
                                pool.addLast(con);
                            } else {
                                // 调用目标对象方法
                                result = method.invoke(con, args);
                            }
                            return result;
                        }
                    }
            );
            return proxy;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // 获取连接
    public static synchronized Connection getConnection() {
        if (pool.size() > 0) {
            return pool.removeFirst();
        } else {

        }
        return null;
    }

    // 释放连接
    public void releaseConnection(Connection connection) {
        pool.addLast(connection);
    }


}
