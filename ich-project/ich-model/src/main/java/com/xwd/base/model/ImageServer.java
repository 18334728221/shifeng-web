package com.xwd.base.model;

import com.frame.base.BaseEntity;

/**
 * 图片服务器表
 * @author ljl
 */
public class ImageServer extends BaseEntity {
		
	private static final long serialVersionUID = -384167570698878652L;
	//答疑上传图片服务器
	public final static Byte IMAGE_SERVER_MALL = 0;
	//网站需要保存的图片
	public final static Byte IMAGE_SERVER_SYSTEM = 1;
	//手艺人图片
	public final static Byte IMAGE_SERVER_CRAFTSMAN = 2;
	//columns START
	//id
	private Long id;
	//图片服务器URL
	private String imageUrl;
	//服务器类别 0:答疑 1:网站用户
	private Byte serverType;
	//columns END

	public ImageServer(){
	}

	public ImageServer(
		Long id
	){
		this.id = id;
	}

	/**
	 * id
	 * @return
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * id
	 * @param value
	 */
	public void setId(Long value) {
		this.id = value;
	}
	/**
	 * 图片服务器URL
	 * @return
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	/**
	 * 图片服务器URL
	 * @param value
	 */
	public void setImageUrl(String value) {
		this.imageUrl = value;
	}
	/**
	 * 服务器类别 0:答疑 1:网站用户
	 * @return
	 */
	public Byte getServerType() {
		return this.serverType;
	}
	
	/**
	 * 服务器类别 0:答疑 1:网站用户
	 * @param value
	 */
	public void setServerType(Byte value) {
		this.serverType = value;
	}

}

