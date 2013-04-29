/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

//Computations of basic profit data
public class PutCallPosition {
		
	public PutCallPosition() {
	}
	
	double shortcallprof;
	double[]shortcallprofarray;
	double longcallprof;
	double[]longcallprofarray; // data storage variables //
	double longputprof;
	double[] longputprofarray;
	double shortputprof;
	double[] shortputprofarray;
	
	private void setScallprof(double scallprofit) {
	    this.shortcallprof=scallprofit;
	}
	
	private void setScallprof(double[]scallprofit) {
	    this.shortcallprofarray=scallprofit;
	}
	
	private void setLcallprof(double lcallprofit) {
	    this.longcallprof=lcallprofit;
	}
	
	private void setLcallprof(double[] lcallprofit) {
	    //Methods that contain local calculation results//
	    this.longcallprofarray=lcallprofit;
	}
	private void setLputprof(double lputprofit) {
	this.longputprof=lputprofit;
	}
	
	private void setLputprof(double[] lputprofit) {
	    this.longputprofarray=lputprofit;
	}
	
	private void setSputprof(double sputprofit) {
	    this.shortputprof=sputprofit;
	}
	
	private void setSputprof(double[] sputprofit) {
	    this.shortputprofarray=sputprofit;
	}
	
	public double getShortcallprofit() {
	    return shortcallprof;
	}
	
	public double[] getShortcallprofits() {
	    return shortcallprofarray;
	}
	
	public double getLongcallprofit() {
	    // Public methods allowing access to the set results//
	    return longcallprof;
	}
	
	public double[] getLongcallprofits() {
	    return longcallprofarray;
	}
	
	public double getLongputprofit() {
	    return longputprof;
	}
	
	public double[] getLongputprofits() {
	    return longputprofarray;
	}
	
	public double getSputprofit() {
	    return shortputprof;
	}
	
	public double[] getSputprofits() {
	    return shortputprofarray;
	}
	
	public void callprof(double callpr,
	double exercisepr,double stockprice) {// single data//
		double shrtcallprofit= stockprice<=exercisepr?callpr:
		(callpr-(stockprice-exercisepr));
		setScallprof(shrtcallprofit);
		double lngcallprofit= stockprice<=exercisepr?-callpr:
		(-callpr+(stockprice-exercisepr));
		setLcallprof(lngcallprofit);
	}
	
	public void callprof(double callpr,double
	exercisepr,double[] stockprice) {
			//series data//
		double[]shrtcallprofit=new double[stockprice.length];
		double[]lngcallprofit=new double[stockprice.length];
		int indx=0;
		for(double s:stockprice) {
		shrtcallprofit[indx]= stockprice[indx]<=exercisepr?callpr:
		(callpr-(stockprice[indx]-exercisepr));
		lngcallprofit[indx]= stockprice[indx]<=exercisepr?-callpr:
		(-callpr+(stockprice[indx]-exercisepr));
		indx++;
		}
		setScallprof(shrtcallprofit);
		setLcallprof(lngcallprofit);
	}
	
	public void putprof(double putpr, double
	exercisepr, double stockprice) {
		double lngputprofit= stockprice<=exercisepr?
		(-putpr+(exercisepr-stockprice)):-putpr;
		setLputprof(lngputprofit);
		double shrtputprofit=stockprice<=exercisepr?
		(putpr+(exercisepr-stockprice)):putpr;
		setSputprof(shrtputprofit);
	}
	
	public void putprof(double putpr, double exercisepr,
	double[] stockprice) {
		double[ ]lngputprofit=new double[stockprice.length];
		double[]shrtputprofit=new double[stockprice.length];
		int indx=0;
		for(double s:stockprice) {
		lngputprofit[indx]= stockprice[indx]<=exercisepr?
		(-putpr+(exercisepr-stockprice[indx])):-putpr;
		shrtputprofit[indx]=stockprice[indx]<=exercisepr?
		(putpr+(stockprice[indx]-exercisepr)):putpr;
		indx++;
		}
		setLputprof(lngputprofit);
		setSputprof(shrtputprofit);
	}
}

