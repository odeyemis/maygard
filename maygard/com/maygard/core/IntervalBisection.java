/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

/*
   Solves for a root of the equation by starting with
   two outlying values (the end points X0 and X1) that bracket the root.
 
   The assumption is that the initial range of these end points
   contains the root. By evaluating the function at these points f(X0), f(X1) and
   checking that the function changes sign we know the root is within the range.
   The assumption is also made that the function is continuous at the root and thus
   has at least one zero.
 */
public abstract class IntervalBisection
{
    //computeFunction is implemented to evaluate successive root estimates//
    public abstract double computeFunction(double rootvalue);
    protected double precisionvalue;
    protected int iterations;
    public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	protected double lowerBound;
    protected double upperBound;
    
    //default constructor//
    protected IntervalBisection(){
        iterations=20;
        precisionvalue= 1e-3;
    }
    
    //Constructor with user defined repetitions and precision//
    protected IntervalBisection(int iterations, double precisionvalue)
    {
        this.iterations=iterations;
        this.precisionvalue=precisionvalue;
    }
    
    public double getPrecisionvalue() {
		return precisionvalue;
	}

	public void setPrecisionvalue(double precisionvalue) {
		this.precisionvalue = precisionvalue;
	}

	public double evaluateRoot(double lower, double higher)
    {
        double fa;
        double fb;
        double fc;
        double midvalue=0;
        double precvalue=0;
        fa=computeFunction(lower);
        fb=computeFunction(higher);
        //Check to see if we have the root within the range bounds//
        if (fa*fb>0)
        {
            midvalue=0;//Terminate program//
        }
        else
        do
        {
            precvalue=midvalue;//preceding value for testing//
            //relative precision//
            midvalue=lower+0.5*(higher-lower);
            fc=computeFunction(midvalue);
            if(fa*fc<0)
            {
                higher=midvalue;
            }
            else
            if(fa*fc>0)
            {
                lower=midvalue;
            }
        }
        while((Math.abs(fc)>precisionvalue) & 1<iterations);
        //loops until desired number of iterations or precision is reached//
        return midvalue;
    }
}
