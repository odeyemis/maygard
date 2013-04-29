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
import com.maygard.options.BlackScholes;

//Holder Extendible Option valuation
public class HolderExtendibleOption {
	
	public HolderExtendibleOption(double adpremium, double strikeadj,
	double newtime, double yield, double rate) {
		ap=adpremium;
		x2=strikeadj;
		t2=newtime;
		crate=yield;
		r=rate;
		brate=crate==0.0?0.0:(brate=crate!=r?(r-crate):r);
	}
	
	private double ap;
	private double x2;
	private double t2;
	private double crate;
	private double brate;
	private double r;
	private double strike;
	private double sigma;
	private double time;
	private double timediff;
	private double y;
	private double z;
	private double y2;
	private double z2;
	private double rho;
	private int typeofop;
	private double sval;
	private double sval2;
	
	public void parAms(double s, double x, double volatility,
	double t) {
		time=t;
		timediff=(t2-t);
		strike=x;
		sigma=volatility;
		if(typeofop==0)// If call
		{
			i1 ival=new i1();
			i2 ival2=new i2();
			sval=ival.pars(x);
			sval2=ival2.pars(x);
		} else// If put
		{
			pi1 ival=new pi1();
			pi2 ival2=new pi2();
			sval=ival.pars(x);
			sval2=ival2.pars(x);
		}
		y2=(Math.log(s/sval)+(brate+(sigma*sigma)*0.5)*(time))
		/(sigma*Math.sqrt(time));
		y=(Math.log(s/sval2)+(brate+(sigma*sigma)*0.5)*(time))
		/(sigma*Math.sqrt(time));
		z=(Math.log(s/x2)+(brate+(sigma*sigma)*0.5)*(t2))
		/(sigma*Math.sqrt(t2));
		z2=(Math.log(s/strike)+(brate+(sigma*sigma)*0.5)*(time))
		/(sigma*Math.sqrt(time));
		rho=(Math.sqrt(time/t2));
	}
	
	public double extCall(double s, double x,
	double volatility, double t) {
		typeofop=0;
		Probnormal p=new Probnormal();
		BlackScholes b= new BlackScholes(crate);
		parAms(s,x,volatility,t);
		double m1=(Bivnorm.bivar_params.evalArgs(y2,z,rho));
		double m2=(Bivnorm.bivar_params.evalArgs(y,z,rho));
		double m3=(Bivnorm.bivar_params.evalArgs(y2,-10.0,rho));
		double m4=(Bivnorm.bivar_params.evalArgs(y,-10.0,rho));
		double mm=(m1-m2-m3+m4);
		double n1=(Bivnorm.bivar_params.evalArgs((y2-sigma*Math.sqrt(t)),
		(z-sigma*Math.sqrt(t2)),rho));
		double n2=(Bivnorm.bivar_params.evalArgs((y-sigma*Math.sqrt(t)),
		(z-sigma*Math.sqrt(t2)),rho));
		double n3=(Bivnorm.bivar_params.evalArgs((y2-sigma*Math.sqrt(t)),
		-10.0,rho));
		double n4=(Bivnorm.bivar_params.evalArgs((y-sigma*Math.sqrt(t)),
		-10.0,rho));
		double nn=(n1-n2-n3+n4);
		double prob1=(p.ncDisfnc(z2)-p.ncDisfnc(y));
		double prob2=(p.ncDisfnc(z2-sigma*Math.sqrt(time))
		-p.ncDisfnc(y-sigma*Math.sqrt(time)));
		double prob3=(p.ncDisfnc(y2-sigma*Math.sqrt(time))
		-p.ncDisfnc(y-sigma*Math.sqrt(time)));
		b.bscholEprice(s,strike,sigma,time,r);
		double cval=b.getCalle();
		double call=(cval+s*Math.exp((brate-r)*t2)*mm-x2*Math.exp(-r*t2)
		*nn-s*Math.exp((brate-r)*time)*prob1+strike*Math.exp
		(-r*time)*prob2-ap*Math.exp(-r*time)*prob3);
		return call;
	}
	
