package com.wp.carlos4web.geo.sfr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.geotools.data.FeatureSource;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.FeatureCollection;
import org.opengis.feature.simple.SimpleFeature;

import com.wp.carlos4web.geo.sfr.exceptions.EmptyDefinitionMapException;
import com.wp.carlos4web.geo.sfr.exceptions.FeatureException;

/**
 * This class allows the read of shapefile contents directly to specified target
 * classes.
 * 
 * @author Carlos Alberto Junior Spohr Poletto (carlosjrcabello@gmail.com)
 */
public class ShapeFileReader<T>
{
	private Class<T> 	targetClass;
	
	private File 		shapefile;
	
	private Definition 	definition;
	
	private SimpleFeatureIterator simpleFeatureIterator;
	
	private int 		srid = 4326;
	
	public ShapeFileReader(Class<T> targetClass, File shapeFile, Definition definition, int srid) throws IOException, NullPointerException
	{
		super();
		
		this.targetClass 	= targetClass;
		this.shapefile 		= shapeFile;
		this.definition 	= definition;
		this.srid 			= srid;
		
		this.init();
	}
	
	public ShapeFileReader(Class<T> targetClass, File shapeFile, Definition definition) throws IOException, NullPointerException
	{
		super();
		
		this.targetClass 	= targetClass;
		this.shapefile 		= shapeFile;
		this.definition 	= definition;
		
		this.init();
	}
	
	public ShapeFileReader(Class<T> targetClass, File shapeFile, Map<String, String> definitions) throws IOException, NullPointerException, EmptyDefinitionMapException
	{
		super();
		this.targetClass 	= targetClass;
		this.shapefile 		= shapeFile;
		
		if(definitions == null)
		{
			throw new EmptyDefinitionMapException("No definition was specified.");
		}
		this.definition 	= new Definition(definitions);
		
		this.init();
	}
	
	public ShapeFileReader(Class<T> targetClass, File shapeFile, Map<String, String> definitions, int srid) throws IOException, NullPointerException, EmptyDefinitionMapException
	{
		super();
		this.targetClass 	= targetClass;
		this.shapefile 		= shapeFile;
		this.srid			= srid;
		
		if(definitions == null)
		{
			throw new EmptyDefinitionMapException("No definition was specified.");
		}
		this.definition 	= new Definition(definitions);
		
		this.init();
	}
	
	/**
	 * Private method that configure all shapefile import parameters.
	 * 
	 * @throws IOException - throws when .shp is incorrect.
	 * 
	 * @throws NullPointerException - throws when target class isn't specified.
	 */
	private void init () throws IOException, NullPointerException
	{
		if(this.targetClass == null)
		{
			throw new NullPointerException("The target class wasn't specified. Please specify to continue.");
		}
		
		if(this.shapefile == null || !this.shapefile.exists() || this.shapefile.isDirectory())
		{
			throw new IOException("The specified shapefile is incorrect. The shapefile has extension [.shp].");
		}
		
		FileDataStore store 				= FileDataStoreFinder.getDataStore(this.shapefile);
		FeatureSource<?, ?> source 			= store.getFeatureSource();
		FeatureCollection<?, ?> collection 	= source.getFeatures();
		
		this.simpleFeatureIterator 		= (SimpleFeatureIterator) collection.features();
		
		if(srid <= 0)
		{
			this.srid = 4326;
		}
	}
	
	/**
	 * Returns an object Collection with all elements assigneds in definition object.
	 *  
	 * @return
	 */
	public Collection<T> getRecords ()
	{
		Collection<T> result = new ArrayList<T>(0);
		
		FeatureReader<T> reader = new FeatureReader<T>(this.getDefinition(), this.targetClass);
		
		while (this.getSimpleFeatureIterator().hasNext())
		{
			SimpleFeature feature = (SimpleFeature) this.getSimpleFeatureIterator().next();
			
			try
			{
				T object = (T) reader.getObject(feature, this.getSrid());
				result.add(object);
			} catch (FeatureException e) {
				e.printStackTrace();
				break;
			}
			break;
		}
		
		return result;
	}

	public File getShapefile() {
		return shapefile;
	}

	public Definition getDefinition() {
		return definition;
	}

	public SimpleFeatureIterator getSimpleFeatureIterator() {
		return simpleFeatureIterator;
	}
	
	public int getSrid() {
		return srid;
	}
}
