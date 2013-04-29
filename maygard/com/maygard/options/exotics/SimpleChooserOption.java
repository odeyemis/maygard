/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

//Simple Chooser Option Valuation
public class SimpleChooserOption {
	
	public SimpleChooserOption(double yield, double choicetime) {
		crate=yield;
		time=choicetime;
	}
	
	private double crate;
	private double time;
	private double brate=0.0;
	public double simpChose(double s, double x, double tmaturity,
	double rate, double volatility)
	{
		BlackScholes b=new BlackScholes(crate);
		Probnormal p=new Probnormal();
		b.bscholEprice(s,x,volatility,tmaturity,rate);
		double call=b.getCalle();
		double put=b.getPute();
		brate=crate==0.0?0.0:(brate=crate!=rate?(rate-crate):rate);
		double d;
		double y;
		d=((Math.log(s/x)+(brate+(volatility*volatility)*0.5)
		*tmaturity)/(volatility*Math.sqrt(tmaturity)));
		y=((Math.log(s/x)+(brate*tmaturity)+((volatility*volatility)
		*time)*0.5)/(volatility*Math.sqrt(time)));
		double probd=p.ncDisfnc(d);
		double probdmv=p.ncDisfnc(d-volatility*Math.sqrt(tmaturity));
		double probdmy=p.ncDisfnc(-y+volatility*Math.sqrt(time));
		double proby=p.ncDisfnc(-y);
		double w=(s*Math.exp((brate-rate)*tmaturity)*probd-x*Math.exp
		(-rate*tmaturity)*probdmv-s*Math.exp
		((brate-rate)*tmaturity)*proby+x*
		Math.exp(-rate*tmaturity)*probdmy);
		return w;
	}
}