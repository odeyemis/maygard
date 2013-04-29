/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

//Class to compute some probability distributions
public class Probnormal {
		
	private double bivareprob=0.0;
	
	//Based on the normalised data in general, z=x-u/s
	public double ncDisfnc(double zval)//THE normal cumulative distribution
	{
		zval= zval>35.0?35.0:zval<-35.0?-35.0:zval;//Reasonable limits for very large values
		Igamma ig=new Igamma();
		return 0.5*(1+ig.errf(zval/Math.sqrt(2)));
	}
	
	public double npdfDisfnc(double zval)// normal pdf
	{
	    return (Math.exp(-Math.pow(zval,2)/2)/Math.sqrt(2*Math.PI));
	}
	
	public double ndfP(double zval)//normal (cumulative) distribution function (normalised form)..usual
	{ 
		Igamma ig=new Igamma();
	    return 0.5*(ig.errf(zval/Math.sqrt(2)));
	}
	
	public double ncumRange(double x1, double x2)//Prob of a normal variable having a value in the range x1..x2
	{ 
		Igamma ig=new Igamma();
	    return (0.5*(ig.errf(x1/Math.sqrt(2.0))-ig.errf(x2/Math.sqrt(2.0))));
	}
	
	public double nhazard(double zval)//normal hazard function
	{
	    return npdfDisfnc(zval)/ncDisfnc(-zval);
	}
	
	public double cnhazard(double zval)//normal cumulative hazard function
	{
	    return -Math.log(1-ncDisfnc(zval));
	}
	
	public double nsurvfnc(double zval)//normal survival function
	{
	    return 1-ncDisfnc(zval);
	}
	
	public double probnsd(double n)//The probability that a measurement falls within n sd’s
	{
		Igamma ig=new Igamma();
	    return ig.errf(n/Math.sqrt(2.0));
	}
	
	public double cBiv(double a, double b, double p)
	{
	    return 1.0;
	}
	
	public double cumBiv(double a, double b, double p)//Cumulative Bivariate distribution parameters
	{// ONLY WHERE a*b*p IS POSITIVE for direct use
		double[][] coeffs={
			{0.24840615,0.10024215},
			{0.39233107,0.48281397},
			{0.21141819,1.0609498},
			{0.033246660,1.7797294},
			{0.00082485334,2.6697604}
		};
		
		double fa=(a/(Math.sqrt(2*(1-p*p))));
		double fb=(b/(Math.sqrt(2*(1-p*p))));
		double lead=(Math.sqrt(1.0-p*p)/Math.PI);
		double func=0.0;
		
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				func+=((coeffs[i][0]*coeffs[j][0])
				*Math.exp(fa*(2*coeffs[i][1]-fa)+fb*(2*coeffs[j][1]-fb)+2
				*p*(coeffs[i][1]-fa)*(coeffs[j][1]-fb)));
			}
		}
		return(lead*func);
	}
}
 
