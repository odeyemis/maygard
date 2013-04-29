/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics;

import com.maygard.options.BlackScholes;

public class AsianTurnballWakemanOption {
public AsianTurnballWakemanOption(double rate,double yield,double volatility) {
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
double sigadj;
double badj;
public double tWakeput(double s,double sa, double x, double t,
double t2, double tau) {
paraM(s,x,t,t2,tau);
double t1=t-t2;
double q=r-badj;
BlackScholes bs=new BlackScholes(q);
x=t1>0.0?(((t/t2)*x)-((t1/t2)*sa)):x;
bs.bscholEprice(s,x,sigadj,t2,r);
return t1>0.0?(bs.getPute()*t2/t):(bs.getPute());
}
public double tWakecall(double s,double sa, double x, double t,
double t2, double tau) {
paraM(s,x,t,t2,tau);
double t1=t-t2;
double q=r-badj;
BlackScholes bs=new BlackScholes(q);
x=t1>0.0?(((t/t2)*x)-((t1/t2)*sa)):x;
bs.bscholEprice(s,x,sigadj,t2,r);
return t1>0.0?(bs.getCalle()*t2/t):(bs.getCalle());
}
private void paraM(double s, double x, double t, double t2,
double tau) {
double sig=(sigma*sigma);
double moment1=(Math.exp(b*t)-Math.exp(b*tau))/(b*(t-tau));
double term1=2.0*Math.exp((2.0*b+sig)*t)/((b+sig)*(2.0*b+sig)
*Math.pow((t-tau),(2.0)));
double term1a=2.0*Math.exp(2.0*(b+sig)*tau)/(b*Math.pow((t-tau),(2.0)));
double term2=(1.0/(2.0*b+sig)-Math.exp(b*(t-tau))/(b+sig));
double moment2=(term1+(term1a*term2));
badj=Math.log(moment1)/(t);
sigadj=(Math.sqrt(Math.log(moment2)/(t)-2.0*badj));
double sig2=(sigadj*sigadj);
d1=(Math.log(s/x)+(badj+sig2*0.5)*t2)/(sigadj*Math.sqrt(t2));
d2=(d1-sigadj*Math.sqrt(t2));
}
}

