/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;

//Generalised Wiener process
public class GenWiener {
	
	public GenWiener() {
	}
	
	private double constdrift;
	private double wienervalue;
	
	private void setDrift(double driftval)
	{
	    constdrift=driftval;
	}
	
	public double getDrift()
	{
	    return constdrift;
	}
	
	private void setWiener(double wienval)
	{
	    wienervalue=wienval;
	}
	
	public double getWienerVal()
	{
	    return wienervalue;
	}
	
	public double genWienerProc(double drift, double t, double sd)
	{
		Wiener w=new Wiener();
		double deltaz;
		double driftvalue;
		double deltax;
		deltaz=w.wienerProc(t);
		setWiener(deltaz);
		driftvalue=drift*t;
		setDrift(driftvalue);
		deltax=(driftvalue+(sd*deltaz));
		return deltax;
	}
}

