package com.wp.carlos4web.geo.sfr.tests.pojo;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Geometry;

public class Country implements Serializable
{
	private static final long serialVersionUID = -1151999788401046811L;
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private Geometry limit;
	
	public Country() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Geometry getLimit() {
		return limit;
	}

	public void setLimit(Geometry limit) {
		this.limit = limit;
	}
}