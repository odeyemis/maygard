/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.ir.PresentValue;

/**
 Work in progress - this class makes use of the undefined PresentValue.pVcont method.
 */
public class PutCallPar {
	
	public PutCallPar() {
	}
	
	String typeoption= "call";
	double[] Amerput=new double[2];
	double Amercall;
	
	public PutCallPar(String type)
	{
	    this.typeoption=type;
	}
	public double[] getAmerput()
	{
	    return Amerput;
	}
	private void setAmerput(double limitlower,
	double limithigher)
	{
	    Amerput[0]=limitlower;
	    Amerput[1]=limithigher;
	}
	
	private void setAmercall(double call)
	{
	    Amercall=call;
	}
	
	public double europarity (double optionprice,
	double strike, double stcprice,
	double rate,double time)
	{
		double putfrmcall= (optionprice+(PresentValue.pVcont
		(rate,time,strike))-stcprice);
		double callfrmput=((optionprice+stcprice)-PresentValue.pVcont
		(rate,time,strike));
		return (typeoption=="put") ? putfrmcall:callfrmput;
	}
	
	public void amerparity (double optionprice,double strike,
	double stcprice,double rate,double time)
	{
		if(typeoption=="put")
		{
			double limit1=Math.abs((stcprice-strike))+optionprice;
			double limit2=Math.abs((stcprice-PresentValue.pVcont(rate,time,
			strike)))+optionprice;
			setAmerput(limit1,limit2);
		}else
		{
			double callvalue=(optionprice+stcprice)-strike;
			setAmercall(callvalue);
		}
	}
}
