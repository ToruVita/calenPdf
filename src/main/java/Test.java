import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import sched.page.MonthlyPage;

public class Test {

	public static void main(String[] args) {
		new Test();
	}
	PDDocument doc = new PDDocument();
//	try {
//		File file = new File("C:/Windows/Fonts/meiryo.ttc");
//		TrueTypeCollection collection = new TrueTypeCollection(file);
//		enFontTitle = PDType0Font.load(doc, collection.getFontByName("Meiryo"), true);
//		collection.close();
//	} catch (IOException e) {
//		e.printStackTrace();
//	}
	PDFont waFontTitle;

	void makeMonth(int month){
		MonthlyPage p0 = new MonthlyPage(doc, month, waFontTitle);
		doc.addPage(p0);
		p0.makePage();
		MonthlyPage p1 = new MonthlyPage(doc, month, false, waFontTitle);
		doc.addPage(p1);
		p1.makePage();
	}
	Test(){
		try {
			waFontTitle = PDType0Font.load(doc, new File("C:\\Windows\\Fonts\\yumin.ttf"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File file = new File("C:\\Users\\NobIsh\\Desktop", "A.pdf");
		LocalDateTime nowLocalDt = LocalDateTime.now();
		int year = nowLocalDt.getYear();
		int month = nowLocalDt.getMonth().getValue();
		for(int m = 1; m <=12 ; m++){
			makeMonth(m);
		}
//		makeMonth(month);
//		makeMonth(month+1);
		try {
			doc.save(file);
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

