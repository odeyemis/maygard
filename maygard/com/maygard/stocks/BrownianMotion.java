/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;


//Brownian Motion
public class BrownianMotion {
		
	public BrownianMotion() {
	}
	
	private double pointdrift;
	private double pointsd;
	
	private void setDrift(double drift) {
	    pointdrift=drift;
	}
	
	private void setSd(double sd) {
	pointsd=sd;
	}
	
	public double getpDrift() {
	return pointdrift;
	}
	
	public double getpSd() {
	return pointsd;
	}
	
	GenWiener g=new GenWiener();
	public double[][] expBrownian(double mu, double sigma,
	double times, int points) {
		double[][] wval=new double[points+1][4];
		wval[0][0]=0.0;
		wval[0][1]=0.0;
		wval[0][2]=(Math.sqrt((Math.exp(0.0)-1)*Math.exp(2*0.0)));
		// assumes sd == 1
		double varval;
		double interim=0.0;
		int counter=1;
		double d=points;
		double driftvalues=0.0;
		while(counter<points) {
			varval=(Math.sqrt((Math.exp(counter/d)-1)*Math.exp(2*counter/d)));
			interim=(g.genWienerProc(mu, times,sigma)+interim);
			wval[counter][0]=Math.exp(interim);
			driftvalues=(driftvalues+g.getDrift());
			wval[counter][1]=Math.exp(driftvalues);
			wval[counter][2]=(wval[counter][1]+varval);
			//drift plus variance
			wval[counter][3]=(wval[counter][1]-varval);
			//drift minus variance
			counter++;
		}
		return wval;
	}
	
	//original geoBrownian/getBrownian implementation seems flawed
	//process is not returned (i.e. return type is void. why?)
	/**
	public void geoBrownian(double mu,double sigma,double time)
	//Assumes annual periods/ratios
	{
		GenWiener g=new GenWiener();
		double process=Math.exp(g.genWienerProc(mu,time,sigma));
		setDrift(Math.exp( (g.getDrift())));
		setSd( Math.sqrt( (Math.exp(2.0*mu*(time)
		+Math.pow(sigma, 2.0)*(time))*
		(Math.exp(Math.pow (sigma, 2.0)*(time))-1))));
	}**/
	
	public double getBrownian(double mu,double sigma,double time)
	//Assumes annual periods/ratios
	{
		GenWiener g=new GenWiener();
		double process=Math.exp(g.genWienerProc(mu,time,sigma));
		setDrift(Math.exp( (g.getDrift())));
		setSd( Math.sqrt( (Math.exp(2.0*mu*(time)
		+Math.pow(sigma, 2.0)*(time))*
		(Math.exp(Math.pow (sigma, 2.0)*(time))-1))));
		
		return process;
	}
}

