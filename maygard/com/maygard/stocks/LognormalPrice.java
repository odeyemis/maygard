/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;

import com.maygard.core.Csmallnumber;
import com.maygard.core.Probnormal;

public class LognormalPrice {
		
	public LognormalPrice() {
		conflevel=1.0;
	}
	public LognormalPrice(double confidence) {
		conflevel=confidence;
	}
	
	private double conflevel;
	private double pdf;
	private double cdf;
	private double vaverage;
	private double vsd;
	private double[] range= new double[2];
	private double[] retrange=new double[2];
	
	private void setPdf(double pdfvalue) {
	pdf=pdfvalue;
	}
	
	private void setCdf(double cdfvalue)//P(X>x)
	{
	cdf=cdfvalue;
	}
	public double getPdf() {
	return pdf;
	}
	public double getCdf() {
	return cdf;
	}
	private void setVaverage(double average) {
	vaverage=average;
	}
	private void setSd(double sd) {
	vsd=sd;
	}
	public double getAverage() {
	return vaverage;
	}
	public double getSd() {
	return vsd;
	}
	public double[] getRange() {
	return range;
	}
	public double[]getRetrange() {
	return retrange;
	}
	
	public void logprice(double So,double St, double mulog, double
	sdlog, double t) {
		Probnormal p=new Probnormal();
		double meanval=(Math.log(So)+((mulog-(Math.pow(sdlog,2.0)*0.5))*t));
		setSd((sdlog*Math.sqrt(t)));
		//sets a variance value
		setVaverage(meanval);
		double sdlevel=(getSd()*conflevel);
		range[0]=Math.exp((getAverage()-sdlevel));
		range[1]=Math.exp((getAverage()+sdlevel));
		setCdf(p.ncDisfnc((Math.log(St)-getAverage())/getSd()));
		double divisor=0.0;
		divisor=(Math.sqrt(2*Math.PI));
		divisor=(1.0/(divisor*getSd()*St));
		Double testval=new Double(divisor);
		divisor= testval.isInfinite()?Csmallnumber.
		getSmallnumber():divisor;
		setPdf( floorvalue( (Math.exp(-0.5*Math.pow(((Math.log(St)-getAverage())/
		getSd()),2)))*divisor));
	}
	
	public void returnrate(double exreturn,double volatility,
	double time) {
		double mean=(exreturn-(Math.pow(volatility,2.0)*0.5));
		double sd=(volatility/Math.sqrt(time));
		retrange[0]=((mean-(conflevel*sd))*100.0);
		retrange[1]=((mean+(conflevel*sd))*100.0);
	}
	
	public double floorvalue(double x) {
		return Math.abs(x)<Csmallnumber.getSmallnumber()?Csmallnumber.
		getSmallnumber():x;
	}
}

