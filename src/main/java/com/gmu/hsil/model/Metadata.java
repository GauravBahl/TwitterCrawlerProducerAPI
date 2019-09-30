package com.gmu.hsil.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metadata implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SerializedName("1. Information")
	@Expose
	private String Information;
	
	@SerializedName("2. Symbol")
	@Expose
	private String symbol;
	
	@SerializedName("3. Last Refreshed")
	@Expose
	private String lastRefreshed;
	
	@SerializedName("4. Interval")
	@Expose
	private String interval;
	
	@SerializedName("5. Output Size")
	@Expose
	private String outputSize;
	
	@SerializedName("6. Time Zone")
	@Expose
	private String timeZone;

	public String getInformation() {
		return Information;
	}

	public void setInformation(String information) {
		Information = information;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLastRefreshed() {
		return lastRefreshed;
	}

	public void setLastRefreshed(String lastRefreshed) {
		this.lastRefreshed = lastRefreshed;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getOutputSize() {
		return outputSize;
	}

	public void setOutputSize(String outputSize) {
		this.outputSize = outputSize;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public String toString() {
		return "Metadata [Information=" + Information + ", symbol=" + symbol + ", lastRefreshed=" + lastRefreshed
				+ ", interval=" + interval + ", outputSize=" + outputSize + ", timeZone=" + timeZone + "]";
	}
	
	

}
