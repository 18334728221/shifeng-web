package com.xwd.pay.weixin.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
 * xml工具类
 * @author miklchen
 *
 */
public class XMLUtil {

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws JDOMException, IOException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = XMLUtil.getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取xml编码字符集
	 * @param strxml
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static String getXMLEncoding(String strxml) throws JDOMException, IOException {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		in.close();
		return (String)doc.getProperty("encoding");
	}
	
	public static String setXML(String return_code, String return_msg){
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}
	
	 /** 
     * 把xml文件转换为map形式，其中key为有值的节点名称，并以其所有的祖先节点为前缀，用 
     * "."相连接。如：SubscribeServiceReq.Send_Address.Address_Info.DeviceType 
     *  
     * @param xmlStr 
     *            xml内容 
     * @return Map 转换为map返回 
     */  
    public static Map<String, String> xml2Map(String xmlStr) throws JDOMException, IOException {  
        Map<String, String> rtnMap = new HashMap<String, String>();  
        SAXBuilder builder = new SAXBuilder();  
        Document doc = (Document) builder.build(new StringReader(xmlStr));  
        // 得到根节点  
        Element root = doc.getRootElement();  
        String rootName = root.getName();  
        rtnMap.put("root.name", rootName);  
        // 调用递归函数，得到所有最底层元素的名称和值，加入map中  
        convert(root, rtnMap, rootName);  
        return rtnMap;  
    }  
  
    /** 
     * 递归函数，找出最下层的节点并加入到map中，由xml2Map方法调用。 
     *  
     * @param e 
     *            xml节点，包括根节点 
     * @param map 
     *            目标map 
     * @param lastname 
     *            从根节点到上一级节点名称连接的字串 
     */  
    @SuppressWarnings("rawtypes")  
    public static void convert(Element e, Map<String, String> map, String lastname) {  
        if (e.getAttributes().size() > 0) {  
            Iterator it_attr = e.getAttributes().iterator();  
            while (it_attr.hasNext()) {  
                Attribute attribute = (Attribute) it_attr.next();  
                String attrname = attribute.getName();  
                String attrvalue = e.getAttributeValue(attrname);  
                // map.put( attrname, attrvalue);  
                map.put(lastname + "." + attrname, attrvalue); // key 根据根节点 进行生成  
            }  
        }  
        List children = e.getChildren();  
        Iterator it = children.iterator();  
        while (it.hasNext()) {  
            Element child = (Element) it.next();  
            /* String name = lastname + "." + child.getName(); */  
            String name = child.getName();  
            // 如果有子节点，则递归调用  
            if (child.getChildren().size() > 0) {  
                convert(child, map, lastname + "." + child.getName());  
            } else {  
                // 如果没有子节点，则把值加入map  
                map.put(name, child.getText());  
                // 如果该节点有属性，则把所有的属性值也加入map  
                if (child.getAttributes().size() > 0) {  
                    Iterator attr = child.getAttributes().iterator();  
                    while (attr.hasNext()) {  
                        Attribute attribute = (Attribute) attr.next();  
                        String attrname = attribute.getName();  
                        String attrvalue = child.getAttributeValue(attrname);  
                        map.put(lastname + "." + child.getName() + "." + attrname, attrvalue);  
                    }  
                }  
            }  
        }  
    }  
	
    
    public  static String map2Xml(Map<String, String> map) {
    	org.dom4j.Document  document = org.dom4j.DocumentHelper.createDocument();
    	org.dom4j.Element root= document.addElement("xml");
    	Set<String>  keys=map.keySet();
    	for(String key:keys){
    	root.addElement(key).addText(map.get(key));
    	}
    	StringWriter sw=new StringWriter();
    	org.dom4j.io.XMLWriter xw = new org.dom4j.io.XMLWriter(sw);
    	xw.setEscapeText(false);
    	try {
    	xw.write(document);
    	} catch (IOException e) {

    	e.printStackTrace();
    	}
    	return sw.toString();
    	}
    
    public static void main(String args[]){
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("abc", "ddddss");
    	String amp = map2Xml(map);
    	System.out.println(amp);
    }
}
