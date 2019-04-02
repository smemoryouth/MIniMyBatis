package com.tulun.employee.mybatisutil;

import com.tulun.sqlfactory.TlSqlSession;
import com.tulun.sqlfactory.TlSqlSessionFactory;
import com.tulun.sqlfactory.TlSqlSessionFactoryBuilder;

import java.io.InputStream;


/**
 * description：
 *
 * @author ajie
 * data 2018/10/29 11:21
 */
public class Utils {
    private static TlSqlSessionFactory ssf;

    static{
        String configFile = "mybatis.xml";
        InputStream in = Utils.class.getClassLoader().getResourceAsStream(configFile);
        ssf = new TlSqlSessionFactoryBuilder().build(in);
    }

    /**
     * MyBatisUtils.getSession
     * @return
     */
    public static TlSqlSession getSession(){
        TlSqlSession ss = ssf.openSession(false);
        return ss;
    }
}
