package com.maygard.core;
/**
Copyright (C) 2013 Sijuola F. Odeyemi

This source code is released under the Artistic License 2.0.

Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

Contact: odeyemis@gmail.com
*/
public class Inversenormal extends NewtonRaphson{
		
	public Inversenormal() {accuracy(1e-9,20);//optimum values for xup to 5
	}
	
	Probnormal p=new Probnormal();
	private double target=0.0;
	public double InverseNormal( double uvalue)//Probability
	//between 0 and 1.0
	{
		double xval=0.0;
		target=uvalue;
		if(target==0.5)
		return 0.0;
		if(uvalue<0.5) {
		uvalue=(1.0-uvalue);
		xval=-Math.sqrt(Math.abs(-1.6*Math.log(1.0004-Math.pow((1.0-2.0*uvalue),2))));
		return newtraph(xval);
		}else{
		xval=Math.sqrt(Math.abs(-1.6*Math.log(1.0004-Math.pow((1.0-2.0*uvalue),2))));}
		return newtraph(xval);
	}
	
	public double newtonroot(double rootinput) {
	    return (target-p.ncDisfnc(rootinput));
	}
}
