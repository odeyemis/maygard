/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */

package com.maygard.bonds;

import java.util.Calendar;


/**
The application program using this class would make the appropriate market
basis adjustment by a multiple of the coupon frequency. The daycounts method
returns the accrued value proportion for the period settlement to next coupon
date. The method getPreviousCouponDays returns the proportionate value for
the period last coupon to settlement, which is the portion of accrued time due to a
seller.
 */

public class AccruedConvention {
	/** Creates a new instance of Daycounts according to the convention */
	//Default constructor assumes 2*6 monthly coupon payments...//
	//semi annual//
	public AccruedConvention() {
	this.coupons=2.0;
	}
	
	public AccruedConvention(double couponperiod)
	// non default constructor to set the coupon period monthly times per annum
	{
	this.coupons=12.0/couponperiod;
	}
	
	public double getPreviousCoupondays() {
	return previouscoupondays;
	}
	
	private void setPreviousCoupondays(double prevcoupdate) {
	this.previouscoupondays=prevcoupdate;
	}
	
	Calendar previouscoupon=Calendar.getInstance();
	private double coupons;
	public double previouscoupondays;
	
	public double daycounts(int flagvalue,Calendar settlementdate,
	Calendar nextcoupondate) {
		Calendar temp=Calendar.getInstance();
		previouscoupon.set(Calendar.YEAR,(nextcoupondate.get(Calendar.YEAR)));
		previouscoupon.set(Calendar.MONTH,(nextcoupondate.get(Calendar.MONTH)-6));
		// assumes default 6 monthly period
		previouscoupon.set(Calendar.DATE,(nextcoupondate.get(Calendar.DATE)));
		int actualday=0;
		int actualdays=0;
		int samedays=0;
		
		switch(flagvalue) {
			case 1:
				if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
				//Actual/actual in period (eg, US gov)
				{
					samedays=(nextcoupondate.get(Calendar.DATE)
					-settlementdate.get(Calendar.DATE));
					for(int n=(previouscoupon.get(Calendar.MONTH)+1);
					n<nextcoupondate.get(Calendar.MONTH);n++) {
					    temp.set(Calendar.MONTH,n);
					    actualdays+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				    }
				    actualdays+=(previouscoupon.getActualMaximum(Calendar.DAY_OF_MONTH)
				    -previouscoupon.get(Calendar.DATE));
				    setPreviousCoupondays((double)(actualdays-samedays)/
				    (actualdays));
				    return (double)samedays/actualdays;
				}
				int setdays= (settlementdate.getActualMaximum(Calendar.DAY_OF_MONTH)-
				settlementdate.get(Calendar.DATE));
				actualday=setdays;
				for(int i=(settlementdate.get(Calendar.MONTH)+1);
					i<nextcoupondate.get(Calendar.MONTH);i++){
					temp.set(Calendar.MONTH,i);
					actualday+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
				actualday+=nextcoupondate.get(Calendar.DATE);
				actualdays=nextcoupondate.get(Calendar.DATE);
				temp.clear();
				for(int n=(previouscoupon.get(Calendar.MONTH)+1);
					n<nextcoupondate.get(Calendar.MONTH);n++){
					temp.set(Calendar.MONTH,n);
					actualdays+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				}
				actualdays+=(previouscoupon.getActualMaximum(Calendar.DAY_OF_MONTH)
				-previouscoupon.get(Calendar.DATE));
				setPreviousCoupondays((double)(actualdays-actualday)
				/(actualdays));
				return (double)actualday/actualdays; //returns fraction of the
				// coupon period
			
			case 2:
			for(int n=(previouscoupon.get(Calendar.MONTH)+1);
			n<settlementdate.get(Calendar.MONTH);n++) {
				temp.set(Calendar.MONTH,n);
				actualdays+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			actualdays+=(previouscoupon.getActualMaximum
			(Calendar.DAY_OF_MONTH)-previouscoupon.get(Calendar.DATE));
			actualdays+=settlementdate.get(Calendar.DATE);
			if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
			//Actual/365 (eg,UK gov)
			{
				samedays=(nextcoupondate.get(Calendar.DATE)
				-settlementdate.get(Calendar.DATE));
				setPreviousCoupondays((double)(((365.0/coupons)
				-samedays)/(365.0/coupons)));
				//requires annual multiple of coupon rate
				return (double)(samedays/(365.0/coupons));
				//returns 1/365 ths of the annual coupon rate
			}
			actualday= (settlementdate.getActualMaximum(Calendar.DAY_OF_MONTH)
			-settlementdate.get(Calendar.DATE));
			for(int i=(settlementdate.get(Calendar.MONTH)+1);
				i<nextcoupondate.get(Calendar.MONTH);i++) {
				temp.set(Calendar.MONTH,i);
				actualday+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			actualday+=nextcoupondate.get(Calendar.DATE);
			System.out.println("Actual days between coupon and settlement =="+actualdays);
			setPreviousCoupondays((double)(actualdays/
			(365.0/coupons)));
			return (double)(((365.0/coupons)-actualdays)/
			(365.0/coupons));
			
			
			case 3:
			for(int n=(previouscoupon.get(Calendar.MONTH)+1);
			n<settlementdate.get(Calendar.MONTH);n++) {
				temp.set(Calendar.MONTH,n);
				actualdays+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			actualdays+=(previouscoupon.getActualMaximum
			(Calendar.DAY_OF_MONTH)-previouscoupon.get(Calendar.DATE));
			actualdays+=settlementdate.get(Calendar.DATE);
			if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
			//Actual/365 or 366 in leap year (eg,
			{
				samedays=(nextcoupondate.get(Calendar.DATE)
				-settlementdate.get(Calendar.DATE));
				int total;
				total=(previouscoupon.getActualMaximum(Calendar.DAY_OF_YEAR)|nextcoupondate
				.getActualMaximum(Calendar.DAY_OF_YEAR))==366?366:365;
				setPreviousCoupondays((double)(((total/coupons)
				-samedays)/(total/coupons)));
				return (double)samedays/(total/coupons);
			}
			actualday= (settlementdate.getActualMaximum(Calendar.DAY_OF_MONTH)
			-settlementdate.get(Calendar.DATE));
			for(int i=(settlementdate.get(Calendar.MONTH)+1);
			i<nextcoupondate.get(Calendar.MONTH);i++) {
				temp.set(Calendar.MONTH,i);
				actualday+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			actualday+=nextcoupondate.get(Calendar.DATE);
			int totaldays;
			totaldays=(previouscoupon.getActualMaximum(Calendar.DAY_OF_YEAR)|
			nextcoupondate.getActualMaximum(Calendar.DAY_OF_YEAR))
			==366?366:365;
			System.out.println("Actual days between coupon and settlement =="+actualdays);
			setPreviousCoupondays((double)(actualdays/
			(totaldays/coupons)));
			return (double)(((totaldays/coupons)-actualdays)/
			(totaldays/coupons));
			
			
			
			case 4:
			if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
			// Coupon annual eg US Gov’t agency..
			{
				samedays=(nextcoupondate.get(Calendar.DATE)
				-settlementdate.get(Calendar.DATE));
				setPreviousCoupondays((double)((360.0)-samedays)/
				(360.0));
				return (double)samedays/(360.0);
			}
			actualday= (settlementdate.getActualMaximum(Calendar.DAY_OF_MONTH)
			- settlementdate.get(Calendar.DATE));
			for(int i=(settlementdate.get(Calendar.MONTH)+1);
			i<nextcoupondate.get(Calendar.MONTH);i++) {
				temp.set(Calendar.MONTH,i);
				actualday+=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
			}
			actualday+=nextcoupondate.get(Calendar.DATE);
			setPreviousCoupondays((double)((360.0)-actualday)/
			(360.0));
			return (double)actualday/(360.0);
			
			
			
			case 5:
			if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
			//30/360..one day only..(eg,US corporate)
			{
				int numsetd=settlementdate.get(Calendar.DATE);
				numsetd=numsetd==31?30:numsetd;
				int numd=nextcoupondate.get(Calendar.DATE);
				numd=((numd==31)&(numsetd==30))?30:numd;
				samedays=numd-numsetd;
				samedays=(nextcoupondate.get(Calendar.DATE)
				-settlementdate.get(Calendar.DATE));
				samedays=samedays==31?30:samedays;
				setPreviousCoupondays
				((double)((360.0/coupons)-samedays)/(360.0/coupons));
				return (double)samedays/(360.0/coupons);
			}
			int couponset;
			int dayset=settlementdate.getActualMaximum(Calendar.DAY_OF_MONTH);
			int dateset=settlementdate.get(Calendar.DATE);
			dayset=dayset==31?30:dayset;
			dateset=dateset==31?30:dateset;
			actualday=dayset-dateset;
			for(int i=(settlementdate.get(Calendar.MONTH)+1);
			i<nextcoupondate.get(Calendar.MONTH);i++) {
				temp.set(Calendar.MONTH,i);
				couponset=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				couponset=((couponset==31)&(dayset==30))?30:couponset;
				actualday+=couponset;
			}
			int coupdate=nextcoupondate.get(Calendar.DATE);
			coupdate=((coupdate==31)&(dateset==30))?30:coupdate;
			actualday+=coupdate;
			setPreviousCoupondays((double)((360.0/coupons)
			-actualday)/(360.0/coupons));
			return (double)actualday/(360.0/coupons);
			
			
			case 6:
			if(settlementdate.get(Calendar.MONTH)==nextcoupondate.get(Calendar.MONTH))
			//30e/360
			{
				int numdays=nextcoupondate.get(Calendar.DATE);
				numdays=numdays==31?30:numdays;
				int numsetdays=settlementdate.get(Calendar.DATE);
				numsetdays=numsetdays==31?30:numsetdays;
				samedays=numdays-numsetdays;
				setPreviousCoupondays((double)((360.0/coupons)
				-samedays)/(360.0/coupons));
				return (double)samedays/(360.0/coupons);
			}
			int couponsettle;
			int daysettle=settlementdate.getActualMaximum
			(Calendar.DAY_OF_MONTH);
			int datesettle=settlementdate.get(Calendar.DATE);
			daysettle=daysettle==31?30:daysettle;
			datesettle=datesettle==31?30:datesettle;
			actualday=daysettle-datesettle;
			for(int i=(settlementdate.get(Calendar.MONTH)+1);
			i<nextcoupondate.get(Calendar.MONTH);i++) {
				temp.set(Calendar.MONTH,i);
				couponsettle=temp.getActualMaximum(Calendar.DAY_OF_MONTH);
				couponsettle=couponsettle==31?30:couponsettle;
				actualday+=couponsettle;
			}
			int coupondate=nextcoupondate.get(Calendar.DATE);
			coupondate=coupondate==31?30:coupondate;
			actualday+=coupondate;
			System.out.println("actualdays "+actualday);
			setPreviousCoupondays((double)((360.0/coupons)-actualday)
			/(360.0/coupons));
			return (double)actualday/(360.0/coupons);
			default: throw new AssertionError
			("Unknown market :"+flagvalue);
		}
	}
}

