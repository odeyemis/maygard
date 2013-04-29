/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is release under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;


//Computation of the Binomial Tree
public class BinomPrice {
	
	public BinomPrice() {
	}
	
	public BinomPrice(double u, double d, double p) {
		this.upvalue=u;
		this.downvalue=d;
		this.prob=p;
	}
	private double upvalue=0.0;
	private double downvalue=0.0;
	private double prob=0.0;
	public double[] binomprob(int n,int nodes,int k,double probability)
	{//altering nodes from n goes from 0 to node, k is starting point from 0
		double[]nodeprobs=new double[nodes-(k-1)];
		nodeprobs[0]=(Math.log(((Function.binom(n,k)*Math.pow(probability, k))
		*Math.pow((1.0-probability), (n-k)))));
		int h=1;
		for (int j=k+1;j<(nodes+1);j++)// from 1 to k inclusive
		//Does the strip and each node in it
		{
		nodeprobs[h]=(nodeprobs[h-1]+(Math.log(probability*(n-j+1))
		-Math.log((1-probability)*j)));
		h++;
		}
		return nodeprobs;
	}
	
	public double[] binomTprice(int n,int nodes,int h,double price) {
		double stripsum=0.0;
		double[]nodeprices=new double[nodes-(h-1)];
		int k=0;
		for (int j=h;j<(nodes+1);j++)// from h to nodes inclusive...
		//Does the strip n and each node in it
		{
		nodeprices[k]=(price*Math.pow(upvalue,j)*Math.pow(downvalue,(n-j)));
		k++;
		}
		return nodeprices;
	}
	
	public double[][] binodeVals(int n,int nodes,int h,double price) {
		int k=0;
		double[][] nodeval=new double[nodes-(h-1)][2];
		nodeval[k][0]=Math.log(((Function.binom(n,h)*Math.pow(prob,h))
		*Math.pow((1.0-prob), (n-h))));
		nodeval[k][1]=(Math.log(price*Math.pow(upvalue,h))+Math.log(Math.pow
		(downvalue,(n-h))));
		k=1;
		for (int j=h+1;j<(nodes+1);j++)// from 1 to k inclusive....
		//Does the strip and each node in it
		{
			nodeval[k][0]=(nodeval[k-1][0]+(Math.log(prob*(n-j+1))
			-Math.log((1-prob)*j)));
			nodeval[k][1]=(Math.log(price*Math.pow(upvalue,j))
			+Math.log(Math.pow(downvalue,(n-j))));
			k++;
		}
		return nodeval;
	}
	
	public double cumbinomDistL(double k, double n, double x)
	{//Does the basic probability for that node
		Ibeta m= new Ibeta();
		double inverseprob;
		inverseprob=m.betai(n-k,k+1.0, 1.0-x);
		// For direct computation
		return inverseprob;
	}
	
	public double cumbinomDist( double k, double n, double x) {
		Ibeta j= new Ibeta();
		double cumprob;
		cumprob=j.betai(k, (n-k+1.0), x);
		return cumprob;
	}
}

