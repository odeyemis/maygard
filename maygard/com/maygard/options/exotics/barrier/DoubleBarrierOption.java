/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

//Double Barrier Option
public class DoubleBarrierOption {

	public DoubleBarrierOption(double rate,double q,double time, double volatility,
	int period ) {
		crate=q;
		r=rate;
		t=time;
		//0 continuos,1 hourly,2 daily,3 weekly, 4 monthly
		sigma=volatility;
		tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
		period==3?(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
		b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
	}
	
	double b;
	double tau;
	double crate;
	double t;
	double r;
	double s;
	double u;
	double x;
	double l;
	double sigma;
	double f;
	double delta1;
	double delta2;
	
	public double uoDoc(double stock, double strike, double up,
	double low,double delt1, double delt2 )
	{
		s=stock;
		x=strike;
		u= up>s? (up*Math.exp(Math.sqrt(tau)*sigma*0.5826)):up;
		l= low<s? (low*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):low;
		if(s>=u|s<=l)
		return 0.0;// no need to continue
		delta1=delt1;
		delta2=delt2;
		double mu2;
		int n=0;
		double sum=0.0;
		double sum1=0.0;
		double c;
		f=(u*Math.exp(delta1*t));
		for(n=-5;n<6;n++)
		{
		mu2=2.0*n*((delta1-delta2)/(sigma*sigma));
		sum+=Math.pow((Math.pow(u,n)/Math.pow(l,n)),mu1(n)) *Math.pow((l/s),mu2)
		*(N(d1(n))-N(d2(n)))-Math.pow((Math.pow(l,(n+1.0))
		/(Math.pow(u,n)*s)),mu3(n))*(N(d3(n))-N(d4(n)));
		sum1+=Math.pow((Math.pow(u,n)/Math.pow(l,n)),(mu1(n)-2.0))*Math.pow((l/s),mu2)
		*(N(d1(n)-sigma*Math.sqrt(t))-N(d2(n)-sigma*Math.sqrt(t)))
		-Math.pow(Math.pow(l,(n+1.0))/(Math.pow(u,n)*s),mu3(n)-2.0)
		*(N(d3(n)-sigma*Math.sqrt(t))-N(d4(n)-sigma*Math.sqrt(t)));
		}
		c=s*Math.exp((b-r)*t)*sum-x*Math.exp(-r*t)*sum1;
		return c;
	}
	
	public double uoDop(double stock, double strike, double up,
	double low,double delt1, double delt2 )
	{
		s=stock;
		x=strike;
		delta1=delt1;
		delta2=delt2;
		u= up>s? (up*Math.exp(Math.sqrt(tau)*sigma*0.5826)):up;
		l= low<s? (low*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):low;
		if(s>=u|s<=l)
		return 0.0;// no need to continue
		double mu2;
		int n=0;
		double sum=0.0;
		double sum1=0.0;
		double p;
		f=(l*Math.exp(delta1*t));
		for(n=-5;n<6;n++)
		{
		mu2=2.0*n*((delta1-delta2)/(sigma*sigma));
		sum1+=Math.pow((Math.pow(u,n)/Math.pow(l,n)),mu1(n))*Math.pow((l/s),mu2)*(N(d2(n))
		-N(d1(n)))-Math.pow((Math.pow(l,(n+1.0))/(Math.pow(u,n)*s)),mu3(n))
		*(N(d4(n))-N(d3(n)));
		sum+=Math.pow((Math.pow(u,n)/Math.pow(l,n)),(mu1(n)-2.0))*Math.pow((l/s),mu2)
		*(N(d2(n)-sigma*Math.sqrt(t))-N(d1(n)-sigma*Math.sqrt(t)))-Math.pow
		(Math.pow(l,(n+1.0))/(Math.pow(u,n)*s),mu3(n)-2.0)*(N(d4(n)
		-sigma*Math.sqrt(t))-N(d3(n)-sigma*Math.sqrt(t)));
		}
		p=-s*Math.exp((b-r)*t)*sum1+x*Math.exp(-r*t)*sum;
		return p;
	}
	
	public double uiDinc(double stock, double strike, double up,
	double low,double delt1, double delt2 )
	{
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		return (b.getCalle()-uoDoc(stock,strike,up,low,delt1,delt2));
	}
	
	public double uiDinp(double stock, double strike, double up,
	double low,double delt1, double delt2 )
	{
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		double outvalue=uoDop(stock,strike,up,low,delt1,delt2);
		return b.getPute()-outvalue;
	}
	
	private double d1(double n)
	{
		double dvalue=(Math.log((s*Math.pow(u,2.0*n))/(x*Math.pow(l,2.0*n)))
		+((b+((sigma*sigma)*0.5))*t))/(sigma*Math.sqrt(t));
		return dvalue;
	}
	
	private double d2(double n)
	{
		double dvalue=(Math.log((s*Math.pow(u,2.0*n))/(f*Math.pow(l,2.0*n)))
		+((b+((sigma*sigma)*0.5))*t))/(sigma*Math.sqrt(t));
		return dvalue;
	}
	
	private double d3(double n)
	{
		double dvalue=(Math.log((Math.pow(l,2.0*n+2.0))/(x*s*Math.pow(u,2.0*n)))
		+((b+((sigma*sigma)*0.5))*t))/(sigma*Math.sqrt(t));
		return dvalue;
	}
	
	private double d4(double n)
	{
		double dvalue=(Math.log((Math.pow(l,2.0*n+2.0))/(f*s*Math.pow(u,2.0*n)))
		+((b+((sigma*sigma)*0.5))*t))/(sigma*Math.sqrt(t));
		return dvalue;
	}
	
	private double mu1(double n)
	{
		double mvalue= 2.0*(b-delta2-n*(delta1-delta2))/(sigma*sigma)+1.0;
		return mvalue;
	}
	
	private double mu3(double n)
	{
		double mvalue=2.0*(b-delta2+n*(delta1-delta2))/(sigma*sigma)+1.0;
		return mvalue;
	}
	
	private double N(double d)
	{
		Probnormal p=new Probnormal();
		double probvalue=d>(6.95)?1.0:d<(-6.95)?0.0:p.ncDisfnc(d);
		//restrict the range of cdf values
		return probvalue;
	}
}
