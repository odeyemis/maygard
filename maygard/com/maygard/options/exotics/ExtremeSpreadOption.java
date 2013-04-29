/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Csmallnumber;
import com.maygard.core.Probnormal;

//Extreme Spread Option Valuation.
public class ExtremeSpreadOption {
	
	public ExtremeSpreadOption(double rate, double yield, double eta) {
		//eta=1=call, -1 = put
		e=eta;
		r=rate;
		crate=yield;
		b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
	}
	
	private double e;
	private double r;
	private double b;
	private double crate;
	private double m;
	private double mu;
	private double mu1;
	
	public double extremeSp(double s, double s2, double t1, double t2,
	double vol)
	{
		Probnormal p=new Probnormal();
		double sig=(vol*vol);
		mu1=(b-sig*0.5);
		mu=(mu1+sig);
		m=(Math.log(s2/s));//where s2=Mo
		t1=(t1==0.0&m==0.0)?floorvalue(t1):t1==0.0?(sig*m*0.50):t1;//An approximation for zero values, which works for s2<50% of s1
		// for situations where t==0 and log s1/s2=0
		double term1=(1.0+sig/(2.0*b));
		double term2=p.ncDisfnc(e*(-m+mu*t2)/(vol*Math.sqrt(t2)));
		double term3= p.ncDisfnc(e*(-m+mu*t1)/(vol*Math.sqrt(t1)));
		double term4= p.ncDisfnc(e*(m-mu1*t2)/(vol*Math.sqrt(t2)));
		double term5= p.ncDisfnc(e*(-m-mu1*t2)/(vol*Math.sqrt(t2)));
		double term6= p.ncDisfnc(e*(m-mu1*t1)/(vol*Math.sqrt(t1)));
		double term7= p.ncDisfnc(e*(-m-mu1*t1)/(vol*Math.sqrt(t1)));
		double w=e*(s*Math.exp((b-r)*t2)*term1*term2-Math.exp(-r*(t2-t1))
		*s*Math.exp((b-r)*t2)*term1*term3+Math.exp(-r*t2)*s2*term4
		-Math.exp(-r*t2)*s2*sig/(2.0*b)*Math.exp(2.0*mu1*m/sig)
		*term5-Math.exp(-r*t2)*s2*term6+Math.exp(-r*t2)
		*s2*sig/(2.0*b)*Math.exp(2.0*mu1*m/sig)*term7);
		return w;
	}
	
	public double extremeSprev(double s, double s2, double t1, double t2,
	double vol)
	{
		Probnormal p=new Probnormal();
		double sig=(vol*vol);
		mu1=(b-sig*0.5);
		mu=(mu1+sig);
		m=(Math.log(s2/s));//where s2=Mo
		double term1=(1.0+sig/(2.0*b));
		double term2= p.ncDisfnc(e*(m-mu*t2)/(vol*Math.sqrt(t2)));
		double term3=p.ncDisfnc(e*(-m+mu1*t2)/(vol*Math.sqrt(t2)));
		double term4=p.ncDisfnc(e*(m+mu1*t2)/(vol*Math.sqrt(t2)));
		double term5=p.ncDisfnc(e*(-mu*(t2-t1))/(vol*Math.sqrt(t2-t1)));
		double term6=p.ncDisfnc(e*(mu1*(t2-t1))/(vol*Math.sqrt(t2-t1)));
		double w=-e*(s*Math.exp((b-r)*t2)*term1*term2+Math.exp(-r*t2)*s2
        *term3-Math.exp(-r*t2)*s2*sig/(2.0*b)*Math.exp(2.0*mu1*m
		/sig)*term4-s*Math.exp((b-r)*t2)*term1*term5
		-Math.exp(-r*(t2-t1))*s*Math.exp((b-r)*t2)
		*(1.0-sig/(2.0*b))*term6);
		return w;
	}
	public double floorvalue(double x) {
	    return Math.abs(x)<Csmallnumber.getSmallnumber()?Csmallnumber.getSmallnumber():x;
	}
}
 
