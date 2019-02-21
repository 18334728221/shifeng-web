package com.xwd.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class ZimgUtils {

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param PostData
	 *            请求参数，请求参数应该是 byte[] 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, byte[] postData, String fileType) {
		OutputStream outStream = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
			conn.setRequestProperty("Content-Type", fileType);
			// conn.setRequestProperty("Content-Desposition", "form-data; name=ff; filename=duola-a-meng_49.png");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 二进制
			outStream = conn.getOutputStream();
			outStream.write(postData);
			outStream.flush();

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (outStream != null) {
					outStream.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	private static String macthContentType(String fileType) {
		String contentType = "image/jpeg";
		switch (fileType) {
		case "bmp":
			contentType = "application/x-bmp";
			break;
		case "img":
			contentType = "application/x-img";
			break;
		case "jpeg":
			contentType = "image/jpeg";
			break;
		case "jpg":
			contentType = "application/x-jpg";
			break;
		case "png":
			contentType = "image/png";
			break;
		default:
			break;
		}
		return contentType;
	}
}
