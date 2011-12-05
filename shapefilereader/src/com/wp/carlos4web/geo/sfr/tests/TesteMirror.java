package com.wp.carlos4web.geo.sfr.tests;

import net.vidageek.mirror.dsl.Mirror;

import com.wp.carlos4web.geo.sfr.tests.pojo.Country;
import com.wp.carlos4web.geo.sfr.tests.pojo.State;

public class TesteMirror {
	public static void main(String[] args) {
		
		State s = new State();
		
		s.setCode("BA");
		
		Class<Country> clazz = Country.class;
		Country t = (Country) new Mirror().on(clazz).invoke().constructor().bypasser();
		
		new Mirror().on(t).set().field("code").withValue("BARBARIDADE");
		
		new Mirror().on(s).set().field("country").withValue(t);
		
		
		
		System.out.println(new Mirror().on(s).get().field("country"));
		
		
	}
}
