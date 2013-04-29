/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

public class SoftBarrierOption {
public SoftBarrierOption(double rate,double q,double volatility,
int period) {
crate=q;
sigma=volatility;
r=rate;
b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):period==3?
(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
}
double tau;
double crate;
double sigma;
double h;
double b;
double r;
double eta;
public double downOcall(double s, double x, double up, double low,
double t) {
eta=1;
if(up==low)// It is a standard barrier with one level
{
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
return sb.downOcall(s,x,sigma,low,0.0);
}
BlackScholes bs=new BlackScholes(crate);
eta=1.0;
if(s<low)// down and out.. stop here
return 0.0;
bs.bscholEprice(s,x,sigma,t,r);
double barval=barrierVal(s,x,up,low,t);
double adjusted =(bs.getCalle()-barval);
return adjusted ;
}
public double upOput(double s, double x, double up, double low,
double t) {
eta=-1;
if(up==low)// It is a standard barrier with one level
{
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
return sb.upOput(s,x,sigma,low,0.0);
}
if(s>up)
return 0.0;
BlackScholes bs=new BlackScholes(crate);
eta=1.0;
bs.bscholEprice(s,x,sigma,t,r);
double barval=barrierVal(s,x,up,low,t);
double adjusted =(bs.getPute()-barval);
return adjusted ;
}
public double downIcall(double s, double x, double up, double low,
double t) {
eta=1;
if(up==low)// It is a standard barrier with one level
{
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
return sb.downIcall(s,x,sigma,low,0.0);
}
double barval=barrierVal(s,x,up,low,t);
return barval;
}
public double upIput(double s, double x, double up, double low,
double t) {
eta=-1;
if(up==low)// It is a standard barrier with one level
{
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
return sb.upIput(s,x,sigma,low,0.0);
}
double barval=barrierVal(s,x,up,low,t);
return barval;
}
private double barrierVal(double s, double x,double up,
double low, double t ) {// call down and in...put up and in eta set to -1 for put
double sig=(sigma*sigma);
double mu=(b+sig/2)/sig;
double d1=(Math.log((up*up)/(s*x))/(sigma*Math.sqrt(t)))
+mu*sigma*Math.sqrt(t);
double d2=d1-((mu+0.5)*sigma*Math.sqrt(t));
double d3=(Math.log(up*up/(s*x))/(sigma*Math.sqrt(t)))+(mu-1.0)
*sigma*Math.sqrt(t);
double d4=d3-((mu-0.5)*sigma*Math.sqrt(t));
double eps1=(Math.log(low*low/(s*x))/(sigma*Math.sqrt(t)))
+mu*sigma*Math.sqrt(t);
double eps2=eps1-(mu+0.5)*sigma*Math.sqrt(t);
double eps3=(Math.log(low*low/(s*x))/(sigma*Math.sqrt(t)))
+(mu-1.0)*sigma*Math.sqrt(t);
double eps4=eps3-(mu-0.5)*sigma*Math.sqrt(t);
double UPlambda1=Math.exp(-0.5*(sig*t*(mu+0.5)*(mu-0.5)));
double UPlambda2=Math.exp(-0.5*(sig*t*(mu-0.5)*(mu-1.5)));
double term1=s*Math.exp((b-r)*t)*Math.pow(s,(-2.0*mu))
*(Math.pow(s*x,(mu+0.5))/(2.0*(mu+0.5)));
double term2=(Math.pow(up*up/(s*x),(mu+0.5))*N(eta*d1)
-UPlambda1*N(eta*d2)-Math.pow((low*low/(s*x)),(mu+0.5))
*N(eta*eps1)+UPlambda1*N(eta*eps2));
double term3=-x*Math.exp(-r*t)*Math.pow(s,(-2.0*(mu-1.0)))
*(Math.pow(s*x,(mu-0.5))/(2.0*(mu-0.5)));
double term4=(Math.pow(up*up/(s*x),(mu-0.5))*N(eta*d3)-UPlambda2
*N(eta*d4)-Math.pow(low*low/(s*x),(mu-0.5))*N(eta*eps3)
+UPlambda2*N(eta*eps4));
return (eta*1.0/(up-low)*(term1*term2+term3*term4));
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}

