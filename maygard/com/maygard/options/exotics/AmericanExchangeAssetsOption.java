/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;
import com.maygard.options.AmericanBS;

//Exchange-One-Asset-For-Another Valuation
public class AmericanExchangeAssetsOption {
	
	public AmericanExchangeAssetsOption(double p, double y1, double rate,double y2,
	double sig1, double sig2)
	{
		crate1=y1;
		crate2=y2;
		r=rate;
		sigma1=sig1;
		sigma2=sig2;
		rho=p;
		b1=crate1==0.0?0.0:(b1=crate1!=r?(r-crate1):r);
		b2=crate2==0.0?0.0:(b2=crate2!=r?(r-crate2):r);
	}
	
	private double r;
	private double sigma1;
	private double sigma2;
	private double b2;
	private double b1;
	private double rho;
	private double d1;
	private double d2;
	private double sigma;
	private double crate1;
	private double crate2;
	
	private void params(double s1,double q1,double s2,
	double q2, double time)
	{
		sigma=(Math.sqrt((sigma1*sigma1)+(sigma2*sigma2)
		-2.0*rho*sigma1*sigma2));
		d1=((Math.log((q1*s1)/(q2*s2))+(b1-b2+(sigma*sigma)*0.5)
		*time)/(sigma*Math.sqrt(time)));
		d2=(d1-sigma*Math.sqrt(time));
	}
	
	public double exoneFother(double s1, double q1, double s2,
	double q2, double time)
	{
		Probnormal p = new Probnormal();
		params(s1,q1,s2,q2,time);
		double n=p.ncDisfnc(d1);
		double n2=p.ncDisfnc(d2);
		double c=(q1*s1*Math.exp((b1-r)*time)*n-q2*s2*Math.exp((b2-r)*time)*n2);
		return c;
	}
	
	public double amexoneFother(double s1, double q1, double s2,
	double q2, double time)
	{
		params(s1,q1,s2,q2,time);
		AmericanBS a=new AmericanBS(b2);
		double c= a.amerBs(s1,s2,sigma,time,r-b2,1);
		return c;
	}
}

