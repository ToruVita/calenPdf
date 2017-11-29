package wk201711;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainPanel extends JPanel{
	private ArrayList <BufferedImage> images = new ArrayList <BufferedImage>();
	private int index = -1;
	private int gridSize = 100;
	JPanel thumbPanel = new JPanel(){
		public void paint(Graphics g){
			int x = 0;
			for(BufferedImage im : images){
				g.drawImage(im, x, 0, gridSize, gridSize,this);
				x+=gridSize;
			}
			g.drawRect(gridSize*index, 0, gridSize, gridSize);
		}
	};
	
	ShowCase showCase;
	MainPanel(){
		this(128);
	}
	MainPanel(final int gridSize){
		super();
		this.gridSize = gridSize;
		setLayout(new BorderLayout());
		thumbPanel.setPreferredSize(new Dimension(120,gridSize));
		thumbPanel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				index = e.getX() / gridSize;
				showCase.setImage(images.get(index));
				repaint();
			}
		});
		add(new JScrollPane(thumbPanel), BorderLayout.SOUTH);
		add(showCase = new ShowCase(), BorderLayout.CENTER);
	}
	public void addImage(BufferedImage image){
		images.add(image);
		thumbPanel.setPreferredSize(new Dimension(gridSize*images.size(),gridSize));
	}
}
