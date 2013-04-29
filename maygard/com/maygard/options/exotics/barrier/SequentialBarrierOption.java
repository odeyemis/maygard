/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import com.maygard.core.Probnormal;

public class SequentialBarrierOption {
public SequentialBarrierOption(double rate,double q,double time,
double volatility, int period ) {
crate=q;
r=rate;
t=time;
//0 continuos,1 hourly,2 daily,3 weekly, 4 monthly
sigma=volatility;
tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
period==3?(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
}
double b;
double tau;
double crate;
double t;
double r;
double sigma;
double d;
double d1;
double lambda;
double eta;
double eta1;
double eta2;
double v;
double chi;
double xo;
double gamma;
double gamma1;
public double uiDoc(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
if(stock>=up)
return sb.downOcall(stock,strike,sigma,low,0.0);
double cui=sb.upIcall(stock,strike,sigma,up,0.0);
return (cui-uiDic(stock,strike,up,low));
}
public double uiDic(double stock, double strike, double up,
double low ) {
paRam(up,low,stock,strike);
return (d1*stock*Math.pow((low/up),(2.0*lambda))*N(eta)
-d*strike*Math.pow((low/up),(2.0*lambda-(2.0)))*N(eta-v));
}
public double diUic(double stock, double strike, double up,
double low ) {
paRam(up,low,stock,strike);
double term1=d1*stock*Math.pow((up/stock),(2.0*lambda))*N(chi)-d
*strike*Math.pow((up/stock),(2.0*lambda-(2.0)))*N(chi-v);
double term2=d1*stock*Math.pow((low/up),(2.0*lambda))
*(N(eta1)-N(eta2))-d*strike*Math.pow((low/up),(2.0*lambda-(2.0)))
*(N(eta1-v)-N(eta2-v));
return(term1+term2);
}
public double diUoc(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
double uoc=sb.upOcall(stock,strike,sigma,up,0.0);
if(stock<=low)
return uoc;
double cdi=sb.downIcall(stock,strike,sigma,low,0.0);
return (cdi-diUic(stock,strike,low,up));
}
public double uoDic(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
HaugDoubleBarrierOption sh=new HaugDoubleBarrierOption(r,crate,t,sigma,0);
double dbc=sh.uoDoc(stock,strike,up,low);
double uoc=sb.upOcall(stock,strike,sigma,up,0.0);
return (uoc-dbc);
}
public double doUic(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
HaugDoubleBarrierOption sh=new HaugDoubleBarrierOption(r,crate,t,sigma,0);
double dbc=sh.uoDoc(stock,strike,up,low);
double doc=sb.downOcall(stock,strike,sigma,low,0.0);
return (doc-dbc);
}
private void paRam(double up, double low, double s, double x) {
d=Math.exp(-r*t);
d1=Math.exp(-b*t);
lambda=(((r-b)/(sigma*sigma))+0.50);
v=(sigma*Math.sqrt(t));
eta1=((1.0/v)*Math.log((low*low*s)/(up*up*x))+(lambda*v));
eta2=((1.0/v)*Math.log((low*s)/(up*up))+(lambda*v));
eta=x>=low?eta1:eta2;
chi=((1.0/v)*Math.log((up*up)/(low*s))+(lambda*v));
xo=((1.0/v)*Math.log((s)/(up))+(lambda*v));
gamma=((1.0/v)*Math.log((up*up)/(s*x))+(lambda*v));
gamma1=((1.0/v)*Math.log((up)/(s))+(lambda*v));
}
public double uoDip(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
HaugDoubleBarrierOption sh=new HaugDoubleBarrierOption(r,crate,t,sigma,0);
double uop=sb.upOput(stock,strike,sigma,up,0.0);
double dbp=sh.uoDop(stock,strike,up,low);
return (uop-dbp);
}
public double doUip(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
HaugDoubleBarrierOption sh=new HaugDoubleBarrierOption(r,crate,t,sigma,0);
double dop=sb.downOput(stock,strike,sigma,up,0.0);
double dbp=sh.uoDop(stock,strike,up,low);
return (dop-dbp);
}
public double uiDop(double stock, double strike, double up,
double low ) {//up>low,strike>low,stock<up,for a positive payoff
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
if(stock>=up)
return sb.downOput(stock,strike,sigma,low,0.0);
double uip=sb.upIput(stock,strike,sigma,up,0.0);
paRam(up,low,stock,strike);
double term= d1*stock*Math.pow((up/stock),(2.0*lambda))
*N(-chi)-d*strike*Math.pow((up/stock),((2.0*lambda)-2.0))
*N(-chi+v)-d1*Math.pow((low/up),(2.0*lambda))
*(N(eta1)-N(eta2));
double term1=d*strike*Math.pow((low/up),((2.0*lambda)-2.0))
*(N(eta1-v)-N(eta2-v));
return (uip+term+term1);
}
public double uiDip(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
if(stock>=up)
return sb.downIput(stock,strike,sigma,low,0.0);
double uip=sb.upIput(stock,strike,sigma,up,0.0);
return(uip-uiDop(stock,strike,up,low));
}
public double diUop(double stock, double strike, double up,
double low ) { //Assumes up<low,strike>up,stock>up
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
if(stock<=low)
return sb.upOput(stock,strike,sigma,up,0.0);
double reverse=up;
up=low;low=reverse;
paRam(up,low,stock,strike);
if(strike>=low) {
double term1= d*strike*N(-xo+v)-d1*stock*N(-xo)+d*strike
*Math.pow((up/stock),((2.0*lambda)-2.0))*(N(gamma1-v)-N(chi-v));
double term2=d1*stock*Math.pow((up/stock),(2.0*lambda))
*(N(gamma1)-N(chi))+d1*stock*Math.pow((low/up),(2.0*lambda))
*N(-eta2)-d*strike*Math.pow((low/up),((2.0*lambda)-2.0))
*N(-eta2+v);
return (term1-term2);
}
double term1=d*strike*N(-xo+v)-d1*stock*N(-xo)
+d*strike*Math.pow
((up/stock),((2.0*lambda)-2.0))*(N(gamma1-v)-N(gamma-v));
double term2=d1*stock*Math.pow((up/stock),(2.0*lambda))
*(N(gamma1)-N(gamma))+d1*stock*Math.pow((low/up),(2.0*lambda))
*N(-eta1)-d*strike*Math.pow((low/up),((2.0*lambda)-2.0))
*N(-eta1+v);
return (term1-term2);
}
public double diUip(double stock, double strike, double up,
double low ) {
SingleBarrierOption sb=new SingleBarrierOption(r,crate,t,0);
if(stock<=low)
return sb.upIput(stock,strike,sigma,up,0.0);
return(sb.downIput(stock,strike,sigma,low,0.0)-diUop
(stock,strike,up,low));
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
}
