/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.ir.PresentValue;

public class OptionLimits {
	
		
	public OptionLimits() {
	}
	
	public double lowerlimitCall(double stockprice,double rate,
	double time,double strikeprice)
	{
	    return Math.max( (stockprice-PresentValue.pVcont(rate,time,strikeprice)),0);
	}
	
	public double[] lowerlimitCall(double[]stockprice,double rate,
	double time, double[] strikeprice)
	{
		int indx=0;
		double[] lowervalues = new double[stockprice.length];
		for(double s:stockprice)
		{
		lowervalues[indx]=Math.max((s-PresentValue.pVcont(rate,time,
		strikeprice[indx])),0);
		indx++;
		}
		return lowervalues;
	}
	
	public double lowerlimitPut(double stockprice,double
	rate,double time,double strikeprice)
	{
	    return Math.max( (PresentValue.pVcont(rate,time,strikeprice)-stockprice),0);
	}
	
	public double[] lowerlimitPut(double[]stockprice,double rate,double
	time, double[] strikeprice)
	{
		int indx=0;
		double[] lowervalues = new double[stockprice.length];
		for(double s:stockprice)
		{
			lowervalues[indx]=Math.max((PresentValue.pVcont(rate,time,
			strikeprice[indx])-s),0);
			indx++;
		}
		return lowervalues;
	}
}

