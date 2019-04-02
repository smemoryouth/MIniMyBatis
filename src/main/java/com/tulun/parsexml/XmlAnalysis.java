package com.tulun.parsexml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * description：xml解析类
 *
 * @author ajie
 * data 2018/11/12 8:05
 */
public class XmlAnalysis {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private static DocumentBuilder builder;
    private static Document document = null;
    static HashMap<String, String> map = new HashMap<>();
    private static List<String> interfacePathList = new ArrayList<>();
    private static List<String> sqlXmlFilePathList = new ArrayList<>();

    public XmlAnalysis(InputStream in) {
        try {
            builder = factory.newDocumentBuilder();
            document = builder.parse(in);
            analysisDataSource();
            analysisMapper();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void analysisDataSource() {
        NodeList list = document.getElementsByTagName("environment");
        for (int i = 0; i < list.getLength(); i++) {
            Element element = (Element) list.item(i);
            String id = element.getAttribute("id");
            if ("development".equals(id)) {
                NodeList chList = element.getChildNodes();
                for (int j = 0; j < chList.getLength(); j++) {
                    Node node = chList.item(j);
                    if ("dataSource".equals(node.getNodeName())) {
                        Element dataSourceElement = (Element) node;
                        for (Node childNode = dataSourceElement.getFirstChild();
                             childNode != null;
                             childNode = childNode.getNextSibling()) {

                            if (childNode instanceof Element) {
                                Element property = (Element) childNode;
                                map.put(property.getAttribute("name"), property.getAttribute("value"));
                            }
                        }
                        break;
                    }
                }
            }
            break;
        }
    }

    private static void analysisMapper() {
        NodeList nodeList = document.getElementsByTagName("mappers");
        NodeList mapperNodeList = (nodeList.item(0)).getChildNodes();
        for (int i = 0; i < mapperNodeList.getLength(); ++i) {
            Node node = mapperNodeList.item(i);
            if (node instanceof Element) {
                Element mapperElm = (Element) node;
                String path;
                // 指定了sql映射文件的相对路径
                if ((path = mapperElm.getAttribute("resource")).compareTo("") != 0) {
                    sqlXmlFilePathList.add(path);
                }

                // 指定了框架注解修饰的接口mapper接口
                if ((path = mapperElm.getAttribute("class")).compareTo("") != 0) {
                    interfacePathList.add(path);
                }
            }
        }
    }

    public String getDriver() {
        System.out.println(map.get("driver"));
        return map.get("driver");
    }

    public String getUrl() {
        return map.get("url");
    }

    public String getName() {
        return map.get("username");
    }

    public String getPassword() {
        return map.get("password");
    }

    public List<String> getInterfacesPath() {
        return interfacePathList;
    }

    public List<String> getSqlXmlFilePath() {
        return sqlXmlFilePathList;
    }
}
