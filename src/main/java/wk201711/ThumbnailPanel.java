package wk201711;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ThumbnailPanel extends JPanel implements MouseListener{
	private ArrayList <BufferedImage> images;
	ShowCase papa;
	int index;
	int targetIndex=0;
	BufferedImage thumbnail;
	public void setImages(ArrayList<BufferedImage> images) {
		this.images = images;
	}
	public void paint(Graphics g){
		int x = 0;
		for(BufferedImage im : images){
			g.drawImage(im, x, 0, gridSize, gridSize,this);
			x+=gridSize;
		}
		g.drawRect(gridSize*index, 0, gridSize, gridSize);
		g.drawRect(gridSize*targetIndex, 0, 1	, gridSize);
	}
	int gridSize;
	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}
	public void setPapa(ShowCase papa) {
		this.papa = papa;
	}
	ThumbnailPanel(ShowCase papa, int gridSize){
		super();
		setGridSize(gridSize);
		setPapa(papa);
		setPreferredSize(new Dimension(120,gridSize));
		addMouseListener(this);
	}
	public void mouseClicked(MouseEvent e) {
		index = e.getX() / gridSize;
		papa.setImage(images.get(index));
		repaint();
	}
	public void mousePressed(MouseEvent e) {
		index = e.getX() / gridSize;
		repaint();
	}
	public void mouseReleased(MouseEvent e) {
		targetIndex = (e.getX()+gridSize/2) / gridSize;
		reOrder(targetIndex, index);
		repaint();
	}
	void reOrder(int t, int s){
		if(t!=s){
			BufferedImage im = images.get(s);
			images.remove(s);
			images.add(t, images.get(s));
		}
	}
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
