/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

import java.sql.Time;

import com.maygard.core.Bivnorm;
import com.maygard.core.Probnormal;
import com.maygard.options.BlackScholes;

public class PartialTimeSingleBarrierAssetOption {
public PartialTimeSingleBarrierAssetOption(double rate,double q,double volatility,
int period ) {
crate=q;
sigma=volatility;
r=rate;
b=crate==0.0?0.0:(b=crate!=r?(r-crate):r);
tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):
period==3?(1.0/(52.0)):
period==4?(1.0/(12.0)):0.0;
}
double tau;
double crate;
double d1;
double d2;
double sigma;
double h;
private static double b;
private static double t1;
private static double t2;
private static double r;
private static double f1;
private static double f2;
private static double f3;
private static double f4;
private static double f5;
private static double f6;
private static double mu;
private static double rho;
private static double g1;
private static double g2;
private static double g3;
private static double g4;
public double ptsoUocall(double s, double x, double barrier,
double time1, double time2)
{//partial time start out up and out callA
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)
*sigma*0.5826)):barrier;// do any h adjustments now
if(s>=barrier)
return 0.0;
params(s,x,barrier,time1,time2);
double term1=(s*Math.exp((b-r)*t2)*(M(d1,-f3,-rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(f1,-f5,-rho)));
double term2=(x*Math.exp(-r*t2)*(M(d2,-f4,-rho)-Math.pow((h/s),
(2.0*mu))*M(f2,-f6,-rho)));
return term1-term2;
}
public double ptsoDocall(double s, double x, double barrier,
double time1, double time2)
{// time start out down and out callA
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):barrier;
if(s<=barrier)
return 0.0;
params(s,x,barrier,time1,time2);
double term1=(s*Math.exp((b-r)*t2)*(M(d1,f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(f1,f5,rho)));
double term2=(x*Math.exp(-r*t2)*(M(d2,f4,rho)-Math.pow((h/s),
(2.0*mu))*M(f2,f6,rho)));
return term1-term2;
}
public double ptsoDicall(double s, double x, double barrier,
double time1, double time2)
{// time start out down and in callA
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):barrier;
BlackScholes b=new BlackScholes(crate);
double downout= ptsoDocall(s,x,barrier,time1,time2);
b.bscholEprice(s,x,sigma,t2,r);
return (b.getCalle()-downout);
}
public double ptsoUicall(double s, double x,
double barrier,double time1, double time2)
{//partial time start out up and in callA
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):barrier;
BlackScholes b=new BlackScholes(crate);
double upout= ptsoUocall(s,x,barrier,time1,time2);
b.bscholEprice(s,x,sigma,t2,r);
return (b.getCalle()-upout);
}
public double pteoCall(double s, double x, double barrier,
double time1, double time2)
{//partial time end out call cob1
params(s,x,barrier,time1,time2);
double term1=(s*Math.exp((b-r))*t2)*(M(d1,f3,rho)-(Math.pow((h/s),
(2.0*(mu+1.0))))*M(f1,-f5,-rho));
double term2=(x*Math.exp(-r*t2))*(M(d2,f4,rho)-(Math.pow((h/s),
(2.0*mu)))*M(f2,-f6,-rho));
double term3=(s*Math.exp((b-r)*t2)*(M(-g1,-f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(-g3,f5,-rho)));
double term4=(x*Math.exp(-r*t2)*(M(-g2,-f4,rho)-Math.pow((h/s),
(2.0*mu))*M(-g4,f6,-rho)));
double term5=(s*Math.exp((b-r)*t2)*(M(-d1,-f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(-f1,f5,-rho)));
double term6=(x*Math.exp(-r*t2)*(M(-d2,-f4,rho)-Math.pow((h/s),
(2.0*mu))*M(-f2,f6,-rho)));
double term7=(s*Math.exp((b-r)*t2)*(M(g1,f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(g3,-f5,-rho)));
double term8=(x*Math.exp(-r*t2)*(M(g2,f4,rho)-Math.pow((h/s),
(2.0*mu))*M(g4,-f6,-rho)));
return x>h?(term1-term2):(term3-term4-term5+term6+term7-term8);
}
public double pEuocall(double s, double x, double barrier,
double time1, double time2)
{//partial end barrier x>h B2 up and out
if(s>=barrier)
return 0.0;
if(x>=barrier)
return pteoCall(s,x,barrier,time1,time2);
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):barrier;
params(s,x,barrier,time1,time2);
double term1=(s*Math.exp((b-r)*t2)*(M(-g1,-f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(-g3,-f5,-rho)));
double term2=(x*Math.exp(-r*t2)*(M(-g2,-f4,rho)-Math.pow((h/s),
(2.0*mu))*M(-g4,f6,-rho)));
double term3=(s*Math.exp((b-r)*t2)*(M(-d1,-f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(f5,-f1,-rho)));
double term4=(x*Math.exp(-r*t2)*(M(-d2,-f4,rho)-Math.pow((h/s),
(2.0*mu))*M(f6,-f2,-rho)));
return (term1-term2-term3+term4);
}
public double pEdocall(double s, double x, double barrier,
double time1, double time2)
{// x<h partial end barrierB2 down and out
if(s<=barrier)
return 0.0;
if(x<=barrier)
return pteoCall(s,x,barrier,time1,time2);
barrier= x>s? (barrier*Math.exp(Math.sqrt(tau)*sigma*0.5826)):x<s?
(barrier*Math.exp(-Math.sqrt(tau)*sigma*0.5826)):barrier;
params(s,x,barrier,time1,time2);
double term1=(s*Math.exp((b-r)*t2)*(M(g1,f3,rho)-Math.pow((h/s),
(2.0*(mu+1.0)))*M(g3,-f5,-rho)));
double term2=(x*Math.exp(-r*t2)*(M(g2,f4,rho)-Math.pow((h/s),
(2.0*mu))*M(g4,-f6,-rho)));
return (term1-term2);
}
private void params(double s,double x,double barrier,
double time1,double time2) {
t1=time1;
t2=time2;
h=barrier;
t1=t1==0.0?(1e-4):t1==1.0?(1.0-1e-12):t1;// restrict outer extermes for approximations using M(a,b:p)
double sigmas=((sigma*sigma)*0.5);
d1=(Math.log(s/x)+(b+sigmas)*t2)/(sigma*Math.sqrt(t2));
d2=d1-sigma*Math.sqrt(t2);
System.out.println("d1=="+d1+" d2=="+d2);
f1=(Math.log(s/x)+2.0*Math.log(h/s)+(b+sigmas)*t2)/(sigma*Math.sqrt(t2));
f2=f1-sigma*Math.sqrt(t2);
f3=(Math.log(s/h)+(b+sigmas)*t1)/(sigma*Math.sqrt(t1));
f4=f3-sigma*Math.sqrt(t1);
f5=f3+2.0*Math.log(h/s)/(sigma*Math.sqrt(t1));
f6=f5-sigma*Math.sqrt(t1);
System.out.println("f1=="+f1+" f2=="+f2+" f3=="+f3+"f4=="+f4+" f5=="+f5+" f6=="+f6);
mu=(b-sigmas)/(sigma*sigma);
rho=Math.sqrt(t1/t2);
g1=(Math.log(s/h)+(b+sigmas)*t2)/(sigma*Math.sqrt(t2));
g2=g1-sigma*Math.sqrt(t2);
g3=g1+2.0*Math.log(h/s)/(sigma*Math.sqrt(t2));
g4=g3-sigma*Math.sqrt(t2);
}
public double putDouta(double s,double x,double barrier,
double time1,double time2) {
if(s<=barrier)
return 0.0;
double callvalue=ptsoDocall(s,x,barrier,time1,time2);
return (callvalue-puT.P_par.Argsval(s,x,h,1));
}
public double putUouta(double s, double x, double barrier,
double time1, double time2) {
if(s>=barrier)
return 0.0;
double callvalue=ptsoUocall(s,x,barrier,time1,time2);
return (callvalue-puT.P_par.Argsval(s,x,h,2));
}
public double putOutb1(double s, double x, double barrier,
double time1,double time2) {
double callvalue=pteoCall(s,x,barrier,time1,time2);
return (callvalue-puT.P_par.Argsval(s,x,h,3));
}
public double putDob2(double s, double x, double barrier,
double time1,double time2) {
if(s<=barrier)
return 0.0;
double callvalue= pEdocall(s,x,barrier,time1,time2);
return (callvalue-puT.P_par.Argsval(s,x,h,4));
}
public double putUob2(double s, double x, double barrier,
double time1,double time2) {
if(s>=barrier)
return 0.0;
double callvalue=pEuocall(s,x,barrier,time1,time2);
return (callvalue-puT.P_par.Argsval(s,x,h,5));
}
private static double M(double a, double b, double r) {
return Bivnorm.bivar_params.evalArgs(a,b,r);
}
private static double N(double x) {
Probnormal p=new Probnormal();
double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
return ret;
}
private enum puT {
P_par{
public double Argsval(double s, double x, double h,int tp) {
int sw=tp;
double puterm=0.0;
double z1=N(f4)-Math.pow((h/s),(2.0*mu))*N(f6);
double z2=N(-f4)-Math.pow((h/s),(2.0*mu))*N(-f6);
double z3=M(g2,f4,rho)-Math.pow((h/s),(2.0*mu))*M(g4,-f6,-rho);
double z4=M(-g2,-f4,rho)-Math.pow((h/s),(2.0*mu))*M(-g4,f6,-rho);
double z5=N(f3)-Math.pow((h/s),(2.0*(mu+1.0)))*N(f5);
double z6=N(-f3)-Math.pow((h/s),(2.0*(mu+1.0)))*N(-f5);
double z7=M(g1,f3,rho)-Math.pow((h/s),(2.0*(mu+1.0)))*M(g3,-f5,-rho);
double z8=M(-g1,-f3,rho)-Math.pow((h/s),(2.0*(mu+1)))*M(-g3,f5,-rho);
switch(sw) {
case 1: puterm=DoA.valuesz(z1,z5,s,x);//down out type A
break;
case 2: puterm=UoA.valuesz(z2,z6,s,x);//up out type A
break;
case 3: puterm=oB1.valuesz2(z3,z4,z7,z8,s,x);//out type B1
break;
case 4: puterm=Dob2.valuesz(z3,z7,s,x);//down and out type B2
break;
case 5: puterm=Uob2.valuesz(z4,z8,s,x);//up and out type B2
break;
}
return puterm;
}
},
DoA{
public double valuesz(double za, double zb,double s,double x) {
double val=s*Math.exp((b-r)*t2)*zb+x*Math.exp(-r*t2)*za;
return val;
}
},
UoA{
public double valuesz(double za, double zb,double s,double x) {
double val=s*Math.exp((b-r)*t2)*zb+x*Math.exp(-r*t2)*za;
return val;
}
},
oB1{
public double valuesz2(double za, double zb,double zc, double zd,
double s,double x) {
double val=s*Math.exp((b-r)*t2)*zd+x*Math.exp(-r*t2)
*zb-s*Math.exp((b-r)*t2)*zc+x*Math.exp(-r*t2)*za;
return val;
}
},
Dob2{
public double valuesz(double za, double zb,double s,double x) {
double val=s*Math.exp((b-r)*t2)*zb+x*Math.exp(-r*t2)*za;
return val;
}
},
Uob2{
public double valuesz(double za, double zb,double s,double x) {
double val=s*Math.exp((b-r)*t2)*zb+x*Math.exp(-r*t2)*za;
return val;
}
};
puT() {
}
public double valuesz(double a, double b,double c, double d) {
throw new UnsupportedOperationException("Not yet implemented");
}
public double valuesz2(double za, double zb,double zc, double zd,
double s,double x) {
throw new UnsupportedOperationException("Not yet implemented");
}
public double Argsval(double a, double b, double p,int tp) {
throw new UnsupportedOperationException("Not yet implemented");
}
}
}

