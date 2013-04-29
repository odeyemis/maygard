/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;

public class MonteCarloPrices {
	
	public MonteCarloPrices() {
	}
	
	public MonteCarloPrices(int n)
	{
	    iterations=n;
	}
	
	public double getValuesim()
	{
	    return finalvalue;
	}
	
	public double getValuesimln()
	{
	    return finalvalueln;
	}
	
	public double getValuesimp()
	{
	    return finalvaluep;
	}
	
	private int iterations;
	private double finalvalue;
	private double finalvalueln;
	private double finalvaluep;
	
	public double[] simValue(double mean,double sd,double
	initialvalue,double time)
	{
		double[] simvalues=new double[iterations];
		ItoProcess ito=new ItoProcess();
		for(int i=0;i<iterations;i++)
		{
			simvalues[i]=ito.itoValue(mean, sd, time, initialvalue);
			initialvalue=initialvalue+simvalues[i];
		}
		finalvalue=initialvalue;
		return simvalues;//returns the changes from period to period
	}
	
	public double[] simValueP(double mean,double sd,double
	initialvalue,double time)
	{
		double[] simvalues=new double[iterations];
		ItoProcess ito=new ItoProcess();
		double change;
		for(int i=0;i<iterations;i++)
		{
			simvalues[i]=initialvalue;
			change=ito.itoValue(mean, sd, time, initialvalue);
			initialvalue=initialvalue+change;
		}
		finalvalue=initialvalue;
		return simvalues;//returns the new price from period to period
	}
	
	public double[] simValueln(double mean,double sd,double
	initialvalue,double time)//continuos time
	{
		double[] simvalues=new double[iterations];
		double so=initialvalue;
		initialvalue=Math.log(initialvalue);
		GenWiener g=new GenWiener();
		mean=((mean-(sd*sd))/2.0);
		sd=(Math.sqrt(time)*sd);
		for(int i=0;i<iterations;i++)
		{
			simvalues[i]=initialvalue;
			double change=g.genWienerProc(mean, time, sd);// period to
			//period change
			initialvalue=(change+initialvalue);
		}
		finalvalueln=Math.exp(initialvalue);
		return simvalues;//returns the new prices from period to period
	}
}

