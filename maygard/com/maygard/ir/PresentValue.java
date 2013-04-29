/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;

public final class PresentValue {
/** creates a new instance of PresentValue */
	public PresentValue() {
	}
	public static double pV(double[] discounts,double[]cashflows)
	{
		int n=cashflows.length;
		double presval=0;
		for(int i=0;i<n;i++)
		{
		    presval+=discounts[i]*cashflows[i]; // returns sum of
		    // discounted values..
		    //for each period cashflow
		}
		return presval;
	}
	public static double pV(double r,double[] cashflows)
	{
		int indx=1;
		double sum=0;
		for(int i=0;i<cashflows.length;i++)
		{
		sum+=(cashflows[i]/(Math.pow((1+r),(indx))));//Implements
		indx++;
		}
		return sum;
	}
	
	public static double pV(double r,double cash,int period)
	{
		double sum=0;
		int indx=1;
		for(int i=0;i<period;i++)
		{
			sum+=(cash/(Math.pow((1+r),(indx))));// Implements PV 
			indx++;
		}
		return sum;
	}
	
	public static double pVonecash(double[] discounts, double cashflow) {
		int i = 0;
		double pv = 0;
		for (double d : discounts) {
			pv += (Math.exp(-(i + 1) * Math.log(1.0 + (d / 100.0))) * cashflow);
			i++;
		}
		return pv;
	}
	public static double pVcont(double reporate, double d, double deliveryprice) {
		// TODO Method not found in Dr/ Phil's book 
		//or in any of the ir classes. We need to find a solution
		//as this is incorrect.
		return 0;
	}
	public static double pVcont(double d, double e) {
		// TODO Method not found in Dr/ Phil's book 
		//or in any of the ir classes. We need to find a solution
		//as this is incorrect.
		return 0;
	}
}
