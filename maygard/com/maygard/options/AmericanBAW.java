/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.core.IntervalBisection;
import com.maygard.core.Probnormal;

//Barone-Adesi Whaley quadratic approximation
public class AmericanBAW extends IntervalBisection {
	
	public AmericanBAW() {
	}
	
	public AmericanBAW(double carryrate) {
		crate=carryrate;
		super.setIterations(30);
		super.setPrecisionvalue(1e-6);
	}
	
	double crate=0.0;
	double brate=0.0;
	double d1;
	double q2;
	double su;
	double strike;
	double rate;
	double volatility;
	double time;
	double stockprice;
	double european;
	double n;
	double m;
	double amcall;
	double K;
	double ss;
	int v;
	
	BlackScholes bp =new BlackScholes(crate);
	Probnormal p=new Probnormal();
	
	public double computeFunction(double rootvalue) {
		double stockvalue=0.0;
		bp.bscholEprice(rootvalue,strike,volatility,time,rate);
		double ds=(Math.log(rootvalue/strike)+(brate+
		(((volatility*volatility)*0.5)
		*time))/(volatility*Math.sqrt(time)));
		double c=bp.getCalle();
		stockvalue=((ss-strike)-(c+((1.0-Math.exp(brate-rate)*time)
		*p.ncDisfnc(ds))*(rootvalue/q2)));
		return stockvalue;
	}
	
	public double Si(double s,double x, double sigma, double t, double r) {
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
		stockprice=s;
		strike=x;
		volatility=sigma;
		time=t;
		rate=r;
		BlackScholes bps =new BlackScholes(crate);
		bps.bscholEprice(s,x,sigma,t,r);// Black ’76 model
		double european=bps.getCalle();
		if(brate>=r) {
		    return bps.getCalle();
		}
		Probnormal p=new Probnormal();
		ss=startvalue(s,x,sigma,t,r);
		//get an initial point for range
		K=(1.0-Math.exp(-r*t));
		double k=2.0*r/((sigma*sigma)*(1.0-Math.exp(-r*t)));
		q2=((-(n-1)+Math.sqrt((n-1*n-1)+4.0*k))*0.5);
		double sev=evaluateRoot((ss-15.0),(ss+15.0));
		double endval=((Math.log(sev/strike)+(brate+
		(((volatility*volatility)*0.5)
		*time))/(volatility*Math.sqrt(time))));
		double a2=((sev/q2)*(1.0-Math.exp((brate-rate)*time))
		*p.ncDisfnc(endval));
		amcall=s<=sev?(european+(a2*Math.pow((s/sev),q2))):(s-x);
		return amcall;
	}
	private double startvalue(double s, double x, double sigma,
	double t, double r) {
		m=(2*r/(sigma*sigma));
		n=(2*brate/(sigma*sigma));
		double q2u=(-(n-1.0)+Math.sqrt((n-1.0)*(n-1.0)+4.0*m))*0.5;
		double su=x/(1.0-1.0/q2u);
		double h2=-(brate*t+2.0*sigma*Math.sqrt(t))*x/(su-x);
		double si=x+(su-x)*(1.0-Math.exp(h2));
		return si;
	}
	
	public double amCall(double s, double x, double sigma,
	double t, double r) {
	    return Si(s,x,sigma,t,r);
	}
	
	public double amPut(double s, double x, double sigma,
	double t, double r) {
	    return Si(x,s,sigma,t,r-brate);
	}
	
	//delete if not needed
	public static void main(String[] args) {
	AmericanBAW a=new AmericanBAW(0.0);
	double retc=a.amCall(90.0,100.0,0.15,0.10,0.10);
	System.out.println(retc);
	}
}

