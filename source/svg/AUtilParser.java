package svg;

import java.util.*;

public abstract class AUtilParser {
	
	protected double transX = 0;
	protected double transY = 0;
	protected int i=0;
	protected String d;
	
	protected boolean isDouble() {
		return (i<d.length() && ((d.charAt(i)<='9' && d.charAt(i)>='0') || d.charAt(i)=='.' || d.charAt(i)=='-' || d.charAt(i)=='e' || d.charAt(i)=='E'));
	}
	protected double readDouble() {
		while(!isDouble())i++;
		int min = i;
		while(isDouble())i++;
		int max = i;
		readSpace();
		return Double.parseDouble(d.substring(min, max));
	}
	protected void readSpace() {
		while(i<d.length() && (d.charAt(i)==' ' || d.charAt(i)==','))i++;
	}
}