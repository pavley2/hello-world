package org.eclipse.che.examples;
import java.util.*;
public class Position
{
	private static int qty=0,curBar=0,minsToCheckCor;
	private static float pointsTP=0.0f;
	private static float pointsSL=0.0f;
	private static DateTime curDateTime=null,checkingDT;
	private static float curPrice=0.0f;
	private static float avPrice=0.0f;
	private ArrayList<Deal>deals=new ArrayList<Deal>();
	private float totalProfit=0.0f;
	private static float curProfit=0.0f;
	private static ArrayList<Bar>bars;
	private float[] params;
	private double profitCorrel,profitVarK;
	boolean dealsDifferMins=false;
	
	public Position(float[] params){
		this.params=params;
		pointsTP=params[0];
		pointsSL=params[1];
		minsToCheckCor=(int)params[3];
		qty=0;avPrice=0.0f;
	}
	public void deal(int barNo)
	{
		curPrice=bars.get(barNo).getOpen();
		curDateTime=bars.get(barNo).getDateTime();
		curBar=barNo;
		close(getPriceToClose());
		open(getQtyToOpen());
	}
	
	public static void setBars(ArrayList<Bar> bars)
	{
		Position.bars = bars;
	}

	public static ArrayList<Bar> getBars()
	{
		return bars;
	}
	public double getProfitVarK()
	{
		return profitVarK;
	}

	public void calcProfitVarK()
	{
		double profitPerBar[]=new double[deals.size()];
		int i=0,prevBar=0;
		for(Deal d:deals){
			profitPerBar[i]=d.getTotalProfit()/d.getCloseBar();
			i++;
			prevBar=d.getCloseBar();
		}
		profitVarK=MyMath.getVariationK(profitPerBar);
	}

	public boolean isDealsDifferMins()
	{
		return dealsDifferMins;
	}

	public void checkDealsDiffMinsetat()
	{
		long lastDealMinute=0;
		long curDealMinute;
		for(Deal d:deals){
			curDealMinute=d.getOpenDateTime().toDate().getTime()/60000;
			if(curDealMinute==lastDealMinute){
				dealsDifferMins=false;
				return;
			}
			lastDealMinute=curDealMinute;
			curDealMinute=d.getCloseDateTime().toDate().getTime()/60000;
			if(curDealMinute==lastDealMinute){
				dealsDifferMins=false;
				return;
			}
			lastDealMinute=curDealMinute;
		}
		dealsDifferMins=true;
	}

	public void showExtFigures()
	{
		if(deals.size()==0){
			System.out.println("no deals");
			return;
		}
		int i=1;
		int firstBar=deals.get(0).getOpenBar();
		int prevLastDealCloseBar=firstBar;
		float prevLastDealTotalProfit=0f;
		int lastBar=deals.get(deals.size()-1).getCloseBar();
		int duration=lastBar-firstBar;
		long step=duration/10;
		for(Deal d:deals){
			if(d.getCloseBar()-firstBar>step*i){
				System.out.println("profit for "+(d.getCloseBar()-
					prevLastDealCloseBar)+" bars: "+
					(d.getTotalProfit()-prevLastDealTotalProfit)+
					" (total "+d.getCloseBar()+" bars with profit "+d.getTotalProfit());
				prevLastDealCloseBar=d.getCloseBar();
				prevLastDealTotalProfit=d.getTotalProfit();
				i++;
			}			
		}					 
	}
	double getProfitCorrel(){
		return profitCorrel;
	}
	public void calcProfitCorrel(){
		double[] closeBars=new double[deals.size()];
		double[] totalProfits=new double[deals.size()];
		int i=0;
		for(Deal d:deals){
			closeBars[i]=d.getCloseBar();
			totalProfits[i]=d.getTotalProfit();
			i++;
		}
		profitCorrel=MyMath.getCorrelation(closeBars,totalProfits);
	}

	@Override
	public String toString()
	{
		String s="";
		for(int i=0;i<params.length;i++)
			s+=String.format("%.1f",params[i])+" ";
		s=s.substring(0,s.length()-1);
		return "params:{"+s+"} deals:"+deals.size()+
			" total result:"+totalProfit+" profit varyK:"+String.format("%.2f",profitVarK)+
			" profit correl:"+String.format("%.2f",profitCorrel);
	}
	public float getTotalProfit()
	{
		return totalProfit;
	}
	
	public ArrayList<Deal> getDeals()
	{
		return deals;
	}
	
	private void open(int qtyToOpen)
	{
		if(qtyToOpen==0)return;
		avPrice=(avPrice*qty+qtyToOpen*curPrice)/(qty+qtyToOpen);
		qty=qty+qtyToOpen;
		Deal newDeal=new Deal();
		newDeal.setOpenDateTime(curDateTime);
		newDeal.setOpenPrice(curPrice);
		newDeal.setOpenBar(curBar);
		newDeal.setQty(qtyToOpen);
		deals.add(newDeal); 		
	}

	public boolean close(float priceToClose)
	{
		if(priceToClose==0.0f)return false;
		Deal lastDeal=deals.get(deals.size()-1);
		curProfit=qty*(priceToClose-avPrice);
		lastDeal.setProfit(curProfit);
		lastDeal.setCloseDateTime(curDateTime);
		lastDeal.setCloseBar(curBar);
		lastDeal.setBars(curBar-lastDeal.getOpenBar());
		lastDeal.setClosePrice(priceToClose);
		qty=0;
		avPrice=0;
		totalProfit=totalProfit+curProfit;
		lastDeal.setTotalProfit(totalProfit);
		return true;
	}

	private float getPriceToClose()
	{
		if(qty==0)return 0.0f;
		if(qty>0){
			if(curPrice>=avPrice+pointsTP)
				return avPrice+pointsTP;
			if(curPrice<=avPrice-pointsSL)
				return avPrice-pointsSL;
		}
		else{
			if(curPrice<=avPrice-pointsTP)
				return avPrice-pointsTP;
			if(curPrice>=avPrice+pointsSL)
				return avPrice+pointsSL;
		}
		if(curDateTime.getHour()==23&&curDateTime.getMinute()==49)return curPrice;
		return 0.0f;		
	}

	private int getQtyToOpen()
	{
		if(qty!=0)return 0;
//		if(curDateTime.getMinute()==(int)params[2])
//			return (int)params[3];
		for(int i=curBar-1;i>-1;i--){
			checkingDT=bars.get(i).getDateTime();
			if(!checkingDT.isSameDate(curDateTime)) return 0;
			if(curDateTime.getHour()*60+curDateTime.getMinute()-checkingDT.getHour()*60-checkingDT.getMinute()>minsToCheckCor) return 0;
		
		}
		
		return 0;
	}
}
