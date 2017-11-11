package sched.page;

import java.awt.Rectangle;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public abstract class Page extends PDPage{
//	PDFont enFontTitle = PDType1Font.TIMES_ROMAN;
	PDFont waFontTitle;
	public void setWaFontTitle(PDFont waFontTitle) {
		this.waFontTitle = waFontTitle;
	}
	PDDocument doc;
	Page(PDDocument doc){
		super();
		this.doc = doc;
	}
	public void makePage(){
		try {
			contentStream = new PDPageContentStream(doc, this);
			drawDays();
			contentStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	PDPageContentStream contentStream;
	abstract void drawDays();
	protected void drawString(Rectangle r, String str, int flg, PDFont font) throws IOException{
		float fontSize = r.height*0.9f;
		float strWidth = font.getStringWidth(str)*fontSize/1000f;
		contentStream.beginText();
		if(containsUnicode(str)){
			contentStream.setFont(font, fontSize);
		}else{
			contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
		}
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
	protected void drawStrings13(Rectangle r, String[] strs) throws IOException{
		Rectangle r0 = new Rectangle(r);
		r0.width = r.height;
		drawString(r0, strs[0], 1, waFontTitle);
		r0.height = r.height / 3 ;
		r0.width = r.width - r.height;
		r0.x = r.x + r.height;
		for(int i = 0; i < 3; i++){
			r0.y = r.y + i*r.height/3-(i-1)*r.height/20;
			drawString(r0, strs[i+1], 0, waFontTitle);
		}
	}
	protected void drawLine(Rectangle r, int flg, float strokeWidth) throws IOException{
		contentStream.setLineWidth(strokeWidth);
		if(((flg/4)%2)==1){
			contentStream.moveTo(r.x, r.y+r.height);
			contentStream.lineTo(r.x+r.width, r.y+r.height);
			contentStream.stroke();
		}
		if(((flg/2)%2)==1){
			contentStream.moveTo(r.x, r.y);
			contentStream.lineTo(r.x, r.y+r.height);
			contentStream.stroke();
		}
		if(((flg/4)%2)==1){
			contentStream.moveTo(r.x, r.y);
			contentStream.lineTo(r.x+r.width, r.y);
			contentStream.stroke();
		}
		if(((flg/8)%2)==1){
			contentStream.moveTo(r.x+r.width, r.y);
			contentStream.lineTo(r.x+r.width, r.y+r.height);
			contentStream.stroke();
		}
	}
	public static boolean containsUnicode(String str) {
		for(int i = 0 ; i < str.length() ; i++) {
			char ch = str.charAt(i);
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(ch);
			if (Character.UnicodeBlock.HIRAGANA.equals(unicodeBlock))
				return true;
			if (Character.UnicodeBlock.KATAKANA.equals(unicodeBlock))
				return true;
			if (Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS.equals(unicodeBlock))
				return true;
			if (Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS.equals(unicodeBlock))
				return true;
			if (Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock))
				return true;
		}
		return false;
	}
}
