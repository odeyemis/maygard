/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.core.Probnormal;

//Partial Time Floating Strike Lookback Option Valuation.
public class PartialTimeFloatingStrikeLookbackOption {
		
	public PartialTimeFloatingStrikeLookbackOption(double rate, double yield, double l) {
		crate=yield;
		r=rate;
		lambda=l;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	
	private double r;
	private double crate;
	private double brate;
	private double lambda;
	private double d1;
	private double d2;
	private double e1;
	private double e2;
	private double f1;
	private double f2;
	private double sig;
	private double g1;
	private double g2;
	
	private void params(double s,double sminax, double t1, double t2,
	double sigma) {
		sig=(sigma*sigma);
		d1=((Math.log(s/sminax)+(brate+sig*0.5)*t2)/(sigma*Math.sqrt(t2)));
		e1=(((brate+sig*0.5)*(t2-t1))/(sigma*Math.sqrt(t2-t1)));
		f1=((Math.log(s/sminax)+(brate+sig*0.5)*t1)/(sigma*Math.sqrt(t1)));
		d2=(d1-(sigma*Math.sqrt(t2)));
		e2=(e1-(sigma*Math.sqrt(t2-t1)));
		f2=(f1-(sigma*Math.sqrt(t1)));
		g1=(Math.log(lambda)/(sigma*Math.sqrt(t2)));
		g2=(Math.log(lambda)/(sigma*Math.sqrt(t2-t1)));
	}
	
	public double partxCall(double s,double smin, double t1,
	double t2,double sigma) {
		Probnormal p=new Probnormal();
		params(s,smin,t1,t2,sigma);
		double n1=p.ncDisfnc(d1-g1);
		double n2=p.ncDisfnc(d2-g1);
		double n3=p.ncDisfnc(e2-g2);
		double n4=p.ncDisfnc(-f1);
		double m1=(Bivnorm.bivar_params.evalArgs(-f1+2.0*brate*
				Math.sqrt(t1)/sigma,-d1+2.0*brate*Math.sqrt(t2)/sigma-g1,
				Math.sqrt(t1/t2)));
		double m2=(Bivnorm.bivar_params.evalArgs((-d1-g1),(e1+g2),
		(-Math.sqrt(1.0-t1/t2))));
		double m3=(Bivnorm.bivar_params.evalArgs(-d1+g1,e1-g2,-Math.sqrt
		(1.0-t1/t2)));
		double m4=(Bivnorm.bivar_params.evalArgs(-f2,d2-g1,-Math.sqrt
		(t1/t2)));
		double term=(Math.pow((s/smin),((-2.0*brate/sig)))*m1-Math.exp(brate*t2)*
				Math.pow(lambda,(2.0*brate/sig))*m2);
		double fact1=s*Math.exp((brate-r)*t2)*n1-lambda*smin*Math.exp(-r*t2)*n2;
		double fact2=Math.exp(-r*t2)*((sigma*sigma)/(2.0*brate))*lambda*s*
		term+s*Math.exp((brate-r)*t2)*m3;
		double fact3=Math.exp(-r*t2)*lambda*smin*m4-Math.exp(-brate*(t2-t1))*Math.exp
		((brate-r)*t2)*(1.0+sig/(2.0*brate))*lambda*s*n3*n4;
		double op=(fact1+fact2+fact3);
		return op;
	}
	
	public double partxPut(double s,double smax, double t1, double t2,
	double sigma) {
		Probnormal p=new Probnormal();
		params(s,smax,t1,t2,sigma);
		double n1=p.ncDisfnc(-d2+g1);
		double n2=p.ncDisfnc(-d1+g1);
		double n3=p.ncDisfnc(-e2+g2);
		double n4=p.ncDisfnc(f1);
		double m1=(Bivnorm.bivar_params.evalArgs(f1-2.0*brate*Math.sqrt(t1)/
		sigma,d1-2.0*brate*Math.sqrt(t2) /sigma+g1,Math.sqrt(t1/t2)));
		double m2=(Bivnorm.bivar_params.evalArgs(d1+g1,-e1-g2,-Math.sqrt
		(1.0-t1/t2)));
		double m3=(Bivnorm.bivar_params.evalArgs(d1-g1,-e1+g2,-Math.sqrt
		(1.0-t1/t2)));
		double m4=(Bivnorm.bivar_params.evalArgs(f2,-d2+g1,-Math.sqrt
		(t1/t2)));
		double term=(Math.pow((s/smax),((-2.0*brate/sig)))*m1-Math.exp(brate*t2)*
				Math.pow(lambda, (2.0*brate/sig))*m2);
		double fact1=lambda*smax*Math.exp(-r*t2)*n1-s*Math.exp
		((brate-r)*t2)*n2;
		double fact2=-Math.exp(-r*t2)*((sigma*sigma)/(2.0*brate))*lambda*s*
		term-s*Math.exp((brate-r)*t2)*m3;
		double fact3=-Math.exp(-r*t2)*lambda*smax*m4+Math.exp(-brate*(t2-t1))*
				Math.exp((brate-r)*t2)*(1.0+sig/(2.0*brate))*lambda*
		s*n3*n4;
		double op=(fact1+fact2+fact3);
		return op;
	}
}

