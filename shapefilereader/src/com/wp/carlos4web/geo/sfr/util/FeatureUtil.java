package com.wp.carlos4web.geo.sfr.util;

import org.opengis.feature.simple.SimpleFeature;

/**
 * Classe com métodos utilitários para auxiliar a leitura das
 * features do shapefile.
 * 
 * @author Carlos A. Junior (CIH - Centro Internacional de Hidroinformática)
 */
public class FeatureUtil
{
	
	private FeatureUtil()
	{
		
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
	public static boolean hasValueInFeatureColumn(SimpleFeature feature, String column)
	{
		return feature != null && feature.getAttribute(column) != null;
	}
	
	public static Object getValueOfFeatureColumn(SimpleFeature feature, String column)
	{
		if(hasValueInFeatureColumn(feature, column))
		{
			return feature.getAttribute(column);
		}
		
		return null;
	}
}
