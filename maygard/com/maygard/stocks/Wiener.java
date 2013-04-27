/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.stocks;

import java.util.Random;

public class Wiener {
	
	public Wiener() {
	}
	
	public double wienerProc( double t)
	{
		Random r= new Random();
		double epsilon=r.nextGaussian();
		return Math.sqrt(t)*epsilon;
	}
}

