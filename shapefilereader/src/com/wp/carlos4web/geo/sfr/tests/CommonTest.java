package com.wp.carlos4web.geo.sfr.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import com.wp.carlos4web.geo.sfr.ShapeFileReader;
import com.wp.carlos4web.geo.sfr.definitions.Definition;
import com.wp.carlos4web.geo.sfr.tests.pojo.State;

public class CommonTest
{
	public static void main(String[] args)
	{
		String pathname = "./resources/level4.shp";

		try
		{
			Definition def = new Definition();
			
			def.from("LEVEL_4_CO").to("code")
				.and()
				.from("LEVEL_4_NA").to("name")
				.and()
				.from("the_geom").to(new String[]{"limit", "country.limit"})
				.and()
				.from("ISO_CODEs").to("country.code")
				.and()
				.from("LEVEL_NAME").to("country.name");
			
			File shapefile = new File(pathname);
			ShapeFileReader<State> reader = new ShapeFileReader<State>(State.class, shapefile, def);
			
			
			Collection<State> estados = reader.getRecords();
			
			if (estados != null && !estados.isEmpty())
			{
				for (State estado : estados)
				{
					System.out.println("State code: " + estado.getCode());
					System.out.println("State name: " + estado.getName());
					System.out.println("Country: " + estado.getCountry().getName() + " CODE: " + estado.getCountry().getCode());
					
					break;
				}
				System.out.println(estados.size() + " states imported from shapefile.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}
