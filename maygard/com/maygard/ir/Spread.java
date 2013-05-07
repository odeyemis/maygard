/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.ir;

import com.maygard.core.NewtonRaphson;
import com.maygard.core.YieldBisect;

public class Spread extends NewtonRaphson {
	
	private double precision=1e-5;
	private int iterations=20;
	
	public Spread() //default constructor//
	{
		this. terms=2.0;// default twice annual coupon payments
		this.dataperiod=1;//period of spot rate data
		this.facevalue=100.0;//default par
	}
	
	public Spread(double frequency,int dataterms,double parvalue ) {
		this.terms=frequency;
		this.dataperiod=dataterms;
		this.facevalue=parvalue;
	}
	
	double terms=0.0;
	int dataperiod=0;
	double facevalue=0.0;
	double periodyield=0.0;
	double nperiods=0.0;
	double periodcoupon=0.0;
	double price=0.0;
	double[]spots;
	double coupon=0.0;
	double periods=0.0;
	/** Method computes spread for the annual period rates
	*
	* provides the static spread from the corporate bond price. The amount
	by which each spot needs to be adjusted
	* Assumes annual coupon rate and annual yield estimate
	*/
	public double spreadsT(double[]spotrates,double pcorp,
		double maturity,double couponrate,
		double estimate) {
		accuracy(precision,iterations);
		spots=spotrates;
		price=pcorp;
		coupon=couponrate;
		return newtraph(estimate);
	}
	/**
	*Calculates the yield spread for YTM of corretly priced risk
	free and arbitrary priced corporate bond
	*/
	public double spreadrateS(double[]spotrates,double priceval,
	double couponrate,double maturity,
	double yieldapprox ) {
		double baseyield=0.0;
		double curveyield=0.0;
		double spotapprox=0.0;
		spots=spotrates;
		coupon=couponrate;
		nperiods=maturity*terms;// number of compounding periods
		if(((int)maturity*dataperiod)!=spots.length)
		{
			System.out.println("error: spots data is not == to the maturity*dataperiods");
		    return 0.0;
		}
		periodyield=((yieldapprox/100.0)/terms);
		periodcoupon=(coupon/(terms));
		spotapprox=(((spots[0]+spots[(spots.length-1)])/2.0)/100.0);
		//first guess
		spotapprox=spotapprox/terms;
		price=dataperiod==1?spotPvannual(spots,coupon):
		spotPvperiod(spots,coupon);
		//TODO: Note: we could use a NewtonYield object also.
		//Tyield c=new Tyield();// create a yield object
		YieldBisect c= new YieldBisect();
		//curveyield=c.yieldEstimate(facevalue,6.0,coupon,price,
		//maturity,spotapprox);
		curveyield = c.yield(facevalue,6.0,
				coupon,price,maturity);
		price=priceval;
		//Tyield t=new Tyield();
		YieldBisect t= new YieldBisect();
		//baseyield=t.yieldEstimate(facevalue,6.0,coupon,price,
		//maturity,periodyield);
		baseyield=t.yield(facevalue,6.0,coupon,price,
		maturity);
		return (Math.abs(baseyield-curveyield));// returns annualised rates
	}
	/**
	*Assumes annual coupon and annual yield with years to maturity
	as input parameters
	*assumes coupon and yield is entered as percentage value
	*/
	public double[] spreadrateS(double[]spotrates,double prices[],
	double couponrate,double maturity,
	double yieldapprox ) {
		double curveyield=0.0;
		double spotapprox=0.0;
		spots=spotrates;
		coupon=couponrate;
		double curvest=yieldapprox;
		int index=0;
		double spreads[]=new double[prices.length];
		//Tyield t=new Tyield();
		YieldBisect t= new YieldBisect();
		nperiods=maturity*terms;
		periodcoupon=(coupon/(terms));
		price=dataperiod==1?spotPvannual(spots,coupon):
		spotPvperiod(spots,coupon);
		//curveyield=t.yieldEstimate(facevalue,6.0,coupon,price,
		//maturity,(curvest/100.0));
		curveyield=t.yield(facevalue,6.0,coupon,price,
		maturity);
		for(double p:prices) {
		//Tyield yld=new Tyield();
		YieldBisect yld= new YieldBisect();
		price=p;
		//double y=yld.yieldEstimate(facevalue,6.0,coupon,price,
		//maturity,(yieldapprox/100.0));
		double y=yld.yield(facevalue,6.0,coupon,price,
		maturity);
		spreads[index]=(y-curveyield);
		index++;
		}
		return spreads;
	}
	
