/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

import com.maygard.core.Probnormal;

//Implementation of class to compute Vasicek algorithm
public class VasicekOption {
	
	public VasicekOption(double meanrev,double revlevel,
	double volatility, double starttime) {
		a=meanrev;
		theta=revlevel;
		sigma=volatility;
		start=starttime;
	}
	
	private double a;
	private double theta;
	private double sigma;
	private double start;
	private double pstart;
	private double pmat;
	private double h;
	private double hw;
	private double bondvol;
	private double bondvolw;
	private double btstart;
	private double bmaturity;
	double bexpiry;
	
	private void vasiParams(double f, double x, double rate,
	double time,double tmaturity ) {
		btstart=((1.0-Math.exp(-a*(time-start)))/a);
		bexpiry=((1.0-Math.exp(-a*(tmaturity-time)))/a);
		bmaturity=((1.0-Math.exp(-a*(tmaturity-start)))/a);
		double startat1=((btstart-time+start)*(((a*a)*theta)
		-((sigma*sigma)*0.5))/(a*a));
		double startat2=(((sigma*sigma)*(btstart*btstart))/(4*a));
		double starta=Math.exp(startat1-startat2);
		double matat1=((bmaturity-tmaturity+start)*(((a*a)*theta)
		-((sigma*sigma)*0.5))/(a*a));
		double matat2=(((sigma*sigma)*(bmaturity*bmaturity))/(4*a));
		double mata=Math.exp(matat1-matat2);
		pstart=(starta*Math.exp(-btstart*rate));
		pmat=(mata*Math.exp(-bmaturity*rate));
		bondvol=(bexpiry*(Math.sqrt((sigma*sigma)
		*(1.0-Math.exp(-2*a*(time-start)))/(2*a))));
		h=((1.0/bondvol)*Math.log((pmat*f)/(pstart*x))+(bondvol*0.5));
	}
	
	public double vasiCall(double f, double x, double rate,
	double time,double tmaturity ) {
		Probnormal p=new Probnormal();
		vasiParams(f,x,rate,time,tmaturity);
		return ((f*pmat*p.ncDisfnc(h))
		-(x*pstart*p.ncDisfnc(h-bondvol)));
	}
	
	public double vasiPut(double f, double x, double rate,
	double time,double tmaturity ) {
		Probnormal p=new Probnormal();
		vasiParams(f,x,rate,time,tmaturity);
		return ((x*pstart*p.ncDisfnc(-h+bondvol))
		-(f*pmat*p.ncDisfnc(-h)));
	}
	
	//delete if not needed
	public static void main(String[] args) {
		VasicekOption v= new VasicekOption(0.055,0.095,0.04,0.0);
	double returnvalue=v.vasiCall(110.0,100.0,0.085,3.0,4.0);
	System.out.println(" CALL =="+returnvalue);
	}
}
 