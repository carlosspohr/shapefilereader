package com.wp.carlos4web.geo.sfr;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import net.vidageek.mirror.dsl.Mirror;

import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;
import com.wp.carlos4web.geo.sfr.definitions.Definition;
import com.wp.carlos4web.geo.sfr.exceptions.FeatureException;
import com.wp.carlos4web.geo.sfr.util.FeatureUtil;

/**
 * This class contains all logic to process the reader an instantiation of
 * shapefile and target classes.
 * 
 * @author Carlos Alberto Junior Spohr Poletto (carlosjrcabello@gmail.com)
 */
public class FeatureReader<T> implements Serializable
{
	private static final long serialVersionUID = -6664387995634473604L;
	
	private Definition 	definition;
	
	private Class<T>	targetClass;
	
	public FeatureReader(Definition definition, Class<T> targetClass)
	{
		super();
		
		this.definition 	= definition;
		this.targetClass 	= targetClass;
	}
	
	/**
	 * This method contains the logic to process the read of the shapefile tuple to an
	 * specified target class.
	 * 
	 * @param feature - an single tuple of shapefile.
	 * 
	 * @param srid - the projection code, e.g: 4326, 900913..
	 * 
	 * @return T - the populated target class object.
	 * 
	 * @throws FeatureException - throws when feature has any error or inconsistency.
	 * 
	 * @throws InstantiationException - throws when reflection errors occurs
	 * 
	 * @throws IllegalAccessException - throws when reflection errors occurs
	 */
	private T readFeature (SimpleFeature feature, int srid)
	{
		Iterator<Entry<String, String>> definitions = this.getDefinition().getIteratorDefinitions();
		
		// Creates an new instance of target class.
		Class<T> clazz = this.targetClass;
		
		Mirror mirror = new Mirror();
		
		T object = new Mirror().on(clazz).invoke().constructor().bypasser();
		
		while (definitions.hasNext())
		{
			Entry<String, String> entry = (Entry<String, String>) definitions.next();
			
			if(!FeatureUtil.hasValueInFeatureColumn(feature, entry.getValue()))
			{
				continue;
			}
			
			Object value = FeatureUtil.getValueOfFeatureColumn(feature, entry.getValue());
			
			if(!FeatureUtil.hasRule(entry.getKey()))
			{
				mirror.on(object).set().field(entry.getKey()).withValue(value);
				
				if(mirror.on(object.getClass()).reflect().field(entry.getKey()).getType().equals(Geometry.class))
				{
					Geometry geo = (Geometry) mirror.on(object).get().field(entry.getKey());
					geo.setSRID(srid);
					mirror.on(object).set().field(entry.getKey()).withValue(geo);
				}
			}
			else
			{
				String[] split = entry.getKey().split("\\.");
				
				if(mirror.on(object).get().field(split[0]) == null)
				{
					Field field = mirror.on(object.getClass()).reflect().field(split[0]);
					
					mirror.on(object).set().field(split[0]).withValue(
						mirror.on(field.getType()).invoke().constructor().bypasser()
					);
				}
				
				// With a correct instance of class field type, fill with data ;)
				Object instance = mirror.on(object).get().field(split[0]);
				mirror.on(instance).set().field(split[1]).withValue(value);
			}
		}
		
		return (T) object;
	}
	
	/**
	 * This method extract the content of an SimpleFeature to the specified target class 
	 * without precision model specified.
	 * 
	 * @param feature - Sigle tuple of shapefile.
	 * 
	 * @return T - the populated target class object.
	 * 
	 * @throws FeatureException - throws when feature has any error or inconsistency.
	 * 
	 * @throws InstantiationException - throws when reflection errors occurs
	 * 
	 * @throws IllegalAccessException - throws when reflection errors occurs
	 */
	public T getObject (SimpleFeature feature) throws FeatureException
	{
		return this.readFeature(feature, -1);
	}
	
	/**
	 * This method extract the content of an SimpleFeature to the specified target class 
	 * with an precision model.
	 * 
	 * @param feature - Single tuple of shapefile.
	 * 
	 * @param precisionModel - the projection code, e.g.: 4326, 900913..
	 * 
	 * @return T - the populated target class object.
	 * 
	 * @throws FeatureException - throws when feature has any error or inconsistency.
	 * 
	 * @throws InstantiationException - throws when reflection errors occurs
	 * 
	 * @throws IllegalAccessException - throws when reflection errors occurs
	 */
	public T getObject (SimpleFeature feature, int precisionModel) throws FeatureException
	{
		return this.readFeature(feature, precisionModel);
	}

	public Definition getDefinition()
	{
		return definition;
	}
}
