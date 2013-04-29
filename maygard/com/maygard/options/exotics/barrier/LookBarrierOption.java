/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Bivnorm;
import com.maygard.core.Probnormal;
import com.maygard.options.exotics.PartialTimeFixedStrikeLookbackOption;

public class LookBarrierOption {
public LookBarrierOption(double rate,double q,double volatility,
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
double t1;
double t2;
double r;
double eta;
double heta;
double kappa;
double m;
double mu1;
double mu2;
double rho;
double gamma1;
double gamma2;
private void paRs(double s, double x, double barrier,
double time1, double time2) {
time1=time1==0.0?(1e-6):time1==1.0?(1.0-1e-12):time1;
//Avoid divide by zero
t1=time1;
t2=time2;
double sig=(sigma*sigma);
heta=Math.log(barrier/s);
kappa=Math.log(x/s);
m=eta==1.0?Math.min(heta,kappa):Math.max(heta,kappa);
mu1=(b-(sig/2.0));
mu2=(b+(sig/2.0));
rho=Math.sqrt(t1/t2);
gamma1=gam(heta,mu2,m,t1,sigma);
gamma2=gam(heta,mu1,m,t1,sigma);
}
public double callUout(double s, double x, double barrier,
double time1, double time2) {
eta=1.0;
paRs(s,x,barrier,time1,time2);
double retval= lookbar(s,x,barrier);
return retval;
}
public double putDout(double s, double x, double barrier,
double time1, double time2) {
eta=-1.0;
paRs(s,x,barrier,time1,time2);
double retval= lookbar(s,x,barrier);
return retval;
}
public double callUin(double s, double x, double barrier,
double time1, double time2) {
eta=1.0;
PartialTimeFixedStrikeLookbackOption p=new PartialTimeFixedStrikeLookbackOption(r,crate);
double part=p.partFxCall(s,x,time1,time2,sigma);
paRs(s,x,barrier,time1,time2);
double retval= lookbar(s,x,barrier);
return part-retval;
}
public double putDin(double s, double x, double barrier,
double time1, double time2) {
eta=-1.0;
PartialTimeFixedStrikeLookbackOption p=new PartialTimeFixedStrikeLookbackOption(r,crate);
double part=p.partFxPut(s,x,time1,time2,sigma);
paRs(s,x,barrier,time1,time2);
double retval= lookbar(s,x,barrier);
return part-retval;
}
private double gam(double h,double u,double m,double t,double vol) {
double term1=N(eta*(h-u*t)/(vol*Math.sqrt(t)))-Math.exp(2.0*u*h/
(vol*vol))*N(eta* (-h-u*t)/(vol*Math.sqrt(t)));
double term2=N(eta*(m-u*t)/(vol*Math.sqrt(t)))-Math.exp(2.0*u*h/
(vol*vol))*N(eta* (m-2.0*h-u*t)/(vol*Math.sqrt(t)));
return (term1-term2);
}
private double lookbar(double s, double x, double bar) {
double sig=(sigma*sigma);
double term1=s*Math.exp((b-r)*t2)*(1.0+sig/(2.0*b))*(M(eta*
(m-mu2*t1)/(sigma*Math.sqrt(t1)),
eta*(-kappa+mu2*t2)/(sigma*Math.sqrt(t2)),-rho)
-Math.exp(2.0*mu2*heta/sig)*M(eta*(m-2.0*heta
-mu2*t1)/(sigma*Math.sqrt(t1)),eta*(2.0*heta-kappa+
mu2*t2)/(sigma *Math.sqrt(t2)),-rho));
double term2=-Math.exp(-r*t2)*x*(M(eta*(m-mu1*t1)/(sigma*Math.sqrt(t1)),
eta*(-kappa+mu1*t2)/(sigma*Math.sqrt(t2)),-rho)
-Math.exp(2.0*mu1*heta/sig)*M(eta*(m-2.0*heta-mu1*t1)/
(sigma*Math.sqrt(t1)),eta*(2.0*heta-kappa+mu1*t2)/
(sigma*Math.sqrt(t2)),-rho));
double term3=-Math.exp(-r*t2)*(sig/(2.0*b))*(s*Math.pow((s/x),
(-2.0*b/sig))*M(eta*(m+mu1*t1)/(sigma*Math.sqrt(t1)),
eta*(-kappa-mu1*t2)/(sigma*Math.sqrt(t2)),-rho)
-bar*Math.pow((bar/x),(-2.0*b/sig))
*M(eta*(m-2.0*heta+mu1*t1)/(sigma*Math.sqrt(t1)),eta
*(2.0*heta-kappa-mu1*t2)/(sigma*Math.sqrt(t2)),-rho));
double term4=s*Math.exp((b-r)*t2)*((1.0+sig/(2.0*b))*N(eta*mu2
*(t2-t1)/(sigma*Math.sqrt(t2-t1)))+Math.exp(-b*(t2-t1))
*(1.0-sig/(2.0*b))*N(eta*(-mu1*(t2-t1))/
(sigma*Math.sqrt(t2-t1))))*gamma1-Math.exp(-r*t2)*x*gamma2;
double w=eta*(term1+term2+term3+term4);
return w;
}
private static double M(double a, double b, double r) {
return Bivnorm.bivar_params.evalArgs(a,b,r);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);
//restrict the range of cdf values to stable values
return ret;
}
}
