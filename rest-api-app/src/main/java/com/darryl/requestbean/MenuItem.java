package com.darryl.requestbean;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItem {

	@JsonProperty
	private String name;
	
	@JsonProperty
	private String type;
	
	public MenuItem ( String name, String type ) {
		super();
		this.setName(name);
		this.setType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if ( name == null || "".equals(name) ) {
			throw new ResponseStatusException ( 
					HttpStatus.BAD_REQUEST, "Missing/Invalid name menu item");
		}
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if ( type == null || "".equals(type) ) {
			throw new ResponseStatusException ( 
					HttpStatus.BAD_REQUEST, "Missing/Invalid type menu item");
		}
		this.type = type;
	}	
}
