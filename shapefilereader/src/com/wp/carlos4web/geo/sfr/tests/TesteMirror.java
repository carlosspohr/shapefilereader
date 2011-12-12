package com.wp.carlos4web.geo.sfr.tests;

import java.io.IOException;

import net.vidageek.mirror.dsl.Mirror;

import com.wp.carlos4web.geo.sfr.ShapeFileReader;
import com.wp.carlos4web.geo.sfr.tests.pojo.State;

public class TesteMirror {
	public static void main(String[] args) {
		
		Mirror m = new Mirror();
		
		try
		{
			ShapeFileReader<State> reader = new ShapeFileReader<State>(State.class, null, null);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
