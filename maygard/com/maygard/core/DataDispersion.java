package com.maygard.core;

public class DataDispersion{

	//arithmetic mean for a single list//
	public static double mean(double[] x)
	{
		double total=0.0;
		for(int i=0;i<x.length;i++)
		total+=x[i];
		return total/x.length;
	}

	public static double mean(double[][] x) //returns expected value//	//for variable * probability//
	throws StatisticalException{
		double total= 0.0;
		double probability=0.0;
		for(int i=0;i<x.length;i++)//the number of rows//
		{
		total+=(x[i][0]*x[i][1]);
		probability+=x[i][1];
		}
		if(probability!=1.0){
			throw new StatisticalException("~Probability does not sum up to 1.0");
		}
		else{
			return total;
		}

		
	}

	public static double variance(double[] v1)
	//variance of a single variable with equal likelihood//
	{
		double sumd=0.0;
		double total=0.0;
		for(int i=0;i<v1.length;i++)
		{
		    total+=v1[i];
		    sumd+= Math.pow(v1[i],2);//sum of x sqrd
		}
		return (sumd-total)/((v1.length)-1);
		//true value of convergence as length is large//
	}

	//variance of a variable with different
	//probability of otcome//
	public static double variance(double[][] v1)
	throws StatisticalException{
		double sumd=0.0;
		double total=0.0;
		double totalpow=0.0;
		double probability=0.0;
		for(int i=0;i<v1.length;i++)
		{
		total+=(v1[i][0]*v1[i][1]);//mean or expected value//
		totalpow+=(Math.pow(v1[i][0],2)*v1[i][1]);//E[X2]//
		probability+=v1[i][1];
		}
		if(probability!=1.0)
			throw new StatisticalException("~Probability does not sum up to 1.0");
		total=Math.pow(total,2);
		return (totalpow-total);
	}
	public static double standardDeviation(double s1)
	// computes standard deviation for variance s1//
	{
		double sdev;
		sdev=Math.sqrt(s1);
		return sdev;
	}

	
	
	
	
	
	
	
	public static double[] dumean(double[][] x)//arithmetic mean//
	//for a double list//
	{
		double x1=0.0;
		double y=0.0;
		double[] total=new double[2];
		for(int i=0;i<x.length;i++)
		{
		    x1+=x[i][0];
		    y+=x[i][1];
	    }
		total[0]=x1/x.length;
		total[1]=y/x.length;
		return total;
	}

	public static double convmean(double[] x)// for large length//
	{
	double total=0.0;
	for(int i=0;i<x.length;i++)
	total+=x[i];
	return total/(x.length-1);
	}

    //variance of a single variable with equal likelihood//
    //for double inputs//
	public static double[] variances(double[][]v1)
	{
		double[] output=new double[2];
		double sumd=0.0;
		double sumd1=0.0;
		double total=0.0;
		double total1=0.0;
		for(int i=0;i<v1.length;i++)
		{
			total+=v1[i][0];
			total1+=v1[i][1];
			sumd+= Math.pow(v1[i][0],2);//sum of x sqrd
			sumd1+= Math.pow(v1[i][1],2);//sum of x sqrd
		}
		total=(Math.pow(total,2)/v1.length);//sum of [x]sqrd/n
		total1=(Math.pow(total1,2)/v1.length);//sum of [x]sqrd/n
		output[0]=((sumd-total)/((v1.length)-1));
		output[1]=((sumd1-total1)/((v1.length)-1));
		return output;
	}

	public static double covar(double[][] outcomes)
	//equally likely outcomes//
	{
		double sa=0.0;
		double sb=0.0;
		double product=0.0;
		int size=outcomes.length;
		for(int i=0;i<size;i++)
		{
			sa+=outcomes[i][0];//x values or proprtions//
			sb+=outcomes[i][1];//y values or proportions//
		}
		double samn=sa/size;//expected value of x//
		double sbmn=sb/size;//expected value of y//
		for(int i=0;i<size;i++)
		{
		product+=((outcomes[i][0]-samn) * (outcomes[i][1]-sbmn));
		//sum of the products ofdeviations//
		}
		return product/size;//covariance//
	}

	public static double covar2(double[][] outcomexyp)//inputs of non equal joint outcomes//
	{
		// data in the form A value, B value . Probability(P)
		// of B and A the same//
		double productx=0.0;
		double producty=0.0;
		int size=outcomexyp.length;
		double covariance=0.0;
		for(int i=0;i<size;i++)
		{
			// A[n][0],B[n][1],P[n][2]..........//
			productx+= outcomexyp[i][0]*outcomexyp[i][2];//
			//probability * observed value//
			producty+=outcomexyp[i][1]*outcomexyp[i][2];
		}
		for(int j=0;j<size;j++)
		{
			double xdevs=outcomexyp[j][0]-productx;
			double ydevs=outcomexyp[j][1]-producty;
			double devproduct=xdevs*ydevs;
			double covprobs=devproduct*outcomexyp[j][2];
			covariance+=covprobs;
		}
		return covariance;
	}

	public static double correlation(double cov,double sd1,double sd2)
	{
		double cor=cov/sd1*sd2;
		return cor;
	}
}

