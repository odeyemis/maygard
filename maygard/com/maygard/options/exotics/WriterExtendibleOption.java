/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.options.BlackScholes;

//Writer Extendible Option Valuation
public class WriterExtendibleOption {
	
	public WriterExtendibleOption(double exttime, double newstrike,
	double yield, double rate) {
		t2=exttime;
		x2=newstrike;
		r=rate;
		crate=yield;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	
	double t2;
	double x2;
	double z1;
	double z2;
	double rho;
	double brate;
	double crate;
	double r;
	
	BlackScholes b= new BlackScholes(crate);
	private void params(double s,double x,double volatility,
	double time) {
		z1=((Math.log(s/x2)+(brate+(volatility*volatility)*0.5)*t2)
		/(volatility*Math.sqrt(t2)));
		z2=((Math.log(s/x)+(brate+(volatility*volatility)*0.5)*time)
		/(volatility*Math.sqrt(time)));
		rho=(Math.sqrt(time/t2));
	}
	
	public double writeExtcall(double s, double x, double volatility,
	double time) {
		params(s,x,volatility,time);
		double m1=(Bivnorm.bivar_params.evalArgs(z1,-z2,-rho));
		double m2=(Bivnorm.bivar_params.evalArgs((z1-volatility
		*Math.sqrt(t2)), (-z2+volatility*Math.sqrt(time)), -rho));
		b.bscholEprice(s,x,volatility,time,r);
		double c=(b.getCalle()+s*Math.exp((brate-r)*t2)*m1-x2*Math.exp(-r*t2)
		*m2);
		return c;
	}
	
	public double writeExtput(double s, double x, double volatility,
	double time) {
		params(s,x,volatility,time);
		double m2=(Bivnorm.bivar_params.evalArgs(-z1,z2,-rho));
		double m1=(Bivnorm.bivar_params.evalArgs((-z1+volatility
		*Math.sqrt(t2)), (z2-volatility*Math.sqrt(time)), -rho));
		b.bscholEprice(s,x,volatility,time,r);
		double p=(b.getPute()+x2*Math.exp(-r*t2)*m1-s*Math.exp((brate-r)*t2)
		*m2);
		return p;
	}
}

