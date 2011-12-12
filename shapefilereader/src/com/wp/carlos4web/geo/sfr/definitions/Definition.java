package com.wp.carlos4web.geo.sfr.definitions;

import java.util.HashMap;
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
	private Map<String, String> definitions = new HashMap<String, String>();
	
	private int precisionModel = 4326;

	public Definition()
	{
		super();
	}
	
	public Definition(int precisionModel)
	{
		super();
		this.precisionModel = precisionModel;
	}
	
	public From from (String column)
	{
		return new From(column, this);
	}
	
	/**
	 * Add an link between the shapefile and target class attribute.
	 * 
	 * @param attributeTarget - Name of shapefile column
	 * 
	 * @param shapeColumn - the name of target class attribute.
	 * 
	 * @return
	 */
	public Definition add(String attributeTarget, String shapeColumn)
	{
		this.getDefinicoes().put(attributeTarget, shapeColumn);
		
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

	public int getPrecisionModel() {
		return precisionModel;
	}

	public void setPrecisionModel(int precisionModel) {
		this.precisionModel = precisionModel;
	}
}
