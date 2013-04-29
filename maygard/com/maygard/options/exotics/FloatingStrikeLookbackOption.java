/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

//Floating Strike Lookback Option Valuation.
public class FloatingStrikeLookbackOption {
	
	public FloatingStrikeLookbackOption(double rate, double yield) {
		crate=yield;
		r=rate;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	
	private double r;
	private double crate;
	private double brate;
	
	public double callFxlook(double s1, double s2,
	double t, double sigma) {
		Probnormal p=new Probnormal();
		double a1=((Math.log(s1/s2)+(brate+(sigma*sigma)*0.5)*t)/
		(sigma*Math.sqrt(t)));
		double a2=(a1-(sigma*Math.sqrt(t)));
		double n1=(p.ncDisfnc(a1));
		double n2=(p.ncDisfnc(a2));
		double n3=(p.ncDisfnc(-a1+((2.0*brate/sigma)*Math.sqrt(t))));
		double n4=(p.ncDisfnc(-a1));
		double term1=(Math.pow((s1/s2),(-2.0*brate/(sigma*sigma)))
		*n3-Math.exp(brate*t)*n4);
		double c=(s1*Math.exp((brate-r)*t)*n1-s2*Math.exp(-r*t)*n2+s1*Math.exp(-r*t)*
		((sigma*sigma)/(2.0*brate))*term1);
		return c;
	}
	
	public double putFxlook(double s1, double s2, double t, double sigma)
	{//s2 = smax
	    Probnormal p=new Probnormal();
		double b1=((Math.log(s1/s2)+(brate+(sigma*sigma)*0.5)*t)/
		(sigma*Math.sqrt(t)));
		double b2=(b1-(sigma*Math.sqrt(t)));
		double n1=(p.ncDisfnc(b1));
		double n2=(p.ncDisfnc(-b2));
		double n4=(p.ncDisfnc(b1-((2.0*brate/sigma)*Math.sqrt(t))));
		double n3=(p.ncDisfnc(-b1));
		double term1=(-(Math.pow((s1/s2),(-2.0*brate/(sigma*sigma))))
		*n4+Math.exp(brate*t)*n1);
		double put=(s2*Math.exp(-r*t)*n2-s1*Math.exp((brate-r)*t)*n3+s1*
				Math.exp(-r*t)*((sigma*sigma)/(2.0*brate))*term1);
		return put;
	}
}
