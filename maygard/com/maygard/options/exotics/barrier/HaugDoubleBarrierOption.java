/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.options.BlackScholes;

// Double Barrier Approximation
public class HaugDoubleBarrierOption {
	
	public HaugDoubleBarrierOption(double rate,double q,double time,
	double volatility, int period ) {
		crate=q;
		r=rate;
		t=time;
		//0 continuos,1 hourly,2 daily,3 weekly, 4 monthly
		sigma=volatility;
		tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
		period==3?(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
		b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
	}
	
	double b;
	double tau;
	double crate;
	double t;
	double r;
	double s;
	double u;
	double x;
	double l;
	double sigma;
	double f;
	double delta1;
	double delta2;
	double xu1;
	double xu2;
	double xu3;
	double xu4;
	double up1;
	double up2;
	double up3;
	double up4;
	double xl1;
	double xl2;
	double xl3;
	double xl4;
	double low1;
	double low2;
	double low3;
	double low4;
	
	public double uiDinc(double stock, double strike, double up, double low ) {
		s=stock;
		x=strike;
		paraMs(up,low,x);
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		double bscall=b.getCalle();
		SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
		double cui=sb.upIcall(s,x,sigma,up,0.0);
		double cdi=sb.downIcall(s,x,sigma,low,0.0);
		double pui1=sb.upIput(s,xu1,sigma,up1,0.0);
		double pdi1=sb.downIput(s,xl1,sigma,low1,0.0);
		double cdi1=sb.downIcall(s,xl2,sigma,low2,0.0);
		double cui1=sb.upIcall(s,xu2,sigma,up2,0.0);
		double pui2=sb.upIput(s,xu3,sigma,up3,0.0);
		double pdi2=sb.downIput(s,xl3,sigma,low3,0.0);
		double cdi2=sb.downIcall(s,xl4,sigma,low4,0.0);
		double cui2=sb.upIcall(s,xu4,sigma,up4,0.0);
		double term=(cui+cdi-(x/up)*pui1-(x/low)*pdi1+(up/low)*cdi1
		+(low/up)*cui1-(low*x/(up*up))*pui2-(up*x/(low*low))
		*pdi2+(up*up/(low*low))*cdi2+(low*low/(up*up))*cui2);
		return(Math.min(bscall,term));
	}
	
	public double uiDinp(double stock, double strike, double up, double low ){
		s=stock;
		x=strike;
		paraMs(up,low,x);
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		double bscall=b.getPute();
		SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
		double pui=sb.upIput(s,x,sigma,up,0.0);
		double pdi=sb.downIput(s,x,sigma,low,0.0);
		double cui1=sb.upIcall(s,xu1,sigma,up1,0.0);
		double cdi1=sb.downIcall(s,xl1,sigma,low1,0.0);
		double pdi1=sb.downIput(s,xl2,sigma,low2,0.0);
		double pui1=sb.upIput(s,xu2,sigma,up2,0.0);
		double cui2=sb.upIcall(s,xu3,sigma,up3,0.0);
		double cdi2=sb.downIcall(s,xl3,sigma,low3,0.0);
		double pdi2=sb.downIput(s,xl4,sigma,low4,0.0);
		double pui2=sb.upIput(s,xu4,sigma,up4,0.0);
		double term=(pui+pdi-(x/up)*pui1-(x/low)*pdi1+(up/low)*cdi1+(low/up)
		*cui1-(low*x/(up*up))*pui2-(up*x/(low*low))
		*pdi2+(up*up/(low*low))*cdi2+(low*low/(up*up))*cui2);
		return(Math.min(bscall,term));
	}
	
	public double uoDoc(double stock, double strike, double up, double low ) {
		if(stock>=up|stock <=low)
		return 0.0;// no need to continue
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		double bscall=b.getCalle();
		double val1=(bscall-uiDinc(stock,strike,up,low));
		return val1;
	}
	
	public double uoDop(double stock, double strike, double up, double low ) {
		if(stock>=up|stock<=low)
		return 0.0;// no need to continue
		BlackScholes b=new BlackScholes(crate);
		b.bscholEprice(stock,strike,sigma,t,r);
		double bscall=b.getPute();
		double val1=(bscall-uiDinp(stock,strike,up,low));
		System.out.println(" r=="+r+" b=="+crate);
		return val1;
	}
	
	private void paraMs(double up, double low, double x) {
		xu1=((up*up)/x);
		xu2=((up*up*x)/(low*low));
		xu3=(Math.pow(up,4)/(low*low*x));
		xu4=((Math.pow(up,4)*x)/(Math.pow(low,4)));
		up1=((up*up)/low);
		up2=(Math.pow(up,3)/(low*low));
		up3=(Math.pow(up,4)/(Math.pow(low,3)));
		up4=(Math.pow(up,5)/(Math.pow(low,4)));
		xl1=((low*low)/x);
		xl2=((low*low*x)/(up*up));
		xl3=(Math.pow(low,4)/(up*up*x));
		xl4=((Math.pow(low,4)*x)/(Math.pow(up,4)));
		low1= ((low*low)/up);
		low2=(Math.pow(low,3)/(up*up));
		low3=(Math.pow(low,4)/(Math.pow(up,3)));
		low4=(Math.pow(low,5)/(Math.pow(up,4)));
	}
}

