package com.xwd.base.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Iterator;

/**
 * Created by austere on 17/4/10.
 */
public class RealNameCheckUtil {
	private static String CARD_NO_URL = "http://v.apistore.cn/api/v4/idcard";
	private static String CARD_NO_KEY = "4dc62b9e03d8db341286889a6d20de6c";

	public static Boolean checkCardno(String realname, String cardno) {
		String getUrl = CARD_NO_URL + "?key=" + CARD_NO_KEY + "&cardNo=" + cardno + "&realName=" + realname + "&output=XML";
		String resultXml = HttpUtils.get(getUrl);

		// 解析结果
		Document document = null;
		try {
			document = DocumentHelper.parseText(resultXml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element node = document.getRootElement().element("result");
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			if (e.getName().equals("isok") && e.getTextTrim().equals("1")) {
				return true;
			}
		}
		return false;

	}
}
