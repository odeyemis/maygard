/**
 Copyright (C) 2013 Sijuola F. Odeyemi

 This source code is released under the Artistic License 2.0.
 
 Code license: http://www.opensource.org/licenses/artistic-license-2.0.php

 Contact: odeyemis@gmail.com
 */
package com.maygard.core;

// Class to compute basic machine parameters. Returns smallest number available (After
//Press et al and extensions by Besset, DH)
public class Csmallnumber
{
static private double negmacprec=0;
static private int radix=0;
static private double smallnumber=0;
private static void cRad()
{
double a=1.0d;
double tmp1,tmp2;
do{a+=a;
tmp1=a+1.0d;
tmp2=tmp1-a;
}
while(tmp2-1.0d!=0.0d);
double b=1.0d;
while(radix==0)
{
b+=b;
tmp1=a+b;
radix=(int)(tmp1-a);
}
}
private static void snum()
{
double floatradix=getRadix();
double inverseradix=1.0d/floatradix;
double fullmantissa=1.0d-floatradix
*getnegmachineprec();
while(fullmantissa!=0.0d)
{
smallnumber=fullmantissa;
fullmantissa*=inverseradix;
}
}
public static int getRadix()
{
if(radix==0)
cRad();
return radix;
}
private static void compnegprec()
{
double frad=getRadix();
double invrad=1.0d/frad;
negmacprec=1.0d;
double tmp=1.0d-negmacprec;
while(tmp-1.0d!=0.0d)
{
negmacprec*=invrad;
tmp=1.0d-negmacprec;
}
}
public static double getnegmachineprec()
{
if(negmacprec==0)
compnegprec();
return negmacprec;
}
public static double getSmallnumber()
{
if(smallnumber==0)
snum();
return smallnumber;
}
}

