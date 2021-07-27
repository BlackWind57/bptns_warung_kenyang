package com.darryl.requestbean;

import javax.validation.constraints.NotEmpty;

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
		if ( name == null ) {
			throw new ResponseStatusException ( 
					HttpStatus.BAD_REQUEST, "Missing name menu item");
		}
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if ( type == null ) {
			throw new ResponseStatusException ( 
					HttpStatus.BAD_REQUEST, "Missing type menu item");
		}
		this.type = type;
	}	
}
