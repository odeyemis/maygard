/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import java.text.NumberFormat;

import com.maygard.core.Bivnorm;

//Options on Two Risky Assets.
public class MinOrMaxOfTwoRiskyAssetsOption {
	
	public MinOrMaxOfTwoRiskyAssetsOption(double p, double yield, double rate,double
	brate2, double sig1, double sig2) {
		r=rate;
		crate=yield;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
		sigma1=sig1;
		sigma2=sig2;
		rho=p;
		b2=brate2;
	}
	
	private double crate;
	private double brate;
	private double r;
	private double sigma1;
	private double sigma2;
	private double b2;
	private double y1;
	private double y2;
	private double rho;
	
	private void params(double s1, double s2, double x1, double x2,
	double time){
		y1=((Math.log(s1/x1)+(brate-(sigma1*sigma1)*0.5)*time)/(sigma1*
				Math.sqrt(time)));
		y2=((Math.log(s2/x2)+(b2-(sigma2*sigma2)*0.5)*time)/(sigma2*
				Math.sqrt(time)));
	}
	
	public double twoAssetcall(double s1, double s2, double x1,
	double x2, double time) {
		params(s1,s2,x1,x2,time);
		double m1=(Bivnorm.bivar_params.evalArgs((y2+sigma2*
				Math.sqrt(time)),(y1+rho*sigma2*Math.sqrt(time)),rho));
		double m2=(Bivnorm.bivar_params.evalArgs(y2,y1,rho));
		double c=(s2*Math.exp((b2-r)*time)*m1-x2*Math.exp(-r*time)*m2);
		return c;
	}
	
	public double twoAssetput(double s1, double s2, double x1, double x2,
	double time) {
		NumberFormat formatter=NumberFormat.getNumberInstance();
		formatter.setMaximumFractionDigits(4);
		formatter.setMinimumFractionDigits(3);
		params(s1,s2,x1,x2,time);
		double m2=(Bivnorm.bivar_params.evalArgs((-y2-sigma2*Math.sqrt
		(time)),(y1-rho*sigma2*Math.sqrt(time)),rho));
		double m1=(Bivnorm.bivar_params.evalArgs(-y2,-y1,rho));
		double p=(x2*Math.exp(-r*time)*m1-s2*Math.exp(b2-r*time)*m2);
		return p;
	}
}
