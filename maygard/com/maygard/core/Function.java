/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

//Class dealing with basic functions
public final class Function {
	
	static double factaray[]=new double[200];
	static double vals[]={76.18009172947146,-86.50532032941677,
	24.01409824083091,-1.231739572450155,0.1208650973866179e-2,
	-0.5395239384953e-5};// Lanczos coefficient values
	
	protected static double lgamma(double x)//returns the log of the values.. for large values
	{
		return x>1? Math.log(x+5.5)*(x+0.5)-(x+5.5)+Math.log(coeffs(x)
		*Math.sqrt(2*Math.PI)/x):(x<=1.0&&x>0?lgamma(x+1)-Math.log(x):
		(x<0?Math.log(reflect(x)):Double.NaN));
	}
	
	public static double gamma(double x) // implements the algorithm
	{
		double g=0;
		if(x>1) {
		g=(Math.exp(Math.log(x+5.5)*(x+0.5)-(x+5.5))*coeffs(x)*Math.sqrt(2*Math.PI)/x);
		} else
		if(x<=1.0&&x>0) {
		g=(gamma(x+1)/x);
		} else
		if(x<0) {
		g=(Math.PI)/(gamma(1-x)*(Math.sin(Math.PI*x)));
		}
		return g;
	}
	
	public static double reflect(double x)//negative non-integer values only
	{
		return (Math.PI)/(gamma(1-x)*(Math.sin(Math.PI*x)));
	}
	
	private static double coeffs(double x) {
		double ans=1.000000000190015;
		double term=x;
		for(int i=0;i<6;i++) {
			term+=1;
			ans+=vals[i]/term;
		}
		return ans;
	}
	
	public static double factorial( int x) {
		//maximum to prevent overflow 170 //
		return x <2?1:(x>40?Math.exp(lgamma(x+1.0)):x*factorial(x-1));
	}
	public static double binom(int n, int k) {
	// max 100 n 17 k Binomial coefficient no of ways thatk things can be selected from n items//
	return Math.floor( Math.exp(lgamma(n+1.0)-lgamma(n-k+1.0)-lgamma
	(k+1.0)));
	}
	public static double beta( double x, double y) {
	return Math.exp(lgamma(x)+lgamma(y)-lgamma(x+y));
	}
	public static double betainv( double x, double y) {
	// returns same as 1/beta //
	return Math.exp(lgamma(x+y)-lgamma(x)-lgamma(y));
	}
	public static double lbeta(double x, double y) {
	return (lgamma(x+y)-lgamma(x)-lgamma(y));
	}
	public static double logbeta(double x, double y) {
	return (lgamma(x)+lgamma(y)-lgamma(x+y));
	}
}

