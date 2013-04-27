package com.maygard.core;

/*
This abstract class provides the method derivation to provide
functionality for providing the derivative of a single function. The class has
its abstract method deriveFunction extended by the using class which provides
the controlling logic to provide the single functions for evaluation.

The method derivation uses the technique of difference quotients to arrive at
an approximation of a function. The method being implemented is based on the
general definition of the derivative (i.e. THE OFFICIAL DERIVATIVE FORMULA).

 */
public abstract class Derivative
{
    public abstract double deriveFunction(double fx);
    //returns a double...... the function//
    public double h;// degree of accuracy in the calculation//
    public double derivation(double InputFunc)
    {
        double value;
        double X2=deriveFunction(InputFunc-h);
        double X1=deriveFunction(InputFunc+h);
        value=((X1-X2)/(2*h));
        return value;
    }
}
