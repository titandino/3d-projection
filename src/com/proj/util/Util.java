package com.proj.util;

public class Util {
	
	public static String fmt(float d) {
	    if(d == (int) d)
	        return String.format("%f",(int)d);
	    else
	        return String.format("%s",d);
	}

}
