/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.core.NewtonRaphson;
import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

// Complex Chooser Option Valuation
public class ComplexChooserOption extends NewtonRaphson {
	
	public ComplexChooserOption() {
	}
	
	public ComplexChooserOption(double yield, double rate, double volatility) {
		crate=yield;
		r=rate;
		sigma=volatility;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
		accuracy(1e-6,10);
	}
	
	double r;
	double sigma;
	double brate;
	double crate;
	double stprice;
	double callx;
	double ival;
	double putx;
	double timec;
	double timep;
	double t;
	private double d1;
	private double d2;
	private double y1;
	private double y2;
	private double rho1;
	private double rho2;
	Probnormal p=new Probnormal();
	
	public double newtonroot(double rootvalue)
	{
		double solution =0.0;
		double z1=(Math.log(rootvalue/callx)+(brate+
		(sigma*sigma)*0.5)*(timec-t))/(sigma*Math.sqrt(timec-t));
		double z2=(Math.log(rootvalue/putx)+(brate+
		(sigma*sigma)*0.5)*(timep-t))/(sigma*Math.sqrt(timep-t));
		double factor1=(rootvalue*Math.exp((brate-r)*(timec-t))
		*p.ncDisfnc(z1)-callx*Math.exp(-r*(timec-t))
		*p.ncDisfnc(z1-sigma*Math.sqrt(timec-t)));
		double factor2=(rootvalue*Math.exp((brate-r)*(timep-t))
		*p.ncDisfnc(-z2)-putx*Math.exp(-r*(timep-t))
		*p.ncDisfnc(-z2+sigma*Math.sqrt(timep-t)));
		solution =(factor1+factor2);
		return solution;
	}
	
	public double Rubinchooser(double s, double xc,double xp,
	double tc, double tp,double time)
	{
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(s,xc,sigma,tc,r);
		double call=b.getCalle();
		b.bscholEprice(s,xp,sigma,tp,r);
		double put=b.getPute();
		stprice=s;
		putx=xp;
		callx=xc;
		timec=tc;
		timep=tp;
		t=time;
		ival= newtraph(s);
		paR();
		return ival;
	}
	
	private void paR()
	{
		d1=(Math.log(stprice/ival)+(brate+(sigma*sigma)*0.5)*t)
		/(sigma*Math.sqrt(t));
		d2=(d1-sigma*Math.sqrt(t));
		y1=(Math.log(stprice/callx)+(brate+(sigma*sigma)*0.5)*timec)
		/(sigma*Math.sqrt(timec));
		y2=(Math.log(stprice/putx)+(brate+(sigma*sigma)*0.5)*timep)
		/(sigma*Math.sqrt(timep));
		rho1=(Math.sqrt(t/timec));
		rho2=(Math.sqrt(t/timep));
		double m1=Bivnorm.bivar_params.evalArgs(d1,y1,rho1);
		double m2=Bivnorm.bivar_params.evalArgs(d2,y1-sigma
		*Math.sqrt(timec),rho1);
		double m3=Bivnorm.bivar_params.evalArgs(-d1,-y2,rho2);
		double m4=Bivnorm.bivar_params.evalArgs(-d2,-y2+sigma
		*Math.sqrt(timep),rho2);
		double w=((stprice*Math.exp((brate-r)*timec)*m1)
		-(callx*(Math.exp(-r*timec))*m2)
		-(stprice*Math.exp((brate-r)*timep)*m3)
		+(putx*(Math.exp(-r*timep))*m4));
	}
}