	public double[] spreadrateT(double[]spotrates,
	double prices[],double couponrate,
	double maturity,double yieldapprox ) {
		spots=spotrates;
		coupon=couponrate;
		int index=0;
		double spreads[]=new double[prices.length];
		accuracy(precision,iterations);
		periodyield=yieldapprox;
		nperiods=maturity*terms;
		periodcoupon=(coupon/(terms));
		for(double p:prices) {
		price=p;
		periodyield=((periodyield/100.0)/terms);
		periodyield=newtraph(periodyield);
		spreads[index]=periodyield;
		index++;
		}
		return spreads;
	}
	/**
	*Method computes the PV for an array of period spots
	* and the annual coupon
	*periods are user defined
	**/
	private double spotPvperiod(double[]periodspot,double coupon) {
		double pv=0.0;
		double par=0.0;
		double periodcoupon=0.0;
		double couponadjust=coupon/terms;
		int size=0;
		size=periodspot.length*(int)terms;
		//pv= pVonecash(periodspot,couponadjust);
		pv= PresentValue.pVonecash(periodspot,couponadjust);
		par=(100.0*Math.exp(-(double)size*Math.log(1.0+
		(periodspot[(periodspot.length-1)]/100.0))));
		return(pv+par);
	} 
	
	/** Method to compute the PV of an array of annual
	spots and annual coupon with given annual frequency of compounding
	**
	**/
	private double spotPvannual(double[]periodspot,double coupon ) {
		double pv=0.0;
		double par=0.0;
		if(terms>1.0) {
			int size=0;
			int compfreq=0;
			int index=0;
			compfreq=(int)terms;
			size=periodspot.length*(int)terms;
			double[]periodspotadj=new double[(size)];
			for(double d:periodspot) {
				for (int i=0;i<compfreq;i++) {
					periodspotadj[index]=d/terms;
					index++;
				}
			}
			double couponadjust=(coupon/terms); //from an annual coupon
			//to the period rate
			//pv=pVonecash(periodspotadj,couponadjust);
			pv= PresentValue.pVonecash(periodspotadj,couponadjust);
			par=(100.0*Math.exp(-(double)size*Math.log(1.0+
			(periodspotadj[(periodspotadj.length-1)]/100.0))));
			return(pv+par);
		}
		else{
			double couponadjust=(coupon/terms); //from an annual coupon//
			//to the period rate
			//pv=pVonecash(periodspot,couponadjust);
			pv= PresentValue.pVonecash(periodspot,couponadjust);
			par=(100.0*Math.exp(-(double)periodspot.length*Math.log(1.0+
			(periodspot[(periodspot.length-1)]/100.0))));
			return(pv+par);
		}
	}
	
	
	
	
	public double spotPvannualT(double[]periodspot,double coupon,
	double terms ){
		double pv=0.0;
		double par=0.0;
		if(terms>1.0) {
			int size=0;
			int compfreq=0;
			int index=0;
			compfreq=(int)terms;
			size=periodspot.length*(int)terms;
			double[]periodspotadj=new double[(size)];
			for(double d:periodspot) {
				for (int i=0;i<compfreq;i++) {
					periodspotadj[index]=d/terms;
					index++;
				}
			}
			double couponadjust=(coupon/terms); //from an annual coupon
			// to the period rate
			//pv=pVonecash(periodspotadj,couponadjust);
			pv=PresentValue.pVonecash(periodspotadj,couponadjust);
			par=(100.0*Math.exp(-(double)size*Math.log(1.0+
			(periodspotadj[(periodspotadj.length-1)]/100.0))));
			return(pv+par);
		}
		else {
			double couponadjust=(coupon/terms); //from an annual coupon to the period rate
			//pv=pVonecash(periodspot,couponadjust);
			pv=PresentValue.pVonecash(periodspot,couponadjust);
			par=(100.0*Math.exp(-(double)periodspot.length*Math.log(1.0+
			(periodspot[(periodspot.length-1)]/100.0))));
			return(pv+par);
		}
	}
	
	
	
	/**Assumes period spot and the yield approximation are period rates
	with period percentages
	*Assumes flat rate spot for entire maturity period
	*assumes the coupon is a coupon percent of par rate.
	*/
	public double spreadrate(double periodspot,double priceval,
	double coupon,double maturity,
	double yieldapprox ) {
		accuracy(precision,iterations);
		double baseyield=0.0;
		double frequency=0.0;
		price=priceval;
		periods=maturity*terms;
		periodcoupon=coupon;
		periodyield=yieldapprox/100.0;
		periodspot=periodspot/100.0;
		baseyield=newtraph(periodyield);
		return (Math.abs(baseyield-periodspot)*terms*100.0);
		//returns annualised spread
	}
	
	
	
	/**
	*credit spread computes probability of default and forward
	prob of default
	*assumes corporate bond zero and treasury zero (riskless)
	*/
	public double[] creditS(double[]riskless,double[]risky) {
		int size=riskless.length;
		double[]fdefault=new double[size];
		double[]pdefault=new double[size];
		pdefault[0]=(1.0-(risky[0]/riskless[0]));
		fdefault[0]=pdefault[0];
		for(int i=1;i<size;i++) {
		fdefault[i]=(1.0-(Math.exp(-Math.log(1.0-pdefault[i-1])))*
		(risky[i]/riskless[i]));
		pdefault[i]=(pdefault[i-1]*fdefault[i]);
		}
		return fdefault;
	}
	
	
	public double newtonroot(double spread)
	{
		int indx=0;
		double[] spotspreads=new double[spots.length];
		for(double d:spots) {
		spotspreads[indx]=(d+spread);
		indx++;
		}
		spread=(spotPvannual(spotspreads,coupon)-price);
		return spread;
	}
}
