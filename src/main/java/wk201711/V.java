package wk201711;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.PDFRenderer;

public class V extends JFrame implements ActionListener{
	MainPanel mp = new MainPanel(128);
	public static void main(String[] args) {
		new V();
	}
	V(){
		setSize(800, 600);
		setJMenuBar(menuBar);
		addMenu();
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exiting();
			}
		});
		setContentPane(mp);
		setVisible(true);
	}
	
	JMenuBar menuBar = new JMenuBar();
	private void addMenus(String menuTitle, String[] menus){
		JMenu menu = new JMenu(menuTitle);
		for(String s: menus){
			if(s.equals("-")){
				menu.addSeparator();
			}else{
				JMenuItem a = new JMenuItem(s);
				a.addActionListener(this);
				menu.add(a);
			}
		}
		menuBar.add(menu);
	}
	private void addMenu(){
		addMenus("File", "New,Open,-,eXit".split(","));
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equalsIgnoreCase("exit")){
			exiting();
		}else if(e.getActionCommand().equalsIgnoreCase("open")){
			JFileChooser fc = new JFileChooser();
//			fc.setMultiSelectionEnabled(true);
			if(fc.showOpenDialog(this)==0){
//				loadFiles(fc.getSelectedFiles());
				loadPdfFiles(fc.getSelectedFile());
			}
		}
		repaint();
	}
	void exiting(){
		System.exit(1);
	}
	class LoadThread extends Thread{
		MainPanel mainPanel;
		File targetFile;
		List <PDDocument> docs;
		public LoadThread(List <PDDocument> docs, MainPanel mainPanel, File targetFile) {
			super();
			this.mainPanel = mainPanel;
			this.targetFile = targetFile;
			this.docs = docs;
		}
		public void setTargetFile(File targetFile) {
			this.targetFile = targetFile;
		}
		public void setMainPanel(MainPanel mainPanel) {
			this.mainPanel = mainPanel;
		}
		public void run() {
			try {
				PDDocument doc = PDDocument.load(targetFile);
				Splitter splitter = new Splitter();
				docs.addAll(splitter.split(doc));
				for(PDDocument d: docs){
					PDFRenderer renderer = new PDFRenderer(d) ;
					mp.addImage(renderer.renderImage(0));
					mp.repaint();
				}
				doc.close();
			} catch (InvalidPasswordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void loadPdfFiles(File file){
		System.out.println(file.getAbsolutePath());
		LoadThread ldThread = new LoadThread(docs, mp, file);
		ldThread.start();
	}
	ArrayList <PDDocument> docs = new ArrayList <PDDocument>();
	void loadFiles(File[] files){
		for(File file : files){
			try {
				mp.addImage(ImageIO.read(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
