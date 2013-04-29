/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;

public final class InterestRate
{
	private static double cl0=0;
	private static double cl1=0;
	public InterestRate()
	{
		this.cl0=100; // Sensible default values are put here for the index
		this.cl1=104;
	}
	public InterestRate(double a, double b)
	{
		this.cl0=a; // Calling with proper defaults is the preferred way
		this.cl1=b;
	}
	public static double realInterestRate(double nominalIntr) // implements
	{
		return 100*((cl0*(1+nominalIntr)/cl1)-1.0); 
	}
	public static double effectiveInterestRate(double intr,double convertp)
	{ // Implements
		return Math.pow((1+(intr/convertp)), convertp)-1; 
	}
	public static double fint(double intr)
	{
		return Math.log(1+intr); // implements i = ef -1
	}
	
	public static double ancertain(double intr, double n)
	{ 
		return ((Math.pow((1+intr), n)-1)/intr);
	}
	
	public static double ancertainAd(double intr, double n)
	{
		return (((Math.pow((1+intr), n)-1)/intr)*(1+intr));
	}
	
	public static double pvancert(double intr, double n)
	{
		return (1.0-(1/Math.pow((1+intr),n)))/intr;
	}
	
	public static double pvancertAd(double intr, double n)
	{
		return((1+intr)*(1.0-(1/Math.pow((1+intr),n)))/intr);
	}
	
	public static double pvainfprog(double intr, double growth,
	double value)
	{
		return value/intr-growth;
	}
	
	public static double pvanmult(double intr, double n)
	{
		double value=1/(1+intr);
		return ((pvancertAd(intr, n))-(n*Math.pow(value, n)))/intr;
	}
	
	public static double effectintp(double annualintr,double p)
	{
		return Math.pow((1+annualintr), (1/p))-1; //given the effective
		// annual int rate
		// returns the nominal rate
	}
	
	public static double effectann(double nomnualintr,double p )
	{
		return (Math.pow((1+nomnualintr/p), p)-1);//given a nominal rate
		//returns the effective rate
	}
}