package svg;

import java.util.*;
import java.awt.geom.*;

public class Path extends AUtilParser {
	
	private Path2D.Double path;
	
	public Path(String txtD, double tX, double tY) {
		this();
		d=txtD;
		transX+=tX;
		transY+=tY;
		path.moveTo(transX, transY);
		for(i=0; i<d.length();) {
			switch(d.charAt(i)) {
				case 'z':case 'Z':path.closePath();i++;break;
				case 'm': parsem(); break;
				case 'M': parseM(); break;
				case 'l': parsel(); break;
				case 'L': parseL(); break;
				case 'h': parseh(); break;
				case 'H': parseH(); break;
				case 'v': parsev(); break;
				case 'V': parseV(); break;
				case 'c': parsec(); break;
				case 'C': parseC(); break;
				case ' ': i++;break;
				default:break;
			}
		}
	}
	public Path(String d, double transX) {
		this(d, transX, 0);
	}
	public Path(String d) {
		this(d, 0, 0);
	}
	public Path() {
		path = new Path2D.Double();
	}
	public Path2D getPath() {
		return path;
	}
	
	private void parsem() {
		double x=readDouble();
		double y=readDouble();
		if(path.getCurrentPoint()!=null) {
			x+=path.getCurrentPoint().getX();
			y+=path.getCurrentPoint().getY();
		}
		path.moveTo(x,y);
		readSpace();
		if(isDouble())
			parsel();
	}
	private void parseM() {
		double x=readDouble();
		double y=readDouble();
		path.moveTo(x+transX,y+transY);
		readSpace();
		if(isDouble())
			parseL();
	}
	private void parsel() {
		double x=readDouble();
		double y=readDouble();
		if(path.getCurrentPoint()!=null) {
			x+=path.getCurrentPoint().getX();
			y+=path.getCurrentPoint().getY();
		}
		path.lineTo(x,y);
		readSpace();
		if(isDouble())
			parsel();
	}
	private void parseL() {
		double x=readDouble();
		double y=readDouble();
		path.lineTo(x+transX,y+transY);
		readSpace();
		if(isDouble())
			parseL();
	}
	private void parseh() {
		double x=readDouble();
		double y=transY;
		if(path.getCurrentPoint()!=null) {
			x+=path.getCurrentPoint().getX();
			y=path.getCurrentPoint().getY();
		}
		path.lineTo(x,y);
		readSpace();
		if(isDouble())
			parseh();
	}
	private void parseH() {
		double x=readDouble();
		double y=transY;
		if(path.getCurrentPoint()!=null) {
			y=path.getCurrentPoint().getY();
		}
		path.lineTo(x+transX,y);
		readSpace();
		if(isDouble())
			parseH();
	}
	private void parsev() {
		double x=transX;
		double y=readDouble();
		if(path.getCurrentPoint()!=null) {
			x=path.getCurrentPoint().getX();
			y+=path.getCurrentPoint().getY();
		}
		path.lineTo(x,y);
		readSpace();
		if(isDouble())
			parsev();
	}
	private void parseV() {
		double x=transX;
		double y=readDouble();
		if(path.getCurrentPoint()!=null) {
			x=path.getCurrentPoint().getX();
		}
		path.lineTo(x,y+transY);
		readSpace();
		if(isDouble())
			parseV();
	}
	private void parsec() {
		double x1=readDouble();
		double y1=readDouble();
		double x2=readDouble();
		double y2=readDouble();
		double x=readDouble();
		double y=readDouble();
		if(path.getCurrentPoint()!=null) {
			x+=path.getCurrentPoint().getX();
			y+=path.getCurrentPoint().getY();
			x1+=path.getCurrentPoint().getX();
			y1+=path.getCurrentPoint().getY();
			x2+=path.getCurrentPoint().getX();
			y2+=path.getCurrentPoint().getY();
		}
		path.curveTo(x1, y1, x2, y2, x, y);
		readSpace();
		if(isDouble())
			parsec();
	}
	private void parseC() {
		double x1=readDouble();
		double y1=readDouble();
		double x2=readDouble();
		double y2=readDouble();
		double x=readDouble();
		double y=readDouble();
		path.curveTo(x1+transX, y1+transY, x2+transX, y2+transY, x+transX, y+transY);
		readSpace();
		if(isDouble())
			parseC();
	}
}