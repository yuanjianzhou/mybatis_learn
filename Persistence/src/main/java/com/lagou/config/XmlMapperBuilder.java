package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.mysql.jdbc.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XmlMapperBuilder {
    private Configuration configuration;

    public XmlMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream mapperInputStream) throws DocumentException, ClassNotFoundException {
        Document document = new SAXReader().read(mapperInputStream);
        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.selectNodes("//select");
        for (Element selectElement : selectElements) {
            String id = selectElement.attributeValue("id");
            String parameterType = selectElement.attributeValue("parameterType");
            String resultType = selectElement.attributeValue("resultType");
            Class<?> parameterTypeClass = getClassType(parameterType);
            Class<?> resultTypeClass = getClassType(resultType);
            String sqlText = selectElement.getTextTrim();
            String statementId = namespace + "." + id;
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(statementId);
            mappedStatement.setParameterType(parameterTypeClass);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sqlText);
            configuration.getMappedStatementMap().put(statementId,mappedStatement);
        }
    }

    public Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if(StringUtils.isNullOrEmpty(parameterType)){
            return null;
        }
        return Class.forName(parameterType);
    }
}
