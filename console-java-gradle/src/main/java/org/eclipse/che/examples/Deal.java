package org.eclipse.che.examples;
import java.util.*;

public class Deal
{
	private Float profit,totalProfit=0.0f;
	private DateTime openDateTime,closeDateTime;
	private float openPrice,closePrice;
	private int qty,openBar,closeBar,bars;

	public void setOpenBar(int openBar)
	{
		this.openBar = openBar;
	}

	public int getOpenBar()
	{
		return openBar;
	}

	public void setCloseBar(int closeBar)
	{
		this.closeBar = closeBar;
	}

	public int getCloseBar()
	{
		return closeBar;
	}

	public void setBars(int bars)
	{
		this.bars = bars;
	}

	public int getBars()
	{
		return bars;
	}
	
	public void setTotalProfit(Float totalProfit)
	{
		this.totalProfit = totalProfit;
	}

	public Float getTotalProfit()
	{
		return totalProfit;
	}

	@Override
	public String toString()
	{
		String s=openDateTime+" "+closeDateTime+" "+qty+" "
			+openPrice+" "+closePrice+" "+profit+" "+totalProfit;
		return s;
	}

	
	public void setQty(int qty)
	{
		this.qty = qty;
	}

	public int getQty()
	{
		return qty;
	}

	public void setOpenDateTime(DateTime openDateTime)
	{
		this.openDateTime = openDateTime;
	}

	public DateTime getOpenDateTime()
	{
		return openDateTime;
	}

	public void setCloseDateTime(DateTime closeDateTime)
	{
		this.closeDateTime = closeDateTime;
	}

	public DateTime getCloseDateTime()
	{
		return closeDateTime;
	}

	public void setOpenPrice(float openPrice)
	{
		this.openPrice = openPrice;
	}

	public float getOpenPrice()
	{
		return openPrice;
	}

	public void setClosePrice(float closePrice)
	{
		this.closePrice = closePrice;
	}

	public float getClosePrice()
	{
		return closePrice;
	}

	public void setProfit(Float profit)
	{
		this.profit = profit;
	}

	public Float getProfit()
	{
		return profit;
	}
}

