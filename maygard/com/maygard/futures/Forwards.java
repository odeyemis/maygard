/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.futures;

import com.maygard.ir.PresentValue;

/**
 *TODO:
 This class is Unfinished Business. To complete, delete the mock
 implementations of methods conintr and pVcont (overloaded) and find
 or make their real implementations.
 */
public final class Forwards {
		
	public Forwards() {
	}
	
	/** method to return the dollar intersest value coefficint
	for the term of a repo rate
	*@param term is the term in years (as decimal) greater than 1 day
	*@param reporate the current bank base rate/ federal funds rate
	*/
	public static double dollarIntr(double term,double reporate) {
	    return reporate*(term/360.0);
	}
	
	/** method to return the delivery price of a new forward contract
	*@param spotprice is the spot price of the underlying asset
	*@param maturity is the time (in years as a decimal) to maturity
	of the contract
	*@param currentime is the start time of the new contract
	*/
	public static double delpriceNoinc(double spotprice,double
	maturity, double reporate) {
	    return(spotprice*conintr(reporate,maturity));
	}
	
	private static double conintr(double reporate, double maturity) {
		// TODO Method not found in Dr/ Phil's book 
		//or in any of the ir classes. We need to find a solution
		//as this is incorrect.
		return 0;
	}

	public static double fpriceNoinc(double spotprice,double maturity,
	double currentime,double deliveryprice,double reporate) {

	    return(spotprice-(PresentValue.pVcont(reporate,(maturity-currentime),
	    deliveryprice)));
	}
	

	public static double fpriceInc(double spotprice,double maturity,
	double currentime,double reporate,double period,double dividend) {
		double income=0.0;
		income= maturity==1.0 ?PresentValue.pVcont(reporate,1.0,dividend):0.0;
		//last value
		double limit=0.0;
		limit=(maturity-currentime);//Assumes that later start times
		//will floor the pv of dividend payments
		double time =(period/12.0);
		double increment=time;
		while(time<limit) {
		income+=PresentValue.pVcont(reporate,time,dividend);
		time=time+increment;
		}
		return((spotprice-income)*(conintr(reporate,
		(maturity-currentime))));
	}
	
	public static double fpriceInc(double spotprice,double maturity,
	double currentime,
	double[] reporate,double period,double dividend) {
		double income=0.0;
		double limit=0.0;
		double forwardprice=0.0;
		limit=(maturity-currentime);//Assumes that later start times will floor the pv of dividend payments
		double time =(period/12.0);
		double increment=time;
		for(double r:reporate) {
		income+=PresentValue.pVcont(r,time,dividend);
		time=time+increment;
		}
		return((spotprice-income)*(conintr(reporate[(reporate.
		length-1)], (maturity-currentime))));
	}
	
	public static double fvalueInc(double spotprice,double
	maturity,double
	currentime,double reporate,double period,double dividend,double
	deliveryprice) {
		double income=0.0;
		income= maturity==1.0 ?PresentValue.pVcont(reporate,1.0,dividend):
		0.0;//last value
		double limit=0.0;
		limit=(maturity-currentime);//Assumes that later start times will floor the pv of dividend payments
		double time =(period/12.0);
		double increment=time;
		while(time<limit) {
		income+=PresentValue.pVcont(reporate,time,dividend);
		time=time+increment;
		}
		return ((spotprice-income)-(deliveryprice*PresentValue.pVcont
		(reporate, (maturity-currentime))));
	}
	

	public static double fvalueInc(double spotprice,double
	maturity,double currentime,double[] reporate,double period,
	double dividend,double deliveryprice) {
		double income=0.0;
		double forwardprice=0.0;
		double time =(period/12.0);
		double increment=time;
		for(double r:reporate) {
		income+=PresentValue.pVcont(r,time,dividend);
		time=time+increment;
		}
		return (spotprice-(income+(deliveryprice*PresentValue.pVcont(reporate
		[(reporate.length-1)], (maturity-currentime)))));
	}
	// Also parity rate calculation
	public static double fvaluegen(double fprice,double
	delivprice,double maturity,
	double currentime,double reporate)
	{
		return ((fprice-delivprice)*PresentValue.pVcont(reporate,
		(maturity-currentime)));
	}
	
	public static double fpriceDyld(double spotprice,
	double maturity,double
	currentime,double reporate,double dividendyld) {
		return (spotprice*conintr((reporate-dividendyld),
		(maturity-currentime)));
	}
	
	public static double fvalueDyld(double spotprice,double
	maturity,double currentime,double reporate,double dividendyld,
	double deliveryprice)
	{
		return ((fpriceDyld(spotprice,maturity,currentime,
		reporate,dividendyld)- (deliveryprice))*PresentValue.pVcont(reporate,
		(maturity-currentime)));
	}
}

