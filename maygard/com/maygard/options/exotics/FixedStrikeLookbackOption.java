/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

//Fixed Strike Lookback Option Valuation.
public class FixedStrikeLookbackOption {
	
	public FixedStrikeLookbackOption(double rate, double yield) {
		crate=yield;
		r=rate;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	
	private double r;
	private double crate;
	private double brate;
	
	public double callFixlook(double s,double smax, double k,
	double t, double sigma) {
		Probnormal p=new Probnormal();
		double sig=(sigma*sigma);
		double callvalue;
		if(k>smax) {
			double d1=((Math.log(s/k)+(brate+(sig*0.5))*t)/
			(sigma*Math.sqrt(t)));
			double d2=(d1-sigma*Math.sqrt(t));
			double n1=(p.ncDisfnc(d1));
			double n2=(p.ncDisfnc(d2));
			double n3=(p.ncDisfnc(d1-((2.0*brate/sigma)*Math.sqrt(t))));
			double term1=(-Math.pow((s/k),(-2.0*brate/(sigma*sigma)))
			*n3+Math.exp(brate*t)*n1);
			callvalue=(s*Math.exp((brate-r)*t)*n1-k*Math.exp(-r*t)
			*n2+s*Math.exp(-r*t)*((sigma*sigma)/
			(2.0*brate))*term1);
		} else {
			double e1=((Math.log(s/smax)+(brate+(sig*0.5))*t)/
			(sigma*Math.sqrt(t)));
			double e2=(e1-sigma*Math.sqrt(t));
			double na=(p.ncDisfnc(e1));
			double nb=(p.ncDisfnc(e2));
			double nc=(p.ncDisfnc(e1-((2.0*brate/sigma)*Math.sqrt(t))));
			double term=(-Math.pow((s/smax),(-2.0*brate/(sig)))
			*nc+Math.exp(brate*t)*na);
			callvalue=(Math.exp(-r*t)*(smax-k)+s*Math.exp((brate-r)*t)
			*na-smax*Math.exp(-r*t)*nb+s*
			Math.exp(-r*t)*((sigma*sigma)/(2.0*brate))*term);
		}
		return callvalue;
	}
	
	public double putFixlook(double s,double smin, double k,
	double t, double sigma) {
		Probnormal p=new Probnormal();
		double sig=(sigma*sigma);
		double putvalue;
		if(k<smin) {
			double d1=((Math.log(s/k)+(brate+(sig*0.5))*t)/(sigma*Math.sqrt(t)));
			double d2=(d1-sigma*Math.sqrt(t));
			double n1=(p.ncDisfnc(-d1));
			double n2=(p.ncDisfnc(-d2));
			double n3=(p.ncDisfnc(-d1+((2.0*brate/sigma)*Math.sqrt(t))));
			double term1=(Math.pow((s/k),(-2.0*brate/(sig)))
			*n3-Math.exp(brate*t)*n1);
			putvalue=(k*Math.exp(-r*t)*n2-s*Math.exp((brate-r)*t)*n1+s*Math.exp(-r*t)
			*((sigma*sigma)/(2.0*brate))*term1);
		} else {
			double d5=((Math.log(s/smin)+(brate+(sig*0.5))*t)/
			(sigma*Math.sqrt(t)));
			double d6=(d5-sigma*Math.sqrt(t));
			double na=(p.ncDisfnc(-d5));
			double nb=(p.ncDisfnc(-d6));
			double nc=(p.ncDisfnc(-d5+((2.0*brate/sigma)*Math.sqrt(t))));
			double term=(Math.pow((s/smin),(-2.0*brate/(sig)))
			*nc-Math.exp(brate*t)*na);
			putvalue=(Math.exp(-r*t)*(k-smin)-s*Math.exp((brate-r)*t)
			*na+smin*Math.exp(-r*t)*nb+s*Math.exp(-r*t)*((sig)/
			(2.0*brate))*term);
		}
		return putvalue;
	}
}

