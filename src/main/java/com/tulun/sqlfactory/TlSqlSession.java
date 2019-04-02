package com.tulun.sqlfactory;

import com.tulun.annotation.TlSelect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description：
 *
 * @author ajie
 * data 2018/11/12 7:46
 */
public class TlSqlSession {
    private Connection connection;

    private ConcurrentHashMap<String, Object> firstLevelCache;

    public TlSqlSession(Connection connection) {
        this.connection = connection;
    }


    /**
     * 这个是框架动态代理的核心入口
     * @param c
     * @param <T>
     * @return
     */
    public <T> T getMapper(Class<T> c){
        if (!c.isInterface()) {
            throw new ExceptionInInitializerError("error:" + c.getName() + "is not interface");
        }
        return (T) Proxy.newProxyInstance(TlSqlSession.class.getClassLoader(), new Class[]{c},
                new TlHandler());
    }

    /**
     * 关闭连接SqlSession
     */
    public void close(){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void commit(){
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback(){
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void rollback(Savepoint savepoint){
        try {
            connection.rollback(savepoint);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    class TlHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            TlSelect s = null;
            Annotation[] anns = method.getAnnotations();
            for (Annotation ann : anns) {
                s = (TlSelect) ann;
                break;
            }
            if (s == null) {
                return null;
            }
            String sql = s.value();
            // 把#{id}替换成？
            for (; ; ) {
                int begin = sql.indexOf("#{");
                if (begin == -1) {
                    break;
                }
                int end = sql.indexOf("}", begin);
                sql = sql.substring(0, begin) + "?" + sql.substring(end + 1);
            }
            PreparedStatement pst = TlSqlSession.this.connection.prepareStatement(sql);

            // 解析args
            for (int i = 0; i < args.length; i++) {
                pst.setObject(i + 1, args[i]);
            }
            ResultSet rs = pst.executeQuery();
            // 从method里面提取返回值信息
            Class<?> returnType = method.getReturnType();


            if (rs.next()){
                Field[] fields = returnType.getDeclaredFields();
                Object o = returnType.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    try {
                        Object value = rs.getObject(field.getName());
                        field.set(o, value);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return o;
            }
            return null;
        }
    }
}
