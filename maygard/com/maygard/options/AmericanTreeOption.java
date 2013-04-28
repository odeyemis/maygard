/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.core.BinomParams;
import com.maygard.core.BinomPrice;

//Computation of an American Tree
//TODO: This class is not functional/complete since the 
//required constructor/implementation in the BinomPrice class 
//does not exist.
public class AmericanTreeOption {
	
	public AmericanTreeOption(double time,double rate,double yield,
	double volatility) {
		t=time;
		r=rate;
		q=yield;
		sigma=volatility;
	}
	
	private double t;
	private double r;
	private double q;
	private double sigma;
	
	public double amerCall(int n, int nodes, int h, double price,
	double strike)
	{
		double[] pricetree=new double[n+1];
		BinomParams bp=new BinomParams();
		t=(t/n);
		bp.binomodel(t,r,sigma,q);
		//BinomPrice b=new BinomPrice(bp.getU(),bp.getD(),bp.getP(),t,r,1);// 1 is American
		//pricetree=b.binomTprice(n,nodes,h,price,strike,1);// non zero
		double amps =pricetree[0];
		return amps;
	}
	public double amerPut(int n, int nodes, int h, double price,
	double strike)
	{
		double[] pricetree=new double[n+1];
		BinomParams bp=new BinomParams();
		t=(t/n);
		bp.binomodel(t,r,sigma,q);
		//BinomPrice b=new BinomPrice(bp.getU(),bp.getD(),bp.getP(), t,r,1); // 1 is American
		//pricetree=b.binomTprice(n,nodes,h,price,strike,0);
		// zero is a put
		return pricetree[0];
	}
}
