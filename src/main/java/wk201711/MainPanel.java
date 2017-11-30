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
	ThumbnailPanel thumbPanel;
	ShowCase showCase;
	MainPanel(){
		this(128);
	}
	MainPanel(final int gridSize){
		super();
		this.gridSize = gridSize;
		setLayout(new BorderLayout());
		add(showCase = new ShowCase(), BorderLayout.CENTER);
		thumbPanel = new ThumbnailPanel(showCase, 128);
		thumbPanel.setImages(images);
		add(new JScrollPane(thumbPanel), BorderLayout.SOUTH);
	}
	public void addImage(BufferedImage image){
		images.add(image);
		thumbPanel.setPreferredSize(new Dimension(gridSize*images.size(),gridSize));
	}
}
