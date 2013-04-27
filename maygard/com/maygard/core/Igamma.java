/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

public class Igamma extends ContinuedFraction
{
	private double a;
	private double b;
	private double x;
	private double igamma;
	private double igammaq;
	private double upperigam;
	private double tricomi;
	private double gamcomp;
	private double qval;
	private double pval;
	private double chisquare;
	private double chisquarec;
	
	public void setB(double bval)
	{
	b=bval;
	}
	private double getB()
	{
	return b;
	}
	private void setUpgam(double value)
	{
	upperigam=value;
	}
	private double getUpgam()
	{
	return upperigam;
	}
	private void setTricom(double value)
	{
	tricomi=value;
	}
	private double getTricom()
	{
	return tricomi;
	}
	private void setCumQ(double value)
	{
	qval=value;
	}
	private double getCumQ()
	{
	return qval;
	}
	private void setCumP(double value)
	{
	pval=value;
	}
	private double getCumP()
	{
	return pval;
	}
	private void setChis(double chival)
	{
	chisquare=chival;
	}
	private double getChis()
	{
	return chisquare;
	}
	private void setChisc(double chival)
	{
	chisquarec=chival;
	}
	private double getChisc()
	{
	return chisquarec;
	}
	private void setGam(double value)
	{
	igamma=value;
	}
	public double getGam()
	{
	return igamma;
	}
	private void setGamq(double value)
	{
	igammaq=value;
	}
	public double getGamq()
	{
	return igammaq;
	}
	private void setComp(double compliment)
	{
	gamcomp=compliment;
	}
	public double getComp()
	{
	return gamcomp;// returns the compliment of igamma//
	}
	
	public double errf(double xerr)
	{//error function for all x values positive and negative//
		double aconst;
		double xvar;
		double sign=xerr;
		if(xerr==0)
		return 0;
		aconst=0.5;// constant for igamma //
		xvar=xerr*xerr;
		double ans=igamrp(aconst,xvar);
		return sign<0? -ans:ans;
	}
	
	public double errfc(double xerr)
	{// complimentary error func
		double aconst;
		double xvar;
		if( xerr==0)
		return 1;
		aconst=0.5;
		double sign=xerr;
		xvar=xerr*xerr;
		double erf=igamrq(aconst,xvar);
		return sign<0?(2-erf):erf;
	}
	
	private double rgami(double a, double b)
	{
	    return (b>a+1.0)?igamrq(a,b):igamrp(a,b);
	}
	
	public double tricomi(double aval, double b)//little gamma if divided by gama a gives P(ax) lower gamma ratio//
	{
		a=aval;
		x=b;
		double serdev=computeSeries();
		double lead=leadhilo();
		double value=lead*serdev;
		setTricom(value);
		return getTricom();
	}
	
	public double igamup(double aval,double bval)//R(a,x)if divided by gamma a becomes Q(a,b) //
	{
		setInt(0);
		double fn;// R(a,x) //
		double leadfactor;
		a=aval;
		x=bval;
		double minval=floorvalue(0.0);
		double val=x-a+1.0;
		double den=1.0/val;
		double num=1.0/minval;
		setInitial(num,den);
		setB(val);
		setFrac(den);// CONTINUED FRACTION //
		evalFract();
		fn=getFrac();
		//leadfactor=leadhilo();
		leadfactor=leadhilo();
		double value=leadfactor*fn;
		setUpgam(value);
		return getUpgam();
	}
	public double igamrp(double aval, double valx)//computelower incomplete gamma function ratio//
	{
		a=aval;
		x=valx;
		double serdev=computeSeries();
		//double lead=leadhilo();
		double lead=leadvalue();//tricomi/rrrrra//
		double value=lead*serdev;
		setGam(value);// P(a,x) //
		return getGam();
	}
	
