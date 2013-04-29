/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import java.sql.Time;

import com.maygard.core.Bivnorm;
import com.maygard.options.BlackScholes;

public class PartialTimeTwoBarrierAssetOption {
public PartialTimeTwoBarrierAssetOption(double q1,double q2, double rate, double time,
double time2,double rho,int period) {
crate1=q1;
crate2=q2;
r=rate;
t=time;
t2=time2;
tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):period==3?
(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
b1=crate1==0.0?0.0:(b1=crate1!=r?(r-crate1):r);
b2=crate2==0.0?0.0:(b2=crate2!=r?(r-crate2):r);
p=rho;
}
double b1;
double b2;
double tau;
double crate1;
double crate2;
double t;
double t2;
double r;
double p;
double eta=0;
double phi=0;
private double Opvalue(double a1,double a2,double x, double
sigma1,double sigma2,double h) {
t=t==0.0?(1e-4):t==1.0?(1.0-1e-12):t;
// restrict outer extermes for approximations using M(a,b:p)
double sig1=(sigma1*sigma1);
double sig2=(sigma2*sigma2);
double mu1=(b1-(sig1*0.5));
double mu2=(b2-(sig2*0.5));
double d1=((Math.log(a1/x)+(mu1+sig1)*t2)/(sigma1*Math.sqrt(t2)));
double d2=(d1-sigma1*Math.sqrt(t2));
double d3=(d1+(2.0*p*Math.log(h/a2))/(sigma2*Math.sqrt(t2)));
double d4=(d2+(2.0*p*Math.log(h/a2))/(sigma2*Math.sqrt(t2)));
double f1=((Math.log(h/a2)-((mu2+p*sigma1*sigma2)*t))/
(sigma2*Math.sqrt(t)));
double f2=(f1+(p*sigma1*Math.sqrt(t)));
double f3=(f1-(2.0*Math.log(h/a2)/(sigma2*Math.sqrt(t))));
double f4=(f2-(2.0*Math.log(h/a2)/(sigma2*Math.sqrt(t))));
return (eta*a1*Math.exp((b1-r)*t2)*(M(eta*d1,phi*f1,-eta*phi*p
*Math.sqrt(t/t2))-Math.exp(2.0*(mu2+p*sigma1*sigma2)*Math.log(h/a2)/
(sig2))*M(eta*d3,phi*f3,-eta*phi*p*Math.sqrt(t/t2)))-eta*x
*Math.exp(-r*t2)*(M(eta*d2,phi*f2,-eta*phi*p*Math.sqrt(t/t2))
-Math.exp(2.0*mu2*Math.log(h/a2)/(sig2))*M(eta*d4,phi*f4,
-eta*phi*p*Math.sqrt(t/t2))));
}
private double M(double a, double b, double p) {
return Bivnorm.bivar_params.evalArgs(a,b,p);
}
public double callDout(double a1,double a2,double x,double sigma1,
double sigma2,double h) {
eta=1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*sigma2*0.5826));
return Opvalue(a1,a2,x,sigma1,sigma2,h);
}
public double callUout(double a1,double a2,double x,
double sigma1,double sigma2, double h) {
eta=1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*sigma2*0.5826));
return Opvalue(a1,a2,x,sigma1,sigma2,h);
}
public double putDout(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=-1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*sigma2*0.5826));
return Opvalue(a1,a2,x,sigma1,sigma2,h);
}
public double putUout(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=-1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*sigma2*0.5826));
return Opvalue(a1,a2,x,sigma1,sigma2,h);
}
public double callDin(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*sigma2*0.5826));
BlackScholes b=new BlackScholes(crate1);
b.bscholEprice(a1,x,sigma1,t,r);
return (b.getCalle()-Opvalue(a1,a2,x,sigma1,sigma2,h));
}
public double callUin(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*sigma2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,sigma1,t,r);
return (b.getCalle()- Opvalue(a1,a2,x,sigma1,sigma2,h));
}
public double putDin(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=-1.0;
phi=-1.0;
h=(h*Math.exp(-Math.sqrt(tau)*sigma2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,sigma1,t,r);
return (b.getPute()- Opvalue(a1,a2,x,sigma1,sigma2,h));
}
public double putUin(double a1,double a2,double x,double sigma1,
double sigma2, double h) {
eta=-1.0;
phi=1.0;
h=(h*Math.exp(Math.sqrt(tau)*sigma2*0.5826));
BlackScholes b=new BlackScholes(b1);
b.bscholEprice(a1,x,sigma1,t,r);
return (b.getPute()- Opvalue(a1,a2,x,sigma1,sigma2,h));
}
}