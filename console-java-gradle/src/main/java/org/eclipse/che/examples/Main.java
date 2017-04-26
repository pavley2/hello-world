package org.eclipse.che.examples;

import java.io.*;
import java.util.*;
import java.text.*;
public class Main
 {
	public static void main(String[] args)
	{
//		Scanner input = new Scanner(System.in);
//		System.out.print("Enter maxX: ");
//		int maxX = input.nextInt();
//		input.close();	
//		startMainProgram();
        int arr=1;
        setElems(arr);
        System.out.println(arr);
	}
    private static void setElems(int arr){
        arr=5;
    }
	private static void startMainProgram()
	{
//		String fname="/storage/sdcard0/Finam/SPFB.Si_160101_170129.txt";Bar.openInd=5;	
//		String fname="SPFB.Si_170201_170211.txt";Bar.openInd=6;
		String fname="SPFB.Si_160101_170326.txt";Bar.openInd=6;
		ArrayList <Bar> barList=BarParse.parseBarArray(fname);
//		BarParse.parseAndWriteBarArray(fname, fname+ ".dat");
//		barList=BarParse.read(fname + ".dat");
		barList=BarParse.decompress(barList);
		BarParse.showExamples(barList);
		testAll(barList);
	}
	private static void testAll(ArrayList <Bar>barList)
	{
		ArrayList<Position> results=new ArrayList<Position>();
		Position.setBars(barList);
		long milis =System.currentTimeMillis();
		for(float par0=0.3f;par0<=0.7;par0+=0.1)//5
		for(float par1=0.5f;par1<=1;par1+=0.1)//6
		for(float par2=0.5f;par2<=1;par2+=0.1)//6
		for(float par3=5;par3<=15;par3+=1)//11
		for(float par4=15;par4<=30;par4+=1)//16
		//31680
		{
			if((System.currentTimeMillis()-milis)>=1000){
				System.out.println(results.size()+" paramSets tested");
				milis=System.currentTimeMillis();
			}
			float[] params={par0,par1,par2,par3,par4};
			results.add(testParamSet(barList.size(),params));		
		}
		Collections.sort(results, new Comparator<Position>() {
			public int compare(Position p1, Position p2) {
				return -Double.compare(
				  p1.getTotalProfit(),p2.getTotalProfit());
			}
		});
		int count=0;
		for(Position res:results){
			if(res.getProfitVarK()>0.5)continue;
			System.out.println(res);
			count++;
			for(Deal d:res.getDeals()){
				System.out.println(d);
			}
			if(count>0)break;
		}
		results.get(0).showExtFigures();
	}		
	private	static Position testParamSet(int barsSize,float[]params){
		Position pos=new Position(params);
		for(int i=0;i<barsSize;i++){
			pos.deal(i);
		}
		if(pos.getDeals().size()>0&&pos.getDeals().get(pos.getDeals().size()-1).getClosePrice()==0.0f)
			pos.close(Position.getBars().get(barsSize-1).getOpen());
//		pos.calcProfitVarK();
//		pos.calcProfitCorrel();
		return pos;
	}	
}
