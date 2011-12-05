package com.wp.carlos4web.geo.sfr;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * <p>This class contains the programatic definition to link shapefile attributes to 
 * target class attributes. E.g: <br/> 
 * <code> "shapefile-country-code", "state.country.code"}</code>
 * You can use the linked way: <br/>
 * <code>def.add("from", "to").add("from", "to")</code>
 * </p>
 * 
 * @author Carlos Alberto Junior Spohr Poletto (carlosjrcabello@gmail.com)
 */
public class Definition
{
	private Map<String, String> definitions;

	public Definition(Map<String, String> definitions)
	{
		super();
		
		this.definitions = definitions;
	}

	public Definition()
	{
		super();
	}
	
	/**
	 * Add an link between the shapefile and target class attribute.
	 * 
	 * @param shapeKey - Name of shapefile column
	 * 
	 * @param targetClassAttribute - the name of target class attribute.
	 * 
	 * @return
	 */
	public Definition add(String shapeKey, String targetClassAttribute)
	{
		this.getDefinicoes().put(shapeKey, targetClassAttribute);
		
		return this;
	}
	
	/**
	 * Returns an Iterator object from Map of definitions.
	 * 
	 * @return
	 */
	public Iterator<Entry<String, String>> getIteratorDefinitions ()
	{
		return this.getDefinicoes().entrySet().iterator();
	}

	public Map<String, String> getDefinicoes() {
		return definitions;
	}
}
