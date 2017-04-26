package org.eclipse.che.examples;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import java.text.*;
import java.util.logging.*;
public class Bar implements Serializable
{
	private float open;
	private float close;
	private float high;
	private float low;
	private DateTime dateTime;
	static int i,j,sLength,lastChar,openInd;
	static char ch;
	static String[] barData=new String[10];
	Bar(){
		dateTime=new DateTime();
		open=0;
		high=0;
		low=0;
		close=0;
	}
	Bar(DateTime d,float o,float h,float l,float c){
		dateTime=d;
		open=o;
		high=h;
		low=l;
		close=c;
	}	
	Bar(Bar b){
		dateTime=b.dateTime;
		open=b.open;
		high=b.high;
		low=b.low;
		close=b.close;
	}
//	Bar(DateTime dt, float o){
//		dateTime=dt;
//		open=o;
//	}
	Bar(String s){
		sLength=s.length();
		lastChar=-1;
		for(i=0,j=0;i<sLength;i++){
			ch=s.charAt(i);
			if(ch==','||ch=='/'||ch==':'){
				barData[j]=s.substring(lastChar+1,i);
				lastChar=i;
				j++;
			}
		}
		dateTime=new DateTime(Integer.parseInt(barData[2])+2000,Integer.parseInt(barData[0]),Integer.parseInt(barData[1]),Integer.parseInt(barData[3]),Integer.parseInt(barData[4]),0);
		open=Float.parseFloat(barData[openInd]);
		high=Float.parseFloat(barData[openInd+1]);
		low=Float.parseFloat(barData[openInd+2]);
		close=Float.parseFloat(barData[openInd+3]);
	}
	public Bar setDateTime(DateTime dt){
		dateTime.set(dt);
		return this;
	}
	public Bar shiftSec(int sec){
		dateTime.setSecond(dateTime.getSecond()+sec);
		return this;
	}
//	public DateTime getShiftedDateTime(int secToShift){
//		Calendar cal=Calendar.getInstance();
//		cal.setTime(dateTime.toDate());
//		cal.add(Calendar.SECOND,secToShift);
//		return new DateTime(cal.getTime());
//	}
	public float getHigh()
	{
		return high;
	}

	public float getLow()
	{
		return low;
	}
	public Bar setOpen(float o)
	{
		open=o;
		return this;
	}
	public float getOpen()
	{
		return open;
	}
	public float getClose(){
		return close;
	}
	public DateTime getDateTime(){
		return dateTime;
	}
	@Override
	public String toString()
	{
		return dateTime+" "+String.format("%.0f",open)+" "+
			String.format("%.0f",high)+" "+String.format("%.0f",low)
			+" "+String.format("%.0f",close);
	}
	
}
