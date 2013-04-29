/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

public class QuantosOption {
public QuantosOption(double rate,double yield,double ratef,double p,
double time ) {
r=rate;
rf=ratef;
crate=yield;
rho=p;
t=time;
}
double r;
double rf;
double crate;
double b;
double rho;
double t;
double d1;
double d2;
public double callDom(double s, double x,double e, double sigd,
double siga) {
paraM(s,x,sigd,siga);
return e*(s*Math.exp((rf-r-crate-rho*siga*sigd)*t)*N(d1)-x*Math.exp(-r*t)
*N(d2));
}
public double callFrn(double s, double x,double e,double ef,
double sigd, double siga) {
paraM(s,x,sigd,siga);
return ef*e*(s*Math.exp((rf-r-crate-rho*siga*sigd)*t)*N(d1)
-x*Math.exp(-r*t) *N(d2));
}
public double putFrn(double s, double x,double e,double ef,
double sigd, double siga) {
paraM(s,x,sigd,siga);
return e*ef*(x*Math.exp(-r*t)*N(-d2)-s*Math.exp((rf-r-crate-rho*siga
*sigd)*t) *N(-d1));
}
public double putDom(double s, double x,double e, double sigd,
double siga) {
paraM(s,x,sigd,siga);
return e*(x*Math.exp(-r*t)*N(-d2)-s*Math.exp((rf-r-crate-rho*siga*sigd)
*t) *N(-d1));
}
private void paraM(double s, double x, double sigd, double siga) {
d1=(Math.log(s/x)+(rf-crate-rho*siga*sigd+(siga*siga*0.5))*t)
/(siga*Math.sqrt(t));
d2=d1-siga*Math.sqrt(t);
System.out.println("d1=="+d1+" d2=="+d2);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}
