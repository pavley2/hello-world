package org.eclipse.che.examples;
import java.io.*;
import java.util.*;
import java.text.*;public class DateTime implements Serializable
{
	private int year=0,month=1,day=1,
	hour=0,minute=0,second=0;
	public DateTime(){	
	}
	public DateTime(DateTime d){
		this(d.getYear(),d.getMonth(),d.getDay(),d.getHour(),d.getMinute(),d.getSecond());
	}

	public boolean isSameDate(DateTime dt)
	{
		if(year==dt.year&&month==dt.month&&day==dt.day) return true;
		return false;
	}
//	public DateTime(Date d){
//		this(d.getYear(),d.getMonth(),d.getDate(),d.getHours(),d.getMinutes(),d.getSeconds());
//	}

	public void set(DateTime dt)
	{
		year=dt.year;month=dt.month;day=dt.day;
		hour=dt.hour;minute=dt.minute;second=dt.second;
	}
	@Override
	public String toString()
	{
		return new SimpleDateFormat("MM/dd/yy,HH:mm:ss").format(toDate());
	}
	public Date toDate(){
		return new Date(year,month,day,hour,minute,second);
	}
	public DateTime(int y,int m,int d,int h,int min,int s){
		year=y;month=m;day=d;
		hour=h;minute=min;second=s;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public int getYear()
	{
		return year;
	}

	public void setMonth(int month)
	{
		this.month = month;
	}

	public int getMonth()
	{
		return month;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getDay()
	{
		return day;
	}

	public void setHour(int hour)
	{
		this.hour = hour;
	}

	public int getHour()
	{
		return hour;
	}

	public void setMinute(int minute)
	{
		this.minute = minute;
	}

	public int getMinute()
	{
		return minute;
	}

	public void setSecond(int second)
	{
		this.second = second;
	}

	public int getSecond()
	{
		return second;
	}
}
