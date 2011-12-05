package com.wp.carlos4web.geo.sfr;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;

import net.vidageek.mirror.dsl.Mirror;

import org.opengis.feature.simple.SimpleFeature;

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
	
	private FeatureUtil util;
	
	private Class<T>	targetClass;
	
	public FeatureReader(Definition definition, Class<T> targetClass)
	{
		super();
		
		this.definition 	= definition;
		this.targetClass 	= targetClass;
		this.util			= new FeatureUtil();
	}
	
	/**
	 * This method contains the logic to process the read of the shapefile tuple to an
	 * specified target class.
	 * 
	 * @param feature - an single tuple of shapefile.
	 * 
	 * @param precisionModel - the projection code, e.g: 4326, 900913..
	 * 
	 * @return T - the populated target class object.
	 * 
	 * @throws FeatureException - throws when feature has any error or inconsistency.
	 * 
	 * @throws InstantiationException - throws when reflection errors occurs
	 * 
	 * @throws IllegalAccessException - throws when reflection errors occurs
	 */
	private T readFeature (SimpleFeature feature, int precisionModel) throws FeatureException, InstantiationException, IllegalAccessException
	{
		Iterator<Entry<String, String>> definitions = this.getDefinition().getIteratorDefinitions();
		
		// Creates an new instance of target class.
		Class<T> clazz = this.targetClass;
		T object = new Mirror().on(clazz).invoke().constructor().bypasser();
		
		// Setting values based in definitions.
		while (definitions.hasNext())
		{
			Entry<String, String> entry = (Entry<String, String>) definitions.next();
			
			if(FeatureUtil.hasValue(feature, entry.getKey()))
			{
				continue;
			}
			
			Object value = FeatureUtil.getValue(feature, entry.getKey());
			
			try
			{
				Object attributeValue;
				Field atributo;
				
				if(FeatureUtil.hasRule(entry.getValue()))
				{
					atributo = this.getUtil().getRuledField(object, entry.getValue());
				}
				else
				{
					atributo = this.getUtil().getField(object, entry.getValue());
				}
				
				// Libera acesso ao atributo.
				atributo.setAccessible(true);
				
				if(FeatureUtil.hasRule(entry.getValue()))
				{
					attributeValue = atributo.get(object);
					
					if(attributeValue == null)
					{
						attributeValue = atributo.getType().newInstance();
					}
					
					Field recursiveAttribute = this.getUtil().getRuledAttributeField(attributeValue, entry.getValue());
					
					recursiveAttribute.setAccessible(true);
					recursiveAttribute.set(attributeValue, this.getUtil().getParsedValue(value, recursiveAttribute));
				}
				else
				{
					attributeValue = this.getUtil().getParsedValue(value, atributo);
				}
				
				// Seta os dados no objeto principal.
				atributo.set(object, attributeValue);
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(object != null)
		{
			this.getUtil().setGeometrySRID(object, precisionModel);
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
	public T getObject (SimpleFeature feature) throws FeatureException, InstantiationException, IllegalAccessException
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
	public T getObject (SimpleFeature feature, int precisionModel) throws FeatureException, InstantiationException, IllegalAccessException
	{
		return this.readFeature(feature, precisionModel);
	}

	public Definition getDefinition()
	{
		return definition;
	}

	public FeatureUtil getUtil() {
		return util;
	}

	public void setUtil(FeatureUtil util) {
		this.util = util;
	}

	public void setDefinition(Definition definition) {
		this.definition = definition;
	}
}
