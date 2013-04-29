/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Bivnorm;
import com.maygard.options.BlackScholes;

/**
Copyright (C) 2013 Sijuola F. Odeyemi

This source code is release under the Artistic License 2.0.

Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

Contact: odeyemis@gmail.com
*/
public class TwoAssetBarrierOption {
public TwoAssetBarrierOption(double q1,double q2, double rate, double time,
double rho,int period) {
crate1=q1;
crate2=q2;
r=rate;
t=time;
tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
period==3?(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
b1=crate1<0.0?0.0:(b1=crate1!=r?(r-crate1):r);
b2=crate2<0.0?0.0:(b2=crate2!=r?(r-crate2):r);
p=rho;
}
double b1;
double b2;
double tau;
double crate1;
double crate2;
double t;
double r;
double p;
double eta=0;
double phi=0;
public double callDout(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*vol2*0.5826));
return Opvalue(a1,a2,x,vol1,vol2,h);
}
public double callUout(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*vol2*0.5826));
return Opvalue(a1,a2,x,vol1,vol2,h);
}
public double putDout(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=-1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*vol2*0.5826));
return Opvalue(a1,a2,x,vol1,vol2,h);
}
public double putUout(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=-1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*vol2*0.5826));
return Opvalue(a1,a2,x,vol1,vol2,h);
}
public double callDin(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*vol2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,vol1,t,r);
return (b.getCalle()-Opvalue(a1,a2,x,vol1,vol2,h));
}
public double callUin(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*vol2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,vol1,t,r);
return (b.getCalle()- Opvalue(a1,a2,x,vol1,vol2,h));
}
public double putDin(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=-1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*vol2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,vol1,t,r);
return (b.getPute()- Opvalue(a1,a2,x,vol1,vol2,h));
}
public double putUin(double a1,double a2,double x,double vol1,
double vol2,double h) {
eta=-1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*vol2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,vol1,t,r);
return (b.getPute()- Opvalue(a1,a2,x,vol1,vol2,h));
}
private double M(double a, double b, double r) {
return Bivnorm.bivar_params.evalArgs(a,b,r);
}
private double Opvalue(double s1,double s2,double x, double vol1,
double vol2,double h) {
double sig1=(vol1*vol1);
double sig2=(vol2*vol2);
double mu1=(b1-(sig1*0.5));
double mu2=(b2-(sig2*0.5));
double d1=((Math.log(s1/x)+(mu1+sig1)*t)/(vol1*Math.sqrt(t)));
double d2=(d1-vol1*Math.sqrt(t));
double d3=(d1+(2.0*p*Math.log(h/s2))/(vol2*Math.sqrt(t)));
double d4=(d2+(2.0*p*Math.log(h/s2))/(vol2*Math.sqrt(t)));
double f1=((Math.log(h/s2)-(mu2+p*vol1*vol2)*t)/(vol2*Math.sqrt(t)));
double f2=(f1+(p*vol1*Math.sqrt(t)));
double f3=(f1-(2.0*Math.log(h/s2)/(vol2*Math.sqrt(t))));
double f4=(f2-(2.0*Math.log(h/s2)/(vol2*Math.sqrt(t))));
return (eta*s1*Math.exp((b1-r)*t)*(M(eta*d1,phi*f1,-(eta*phi*p))-
Math.exp(2.0*(mu2+p*vol1*vol2)*Math.log(h/s2)/(sig2))
*M(eta*d3,phi*f3,-(eta*phi*p)))
-eta*x*Math.exp(-r*t)*(M(eta*d2,phi*f2,-(eta*phi*p))
-Math.exp(2.0*mu2*Math.log(h/s2)/(sig2))*M(eta*d4,phi*f4,-(eta*phi*p))));
}
}
