package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

public class AsianGeoOption {
public AsianGeoOption(double rate,double yield,double volatility) {
r=rate;
crate=yield;
sigma=volatility;
b=yield;
}
double r;
double crate;
double b;
double sigma;
double d1;
double d2;
double sigadj;
double badj;
private void paraM(double s, double x, double t) {
double adj=Math.sqrt(3);
sigadj=(sigma/adj);
double sig=(sigadj*sigadj);
badj=(b-((sigma*sigma)/6.0))*0.5;
d1=(Math.log(s/x)+(badj+sig*0.5)*t)/(sigadj*Math.sqrt(t));
d2=(d1-sigadj*Math.sqrt(t));
}
public double geoCall(double s, double x, double t) {
paraM(s,x,t);
return s*Math.exp((badj-r)*t)*N(d1)-x*Math.exp(-r*t)*N(d2);
}
public double geoPut(double s, double x, double t) {
paraM(s,x,t);
return x*Math.exp(-r*t)*N(-d2)-s*Math.exp((badj-r)*t)*N(-d1);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}
