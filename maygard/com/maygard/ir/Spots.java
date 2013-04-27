package com.maygard.ir;

public class Spots {
	
	public Spots() {
	}
	
	public double[] spotFcoupon(double[][]pcdata)
	{
		int n= pcdata.length;
		double[]spots=new double[n];
		double price;
		int s=0;
		double indx=1.0;
		spots[0]=((100.0/pcdata[0][0])-1);
		price=(pcdata[0][0]/100.0);
		for(s=1;s<n;s++){
			indx++;
			spots[s]=(Math.exp(1/indx*Math.log((pcdata[s][1]+100.0)
			/(pcdata[s][0]-(pcdata[s][1]*price))))-1);
			price+=(Math.exp(-indx*Math.log(1+spots[s])));
		}
		return spots;
	}
	public double[] spotFcoupon(double[][]pcdata,int periods)
	// for period frequency of annual coupons
	{
		int n=pcdata.length;
		double[]spots=new double[n];
		double price;
		double temp=0;
		int s=0;
		double indx=1.0;
		spots[0]=((100.0/pcdata[0][0])-1);
		price=(pcdata[0][0]/100.0);/* first entry */
		for(s=1;s<n;s++){
			indx++;
			spots[s]=(Math.exp(1/indx*Math.log(((pcdata[s][1]/periods)+100.0)/
			(pcdata[s][0]-((pcdata[s][1]/
			periods)*price))))-1);
			price+=(Math.exp(-indx*Math.log(1+spots[s])));
		}
		return spots;
	}
	/* returns the n period coupon for par price given the spot rate */
	public double parCoupon(double[]spots,int nperiod)
	{
		int i=spots.length;
		int j=0;
		int counter=0;
		double flowdisc=0.0;
		double finaldisc=0.0;
		if(nperiod>i){
		    return -1.0;
		}
		finaldisc=(1.0-(Math.exp(-nperiod*Math.log(1.0+spots[(nperiod-1)]))));
		for(double d:spots)
		{
		    if (j<nperiod)
		    {
		        j++;
		        flowdisc+=((Math.exp(-j*Math.log(1.0+d))));
		    }
		}
		return(finaldisc/flowdisc);
	}
}