	public double igamrq(double aval, double valx)//computeupper incomplete gamma function ratio Q//
	{
		setInt(0);
		double fn;// R(a,x) //
		double leadfactor;
		a=aval;
		x=valx;
		double minval=floorvalue(0.0);
		double val=floorvalue(x-a+1.0);
		double den=1.0/val;
		double num=1.0/minval;
		setInitial(num,den);
		setB(val);
		setFrac(den);// CONTINUED FRACTION //
		evalFract();
		fn=getFrac();
		leadfactor=leadvalue();
		double value=leadfactor*fn;
		setGamq(value);
		return getGamq();
	}
	
	protected void computeFract(int n)
	{
		b+=2;// auxillary bn=x-a+2n for n=0....... x+2,x+4,x+6 etc’//
		vars[0]=n*(a-n);
		vars[1]=b;
		return;
	}
	
	private double computeSeries()
	{
		double sum;
		double terms;
		double summation;
		double termprevious=0;
		terms=1.0/a; // initialize the series //
		summation=a;
		sum=1.0/a;
		while(Math.abs(sum-termprevious)>prec)
		{
		termprevious=sum;
		summation+=1.0;
		terms*=x/summation;
		sum+=terms;
		}
		return sum;
	}
	
	private double leadvalue()// lead value for igamma ratio //
	{
		double value;
		value=Math.exp(Math.log(x)*a-x-Function.lgamma(a));
		return value;
	}
	
	private double leadhilo()
	{
		double value;
		value=(Math.exp(-x)*Math.pow(x,a));
		return value;//returns lower or upper incomplete gamma lead values.....not the ratio//
	}
	
	public final double gcf(double a, double x)
	{
		this.a=a;
		this.x=x;
		int i=1;
		double retval=0;
		double fn,an, b, c, d, den, h;
		double minval=floorvalue(0.0);
		b = x + 1.0 - a;
		c = 1.0 / minval;
		d = 1.0 / b;
		fn=d;
		den=d;
		while (Math.abs(den - 1.0) > 1e-30)
		{
			an = i * (a - i);
			b += 2.0;
			d = floorvalue(an * d + b);
			c =floorvalue( b + an / c);
			d = 1.0 / d;
			den = floorvalue(d * c);
			fn*=den;
			i++;
			// if (abs(del - 1.0) < 2e-15) break;
		}
		double lead=leadhilo();
		double ret=lead*fn;
		return (ret);
	}
	
	public final double poisscumQ(int k,double xval)//x is expected mean, also returns the prob that p(x) is between 0 and k-1 NOT at most k//
	{
		if(xval<0)
		return 0;
		if(k<1)
		return Math.exp(-k);
		this.a=k;
		this.x=xval;
		double value=rgami(a,x);
		return x>a+1.0?getGamq():1-getGam();
	}
	
	public final double poisscumP(int k, double xval)
	{
		if(x<0)
		return 0;
		if(k<1)
		return Math.exp(-k);
		this.a=k;
		this.x=xval;
		double value=rgami(a,x);
		return x>a+1.0?1-getGamq():getGam();
	}
	
	public final double chisqr(double chival,int fval)//lower tail//
	{// CDF //
		double v=fval/2.0;
		double kisqr=chival/2.0;
		double divisor=Function.gamma(v);
		double tric=tricomi(v,kisqr);
		double value=tric/divisor;
		setChis(value);
		return chival==0? 0 :getChis();
	}
	
	public final double chisqrc(double chival,int fval)//Upper tail for x2<35 v<=30//
	{
		double value=chisqr(chival,fval);
		setChisc(1.0-value);
		return chival==0?1:getChisc();
	}
	
	public final double chisqrpdf(double x, int fval)
	{
		double num= Math.pow(x,((0.5*fval)-1))*Math.exp(-0.5*x);
		double den=Function.gamma(0.5*fval)*Math.pow(2,0.5*fval);
		return num/den;
	}
	
	public final double chisqrc2(double chival,int fval)//Upper tail for x2<35 v<=30//
	{
		double v=fval/2.0;
		double kisqr=chival/2.0;
		double divisor=Function.gamma(v);
		double upgam=igamup(v,kisqr);
		//igamratio(v,kisqr);
		double value=upgam/divisor;
		//setChisc(value);
		setChisc(upgam);
		return chival==0? 1 :getChisc();
	}
}

