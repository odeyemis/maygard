/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.core.Probnormal;

//Implementation of class to compute Black scholes valuation and option sensitivities
public final class BlackScholes {
	
	public BlackScholes() {
	}
	
	/**For carryrate=rate. The black Scholes basic model.
	   For carryrate a zero value. Black 1976 futures
	   For carryrate != rate Gives cont yield model
	**/
	public BlackScholes(double carryrate) {
	    this.crate=carryrate;
	}
	
	private double crate=0.0;
	private double brate=0.0;
	private double d1=0.0;
	private double d2=0.0;
	private double callprice=0.0;
	private double putprice=0.0;
	private double deltac=0.0;
	private double deltap=0.0;
	private double gamma=0.0;
	private double vega=0.0;
	private double thetac=0.0;
	private double thetap=0.0;
	private double rhoc=0.0;
	private double rhop=0.0;
	private double elasticityc=0.0;
	private double elasticityp=0.0;
	private double carryc=0.0;
	private double carryp=0.0;
	public double getCalle() {
	return callprice;
	}
	public double getPute() {
	return putprice;
	}
	private void setcalle(double call) {
	callprice=call;
	}
	private void setpute(double put) {
	putprice=put;
	}
	
	public void bscholEprice(double sprice, double strike,
	double volatility,double time, double rate) {
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		double probd1=0.0;
		double probd2=0.0;
		probd1=p.ncDisfnc(d1);
		probd2=p.ncDisfnc(d2);
		double densityfunc=p.npdfDisfnc(d1);
		double densityfunc2=p.npdfDisfnc(d2);
		setcalle(((sprice*Math.exp((brate-rate)*time))
		*probd1)-((strike*Math.exp(-rate
		*time))*probd2));
		setpute(((strike*Math.exp(-rate*time))
		*p.ncDisfnc(-d2))-(sprice*Math.exp((brate-rate)
		*time))*p.ncDisfnc(-d1));
		deltac=(Math.exp((brate-rate)*time)*probd1);
		deltap=(Math.exp((brate-rate)*time)*(probd1-1));
		gamma=((densityfunc*(Math.exp((brate-rate)*time)))
		/(sprice*volatility*Math.sqrt(time)));
		vega=((sprice*Math.exp((brate-rate)*time))
		*densityfunc*Math.sqrt(time));
		double thetaterm1=((brate-rate)*(sprice*Math.exp((brate-rate)*time)*probd1));
		double thetaterm2=(rate*(strike*Math.exp(-rate*time))*densityfunc2);
		thetac=(((-(sprice*Math.exp((brate-rate)*time))
		*densityfunc*volatility)/(2*Math.sqrt(time)))
		-thetaterm1-thetaterm2);
	}
	
	private void dvalues(double sprice,double strike,
	double volatility,double time, double rate) {
		brate=crate==0.0?0.0:(brate=crate!=rate?
		(rate-crate):rate);
		d1=((Math.log(sprice/strike)+(brate+(volatility*volatility)
		*0.5)*time)/(volatility*Math.sqrt(time)));
		d2=(d1-(volatility*Math.sqrt(time)));
	}
	
	public void setDelta(double sprice,double strike,
	double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		double probd1=0.0;
		double probd2=0.0;
		probd1=p.ncDisfnc(d1);
		probd2=p.ncDisfnc(d2);
		deltac=(Math.exp((brate-rate)*time)*probd1);
		deltap=(Math.exp((brate-rate)*time)*(probd1-1));
	}
	
	public void setGamma(double sprice,double strike,
	double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		double probd1=0.0;
		probd1=p.ncDisfnc(d1);
		double densityfunc=p.npdfDisfnc(d1);
		gamma=((densityfunc*(Math.exp((brate-rate)*time)))
		/(sprice*volatility*Math.sqrt(time)));
	}
	
	public void setVega(double sprice,double strike,
	double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		double probd1=0.0;
		probd1=p.ncDisfnc(d1);
		double densityfunc=p.npdfDisfnc(d1);
		vega=((sprice*Math.exp((brate-rate)*time))
		*densityfunc*Math.sqrt(time));
	}
	
	public void setTheta(double sprice,double strike,
	double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		double probd1=p.ncDisfnc(d1);
		double probd2=p.ncDisfnc(d2);
		double probd3=p.ncDisfnc(-d1);
		double probd4=p.ncDisfnc(-d2);
		double densityfunc=p.npdfDisfnc(d1);
		double densityfunc2=p.npdfDisfnc(d2);
		double thetaterm1=((brate-rate)*(sprice*Math.exp((brate-rate)*time)*probd1));
		double thetaterm2=(rate*(strike*Math.exp(-rate*time))*probd2);
		double thetaterm3=((-(sprice*Math.exp((brate-rate)*time))
		*densityfunc*volatility)/(2*Math.sqrt(time)));
		thetac=(thetaterm3-(thetaterm1)-(thetaterm2));
		double thetaterma=((brate-rate)*(sprice*Math.exp((brate-rate)*time)*probd3));
		double thetatermb=(rate*(strike*Math.exp(-rate*time))*probd4);
		double thetatermc=((-(sprice*Math.exp((brate-rate)*time))
		*densityfunc*volatility)/(2*Math.sqrt(time)));
		thetap=(thetatermc+(thetaterma)+(thetatermb));
	}
	
	public void setRho(double sprice,double
	strike,double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		if(brate!=0.0)
		{
		rhoc=(time*strike*Math.exp(-rate*time)*p.ncDisfnc(d2));
		rhop=(-time*strike*Math.exp(-rate*time)*p.ncDisfnc(-d2));
		}
		else
		{
		bscholEprice(sprice,strike,volatility,time,rate);
		rhoc=(-time*getCalle());
		rhop=(-time*getPute());
		}
	}
	
	public void setElstic(double sprice,double strike,
	double volatility,double time, double rate)
	{
		bscholEprice(sprice,strike,volatility,time,rate);
		setDelta(sprice,strike,volatility,time,rate);
		elasticityc=(getDeltac()*(sprice/getCalle()));
		elasticityp=(getDeltap()*(sprice/getPute()));
	}
	
	public void setCarry(double sprice,double
	strike,double volatility,double time, double rate)
	{
		Probnormal p=new Probnormal();
		dvalues(sprice,strike,volatility,time,rate);
		carryc=(time*(sprice*Math.exp((brate-rate)
		*time))*p.ncDisfnc(d1));
		carryp=(-time*(sprice*Math.exp((brate-rate)*time))
		*p.ncDisfnc(-d1));
	}
	
	public double getDeltac() {
		if(deltac>0.0)
		return deltac;
		else
		throw new IllegalStateException("INCORRECT DELTA PARAMETRS"+deltac);
	}
	
	public double getDeltap() {
	    return deltap;
	}
	
	public double getGamma() {
		if(gamma>0.0)
		return gamma;
		else
		throw new IllegalStateException("INCORRECT GAMMA PARAMETRS"+gamma);
	}
	public double getThetac() {
	return thetac;
	}
	public double getThetap() {
	return thetap;
	}
	public double getVega() {
	return vega;
	}
	public double getRhoc()
	{
	return rhoc;
	}
	public double getRhop()
	{
	return rhop;
	}
	public double getElasticc()
	{
	return elasticityc;
	}
	public double getElasticp()
	{
	return elasticityp;
	}
	public double getCarryc()
	{
	return carryc;
	}
	public double getCarryp()
	{
	return carryp;
	}
}
 
