package com.maygard.core;

//Class to compute Incomplete Beta
public final class Ibeta extends ContinuedFraction {
	
	private static double a=0;
	private static double b=0;
	private static double x=0;
	private double ans;
	
	private void setFraction(double aval,double bval,double xval) {
		a=aval;
		b=bval;
		x=xval;
	}
	
	private void setAns(double val) {
	    ans=val;
	}
	
	public double getAns() {
	    return ans;
	}
	
	public double betai(double a1, double a2, double xval) {
		double d;
		double result;
		double interim;
		double lead;
		lead=eval(a1,a2,xval);// get the leading factor//
		if((xval<0.0)||(xval>1.0))
		return 0;//an error to be handled//
		if(xval==0)
		return 0;
		if(xval==1.0)
		return 1;
		if(xval<(a1+1.0)/(a1+a2+2.0)) {
			setFraction(a1,a2,xval);
			d=floorvalue(1.0-(a+b)*x/(a+1.0));//initial value //
			d=1.0/d;
			setInitial(1.0,d);
			setFrac(d);
			evalFract();//get the CF //
			result=lead*getFrac()/a;
			setAns(result);
		}
		else {
			setFraction(a2,a1,(1.0-xval));
			System.out.println("x<a+1/a+b+2 is NOT TRUE");
			d=floorvalue(1.0-(a+b)*x/(a+1.0));//initial value //
			d=1.0/d;
			setInitial(1.0,d);
			setFrac(d);
			evalFract();//get the CF //
			result=1.0-lead*getFrac()/a;
			//result=1.0-lead*betacf(a,b,x)/a; nice test //
			setAns(result);
		}
		return getAns();
	}
	
	private double eval(double a1, double a2, double x)// produces the leading factor to multiply the continued fraction value , this is independent of it being inverse or not//
	{
		double leadinfactor;
		if(x==0)
		return 0;
		if(x==1)
		return 1;
		leadinfactor=Math.exp(Function.lbeta(a1,a2)+a1*Math.log(x)+a2*Math.log(1-x));
		return leadinfactor;
	}
	
	protected void computeFract(int n) //implements the data required to compute the continued fraction //
	{
		// this is sensitive to the inverse so variables are changeable //
		vars[1]=1.0;// for ibeta the numerator is fixed at 1 ..The b’s//
		int i=n/2;
		int j=2*i;
		vars[0]=j==n?x*i*(b-i)/((a-1.0+j)*(a+j)):0.0-(a+i)*(a+b+i)
		*x/((a+j)*(a+1.0+j));
		return;// the latest update to be evaluated at the super class //
	}
	
	private double betacf(double a, double b, double x ) //does the continued fraction only..needs lead for ibeta//
	{
		int n, n2;
		double retval, aa, del;
		double[] lentzfactors= new double[2];
		lentzfactors[0]=1.0;
		lentzfactors[1]= floorvalue(1.0 - (a+b)* x / (a+1.0));//1 //
		lentzfactors[1]=1.0/lentzfactors[1];
		retval=lentzfactors[1];
		n=1;
		del=0;
		while(Math.abs(del-1.0)>prec) {
		n2 = n + n;
		aa = (b-n) * n * x / ( (a-1.0+n2) * (a+n2) );
		lentzfactors[1]=floorvalue( 1.0 + aa * lentzfactors[1]);
		lentzfactors[0]=floorvalue( 1.0 + aa / lentzfactors[0]);
		lentzfactors[1]=1.0/lentzfactors[1];
		retval*=lentzfactors[0]*lentzfactors[1];
		aa = 0.0 - (a+n) * ((a+b)+n) * x / ( (a+n2) * (a+1.0+n2) );
		lentzfactors[1]=floorvalue( 1.0 + aa * lentzfactors[1]);
		lentzfactors[0]=floorvalue( 1.0 + aa / lentzfactors[0]);
		lentzfactors[1]=1.0/lentzfactors[1];
		del=lentzfactors[0]*lentzfactors[1];
		retval *= del;
		n++;
		}
		return (retval);
	}
}

