package com.gmu.hsil.model;

import java.io.Serializable;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StockMetadata implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@SerializedName("Meta Data")
	@Expose
	private Metadata metaData;
	
	@SerializedName("Time Series (1min)")
	@Expose
	private JsonObject timeSeries;

	public JsonObject getTimeSeries() {
		return timeSeries;
	}

	public void setTimeSeries(JsonObject timeSeries) {
		this.timeSeries = timeSeries;
	}

	public Metadata getMetaData() {
		return metaData;
	}

	public void setMetaData(Metadata metaData) {
		this.metaData = metaData;
	}

	@Override
	public String toString() {
		return "StockMetadata [metaData=" + metaData + ", timeSeries=" + timeSeries + "]";
	}

	
}
