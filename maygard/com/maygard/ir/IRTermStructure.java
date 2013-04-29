/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;

import com.maygard.core.Interpolate;

public class IRTermStructure extends IRTerms {
	Interpolate It=new Interpolate();
	// Interpolate is a class which allows the computation of
	private int current_flag=0; // the Lagrange interpolation
	private double[][]currentdata; // This is the yield data
	// in the order ..time/rates
	public IRTermStructure()
	{
	}
	public double DiscpOne(double interate,double time_1) {
	return discountFromYield(interate,time_1);// As in Listing 2.6
	}
	public double SpotpOne(double interate,double time_1) {
	return yieldFromDiscount(interate,time_1);// as in Listing 2.6
	}
	public double Forwdisc(double interate_1,double interate_2,
	double time_1,double time_2) {
	return forwardRateFromDiscount(interate_1,interate_2,time_1,time_2);
	}
	public double Forwyld(double interate_1,double interate_2,
	double time_1,double time_2) {
	return forwardRateFromYield(interate_1,interate_2, time_1,time_2);
	}
	public void setCurrentRateData(double[][]yielddata) { // Provides the
	// yield data
	currentdata=yielddata;
	current_flag=1; // sets a flag to register that current data is
	// available
	}
	public double getCurrentDiscOne(double timepoint_1) {
	//computes the current discount factor
	return Errorcheck(timepoint_1)==1? discountFromYield(It.lagrange
	(currentdata, timepoint_1),timepoint_1):0.0;
	//Does error checking to see if request and data are
	// valid if not this returns 0.0
	}
	public double getCurrentSpotOne(double timepoint_1) {// computes the
	// current spot rate for the input timepoint
	return Errorcheck(timepoint_1)==1? It.lagrange(currentdata,
	timepoint_1):0.0;
	}
	public double getCurrentForwardrateYlds(double timepoint_1,
	double timepoint_2) {
	// computes forward spot rates
	return(Errorcheck(timepoint_1)==1&Errorcheck(timepoint_2)==1)?
	(forwardRateFromYield(getCurrentSpotOne(timepoint_1),
	getCurrentSpotOne(timepoint_2), timepoint_1,timepoint_2)):0.0;
	}
	public void Intermstimes() {
	// This implements the abstract method from Interms
	}
	private int Errorcheck(double timepoint){
	// Method provides basic error checking
	if(current_flag==0)
	// checks to see if there is data from current set method
	{
	System.out.println("Error:no data array found for yield");
	return 0;
	}
	int n=currentdata.length;
	if((timepoint<currentdata[0][0])||
	(timepoint>currentdata[n-1][0]))
	// checks for bounds of calling method
	{
	System.out.println("Error:time variable out of data range");
	return 0;
	}
	return 1;// if successful
	}
	@Override
	public void intermstimes() {
		// TODO Auto-generated method stub
		
	}
}
