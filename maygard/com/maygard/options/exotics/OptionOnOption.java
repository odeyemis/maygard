/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.core.NewtonRaphson;
import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

//Option on Option Valuation
public class OptionOnOption extends NewtonRaphson{
	
	public OptionOnOption(double yield,double rate, double strikeop) {
		crate=yield;
		r=rate;
		strike2=strikeop;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
		accuracy(1e-9,10);
	}
	
	private double crate;
	private double brate;
	private double r;
	private double strike;
	private double sigma;
	private double time;
	private double maturity;
	private double strike2;
	private double timediff;
	private double y;
	private double z;
	private double y2;
	private double z2;
	private double rho;
	private double ccpayoff=0.0;
	private double pcpayoff=0.0;
	private double cppayoff=0.0;
	private double pppayoff=0.0;
	private int type;
	public double getpcC() {
	return ccpayoff;
	}
	public double getppC() {
	return pcpayoff;
	}
	public double getpcP() {
	return cppayoff;
	}
	public double getppP() {
	return pppayoff;
	}
	public void parAms(double s, double x, double volatility, double t,
	double tmaturity) {
		strike=x;
		sigma=volatility;
		time=t;
		maturity=tmaturity;
		timediff=(tmaturity-t);
		double sval=newtraph(x);
		y=(Math.log(s/sval)+(brate+(sigma*sigma)*0.5)*(time))
		/(sigma*Math.sqrt(time));
		y2=(y-sigma*Math.sqrt(time));
		z=(Math.log(s/x)+(brate+(sigma*sigma)*0.5)*(tmaturity))
		/(sigma*Math.sqrt(tmaturity));
		z2=(z-sigma*Math.sqrt(tmaturity));
		rho=Math.sqrt(t/tmaturity);
	}
	
	public double callCall(double s, double x,double volatility,
	double t, double tmaturity) {
		type=1;
		BlackScholes b= new BlackScholes(crate);
		b.bscholEprice(s,x,volatility,tmaturity,r);
		ccpayoff=Math.max((b.getCalle()-strike2),0);
		Probnormal p=new Probnormal();
		parAms(s,x,volatility,t,tmaturity);
		double m1=(Bivnorm.bivar_params.evalArgs(z,y,rho));
		double m2=(Bivnorm.bivar_params.evalArgs(z2,y2,rho));
		double value=((s*Math.exp((brate-r)*maturity)*m1)
		-(strike*Math.exp(-r*maturity)*m2)
		-(strike2*Math.exp(-r*time)*p.ncDisfnc(y2)));
		return value;
	}
	
	public double putCall(double s, double x,double volatility,
	double t, double tmaturity) {
		type=1;
		BlackScholes b= new BlackScholes(crate);
		b.bscholEprice(s,x,volatility,tmaturity,r);
		pcpayoff=Math.max((strike2-b.getCalle()),0);
		Probnormal p=new Probnormal();
		parAms(s,x,volatility,t,tmaturity);
		double m1=(Bivnorm.bivar_params.evalArgs(z2,-y2,-rho));
		double m2=(Bivnorm.bivar_params.evalArgs(z,-y,-rho));
		double fact1=(strike*Math.exp(-r*maturity)*m1);
		double fact2=(s*Math.exp((brate-r)*maturity)*m2);
		double fact3=(strike2*Math.exp(-r*time)*p.ncDisfnc(-y2));
		double value=(fact1-fact2+fact3);
		return value;
	}
	
	public double callPut(double s, double x,double volatility,
	double t, double tmaturity) {
		type=0;
		BlackScholes b= new BlackScholes(crate);
		b.bscholEprice(s,x,volatility,tmaturity,r);
		cppayoff=Math.max((b.getPute()-strike2),0);
		Probnormal p=new Probnormal();
		parAms(s,x,volatility,t,tmaturity);
		double m1=(Bivnorm.bivar_params.evalArgs(-z2,-y2,rho));
		double m2=(Bivnorm.bivar_params.evalArgs(-z,-y,rho));
		double fact1=(strike*Math.exp(-r*maturity)*m1);
		double fact2=(s*Math.exp((brate-r)*maturity)*m2);
		double fact3=(strike2*Math.exp(-r*time)*p.ncDisfnc(-y2));
		double value=(fact1-fact2-fact3);
		return value;
	}
	
	public double putPut(double s, double x,double volatility,
	double t, double tmaturity) {
		type=0;
		BlackScholes b= new BlackScholes(crate);
		b.bscholEprice(s,x,volatility,tmaturity,r);
		pppayoff=Math.max((strike2-b.getPute()),0);
		Probnormal p=new Probnormal();
		parAms(s,x,volatility,t,tmaturity);
		double m1=(Bivnorm.bivar_params.evalArgs(-z,y,-rho));
		double m2=(Bivnorm.bivar_params.evalArgs(-z2,y2,-rho));
		double value=((s*Math.exp((brate-r)*maturity)*m1)
		-(strike*Math.exp(-r*maturity)*m2)
		+(strike2*Math.exp(-r*time)*p.ncDisfnc(y2)));
		return value;
	}
	
	
	public double newtonroot(double rootvalue)
	{ 
		BlackScholes b= new BlackScholes(crate);
		double solution =0.0;
		b.bscholEprice(rootvalue,strike,sigma,timediff,r);
		solution=type==1?(b.getCalle()-strike2):(b.getPute());
		return solution;
	}
}
