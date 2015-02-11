package com.cnvp.paladin.utils.beetl.fn;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.Format;

public class DateFormat implements Format
{

	public Object format(Object data, String pattern)
	{
		if (data == null)
			return null;
		if (Date.class.isAssignableFrom(data.getClass()))
		{
			SimpleDateFormat sdf = null;
			if (pattern == null)
			{
				sdf = new SimpleDateFormat();
			}
			else
			{
				sdf = new SimpleDateFormat(pattern);
			}
			return sdf.format((Date) data);

		}
		else if (data.getClass() == Long.class)
		{
//			System.out.println(data);
			SimpleDateFormat sdf = null;
			long date = Long.valueOf(data.toString());
			if (pattern == null)
			{
				sdf = new SimpleDateFormat();
			}
			else
			{
				sdf = new SimpleDateFormat(pattern);
			}
			if(data.toString().length() != 13)
				date = date*1000;
//			System.out.println(date);
			return sdf.format(new Date(date));

		}		
		else if (data.getClass() == Integer.class)
		{
			if (data.toString().length()!=10) 
				return null;
			long date = Long.valueOf(data.toString());
			SimpleDateFormat sdf = null;
			if (pattern == null)
			{
				sdf = new SimpleDateFormat();
			}
			else
			{
				sdf = new SimpleDateFormat(pattern);
			}
			return sdf.format(new Date(date*1000));
		}
		else
		{
			throw new RuntimeException("Arg Error:Type should be Date:" + data.getClass());
		}

	}

}
