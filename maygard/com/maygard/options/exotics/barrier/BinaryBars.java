/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.options.exotics.barrier;

//This class provides Valuation for the various Barrier Options
public class BinaryBars {
	
	public BinaryBars(double rate, double yield, double barlevel,
	int period) {
		r=rate;
		y=yield;
		barrier=barlevel;
		tau=period==1?(1.0/(24*365)):period==2?(1.0/(365.0)):period==3?
		(1.0/(52.0)):period==4?(1.0/(12.0)):0.0;
	}
	
	private double r;
	private double y;
	private double h;
	private double tau;
	private double barrier;
	
	public double downInch(double asset, double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;//Correction for discrete time (fromcontinuous)
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return cash;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldich= s.barops(asset,0.0,cash,volatility,time,1);
		return barriervaldich;
	}
	
	public double upInch(double asset ,double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return cash;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluich= s.barops(asset,0.0,cash,volatility,time,2);
		return barriervaluich;
	}
	
	public double upInah(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)//No ned to continue
		return asset;//the asset value is the barrier at hit
		double cash=h;//X=H
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluiah= s.barops(asset,0.0,cash,volatility,time,4);
		return barriervaluiah;
	}
	
	public double downInah(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return asset;
		double cash=h;//X=H
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldiah= s.barops(asset,0.0,cash,volatility,time,3);
		return barriervaldiah;
	}
	
	public double downIncx(double asset, double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldicx= s.barops(asset,0.0,cash,volatility,time,5);
		return barriervaldicx;
	}
	
	public double upIncx(double asset , double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluicx= s.barops(asset,0.0,cash,volatility,time,6);
		return barriervaluicx;
	}
	
	public double downInax(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldiax= s.barops(asset,0.0,0.0,volatility,time,7);
		return barriervaldiax;
	}
	
	public double upInax(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluiax= s.barops(asset,0.0,0.0,volatility,time,8);
		return barriervaluiax;
	}
	public double downOcon(double asset , double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldoc= s.barops(asset,0.0,cash,volatility,time,9);
		return barriervaldoc;
	}
	
	public double upOcon(double asset , double cash, double volatility,
	double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluoc=s.barops(asset,0.0,cash,volatility,time,10);
		return barriervaluoc;
	}
	
	public double downOaon(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldoa=s.barops(asset,0.0,0.0,volatility,time,11);
		return barriervaldoa;
	}
	
	public double upOaon(double asset , double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluoa=s.barops(asset,0.0,0.0,volatility,time,12);
		return barriervaluoa;
	}
	
	public double downIconc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldic=s.barops(asset,strike,cash,volatility,
		time,13);
		return barriervaldic;
	}
	
	public double upIconc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluicc=s.barops(asset,strike,cash,volatility,
		time,14);
		return barriervaluicc;
	}
	
	public double downIaonc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldiac=s.barops(asset,strike,cash,volatility,
		time,15);
		return barriervaldiac;
	}
	
	public double upIaonc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluiac=s.barops(asset,strike,cash,volatility,
		time,16);
		return barriervaluiac;
	}
	
	public double downIconp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldicp=s.barops(asset,strike,cash,volatility,
		time,17);
		return barriervaldicp;
	}
	
	public double upIconp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluicp=s.barops(asset,strike,cash,volatility,
		time,18);
		return barriervaluicp;
	}
	
	public double downIaonp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldiap= s.barops(asset,strike,cash,volatility,
		time,19);
		return barriervaldiap;
	}
	
	public double upIaonp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluiap=s.barops(asset,strike,cash,volatility,
		time,20);
		return barriervaluiap;
	}
	
	public double downOconc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldocc=s.barops(asset,strike,cash,volatility,
		time,21);
		return barriervaldocc;
	}
	
	public double upOconc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluocc=s.barops(asset,strike,cash,volatility,
		time,22);
		return barriervaluocc;
	}
	
	public double downOaonc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldoac=s.barops(asset,strike,cash,volatility,
		time,23);
		return barriervaldoac;
	}
	
	public double upOaonc(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluoac=s.barops(asset,strike,cash,volatility,
		time,24);
		return barriervaluoac;
	}
	
	public double downOconp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldocp= s.barops(asset,strike,cash,volatility,
		time,25);
		return barriervaldocp;
	}
	
	public double upOconp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluocp=s.barops(asset,strike,cash,volatility,
		time,26);
		return barriervaluocp;
	}
	
	public double downOaonp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset<=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaldoap=s.barops(asset,strike,cash,volatility,
		time,27);
		return barriervaldoap;
	}
	
	public double upOaonp(double asset , double strike, double cash,
	double volatility,double time) {
		h= barrier>asset? (barrier*Math.exp(Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		h= barrier<asset? (barrier*Math.exp(-Math.sqrt(tau)*volatility*0.5826)):
		barrier;
		if(asset>=h)
		return 0.0;
		BinaryBarrierOption s=new BinaryBarrierOption(r,y,h);
		double barriervaluoap=s.barops(asset,strike,cash,volatility,
		time,28);
		return barriervaluoap;
	}
}

