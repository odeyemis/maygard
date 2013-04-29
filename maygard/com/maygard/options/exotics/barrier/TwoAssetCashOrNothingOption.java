/**
Copyright (C) 2013 Sijuola F. Odeyemi

This source code is released under the Artistic License 2.0.

Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

Contact: odeyemis@gmail.com
*/
package com.maygard.options.exotics.barrier;

import com.maygard.core.Bivnorm;
import com.maygard.core.Probnormal;

public class TwoAssetCashOrNothingOption {
	public TwoAssetCashOrNothingOption(double asset1, double asset2, double r,double q1,
			double q2, double p) {
			crate1=q1;
			crate2=q2;
			s1=asset1;
			s2=asset2;
			rate=r;
			rho=p;
			b1=crate1==0.0?0.0:(b1=crate1!=r?(r-crate1):r);
			b2=crate2==0.0?0.0:(b2=crate2!=r?(r-crate2):r);
			}
			static double b1;
			static double b2;
			double crate1;
			double crate2;
			static double s1;
			static double s2;
			static double rate;
			static double rho;
			public double taconCall(double x1, double x2, double t, double sigma1,
			double sigma2, double cash) {
			return valU.T.Args(x1,x2,t,sigma1,sigma2,cash,1);
			}
			public double taconPut(double x1, double x2, double t, double sigma1,
			double sigma2, double cash) {
			return valU.T.Args(x1,x2,t,sigma1,sigma2,cash,2);
			}
			public double taconDup(double x1, double x2, double t, double sigma1,
			double sigma2, double cash) {
			return valU.T.Args(x1,x2,t,sigma1,sigma2,cash,4);
			}
			public double taconUdown(double x1, double x2, double t, double sigma1,
			double sigma2, double cash) {
			return valU.T.Args(x1,x2,t,sigma1,sigma2,cash,3);
			}
			private static double M(double a, double b, double r) {
			return Bivnorm.bivar_params.evalArgs(a,b,r);
			}
			private static double N(double x) {
			Probnormal p=new Probnormal();
			double ret=x>(6.95)?1.0:x<(-6.95)?0.0:p.ncDisfnc(x);//restrict the range of cdf values to stable values
			return ret;
			}
			private enum valU {
			T{
			public double Args(double x1, double x2, double t,
			double sigma1, double sigma2, double k, int tp) {
			int sw=tp;
			double puterm=0.0;
			switch(sw) {
			case 1: puterm=t1.m(x1,x2,sigma1,sigma2,t);// type 1
			break;
			case 2: puterm=t2.m(x1,x2,sigma1,sigma2,t);// type 2
			break;
			case 3: puterm=t3.m(x1,x2,sigma1,sigma2,t);// type 3
			break;
			case 4: puterm=t4.m(x1,x2,sigma1,sigma2,t);// type 4
			break;
			}
			return k*Math.exp(-rate*t)*puterm;
			}
			},
			t1{
			public double m(double a, double b, double sigma1,
			double sigma2, double t) {
			double term1=paR_1.d1(a,sigma1,t);
			double term2=paR_2.d2(b,sigma2,t);
			return M(term1,term2,rho);
			}
			},
			t2{
			public double m(double a, double b,double sigma1,
			double sigma2, double t) {
			double term1=paR_1.d1(a,sigma1,t);
			double term2=paR_2.d2(b,sigma2,t);
			return M(-term1,-term2,rho);
			}
			},
			t3{
			public double m(double a, double b,double sigma1,
			double sigma2, double t) {
			double term1=paR_1.d1(a,sigma1,t);
			double term2=paR_2.d2(b,sigma2,t);
			return M(term1,-term2,-rho);
			}
			},
			t4{
			public double m(double a, double b,double sigma1,
			double sigma2, double t) {
			double term1=paR_1.d1(a,sigma1,t);
			double term2=paR_2.d2(b,sigma2,t);
			return M(-term1,term2,-rho);
			}
			},
			paR_1{
			public double d1(double x, double sigma, double t) {
			double sig=(sigma*sigma);
			double ans=(Math.log(s1/x)+(b1-sig*0.5)*t)/(sigma*Math.sqrt(t));
			return ans;
			}
			},
			paR_2{
			public double d2(double x, double sigma, double t) {
			double sig=(sigma*sigma);
			double ans=(Math.log(s2/x)+(b2-sig*0.5)*t)/(sigma*Math.sqrt(t));
			return ans;
			}
			};
			valU() {
			}
			public double m(double a, double b,double c, double d, double f) {
			throw new UnsupportedOperationException("Not yet implemented");
			}
			public double d2(double x, double sigma, double t) {
			throw new UnsupportedOperationException("Not yet implemented");
			}
			public double d1(double x, double sigma, double t) {
			throw new UnsupportedOperationException("Not yet implemented");
			}
			public double Args(double x1, double x2, double t, double sigma1,
			double sigma2, double k, int tp) {
			throw new UnsupportedOperationException("Not yet implemented");
			}
			}
}
