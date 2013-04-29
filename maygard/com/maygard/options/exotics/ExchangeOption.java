/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Bivnorm;
import com.maygard.core.NewtonRaphson;
import com.maygard.core.Probnormal;

//Exchange Options on Exchange Option Valuation
public class ExchangeOption {
	
	public ExchangeOption(double rate, double yield1, double yield2,double
	sig1,double sig2,double p) {
		r=rate;
		crate1=yield1;
		crate2=yield2;
		sigma1=sig1;
		sigma2=sig2;
		rho=p;
		b1=crate1==0.0?0.0:(b1=crate1!=r?(r-crate1):r);
		b2=crate2==0.0?0.0:(b2=crate2!=r?(r-crate2):r);
	}
	
	private double crate1;
	private double crate2;
	private double r;
	private double sigma;
	private double sigma1;
	private double sigma2;
	private double b1;
	private double b2;
	private double rho;
	private double y1;
	private double y2;
	private double y3;
	private double y4;
	private double qval;
	private double d1;
	private double d2;
	private double d3;
	private double d4;
	private double t1;
	private double t2;
	private int typeop=0;
	private double sv1;
	private double sv2;
	
	Probnormal p=new Probnormal();
	private void params(double s1, double s2,double time1,
	double time2,double q) {
		double pratio;
		t1=time1;
		t2=time2;
		qval=q;
		sigma=(Math.sqrt((sigma1*sigma1)+(sigma2*sigma2)-2*rho*sigma1*sigma2));
		y1=((Math.log(s1/s2)+(b1-b2+(sigma*sigma)*0.5)*t2)/(sigma*Math.sqrt(t2)));
		y3=((Math.log(s2/s1)+(b2-b1+(sigma*sigma)*0.5)*t2) /(sigma*Math.sqrt(t2)));
		y2=(y1-sigma*Math.sqrt(t2));
		y4=(y3-sigma*Math.sqrt(t2));
		if(typeop==1) {
		i1 ival=new i1();
		pratio=ival.pars();
		} else {
		i2 ival=new i2();
		pratio=ival.pars();
		}
		d1=((Math.log(s1/(pratio*s2))+(b1-b2+(sigma*sigma)*0.5)*t1)
		/(sigma*Math.sqrt(t1)));
		d2=(d1-sigma*Math.sqrt(t1));
		d3=((Math.log((pratio*s2)/s1)+(b2-b1+(sigma*sigma)*0.5)*t1)
		/(sigma*Math.sqrt(t1)));
		d4=(d3-sigma*Math.sqrt(t1));
	}
	
	public double exchqfs(double s1, double s2,double time1,
	double time2,double q) {
		typeop=1;
		sv1=s1;
		sv2=s2;
		params(s1,s2,time1,time2,q);
		double m1=Bivnorm.bivar_params.evalArgs(d1,y1,Math.sqrt(t1/t2));
		double m2=Bivnorm.bivar_params.evalArgs(d2,y2,Math.sqrt(t1/t2));
		double n=p.ncDisfnc(d2);
		double c=(s1*Math.exp((b1-r)*t2)*m1-s2*Math.exp((b2-r)*t2)*m2-qval*s2
		*Math.exp((b2-r)*t1)*n);
		return c;
	}
	
	public double exchsfq(double s1, double s2,double time1,
	double time2,double q) {
		typeop=1;
		sv1=s1;
		sv2=s2;
		params(s1,s2,time1,time2,q);
		double m1=Bivnorm.bivar_params.evalArgs(d3,y2,-Math.sqrt(t1/t2));
		double m2=Bivnorm.bivar_params.evalArgs(d4,y1,-Math.sqrt(t1/t2));
		double n=p.ncDisfnc(d3);
		double c=(s2*Math.exp((b2-r)*t2)*m1-s1*Math.exp((b1-r)*t2)*m2+qval*s2
		*Math.exp((b2-r)*t1)*n);
		return c;
	}
	
	public double exchqfs2(double s1, double s2,double time1,
	double time2,double q) {
		typeop=0;
		sv1=s1;
		sv2=s2;
		params(s1,s2,time1,time2,q);
		double m1=Bivnorm.bivar_params.evalArgs(d3,y3,Math.sqrt(t1/t2));
		double m2=Bivnorm.bivar_params.evalArgs(d4,y4,Math.sqrt(t1/t2));
		double n=p.ncDisfnc(d3);
		double c=(s2*Math.exp((b2-r)*t2)*m1-s1*Math.exp((b1-r)*t2)*m2-qval*s2
		*Math.exp((b2-r)*t1)*n);
		return c;
	}
	
	public double exchs1fs2(double s1, double s2,double time1,
	double time2,double q) {
		typeop=0;
		sv1=s1;
		sv2=s2;
		params(s1,s2,time1,time2,q);
		double m1=Bivnorm.bivar_params.evalArgs(d1,y4,-Math.sqrt(t1/t2));
		double m2=Bivnorm.bivar_params.evalArgs(d2,y3,-Math.sqrt(t1/t2));
		double n=p.ncDisfnc(d2);
		double c=(s1*Math.exp((b1-r)*t2)*m1-s2*Math.exp((b2-r)*t2)*m2+qval*s2
		*Math.exp((b2-r)*t1)*n);
		return c;
	}
	
	private class i1 extends NewtonRaphson {
	i1() {
	accuracy(1e-6,10);
	}
	public double pars() {
	double seedval=(sv1*Math.exp((b1-r)*(t2-t1))/sv2
	*Math.exp((b2-r)*(t2-t1)));
	double sval2=newtraph(seedval);
	return sval2;
	}
	public double newtonroot(double rootvalue) {
	double solution =0.0;
	double z1=((Math.log(rootvalue)+((t2-t1)*(sigma*sigma)*0.5))
	/(sigma*Math.sqrt(t2-t1)));
	double z2=(z1-sigma*Math.sqrt(t2-t1));
	double factor1=p.ncDisfnc(z1);
	double factor2=p.ncDisfnc(z2);
	solution =(((rootvalue*factor1)-factor2)-qval);
	return solution;
	}
	}
	private class i2 extends NewtonRaphson {
	i2() {
	accuracy(1e-6,10);
	}
	public double pars() {
	double seedval=(sv2*Math.exp((b2-r)*(t2-t1))/sv1
	*Math.exp((b1-r)*(t2-t1)));
	double sval2=newtraph(seedval);
	return sval2;
	}
	public double newtonroot(double rootvalue) {
	double solution =0.0;
	double z1=((-Math.log(rootvalue)+((t2-t1)*(sigma*sigma)*0.5))
	/(sigma*Math.sqrt(t2-t1)));
	double z2=(z1-sigma*Math.sqrt(t2-t1));
	double factor1=p.ncDisfnc(z1);
	double factor2=p.ncDisfnc(z2);
	solution=((factor1-(rootvalue*factor2))-qval);
	return solution;
	}
	}
}

