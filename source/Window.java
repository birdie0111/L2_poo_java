import svg.*;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.*;

public class Window extends JFrame {

	private Graphics2D g;
	private Parser svg = null;
	private Vector <Path2D> svgPath = null;
	public void Createwindow(String title, int width, int height) {
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		
		Container c = this.getContentPane();
		JPanel buttons = new JPanel();
		JPanel jp1 = new Pane();
		JPanel jp2 = new JPanel();
		jp1.setBackground(Color.GRAY);
		jp2.setBackground(Color.BLUE);
		
		JButton next = new JButton("optimized");
		JButton pre = new JButton("previous");
		JButton select = new JButton("select an image");
		buttons.add(next);
		buttons.add(select);
		buttons.add(pre);
		JScrollPane scroll1 = new JScrollPane(jp1);
		c.add(buttons, BorderLayout.PAGE_START);
		c.add(scroll1, BorderLayout.CENTER);
		
		this.setVisible(true);
		
		// next button
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.remove(scroll1);
				c.add(jp2);
				c.revalidate();
				c.repaint();
				/*
				to do (optimise the image)
				*/
			}
		});
		//previous button
		pre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				c.remove(jp2);
				c.add(scroll1);
				c.revalidate();
				c.repaint();
			}
		});
		//select a file button
		select.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFileChooser file = new JFileChooser();
				file.setDragEnabled(true);
				int i = file.showOpenDialog(jp1);
				if (i == JFileChooser.APPROVE_OPTION){
					String path = file.getSelectedFile().getPath();
					try {
						svg = new Parser(path);
					} catch (IOException exception) {
						System.out.println("Fichier inexistant");
					}
				}
				scroll1.revalidate();
				scroll1.repaint();
				
			}
		});
	}
	public class Pane extends JPanel  {
		
		@Override
		public Dimension getPreferredSize() {
			if (svg != null)
				return new Dimension((int)svg.getWidth(), (int)svg.getHeight());
			else 
				return new Dimension(0,0);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(4.0f));
			g2.setPaint(Color.GREEN);
			if (svg != null) {
				svgPath = svg.getPath();
				for(int i = 0; i <svgPath.size(); i++)
					g2.draw(svgPath.get(i));
			}
		}
	}
}