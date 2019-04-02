package com.tulun.sqlfactory;

import com.tulun.parsexml.XmlAnalysis;

import java.io.InputStream;
import java.util.List;

/**
 * description：
 *
 * @author ajie
 * data 2018/11/12 7:46
 */
public class TlSqlSessionFactoryBuilder {
    /**
     * 用户会从Mybatis.xml读取配置，放到一个InputStream输入对象里面，
     * 该方法主要是从InputStream里面获取
     * driver, url, name, password
     * 接口或者sql映射文件的路径
     * @return
     */

    public TlSqlSessionFactory build(InputStream in){
            XmlAnalysis xml = new XmlAnalysis(in);

            String driver = xml.getDriver();
            String url = xml.getUrl();
            String name = xml.getName();
            String password = xml.getPassword();
            List<String> interfacePathList = xml.getInterfacesPath();
            List<String> sqlXmlFilePathList = xml.getSqlXmlFilePath();

            return new TlSqlSessionFactory(driver, url, name, password, interfacePathList, sqlXmlFilePathList);
    }
}
