/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

public class SuperShareOption {
	public SuperShareOption(double rate,double q,double time,
	double volatility) {
		crate=q;
		r=rate;
		t=time;
		sigma=volatility;
		b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
	}
	
	double t;
	double r;
	double crate;
	double b;
	double sigma;
	public double sShare(double s, double xl, double xu) {
	double sig=(sigma*sigma);
	double d1=(Math.log(s/xl)+(b+(sig*0.5))*t)/(sigma*Math.sqrt(t));
	double d2=(Math.log(s/xu)+(b+(sig*0.5))*t)/(sigma*Math.sqrt(t));
	Probnormal prob=new Probnormal();
	
	return(s*Math.exp((b-r)*t)/(xl))*prob.ncDisfnc(d1)
	-(s*Math.exp((b-r)*t)/(xl))*prob.ncDisfnc(d2);
	}
}