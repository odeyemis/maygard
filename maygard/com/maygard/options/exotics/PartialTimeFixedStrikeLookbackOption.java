/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.core.Probnormal;

//Partial Time Fixed Strike Look back Option Valuation.
public class PartialTimeFixedStrikeLookbackOption {
	
	public PartialTimeFixedStrikeLookbackOption(double rate, double yield) {
		crate=yield;
		r=rate;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	private double r;
	private double crate;
	private double brate;
	private double d1;
	private double d2;
	private double e1;
	private double e2;
	private double f1;
	private double f2;
	private double sig;
	
	private void params(double s,double x, double t1, double t2,
	double sigma) {
		sig=(sigma*sigma);
		d1=((Math.log(s/x)+(brate+sig*0.5)*t2)/(sigma*Math.sqrt(t2)));
		e1=(((brate+sig*0.5)*(t2-t1))/(sigma*Math.sqrt(t2-t1)));
		f1=((Math.log(s/x)+(brate+sig*0.5)*t1)/(sigma*Math.sqrt(t1)));
		d2=(d1-(sigma*Math.sqrt(t2)));
		e2=(e1-(sigma*Math.sqrt(t2-t1)));
		f2=(f1-(sigma*Math.sqrt(t1)));
	}
	
	public double partFxCall(double s,double x, double t1,
	double t2,double sigma) {
		Probnormal p=new Probnormal();
		params(s,x,t1,t2,sigma);
		double n1=p.ncDisfnc(d1);
		double n2=p.ncDisfnc(d2);
		double n3=p.ncDisfnc(f1);
		double n4=p.ncDisfnc(-e2);
		double fac1=(d1-2.0*brate*Math.sqrt(t2)/sigma);
		double fac2=(-f1+2.0*brate*Math.sqrt(t1)/sigma);
		double fac3=(-Math.pow((s/x),(-2.0*brate/(sigma*sigma))));
		double m1=(Bivnorm.bivar_params.evalArgs(fac1,fac2,
		(-Math.sqrt(t1/t2))));
		double m2=(Bivnorm.bivar_params.evalArgs(e1,d1,
		(Math.sqrt(1.0-t1/t2))));
		double m3=(Bivnorm.bivar_params.evalArgs(-e1,d1,
		(-Math.sqrt(1.0-t1/t2))));
		double m4=(Bivnorm.bivar_params.evalArgs(f2,-d2,
		(-Math.sqrt(t1/t2))));
		double term=fac3*m1+Math.exp(brate*t2)*m2;
		double factor1=s*Math.exp((brate-r)*t2)*n1-Math.exp(-r*t2)*x*
		n2+s*Math.exp(-r*t2)*sig
		/(2.0*brate)*term;
		double factor2=-s*Math.exp((brate-r)*t2)*m3-x*Math.exp(-r*t2)*m4+Math.exp
		(-brate*(t2-t1))*(1.0-sig/(2.0*brate))*s*Math.exp
		((brate-r)*t2)*n3*n4;
		double c=(factor1+factor2);
		return c;
	}
	
	public double partFxPut(double s,double x, double t1,
	double t2,double sigma) {
		Probnormal p=new Probnormal();
		params(s,x,t1,t2,sigma);
		double n1=p.ncDisfnc(-d1);
		double n2=p.ncDisfnc(-d2);
		double n3=p.ncDisfnc(-f1);
		double n4=p.ncDisfnc(e2);
		double fac1=(-d1+2.0*brate*Math.sqrt(t2)/sigma);
		double fac2=(f1-2.0*brate*Math.sqrt(t1)/sigma);
		double fac3=(Math.pow((s/x),(-2.0*brate
		/(sigma*sigma))));
		double m1=(Bivnorm.bivar_params.evalArgs(fac1,fac2,
		(-Math.sqrt(t1/t2))));
		double m2=(Bivnorm.bivar_params.evalArgs(-e1,-d1,
		(Math.sqrt(1.0-t1/t2))));
		double m3=(Bivnorm.bivar_params.evalArgs(e1,-d1,
		(-Math.sqrt(1.0-t1/t2))));
		double m4=(Bivnorm.bivar_params.evalArgs(-f2,d2,
		(-Math.sqrt(t1/t2))));
		double term=fac3*m1-Math.exp(brate*t2)*m2;
		double factor1=x*Math.exp(-r*t2)*n2-s*Math.exp((brate-r)*t2)*n1+s*Math.exp
		(-r*t2)*sig/(2.0*brate)*term;
		double factor2=s*Math.exp((brate-r)*t2)*m3+x*Math.exp(-r*t2)*m4-Math.exp
		(-brate*(t2-t1))*(1.0-sig/(2.0*brate))*s*Math.exp
		((brate-r)*t2)*n3*n4;
		double c=(factor1+factor2);
		return c;
	}
}
