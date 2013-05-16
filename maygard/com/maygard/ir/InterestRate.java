/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;

public final class InterestRate
{
	public InterestRate()
	{
		//this.colIndexYearStart=100; // Sensible default values are put here for the index
		//this.colIndexYearEnd=104;
	}

	//Nominal interest rate can be viewed as quoted rate and the real rate is the quoted rate
	//adjusted for inflation changes as reflected in the cost of living index.
	public static double realInterestRate(double nominalInterestRate, double colIndexYearStart, double colIndexYearEnd) 
	{
		//return the real interest rate
		return 100*((colIndexYearStart*(1+nominalInterestRate)/colIndexYearEnd)-1.0); 
	}
	
	//general formula for converting an annual nominal rate back to the annual effective rate
	public static double effectiveAnnualInterestRate(double nominalInterestRate,double numOfConversionsPerAnnum)
	{ 
		//return the effective annual interest rate
		return Math.pow((1+(nominalInterestRate/numOfConversionsPerAnnum)), numOfConversionsPerAnnum)-1; 
	}
	
	
	public static double forceOfInterest(double effectiveAnnualRate)
	{
		return Math.log(1+effectiveAnnualRate); 
	}
	
	//A series of payments (in arrears) for n periods.
	//e.g. accumulated value of 321.89 paid in arrears each period for
	//6 periods at an interest rate of 6% would be calculated 
	//as follows: annuityCertain(0.06,6)*321.89 = 2245.18
	public static double annuityCertain(double interestRate, double n)
	{ 
		return ((Math.pow((1+interestRate), n)-1)/interestRate);
	}
	
	//A series of payments (in advance) for n periods.
	//e.g. accumulated value of 321.89 paid in advance each period for
	//6 periods at an interest rate of 6% would be calculated 
	//as follows: annuityCertainAdvancePayments(0.06,6)*321.89 = 2379.73
	public static double annuityCertainAdvancePayments(double interestRate, double n)
	{
		return (((Math.pow((1+interestRate), n)-1)/interestRate)*(1+interestRate));
	}
	
	
	//A series of equal payments, for n periods.
	//Annual compounding and fixed interest rate.
	public static double presentValueAnnuityCertain(double interestRate, double n)
	{
		return (1.0-(1/Math.pow((1+interestRate),n)))/interestRate;
	}

	//A series of equal (advance) payments, for n periods.
	//Annual compounding and fixed interest rate.	
	public static double presentValueAnnuityCertainAdvancePayments(double interestRate, double n)
	{
		return((1+interestRate)*(1.0-(1/Math.pow((1+interestRate),n)))/interestRate);
	}
	
	//Present value (perpetuity)
	public static double presentValuePerpetuity(double interestRate, double growth,
	double value)
	{
		return value/interestRate-growth;
	}
	
	public static double presentValueIncreasingAnnuity(double interestRate, double n)
	{
		double value=1/(1+interestRate);
		return ((presentValueAnnuityCertainAdvancePayments(interestRate, n))-(n*Math.pow(value, n)))/interestRate;
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