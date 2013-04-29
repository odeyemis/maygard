/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic  2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options;

public class OptionConvexity {
	
	public OptionConvexity() {
	}
	
	/** assumes that the array contains a series of 3 points in the order
	x1<x2<x3 in the series pairs strike price - option price */
	public double convexcheck(double[][]stprseries)
	{
		double w=((stprseries[2][0]-stprseries[1][0])/
		(stprseries[2][0]-stprseries[0][0]));
		return stprseries[1][1]<= (w*stprseries[0][1]
		+(stprseries[2][1]-w*stprseries
		[2][1]))?
		((w*stprseries[0][1])+(stprseries[2][1]-w*stprseries
		[2][1])):0.0;
	}
}