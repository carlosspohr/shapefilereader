package com.wp.carlos4web.geo.sfr.definitions;


public class From
{
	private String fromColumn;
	
	private Definition definition;

	public From(String fromColumn, Definition definition)
	{
		super();
		this.fromColumn = fromColumn;
		this.definition = definition;
	}

	public From to (String... targetColumns)
	{
		for (int i = 0; i < targetColumns.length; i++)
		{
			this.definition = this.definition.add(targetColumns[i], this.fromColumn);
		}
		
		return this;
	}
	
	public From to (String targetColumn)
	{
		this.definition = this.definition.add(targetColumn, this.fromColumn);
		
		return this;
	}
	
	public Definition and ()
	{
		return this.definition;
	}
}
