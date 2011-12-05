package com.wp.carlos4web.geo.sfr.tests;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.wp.carlos4web.geo.sfr.ShapeFileReader;
import com.wp.carlos4web.geo.sfr.exceptions.EmptyDefinitionMapException;
import com.wp.carlos4web.geo.sfr.tests.pojo.State;

public class CommonTest
{
	public static void main(String[] args) {
		String pathname = "./resources/level4.shp";

		try
		{
			Map<String, String> definitions = new HashMap<String, String>();
			
			definitions.put("LEVEL_4_CO", 	"code");
			definitions.put("LEVEL_4_NA", 	"name");
			definitions.put("the_geom", 	"limit");
			
			definitions.put("the_geom", 	"country.limit");
			definitions.put("LEVEL_NAME", 	"country.name");
			definitions.put("ISO_CODE", 	"country.code");
			
			File shapefile = new File(pathname);
			ShapeFileReader<State> reader = new ShapeFileReader<State>(State.class, shapefile, definitions, 900913);
			
			Collection<State> estados = reader.getRecords();
			

			if (estados != null && !estados.isEmpty())
			{
				for (State estado : estados)
				{
					assertNotNull(estado);
					
					assertNotNull(estado.getCountry());
					
					System.out.println("State code: " + estado.getCode());
					System.out.println("State name: " + estado.getName());
					System.out.println("Country name: " + estado.getCountry().getName());
					break;
				}
				System.out.println(estados.size() + " states imported from shapefile.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (EmptyDefinitionMapException e) {
			e.printStackTrace();
		}
	}

}
