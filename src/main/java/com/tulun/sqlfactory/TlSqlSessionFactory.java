package com.tulun.sqlfactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * description：
 *
 * @author ajie
 * data 2018/11/12 7:46
 */
public class TlSqlSessionFactory {

    private String url;
    private String name;
    private String password;
    private List<String> interfacePath;
    private List<String> sqlXmlFilePath;

    public TlSqlSessionFactory(String driver, String url, String name, String password,
                               List<String> interfacePath, List<String> sqlXmlFilePath) {
        String driver1 = driver;
        this.url = url;
        this.name = name;
        this.password = password;
        this.interfacePath = interfacePath;
        this.sqlXmlFilePath = sqlXmlFilePath;
        try {
            Class.forName(driver1);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("驱动异常");
        }
    }

    /**
     * 返回一个SqlSession连接
     * @return
     */
    public TlSqlSession openSession(){
        try {
            Connection connection = DriverManager.getConnection(url, name, password);
            connection.setAutoCommit(false);
            TlSqlSession sqlSession = new TlSqlSession(connection);
            return sqlSession;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param auto  表示框架是否自动提交事务
     * @return
     */
    public TlSqlSession openSession(boolean auto){
        try {
            System.out.println(url);
            System.out.println(name);
            System.out.println(password);
            Connection connection = DriverManager.getConnection(url,
                    name, password);
            System.out.println(connection);
            connection.setAutoCommit(auto);
            return new TlSqlSession(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
