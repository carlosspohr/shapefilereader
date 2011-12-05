package com.wp.carlos4web.geo.sfr.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe estática com métodos estáticos para auxiliar na leitura 
 * de shapefiles.
 * 
 * @author Carlos A. Junior (CIH - Centro Internacional de Hidroinformática)
 */
public class FeatureParserUtil
{
	/**
	 * Retorna um List com as classes que não podem ser parseadas pelo Java.
	 * 
	 * @param classe
	 * 
	 * @return
	 */
	private List<String> getTiposNegados (String classe)
	{
		List<String> negados = new ArrayList<String>();
		
		if(classe.equals(Integer.class.getSimpleName()) || classe.equals(Long.class.getSimpleName()))
		{
			negados.add(Boolean.class.getSimpleName()	);
			negados.add(Date.class.getSimpleName()		);
		}
		
		if(classe.equals(Date.class.getSimpleName()) || classe.equals(Boolean.class.getSimpleName()))
		{
			negados.add(Integer.class.getSimpleName()	);
			negados.add(Double.class.getSimpleName()	);
		}
		
		return negados;
	}
	
	/**
	 * Realiza o parse de um objeto para Integer.
	 * 
	 * @param value
	 * 
	 * @return
	 */
	private Integer parseToInteger (Object value) throws Exception
	{
		List<String> negados = this.getTiposNegados(value.getClass().getSimpleName());
		
		if(negados.contains(value.getClass().getSimpleName()))
		{
			throw new Exception("Tipo de Classe não parseável para " + value.getClass().getSimpleName());
		}
		else
		{
			if(value.getClass().equals(String.class))
			{
				value = Integer.parseInt((String) value);
			}
			if(value.getClass().equals(Long.class))
			{
				value = ( (Long) value).intValue();
			}
			if(value.getClass().equals(Double.class))
			{
				value = ( (Double) value).intValue();
			}
			
			return (Integer) value;
		}
	}
	
	/**
	 * Realiza o parse de um objeto para Long.
	 * 
	 * @param value
	 * 
	 * @return
	 */
	private Long parseToLong (Object value) throws Exception
	{
		List<String> negados = this.getTiposNegados(value.getClass().getSimpleName());
		
		if(negados.contains(value.getClass().getSimpleName()))
		{
			throw new Exception("Tipo de Classe não parseável para " + value.getClass().getSimpleName());
		}
		else
		{
			if(value.getClass().equals(String.class))
			{
				value = new Long(value.toString());
			}
			if(value.getClass().equals(Double.class))
			{
				value = ( (Double) value).longValue();
			}
			if(value.getClass().equals(Long.class))
			{
				value = ( (Long) value).longValue();
			}
			
			return (Long) value;
		}
	}
	
	/**
	 * Realiza o parse de um objeto para Double.
	 * 
	 * @param value
	 * 
	 * @return
	 */
	private Double parseToDouble (Object value) throws Exception
	{
		List<String> negados = this.getTiposNegados(value.getClass().getSimpleName());
		
		if(negados.contains(value.getClass().getSimpleName()))
		{
			throw new Exception("Tipo de Classe não parseável para " + value.getClass().getSimpleName());
		}
		else
		{
			if(value.getClass().equals(String.class))
			{
				value = new Double(value.toString());
			}
			if(value.getClass().equals(Integer.class))
			{
				value = ( (Integer) value).doubleValue();
			}
			if(value.getClass().equals(Long.class))
			{
				value = ( (Long) value).doubleValue();
			}
			
			return (Double) value;
		}
	}
	
	/**
	 * Realiza o parse de um objeto para Boolean.
	 * 
	 * @param value
	 * 
	 * @return
	 */
	private Boolean parseToBoolean (Object value) throws Exception
	{
		List<String> negados = this.getTiposNegados(value.getClass().getSimpleName());
		
		if(negados.contains(value.getClass().getSimpleName()))
		{
			throw new Exception("Tipo de Classe não parseável para " + value.getClass().getSimpleName());
		}
		else
		{
			if(!(value instanceof Boolean))
			{
				// Somente strings.
				String s = value.toString();
				
				if("1".equals(s) || "true".equals(s.toLowerCase()))
				{
					value = Boolean.TRUE;
				}
				else
				{
					value = Boolean.FALSE;
				}
			}
			
			return (Boolean) value;
		}
	}
	
	public Object parseDate(Object value, Field atributo)
	{
		Object parsed;
		
		Date date = (Date) value;
		
		if(atributo.getType().equals(Calendar.class))
		{
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(date);
			
			parsed = calendar;
		}
		else
		{
			parsed = value;
		}
		return parsed;
	}
	
	/**
	 * Realiza o parse de um Object para o tipo correto do Field de destino.
	 * 
	 * @param value
	 * 
	 * @param field
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	public Object parseValue (Object value, Field field) throws Exception
	{
		Object parsed = null;
		
		if(value != null)
		{
			if(field.getType().equals(String.class))
			{
				parsed = value;
			}
			if(field.getType().equals(Integer.class))
			{
				parsed = this.parseToInteger(value);
			}
			if(field.getType().equals(Long.class))
			{
				parsed = this.parseToLong(value);
			}
			if(field.getType().equals(Double.class))
			{
				parsed = this.parseToDouble(value);
			}
			if(field.getType().equals(Boolean.class))
			{
				parsed = this.parseToBoolean(value);
			}
			if(field.getType().equals(Date.class) || field.getType().equals(Calendar.class))
			{
				parsed = this.parseDate(value, field);
			}
		}
		
		return parsed;
	}
}
