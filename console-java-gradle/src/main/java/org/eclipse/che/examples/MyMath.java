package org.eclipse.che.examples;
public class MyMath
{
	static double getCorrelation(double[] f1,double[] f2){
		double corr=0,xySum=0;
		double xSum=0,x2Sum=0;
		double ySum=0,y2Sum=0;
		if(f1.length!=f2.length) return 0.0f;
		for(int i=0;i<f1.length;i++){
			xSum+=f1[i];
			ySum+=f2[i];
			x2Sum+=f1[i]*f1[i];
			y2Sum+=f2[i]*f2[i];
			xySum+=f1[i]*f2[i];
		}
		double n=f1.length;
		corr=(xySum/n-xSum/n*ySum/n)/
			Math.sqrt(x2Sum/n-xSum/n*xSum/n)/
			Math.sqrt(y2Sum/n-ySum/n*ySum/n);
		return corr;
	}
	static double getVariationK(double[] f1){		
		double xSum=0,x2Sum=0;
		for(int i=0;i<f1.length;i++){
			xSum+=f1[i];
			x2Sum+=f1[i]*f1[i];
		}
		double n=f1.length;
		return Math.sqrt(x2Sum/n-xSum/n*xSum/n)/xSum*n;
	}
}
