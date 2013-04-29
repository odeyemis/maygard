/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;


/*
This class provides methods
for calculating the various transformations of interest rates. The class is not
implemented directly but is accessed through its extending class.
 */
public abstract class IRTerms {
	
	public abstract void intermstimes();
	
	public IRTerms() {
	}
	
	public double discountFromYield(double spotrate,double time)
	{
	return Math.exp(-spotrate*time); 
	}
	
	public double yieldFromDiscount(double discount,double time)
	{
	return -Math.log(discount)/time; 
	}
	
	public double forwardRateFromSpots(double spot1,double spot2)
	{
	return (Math.pow((1+spot2),2)/(1+spot1)-1);
	}
	
	public double forwardRateFromDiscount(double discount1,double discount2,
	double time1,double time2)
	{
	return (Math.log(discount1/discount2)/(time2-time1));
	}
	
	public double forwardRateFromYield(double r1,double r2,double t1,double t2)
	{
	return (r2*(t2/(t2-t1)))-(r1*(t1/(t2-t1)));
	}
}