	public double extPut(double s, double x, double volatility,
	double t) {
		BlackScholes b= new BlackScholes(crate);
		typeofop=1;
		Probnormal p=new Probnormal();
		parAms(s,x,volatility,t);
		double m1=(Bivnorm.bivar_params.evalArgs(y2,z,rho));
		double m2=(Bivnorm.bivar_params.evalArgs(y,z,rho));
		double m3=(Bivnorm.bivar_params.evalArgs(y2,-10.0,rho));
		double m4=(Bivnorm.bivar_params.evalArgs(y,-10.0,rho));
		double mm=(m1-m2-m3+m4);
		double n1=(Bivnorm.bivar_params.evalArgs((y2-sigma*Math.sqrt(t)),
		(z+sigma*Math.sqrt(t2)),rho));
		double n2=(Bivnorm.bivar_params.evalArgs((y-sigma*Math.sqrt(t)),
		(z+sigma*Math.sqrt(t2)),rho));
		double n3=(Bivnorm.bivar_params.evalArgs((y2-sigma*Math.sqrt(t))
		,-10.0,rho));
		double n4=(Bivnorm.bivar_params.evalArgs((y-sigma*Math.sqrt(t))
		,-10.0,rho));
		double nn=(n1-n2-n3+n4);
		double prob1=(p.ncDisfnc(y2)-p.ncDisfnc(z2));
		double prob2=(p.ncDisfnc(y2-sigma*Math.sqrt(time))
		-p.ncDisfnc(z2-sigma*Math.sqrt(time)));
		double prob3=(p.ncDisfnc(y2-sigma*Math.sqrt(time))
		-p.ncDisfnc(y-sigma*Math.sqrt(time)));
		b.bscholEprice(s,strike,sigma,time,r);
		double pval=b.getPute();
		double put=(pval-s*Math.exp((brate-r)*t2)*mm+x2*Math.exp(-r*t2)
		*nn+s*Math.exp((brate-r)*time)*prob1-strike*Math.exp
		(-r*time)*prob2-ap*Math.exp(-r*time)*prob3);
		return put;
	}
	
	private class i1 extends NewtonRaphson {
		i1() {
		    accuracy(1e-6,10);
		}
		public double pars(double seedvalue) {
			double sval2=newtraph(seedvalue);
			return sval2;
		}
		BlackScholes b= new BlackScholes(crate);
		public double newtonroot(double rootvalue) {
			double solution =0.0;
			b.bscholEprice(rootvalue,strike,sigma,timediff,r);
			solution=(b.getCalle()-(ap));
			return solution;
		}
	}
	
	private class pi1 extends NewtonRaphson {
		pi1() {
		    accuracy(1e-6,10);
		}
		public double pars(double seedvalue) {
			double sval2=newtraph(seedvalue);
			return sval2;
		}
		BlackScholes b= new BlackScholes(crate);
		public double newtonroot(double rootvalue) {
			double solution =0.0;
			b.bscholEprice(rootvalue,x2,sigma,timediff,r);
			solution=(b.getPute()-(strike-rootvalue+ap));
			return solution;
		}
	}
	
	private class pi2 extends NewtonRaphson {
		pi2() {
		    accuracy(1e-6,10);
		}
		public double pars(double seedvalue) {
			double sval2=newtraph(seedvalue);
			return sval2;
		}
		BlackScholes b= new BlackScholes(crate);
		public double newtonroot(double rootvalue) {
			double solution =0.0;
			b.bscholEprice(rootvalue,strike,sigma,timediff,r);
			solution=(b.getPute()-(ap));
			return solution;
		}
	}
	
	private class i2 extends NewtonRaphson {
		i2() {
		    accuracy(1e-6,10);
		}
		public double pars(double seedvalue) {
			double sval2=newtraph(seedvalue);
			return sval2;
		}
		BlackScholes b= new BlackScholes(crate);
		public double newtonroot(double rootvalue) {
			double solution =0.0;
			b.bscholEprice(rootvalue,x2,sigma,timediff,r);
			solution=(b.getCalle()-(rootvalue-strike+ap));
			return solution;
		}
	}
}
 
