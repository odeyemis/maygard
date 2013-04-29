package com.maygard.options.exotics;

import com.maygard.core.Probnormal;

public class AsianLevyOption {
public AsianLevyOption(double rate,double yield,double volatility) {
r=rate;
crate=yield;
sigma=volatility;
b=yield;
b=crate==0.0?0.0:(b=crate!=rate?(rate-crate):rate);
}
double r;
double crate;
double b;
double sigma;
double d1;
double d2;
double se;
double xadj;
public double levyCall(double s,double sa, double x, double t,
double t2) {
paraM(s,sa,x,t,t2);
return se*N(d1)-xadj*Math.exp(-r*t2)*N(d2);
}
public double levyPut(double s,double sa, double x, double t, double t2) {
paraM(s,sa,x,t,t2);
double val=-se+xadj*Math.exp(-r*t2);
return (se*N(d1)-xadj*Math.exp(-r*t2)*N(d2))-se+xadj*Math.exp(-r*t2);
}
private void paraM(double s,double sa, double x, double t, double t2) {
double sig=(sigma*sigma);
xadj=x-((t-t2)/(t))*sa;
double m=(2.0*(s*s)/(b+sig))*((Math.exp((2*b+sig)*t2)-1.0)
/(2.0*b+sig)-(Math.exp(b*t2)-1.0)/(b));
se=s/(t*b)*(Math.exp((b-r)*t2)-Math.exp(-r*t2));
double d=(m/(t*t));
double v=Math.log(d)-2.0*(r*t2+Math.log(se));
d1=1/Math.sqrt(v)*(Math.log(d)/(2.0)-Math.log(xadj));
d2=d1-Math.sqrt(v);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}
