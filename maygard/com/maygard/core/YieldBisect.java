/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

public class YieldBisect extends IntervalBisection {
	public YieldBisect() //default constructor//
	{
	}
	
	public YieldBisect(int Nofiterations, double Precision,
	double high, double low) {
	    super(Nofiterations,Precision);
	    //alternate constructor with changed values for precision
	    // and number of iterations Interval Bisection//
	    inputevaluelow=low;
	    inputvaluehigh=high;
	}
	
	//protected double nominalstockprice;
	protected double nominalpricevalue;
	protected double termperiod;
	protected double couponrate;
	protected double marketpricevalue;
	protected double inputevaluelow;
	protected double inputvaluehigh;
	protected double rateperTerm;
	protected double maturityperiod;
	protected double rateindex;

	public double computeFunction(double rootinput)
	//implements the abstract method from interval bisection
	{
	    double poscashflow,solution;
	    poscashflow=rateperTerm;//cashflow out per term
	    //as monthly amount * termperiod//
	    solution=(poscashflow/rootinput*(1.0-
	    1.0/(Math.pow(1.0+rootinput,rateindex))))+(nominalpricevalue/
	    (Math.pow(1.0+rootinput, rateindex)))-marketpricevalue;
	    return solution;
	}

	public double yield(double noms, double term, double coupon,
	double mktp, double period) {
		nominalpricevalue=noms;
		termperiod=term;
		couponrate=coupon;
		marketpricevalue=mktp;
		rateperTerm=((coupon/term));
		maturityperiod=period;
		rateindex=(maturityperiod*term);
		return evaluateRoot(inputevaluelow,inputvaluehigh);
		//evaluateRoot is in the class: IntervalBisection
	}


}