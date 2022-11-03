package svg;

import java.util.*;
import java.awt.geom.*;

public class Groupe extends AUtilParser {
	
	private Vector<Path> path = new Vector<Path>();
	
	public Groupe(String g, double tX, double tY) {
		d=g;
		transX=tX;
		transY=tY;
		if(d.indexOf("<g")!=-1) {
			searchTranslateGroupe();
			while((i=d.indexOf("<path", i))!=-1) {
				pathInit();
				i=d.indexOf("/>",i);
			}
		} else {
			pathInit();
		}
	}
	private void searchTranslateGroupe() {
		int endG = d.indexOf('>', i);
		int translate = d.indexOf("translate(",i);
		if (translate!=-1 && translate<endG) {
			i=d.indexOf("translate(",i);
			while(!((d.charAt(i)<'9' && d.charAt(i)>'0')||d.charAt(i)=='-'))i++;
			readSpace();
			transX+=readDouble();
			readSpace();
			if(isDouble())
				transY+=readDouble();
		}
	}
	private void pathInit() {
		double transPathX=transX;
		double transPathY=transY;
		if((d.indexOf("translate(", i)!=-1) && (d.indexOf("/>", i)==-1 || d.indexOf("translate(", i)<d.indexOf("/>", i))) {
			i=d.indexOf("translate(", i);
			while(!((d.charAt(i)<'9' && d.charAt(i)>'0')||d.charAt(i)=='-'))i++;
			transPathX+=readDouble();
			readSpace();
			if(isDouble())
				transPathY+=readDouble();
			i=d.lastIndexOf("<path", i);
		}
		i=d.indexOf(" d=\"", i);
		i+=4;
		path.add(new Path(d.substring(i, d.indexOf('"', i)),transPathX ,transPathY));
	}
	public Path2D getPath() {
		Path2D p = new Path2D.Double();
		for(int i = 0; i<path.size(); i++)
			p.append(path.get(i).getPath(), false);
		return p;
	}
}