package com.xwd.base.model;

import com.frame.base.BaseEntity;

public class Banner extends BaseEntity {
	
	private static final long serialVersionUID = 976967666830942702L;
	
	public final static Byte BANNER_TYPE_CAROUSEL = 0;//首页轮播图
	public final static Byte BANNER_TYPE_SHOW = 1;//商城轮播
	//columns START
	//id
	private Long id;
	private Byte type;
	private String imageId;
	private Long imageServerId;
	private String url;
	//columns END
	
	private String imageUrl;
	private String typeString;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public Long getImageServerId() {
		return imageServerId;
	}

	public void setImageServerId(long imageServerId) {
		this.imageServerId = imageServerId;
	}

	public String getTypeString() {
		String typeString = "";
		if(this.type!=null){
			if(type==0){
				typeString = "轮播图";
			}else{
				typeString = "展示图";
			}
		}
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
