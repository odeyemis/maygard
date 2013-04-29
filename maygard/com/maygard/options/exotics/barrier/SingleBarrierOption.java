/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Probnormal;

//Computation of Single Barrier Methods
public class SingleBarrierOption {
	//Alows for negative returns,
	//i.e a cost to the seller to sell an option
	//the user program should trap negative
	//values and return zero for no value
	public SingleBarrierOption(double rate,double q,double time,
	int period ) {
		crate=q;
		r=rate;
		t=time;
		b=crate<0.0?0.0:(b=crate!=r?(r-crate):r);
		tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
		period==3?(1.0/(52.0)) :period==4?(1.0/(12.0)):0.0;
	}
	
	double b;
	double tau;
	double crate;
	double t;
	double r;
	double x1;
	double x2;
	double y1;
	double y2;
	double mu;
	double z;
	double lambda;
	double s;
	double x;
	double sigma;
	double k;
	double h;
	
	public double upIcall(double sprice, double strike,
	double volatility, double barrier,
	double rebate) {
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826));
		// barrier above asset price
		inPars(s,x,sigma,h);
		return x> h?(f1(1.0,-1.0)+f5(1.0,-1.0)):
		(f2(1.0,-1.0)-f3(1.0,-1.0)+f4(1.0,-1.0)
		+f5 (1.0,-1.0));
	}
	
	public double downIcall(double sprice, double strike,
	double volatility, double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826));
		// barrier below asset price
		inPars(s,x,sigma,h);
		return x>h?(f3(1.0,1.0)+f5(1.0,1.0)):
		(f1(1.0,1.0)-f2(1.0,1.0)+f4(1.0,1.0)
		+f5 (1.0,1.0));
	}
	
	public double downIput(double sprice, double strike,
	double volatility, double
	barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h= (barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826));
		inPars(s,x,sigma,h);
		return x<h?(f1(-1.0,1.0)+f5(-1.0,1.0)):
		(f2(-1.0,1.0)-f3(-1.0,1.0)+f4(-1.0,1.0)
		+f5 (-1.0,1.0));
	}
	
	public double upIput(double sprice, double strike,
	double volatility, double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826));
		inPars(s,x,sigma,h);
		return x<h?(f3(-1.0,-1.0)+f5(-1.0,-1.0)):(f1(-1.0,-1.0)
		-f2(-1.0,-1.0)+f4 (-1.0,-1.0) +f5 (-1.0,-1.0));
	}
	
	public double downOcall(double sprice, double strike,
	double volatility, double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826));
		outPars(s,x,sigma,h);
		return ((s<=h)&&(t!=0.0))?k: x<h?(f2(1.0,1.0)-f4(1.0,1.0)
		+f6(1.0,1.0)):
		(f1(1.0,1.0)-f3(1.0,1.0)+f6 (1.0,1.0));
	}
	
	public double upOput(double sprice, double strike, double volatility,
	double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826));
		outPars(s,x,sigma,h);
		return ((s>=h)&&(t!=0.0))?k:x<h?(f1(-1.0,-1.0)-f3(-1.0,-1.0)
		+f6(-1.0,-1.0)):(f2(-1.0,-1.0) -f4(-1.0,-1.0)+f6 (-1.0,-1.0));
	}
	
	public double upOcall(double sprice, double strike,
	double volatility, double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826));
		outPars(s,x,sigma,h);
		return ((s>=h)&&(t!=0.0))?k: x<h?(f1(1.0,-1.0)-f2(1.0,-1.0)
		+f3(1.0,-1.0)-f4(1.0,-1.0)+f6(1.0,-1.0)):f6 (1.0,-1.0);
	}
	
	public double downOput(double sprice, double strike,
	double volatility, double barrier, double rebate)
	{
		s=sprice;
		x=strike;
		sigma=volatility;
		k=rebate;
		h=(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826));
		outPars(s,x,sigma,h);
		return ((s<=h)&&(t!=0.0))?k: x>h?(f1(-1.0,1.0)
		-f2(-1.0,1.0)+f3(-1.0,1.0)-f4
		(-1.0,1.0) +f6(-1.0,1.0)):f6 (-1.0,1.0);
	}
	
	private void inPars(double s,double x,double sigma,double h) {
		mu=((b-(sigma*sigma)/2.0)/(sigma*sigma));
		x1=(((Math.log(s)-Math.log(x))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		x2=(((Math.log(s)-Math.log(h))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		y1=(((Math.log(h*h)-Math.log(s*x))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		y2=(((Math.log(h)-Math.log(s))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
	}
	
	private void outPars(double s, double x, double sigma, double h) {
		mu=((b-(sigma*sigma)/2.0)/(sigma*sigma));
		x1=(((Math.log(s)-Math.log(x))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		x2=(((Math.log(s)-Math.log(h))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		y1=(((Math.log(h*h)-Math.log(s*x))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		y2=(((Math.log(h)-Math.log(s))/(sigma*Math.sqrt(t)))+((1.0+mu)
		*sigma*Math.sqrt(t)));
		lambda=(Math.sqrt((mu*mu)+((2.0*r)/(sigma*sigma))));
		z=(((Math.log(h)-Math.log(s))/(sigma*Math.sqrt(t)))+lambda*sigma*Math.sqrt(t));
	}
	
	private double f1(double phi,double eta)
	{
		Probnormal p=new Probnormal();
		return (phi*s*Math.exp((b-r)*t)*p.ncDisfnc(phi*x1)-phi*x*Math.exp(-r*t)
		*p.ncDisfnc (phi*x1-phi*sigma*Math.sqrt(t)));
	}
	
	private double f2 (double phi,double eta)
	{
		Probnormal p=new Probnormal();
		return (phi*s*Math.exp((b-r)*t)*p.ncDisfnc(phi*x2)-phi*x*Math.exp(-r*t)
		*p.ncDisfnc (phi*x2-(phi*sigma*Math.sqrt(t))));
	}
	
	private double f3(double phi,double eta)
	{
	Probnormal p=new Probnormal();
		return (phi*s*Math.exp((b-r)*t)*Math.pow((h/s),(2.0*(mu+1.0)))
		*p.ncDisfnc(eta*y1)
		-phi*x*Math.exp(-
		r*t) *Math.pow((h/s),(2.0*mu))*p.ncDisfnc(eta*y1-eta*sigma*Math.sqrt(t)));
	}
	
	private double f4(double phi, double eta)
	{
		Probnormal p=new Probnormal();
		return (phi*s*Math.exp((b-r)*t)*Math.pow((h/s),(2.0*(mu+1.0)))
		*p.ncDisfnc(eta*y2)-phi*x*Math.exp(-
		r*t)*Math.pow((h/s),(2.0*mu))*p.ncDisfnc(eta*y2-eta*sigma*Math.sqrt(t)));
	}
	
	private double f5(double phi,double eta)
	{ 
		Probnormal p=new Probnormal();
		return(k*Math.exp(-r*t)*(p.ncDisfnc(eta*x2-eta*sigma*Math.sqrt(t))
		-Math.pow((h/s),(2*mu))
		*p.ncDisfnc(eta*y2-eta*sigma*Math.sqrt(t))));
	}
	
	private double f6(double phi,double eta)
	{
		Probnormal p=new Probnormal();
		return(k*(Math.pow((h/s),(mu+lambda))*p.ncDisfnc(eta*z)
		+Math.pow((h/s),
		(mu-lambda))*p.ncDisfnc(eta*z
		-2.0*eta*lambda*sigma*Math.sqrt(t))));
	}
}

