/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

public abstract class NewtonRaphson extends Derivative
{
    public abstract double newtonroot(double rootvalue);
    //the requesting function implements the calculation fx//
    public double precisionvalue;
    public int iterate;
    public void accuracy(double precision,int iterations)//method gets the desired accuracy//
    {
        super.h=precision;//sets the superclass derivative//
        //to the desired precision//
        this.precisionvalue=precision;
        this.iterate=iterations;
    }
    
    public double newtraph(double lowerbound)
    { 
    	int counter=0;
        double fx=newtonroot(lowerbound);
        double Fx=derivation(lowerbound);
        double x=(lowerbound-(fx/Fx));

        //WARNING:
        /*
           There can be problems with Newton’s method;
           one is where the derivative is zero near the root. In this case; fxn/f'xn -> infinity
           Small values of fxn can cause large differences between iterations and slow
           convergence; also the calculation of fxn itself can be complicated.
         */
        while(Math.abs(Math.abs(x)-Math.abs(lowerbound))
        > precisionvalue || counter < iterate)
        {
            lowerbound=x;
            newtraph(lowerbound);//recursive call//
            // to newtraph//
            counter++;
        }
        return x;
    }
    
    public double deriveFunction(double inputa)
    {
        double x1=newtonroot(inputa);
        return x1;
    }
}
