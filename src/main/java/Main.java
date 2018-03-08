
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Main {
	PDDocument document;
	PDFont waFontTitle;
	PDFont waFontWrite;
	PDFont enFontTitle = PDType1Font.TIMES_BOLD;
	PDFont enFontWrite;

	public static void main(String args[]) {
		new Main();
	}
	void monthTitle(int year, int month) throws IOException{
		PDPage page = document.getPage(month*2-1);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		Rectangle r = new Rectangle(20, 680, 100, 100);
		monthTitle(contentStream, r, year, month);
		contentStream.close();
	}
	class PartsOfPDF{
		PDFont waFontTitle;
		PDFont waFontWrite;
		PDFont enFontTitle = PDType1Font.TIMES_BOLD;
		PDFont enFontWrite;
		PDPageContentStream contentStream;
		PartsOfPDF(){
			
		}
	}
	void monthTitle(PDPageContentStream contentStream, Rectangle r, int year, int month) throws IOException{
		contentStream.beginText();
		float size = 0.9f * r.height;
		contentStream.setFont(enFontTitle, size);
		float w = enFontTitle.getStringWidth(""+month) * size / 1000f;
		contentStream.newLineAtOffset(r.x, r.y);
		contentStream.newLineAtOffset((r.width-w)/2, 0.125f*r.height);
		contentStream.showText(""+month);
		contentStream.newLineAtOffset(w, 0f);
		contentStream.setFont(enFontTitle, size/4);
		contentStream.showText(""+year);
		contentStream.newLineAtOffset(0f, size/4);
		contentStream.setFont(enFontTitle, size/4);
		contentStream.showText(ClnUtils.monthName[month-1]);
		contentStream.newLineAtOffset(0f, size/4);
		contentStream.setFont(waFontTitle, size/5);
		contentStream.showText(ClnUtils.yearNameJ(year)+" "+ClnUtils.monthNameJ[month-1]);
		contentStream.endText();

//		contentStream.moveTo(r.x, r.y);
//		contentStream.lineTo(r.x, r.y+r.height);
//		contentStream.lineTo(r.x+r.width, r.y+r.height);
//		contentStream.lineTo(r.x+r.width, r.y);
//		contentStream.lineTo(r.x, r.y);
//		contentStream.stroke();
	}
	void makeDay(LocalDateTime day) throws IOException{
		int year = day.getYear();
		int month = day.getMonth().getValue();
		int d = day.getDayOfMonth();
		int dIndex = (d<=16)? d : d-16;
		PDPage page = document.getPage(month*2-((d<17)?1:0));
		PDPageContentStream contentStream = new PDPageContentStream(document, page,true,true,true);
		Rectangle r = new Rectangle(20, 680-41*dIndex, 580, 41);
		makeDay(contentStream, r, year, month, d);
		contentStream.close();
	}
	void makeDay(PDPageContentStream contentStream, Rectangle r, int year, int month, int day) throws IOException{
		String you = ClnUtils.weekName[ClnUtils.zeller(year, month, day)];
		String you0 = ClnUtils.isHly(year, month, day);
		if(r.height * 5 < r.width){ //横長矩形の場合
			contentStream.beginText();
			float size = 0.9f * r.height;
			contentStream.setFont(enFontTitle, size);
			float w = enFontTitle.getStringWidth(""+day) * size / 1000f;
			contentStream.newLineAtOffset(r.x+r.height/2-w/2, r.y+0.125f*r.height);
			contentStream.showText(""+day);
			contentStream.setFont(waFontTitle, 0.25f * r.height);
			contentStream.newLineAtOffset(w, 0.5f * r.height);
			contentStream.showText(you0 /*上の段*/);
			contentStream.newLineAtOffset(0, -0.25f * r.height);
			contentStream.setFont(enFontTitle, 0.25f * r.height);
			contentStream.showText(you /*中段*/);
			int kouFlg = ClnUtils.check72(year, month, day);
			if(kouFlg>0){
				kouFlg = (kouFlg + 8) % 72;
				contentStream.setFont(waFontTitle, 0.25f * r.height);
				contentStream.newLineAtOffset(0, -0.25f * r.height);
				contentStream.showText(ClnUtils.kou72[kouFlg] /*下段：休日など*/);
			}
			contentStream.endText();
		}
		contentStream.moveTo(r.x, r.y);
		contentStream.lineTo(r.x, r.y+r.height);
		contentStream.lineTo(r.x+r.width, r.y+r.height);
		contentStream.lineTo(r.x+r.width, r.y);
		contentStream.lineTo(r.x, r.y);
		contentStream.stroke();
		for(int i = 1; i < 24; i++){
			float l = 1f;
			if(i%3==0)l=2f;
			if(i%6==0)l=3f;
			if(i%12==0)l=4f;
			contentStream.moveTo(r.x+r.width/24f*i, r.y+l);
			contentStream.lineTo(r.x+r.width/24f*i, r.y);
			contentStream.stroke();
		}
	}
	void drawPage() throws IOException{
		for(int m = 1; m <= 12; m++){
			monthTitle(2018, m);
		}
		LocalDateTime dy = LocalDateTime.of(2018, 1, 1, 12, 0, 0);
		while(dy.getYear()==2018){
			makeDay(dy);
			dy=dy.plusDays(1);
		}
	}
	void appendPage(int month) throws IOException{
		PDPage page = document.getPage(month*2-1);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		Rectangle r = new Rectangle(20, 680, 100, 100);
		monthTitle(contentStream, r, 2017, month);
		contentStream.close();
	}
	void appendPage() throws IOException{
		PDPage page = new PDPage();
		document.addPage(page);
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
		
		Rectangle r = new Rectangle(20, 680, 100, 100);
		monthTitle(contentStream, r, 2017, 8);
		for(int d = 1; d < 17; d++){
			Rectangle rd = new Rectangle(20, 680-41*d, 580, 41);
			makeDay(contentStream, rd, 2017, 8, d);
		}
		contentStream.close();
	}
	Main(){
		try {
			document = new PDDocument();
			waFontTitle = testFont();
			waFontWrite = testFont();
			document.addPage(new PDPage());
			for(int m = 1; m <= 12; m++){
				document.addPage(new PDPage());
				document.addPage(new PDPage());
			}
			document.addPage(new PDPage());
			drawPage();
//			appendPage();
			document.save("C:\\Users\\NobIsh\\Desktop\\helloworld.pdf");
			document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	PDType0Font testFont2() throws IOException {
//		 TrueTypeCollection trueTypeCollection = new TrueTypeCollection(new
//		 File("‪C:\\Windows\\Fonts\\timesi.ttf"));
//		 TrueTypeFont ttFont = trueTypeCollection.getFontByName("TimesNewRomanPSMT");
//		 PDType0Font font = PDType0Font.load(document, ttFont.getOriginalData(),
//		 true);
			PDType0Font font = PDType0Font.load(document, new File("‪C:\\Windows\\Fonts\\timesi.ttf"));
			return font;
	}

	PDType0Font testFont() throws IOException {
		// TrueTypeCollection trueTypeCollection = new TrueTypeCollection(new
		// File("C:\\Windows\\Fonts\\msmincho.ttc"));
		// TrueTypeFont ttFont = trueTypeCollection.getFontByName("MS-Mincho");
		// PDType0Font font = PDType0Font.load(document, ttFont.getOriginalData(),
		// true);
		PDType0Font font = PDType0Font.load(document, new File("C:/Windows/Fonts/ARIALUNI.TTF"));
		return font;
	}
}