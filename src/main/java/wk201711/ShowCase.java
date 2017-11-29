package wk201711;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ShowCase extends JScrollPane{
	private BufferedImage image;
	double scale = 1.0;
	ShowCase(){
		super();
		setViewportView(disp);
	}
	public void setImage(BufferedImage image){
		this.image = image;
		disp.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
	}
	JPanel disp = new JPanel(){
		public void paint(Graphics g){
			if(image!=null){
				g.drawImage(image, 0, 0, this);
			}
		}
	};
}