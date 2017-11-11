import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Sched2017 {
	private static final Log LOG = LogFactory.getLog(Sched2017.class);
	PDDocument doc;
	PDFont enFontTitle;
	PDFont enFontWrite;
	PDFont waFontTitle;
	PDFont waFontWrite;
	public static void main(String[] args) {
		new Sched2017();
	}
	Sched2017(){
		doc = new PDDocument();
		enFontTitle = PDType1Font.COURIER_BOLD;
		enFontWrite = PDType1Font.TIMES_ROMAN;
		try {
			waFontTitle = prepareFont(new File("C:\\Windows\\Fonts\\HGRSMP.TTF"));
			waFontWrite = prepareFont(new File("C:\\Windows\\Fonts\\HGRSMP.TTF"));
		} catch (IOException e) {
			LOG.error(e);
		}
//		doc.addPage(new MonthlyLeftPage(2017,8));
		doc.addPage(new PDPage());

		try {
			File file = File.createTempFile("aaa", "bbb");
			file.delete();
			file.mkdir();
			save(new File(file,"helloworld.pdf"));
			System.out.println(file.getAbsolutePath()+"/"+"helloworld.pdf");			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	class MonthlyLeftPage extends Page{
		MonthlyLeftPage(int year, int month){
			super();
			drawDays();
		}
		@Override
		void drawDays() {
			int i = 100;
			Rectangle r = new Rectangle(i,i,i,i);
			try {
				drawString(r, "A", 1, enFontTitle);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void save(File file){
		try {
			doc.save(file);
		} catch (IOException e) {
			LOG.error(e);
		}
	}
	PDFont prepareFont(File file) throws IOException{
		if(file.getName().endsWith(".ttf")){
			return PDType0Font.load(doc, file);
		}else{
			return null;
		}
	}
	abstract class Page extends PDPage{
		int year = 2017;
		int month = 8;
		int day = 26;
		boolean isLeft = true;
		PDPageContentStream contentStream;
		Page(){
			super();
			try {
				contentStream = new PDPageContentStream(doc, this);
				drawDays();
				contentStream.close();
			} catch (IOException e) {
				LOG.debug(e);
			}
		}
		abstract void drawDays();
		/**
		 * @param r
		 * @param str
		 * @param flg 0:左 1:中央寄せ 2:右詰
		 * @param font
		 * @throws IOException
		 */
		void drawString(Rectangle r, String str, int flg, PDFont font) throws IOException{
			float fontSize = r.height*0.9f;
			float strWidth = font.getStringWidth(str)*fontSize/1000f;
			contentStream.beginText();
			contentStream.setFont(font, fontSize);
			contentStream.newLineAtOffset(r.x, r.y);
			if(flg==0){
				contentStream.newLineAtOffset(0f, r.height*0.18f);
			}else if(flg==1){
				contentStream.newLineAtOffset((r.width-strWidth)/2f, r.height*0.18f);
			}else{
				contentStream.newLineAtOffset((r.width-strWidth), r.height*0.18f);
			}
			contentStream.showText(str);
			contentStream.endText();
		}
	}
}
