package com.xwd.auth.model;

import com.auth.impl.SimpleGroup;

/**
 * 组
 * @author ljl
 *
 */
public class AuthGroup extends SimpleGroup {
	
	private static final long serialVersionUID = 3121004630127561150L;
	//columns START
	private Long id;
	private String name;
	private String description;
	//columns END

	public AuthGroup(){
	}

	public AuthGroup(
		Long id
	){
		this.id = id;
	}

	public void setId(Long value) {
		this.id = value;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	
	public String getDescription() {
		return this.description;
	}
}

