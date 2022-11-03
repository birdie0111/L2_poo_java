package svg;

import java.util.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.awt.geom.*;

public class Parser extends AUtilParser {
	
	private File file;
	private Vector<Groupe> svg = new Vector<Groupe>();
	private double width = 0;
	private double height = 0;
	/**
	*	constructeur Parser depuis un String
	*	@param file le chemin du fichier SVG à parser
	*/
	public Parser (String file) throws IOException {
		this(new File(file));
	}
	/**
	*	constructeur par clonage
	*	@param old le Parser à cloner
	*/
	public Parser (Parser old) throws IOException {
		this(old.file);
	}
	/**
	*	constructeur Parser
	*	@param file un fichier SVG à parser
	*/
	public Parser (File f) throws IOException {
		this.file = f;
		lecture();
		if(d.isEmpty())
			throw new IOException();
		searchTaille();
		if ((i=d.indexOf("<g", i))!=-1) {
			searchTranslate();
			while((i=Math.min(
					((d.indexOf("<path", i)==-1)?d.indexOf("<g", i):d.indexOf("<path", i)),
					((d.indexOf("<g", i)==-1)?d.indexOf("<path", i):d.indexOf("<g", i))
				  ))!=-1) {
				if (i==d.indexOf("<g", i)) {
					svg.add(new Groupe(bloc("</g>"), transX, transY));
					i=d.indexOf("</g>", i);
				} else {
					svg.add(new Groupe(bloc("/>"), transX, transY));
					i++;
				}
			}
		}
	}
	private void searchTaille() {
		i=d.indexOf("<svg");
		int endSVG = d.indexOf('>', i);
		int idxwidth = d.indexOf("width=\"",i);
		int idxheight = d.indexOf("height=\"",i);
		if (idxwidth<endSVG) {
			i = idxwidth;
			i+=7;
			width=readDouble();
		}
		if (idxheight<endSVG) {
			i = idxheight;
			i+=8;
			height=readDouble();
		}
	}
	private void searchTranslate() {
		int endG = d.indexOf('>', i);
		int translate = d.indexOf("translate(",i);
		if (translate<endG) {
			i=d.indexOf("translate(",i);
			i+=10;
			readSpace();
			transX=readDouble();
			readSpace();
			if(isDouble())
				transY=readDouble();
		} else i++;
	}
	/**
	* méthode interne
	* @return String le bloc du curseur à la balise fermante
	* @param ferme la balise fermante du bloc (non comprise)
	*/
	private String bloc (String ferme) {
		StringBuffer buf = new StringBuffer();
		buf.append(d.charAt(i++));
		int end = d.indexOf(ferme, i);
		for (; i<end; i++) {
			buf.append(d.charAt(i));
		}
		return buf.toString();
	}
	
	public String toString() {
		if(file == null)
			return "no file";
		return file.getPath();
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	/**
	*	utilisable pour affichage
	*	@return Path2D[] un tableau de path
	*/
	public Vector<Path2D> getPath() {
		Vector<Path2D> p = new Vector<Path2D>();
		for(int i = 0; i <svg.size(); i++)
			p.add(svg.get(i).getPath());
		return p;
	}
	
	/**
	*	optimise le placement
	*	PAR COPIE OU MUTATION ???!!
	*/
	public void optimize() {
		// TODO
	}
	
	/**
	* methode interne
	*	@see Parser(File file)
	*/
	private void lecture() {
		char[] buf = new char[1024];
		StringBuilder s = new StringBuilder();
		int lu;
		Reader in = null;
		try {
			in = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
		} catch (FileNotFoundException e) {
			d = "";
		}
		try {
			while((lu = in.read(buf, 0, buf.length)) > 0) {
				s.append(buf, 0, lu);
			}
		} catch (IOException e) {
			d = "";
		}
		try {
			in.close();
		} catch (IOException e) {
			d = "";
		}
		d = s.toString();
	}
}