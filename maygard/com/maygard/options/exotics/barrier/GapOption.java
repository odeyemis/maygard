/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.NewtonRaphson;
import com.maygard.core.Probnormal;

public class GapOption extends NewtonRaphson{
public GapOption(double rate, double yield, double time) {
r=rate;
crate=yield;
t=time;
b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
}
double b;
double crate;
double r;
double t;
double d1;
double d2;
double asset;
double strike1;
double strike2;
double vol;
int typeflag=0;
public double gapCall(double s, double x1, double x2, double sigma)
{
paRam(s,x1,sigma);
return s*Math.exp((b-r)*t)*N(d1)-x2*Math.exp(-r*t)*N(d2);
}
public double paylateCall(double s,double st1,double st2,
double sigma) {
typeflag=-1;
asset=s;
strike1=st1;
strike2=st2;
vol=sigma;
accuracy(1e-6,20);
return newtraph(st2);
}
public double paylatePut(double s,double st1,double st2,
double sigma) {
asset=s;
strike1=st1;
strike2=st2;
vol=sigma;
accuracy(1e-6,20);
return newtraph(st2);
}
public double gapPut(double s, double x1, double x2,
double sigma) {
paRam(s,x1,sigma);
return x2*Math.exp(-r*t)*N(-d2)-s*Math.exp((b-r)*t)*N(-d1);
}
private void paRam(double s, double x1, double sigma ) {
double sig=(sigma*sigma);
d1=(Math.log(s/x1)+(b+sig*0.5)*t)/(sigma*Math.sqrt(t));
d2=d1-sigma*Math.sqrt(t);
}
private double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);
//restrict the range of cdf values to stable values
return ret;
}
public double newtonroot(double rootinput) {
double solution;
solution= typeflag==-1?(0.0-gapCall(asset,strike1,rootinput,
vol)):(0.0-gapPut(asset,strike1,rootinput,vol));
return solution;
}
}

