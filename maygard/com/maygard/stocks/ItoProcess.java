/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;

/**
* Computes the generalised Wiener process where the parameters are
functions of the underlying variable*/
public class ItoProcess {
		
	public ItoProcess() {
	}
	
	private double sdchange;
	private double meanvalue;
	private double changebase;
	
	private void setChange(double changevalue)
	{
	changebase=changevalue;
	}
	public double getBaseval()
	{
	return changebase;
	}
	private void setSd(double sd)
	{
	sdchange=sd;
	}
	public double getSd()
	{
	return sdchange;
	}
	private void setMean(double drift)
	{
	meanvalue=drift;
	}
	public double getMean()
	{
	return meanvalue;
	}
	/**
	*
	* @param mu mean value
	* @param sigma The variance
	* @param timedelta time periods for each step
	* @param basevalue the starting value
	* @return The change in the base value
	*/
	public double itoValue(double mu, double sigma, double
	timedelta,double basevalue)
	{
		setSd(basevalue*(sigma*Math.sqrt(timedelta)));
		GenWiener g=new GenWiener();
		mu=mu*basevalue;
		sigma=sigma*basevalue;
		double change=( g.genWienerProc(mu, timedelta, sigma));
		setChange(change);
		setMean(g.getDrift());
		return change;
	}
}

