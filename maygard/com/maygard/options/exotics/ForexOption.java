package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

public class ForexOption {
public ForexOption( double rate,double yield,double ratef,double p,
double time ) {
r=rate;
rf=ratef;
crate=yield;
rho=p;
t=time;
}
double r;
double crate;
double rf;
double rho;
double t;
double d1;
double d2;
public double callDom(double s, double x, double e, double sigd,
double siga) {
paraM(s,x,e,sigd,siga);
return e*s*Math.exp(-crate*t)*N(d1)-x*s*Math.exp((rf-r-crate-rho*siga
*sigd) *t)*N(d2);
}
public double callFrn(double s, double x, double e, double sigd,
double siga) {
paraM(s,x,e,sigd,siga);
return s*Math.exp(-crate*t)*N(d1)-e*x*s*Math.exp((rf-r-crate-rho*siga
*sigd) *t)*N(d2);
}
public double putFrn(double s, double x, double e, double sigd,
double siga) {
paraM(s,x,e,sigd,siga);
return e*x*s*Math.exp((rf-r-crate-rho*siga*sigd)*t)*N(-d2)-s*Math.exp
(-crate*t)*N(-d1);
}
public double putDom(double s, double x, double e, double sigd,
double siga) {
paraM(s,x,e,sigd,siga);
return x*s*Math.exp((rf-r-crate-rho*siga*sigd)*t)*N(-d2)-e*s*Math.exp
(-crate*t)*N(-d1);
}
private void paraM(double s, double x,double e, double sigf,
double siga) {
d1=(Math.log(e/x)+(r-rf+(rho*sigf*siga+(sigf*sigf*0.5)))*t)
/(sigf*Math.sqrt(t));
d2=d1-sigf*Math.sqrt(t);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}
