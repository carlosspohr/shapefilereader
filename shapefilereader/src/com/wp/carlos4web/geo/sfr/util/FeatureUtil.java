package com.wp.carlos4web.geo.sfr.util;

import java.lang.reflect.Field;

import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Geometry;

/**
 * Classe com métodos utilitários para auxiliar a leitura das
 * features do shapefile.
 * 
 * @author Carlos A. Junior (CIH - Centro Internacional de Hidroinformática)
 */
public class FeatureUtil
{
	private FeatureParserUtil parser;
	
	public FeatureUtil()
	{
		this.parser = new FeatureParserUtil();
	}
	
	/**
	 * Seta o SRID na geometria padrão da classe. O atributo tem que estender da super classe
	 * Geometry.
	 * 
	 * @param object
	 * 
	 * @param srid
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @see Geometry
	 */
	public void setGeometrySRID (Object object, int srid) throws IllegalArgumentException, IllegalAccessException
	{
		Field[] fields = object.getClass().getDeclaredFields();
		
		for (Field field : fields)
		{
			field.setAccessible(true);
			
			if(field.getType().equals(Geometry.class))
			{
				Geometry geometry = (Geometry) field.get(object);
				
				if(geometry != null)
				{
					geometry.setSRID(srid);
				}
			}
		}
	}
	
	/**
	 * Retorna o objeto setável no atributo referenciado.
	 * 
	 * @param shapeColumnValue
	 * 
	 * @param atributo
	 * 
	 * @return Object
	 * 
	 * @throws Exception
	 */
	public Object getParsedValue (Object shapeColumnValue, Field atributo) throws Exception
	{
		if(!atributo.getType().equals(Geometry.class))
		{
			return this.parser.parseValue(shapeColumnValue, atributo);
		}
		else
		{
			return shapeColumnValue;
		}
	}
	
	/**
	 * Pega o atributo da classe com regra. Ex.: no atributo Pais pais ele irá
	 * retornar uma referência para o atributo nome por exemplo.
	 * 
	 * @param object
	 * 
	 * @param classAttribute
	 * 
	 * @return Field
	 */
	public Field getRuledAttributeField (Object object, String classAttribute) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		String[] split = classAttribute.split("\\.");
		
		if(split.length > 0)
		{
			return object.getClass().getDeclaredField(split[1]);
		}
		else
		{
			return null;
		}
	}
	
	public Field getField (Object currentInstance, String classAttribute) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		if(currentInstance != null)
		{
			return currentInstance.getClass().getDeclaredField(classAttribute);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Retorna um Field que está contido numa rule nas definições.
	 * 
	 * @param Object currentInstance
	 * 
	 * @param String classAttribute
	 * 
	 * @return Field
	 */
	public Field getRuledField (Object currentInstance, String classAttribute) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		String[] split = classAttribute.split("\\.");
		
		if(split.length > 0)
		{
			return currentInstance.getClass().getDeclaredField(split[0]);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Retorna a instancia de um atributo da classe corrente.
	 * 
	 * @param currentInstance
	 * 
	 * @param classAttribute
	 * 
	 * @return
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @throws SecurityException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws NoSuchFieldException
	 */
	public Object getAttributeInstance (Object currentInstance, String classAttribute) throws IllegalArgumentException, SecurityException, IllegalAccessException, NoSuchFieldException
	{
		String[] split = classAttribute.split("\\.");
		
		if(split.length > 0)
		{
			return currentInstance.getClass().getDeclaredField(split[0]).get(currentInstance);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Verifica se a coluna possui uma regra. Ex.: pais.id
	 * 
	 * @param attribute
	 * 
	 * @return boolean
	 */
	public static boolean hasRule (String attribute)
	{
		if(attribute == null || attribute.isEmpty())
		{
			return false;
		}
		
		return attribute.indexOf(".") > 0;
	}
	
	/**
	 * Returns true with feature contains an value for specified shape column.
	 * 
	 * @param feature - SimpleFeature instance.
	 * 
	 * @param column - Name of column.
	 * 
	 * @return
	 */
	public static boolean hasValue(SimpleFeature feature, String column)
	{
		return feature != null && feature.getAttribute(column) != null;
	}
	
	public static Object getValue(SimpleFeature feature, String column)
	{
		if(hasValue(feature, column))
		{
			return feature.getAttribute(column);
		}
		
		return null;
	}
}
