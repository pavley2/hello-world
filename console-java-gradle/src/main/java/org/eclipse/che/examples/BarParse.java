package org.eclipse.che.examples;
import java.io.*;
import java.util.*;

public class BarParse
{
	public static ArrayList <Bar> decompress(ArrayList <Bar> barArray)
	{
		ArrayList <Bar> newBarArray=new ArrayList <Bar>(barArray.size()*3);
		float firstE,secondE;
		long startTime=System.currentTimeMillis();
		System.out.println("decompression started at "+new Date(startTime));
		int i=-1;
		for(Bar bar:barArray){
			i++;
			newBarArray.add(new Bar(bar));
			if(bar.getClose()>bar.getOpen()){
				firstE=bar.getLow();
				secondE=bar.getHigh();
			}
			else{
				firstE=bar.getHigh();
				secondE=bar.getLow();
			}
			newBarArray.add(new Bar().setDateTime(bar.getDateTime()).shiftSec(20).setOpen(firstE));
			newBarArray.add(new Bar().setDateTime(bar.getDateTime()).shiftSec(40).setOpen(secondE));
		}
		System.out.println("decompressed to "+newBarArray.size()+" bars at " + (System.currentTimeMillis() - startTime) / 1000 + " secs");
		return newBarArray;
	}
	public static void showAll(ArrayList <Bar> barArray)
	{
		System.out.println("<DATE>,<TIME>,<OPEN>,<HIGH>,<LOW>,<CLOSE>,<VOL>");
		for(Bar b:barArray){
			System.out.println(b);
		}
	}
	static void showExamples(ArrayList <Bar>barArray)
	{
		int size=barArray.size();
		int[] number={0,size/3,size/3+1,size/3+2
			,size/3+3,size/3+4,size/3+5,size/3+6,size/3+7,size-1};
		for(int i=0;i<number.length;i++)
			System.out.println(barArray.get(number[i]));
	}
	static ArrayList <Bar>read(String fname)
	{
		try
		{
			String osfnime=fname;
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(osfnime));
			long lastTime=System.currentTimeMillis();
			System.out.println("reading started at "+new Date(lastTime));
			ArrayList <Bar> barArray=(ArrayList <Bar>)ois.readObject();
			System.out.println("have read " + barArray.size() + " bars at " + (System.currentTimeMillis() - lastTime) / 1000 + " secs");
			ois.close();
			return 	barArray;	
        }
        catch (Exception ex)
		{
            System.out.println(ex.getMessage());
			return null;
        }  
	}
	static void writeBarArray(ArrayList <Bar>barArray,String fname){
		String osfnime=fname;
		try
		{
			ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(osfnime));
			long lastTime = System.currentTimeMillis();
			System.out.println("writing started at "+new Date(lastTime));			
			oos.writeObject(barArray);
			oos.close();
			System.out.println("written " + (barArray.size() - 1) + " bars at " + (System.currentTimeMillis() - lastTime) / 1000 + " secs");
			
		}
		catch (IOException e)
		{}
	}
	static ArrayList <Bar> parseBarArray(String ifname)
	{
		try
		{
			ArrayList <Bar> bars=new ArrayList<Bar>();
			BufferedReader br=new BufferedReader(new FileReader(ifname));
			String brstring;
//			Bar[] bars=new Bar[500000];
			int i=0,maxSize=15000;
			int last_i=i;
			br.readLine();
			long lastTime=System.currentTimeMillis();
			long startTime=lastTime;
			while ((brstring = br.readLine()) != null)
			{
				bars.add(new Bar(brstring));
				i++;
				if(System.currentTimeMillis() - lastTime >= 1000)
				{
					System.out.println(i + " added, bars/sec: " + (i - last_i));
					lastTime = System.currentTimeMillis();
					last_i = i;
				}
				//		if(i==maxSize)break;
			}
			lastTime = System.currentTimeMillis();
			System.out.println("added " + i + " bars at "+(lastTime-startTime)/1000+" secs");
			br.close();
			return bars;
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
			return null;
		}
	}
	
}
