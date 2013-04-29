/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.stocks;

import com.maygard.core.Bivnorm;
import com.maygard.core.IntervalBisection;
import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

//Option Pricing with Roll Geske Whaley (RGW) Approximation
public class AmericanRGW extends IntervalBisection{
	
	public AmericanRGW() {
	}
	
	public AmericanRGW(double dividendval, double divitime) {
		dividend=dividendval;
		divtime=divitime;
		super.setIterations(13);
		super.setPrecisionvalue(1e-6);
	}
	
	double dividend=1.0;
	double divtime=1.0;
	double stockprice;
	double strikeprice;
	double rate;
	double time;
	double volatility;
	
	public double computeFunction(double rootinput) {
		double solution=0.0;
		double c=americanCall(rootinput,strikeprice,volatility,
		(time-divtime),rate);
		solution=((c-rootinput)+(strikeprice-dividend));
		return solution;
	}
	
	private double americanCall(double s, double x, double sigma,
	double t,double r) {
		BlackScholes bp =new BlackScholes(r);
		bp.bscholEprice(s,x,sigma,t,r);
		return bp.getCalle();
	}
	
	public double amCall(double s, double x, double sigma,double t,
	double r, double low, double high) {
		stockprice=s;
		strikeprice=x;
		volatility=sigma;
		time=t;
		rate=r;
		double callvalue;
		if(dividend<=(x*(1.0-Math.exp(-r*(t-divtime))))) {
			s=(s-(dividend*Math.exp(-r*t)));
			BlackScholes bp =new BlackScholes(r);
			bp.bscholEprice(s,x,sigma,t,r);
			return bp.getCalle();
		}
		Probnormal pn=new Probnormal();
		double si=evaluateRoot(low,high);
		double a1=(((Math.log((s-dividend*Math.exp(-rate*divtime))/x)+((rate+
		(sigma*sigma)/2)*t))/(sigma*Math.sqrt(t))));
		double a2=(a1-(sigma*Math.sqrt(t)));
		double b1= (((Math.log((s-dividend*Math.exp(-r*divtime))/si)+((rate+
		(sigma*sigma)/2)*divtime))/(sigma*Math.sqrt(divtime))));
		double b2=(b1-(sigma*Math.sqrt(divtime)));
		double norm1=pn.ncDisfnc(b1);
		double norm2=pn.ncDisfnc(b2);
		double m1=Bivnorm.bivar_params.evalArgs(a1,-(b1),
		-(Math.sqrt(divtime/t)));
		double m2=Bivnorm.bivar_params.evalArgs(a2,-(b2),
		-(Math.sqrt(divtime/t)));
		callvalue=((s-dividend*Math.exp(-r*divtime))*norm1+
		(s-dividend*Math.exp(-r*divtime))
		*m1-x*Math.exp(-r*t)*m2-(x-dividend)*Math.exp(-r*divtime)*norm2);
		return si;
	}
	
	
	//DELETE WHEN NO LONGER NEEDED
	public static void main(String[] args) {
		// Requires a reasonable idea of the buest guess for the
		// stockprice with that dividend
			AmericanRGW am = new AmericanRGW(4.0,0.25);
		// higher dividend = lower the low value, lower the divi, higher the upper guess
		double ret= am.amCall(80.0,82.0,0.30,0.3333,0.06,79.0,89.0);
		System.out.println("MIDVALUE IS =="+ret);
	}
}


