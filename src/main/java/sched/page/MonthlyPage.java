package sched.page;

import java.awt.Rectangle;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;

import calen.ClnUtils;


public class MonthlyPage extends Page{
	int year = 2017;
	int month;
	boolean isLeft = true;
	public MonthlyPage(PDDocument doc, int month, PDFont waFontTitle) {
		this(doc, month, true, waFontTitle);
	}
	public MonthlyPage(PDDocument doc, int month, boolean isLeft, PDFont waFontTitle) {
		super(doc);
//		this.year = year;
		this.month = month;
		this.isLeft = isLeft;
		super.waFontTitle = waFontTitle;
	}
	String[] titleStrings(int year, int month){
		String[] ttls = new String[4];
		ttls[0] = String.format("%d", month);
		ttls[1] = "平成"+(year-1988)+"年 " + ClnUtils.getMonthNameJ(month);
		ttls[2] = ""+year;
		ttls[3] = ClnUtils.getMonthName(month);
		return ttls;
	}
	String[] dayStrings(int year, int month, int day){
		String[] wks = new String[4];
		wks[0] = ""+day;
		wks[1] = ClnUtils.get2472kou(year, month, day);
		wks[2] = ""+ClnUtils.getMoonPhase(year, month, day);
		wks[3] = ClnUtils.getWeekName(year, month, day, 0);
		return wks;
	}
	void drawMoon(Rectangle r2, int moonPhase) throws IOException{
		Rectangle r = new Rectangle(r2);
		r.width = r2.height/2;
		r.height = r.width;
		r.x = r2.x + r2.height;
		r.y = r2.y + r2.height / 4;
		int mr = ((moonPhase<15)?-1:1)*r.width*7/10;
		int mr2 = ((moonPhase%15)-7)*r.width*7/80;
		contentStream.setLineWidth(1f);
		contentStream.moveTo(r.x+r.width/2, r.y);
		contentStream.curveTo(r.x+r.width/2-mr, r.y, r.x+r.width/2-mr, r.y+r.height, r.x+r.width/2, r.y+r.height);
		contentStream.curveTo(r.x+r.width/2-mr2, r.y+r.height, r.x+r.width/2-mr2, r.y, r.x+r.width/2, r.y);
		contentStream.stroke();
	}
	int maxDate(int year, int month){
		if(month == 4 || month == 6 || month == 9 || month == 11){
			return 30;
		}else if(month==2){
			if(year%100==0) return 28;
			else if(year%4==0) return 29;
			else return 28;
		}else{
			return 31;
		}
		
	}
	@Override
	void drawDays() {
		float w = getBleedBox().getWidth();
		float h = getBleedBox().getHeight()/18;
		if(isLeft){
			Rectangle rt= new Rectangle(10,(int) (16*h),(int)w-20,(int)h*2);
			try {
				drawStrings13(rt, titleStrings(year, month));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			int b = (int) (h/3);
			int bw = (int) (b*1.2);
			int nextMonth = (month+1>12)?month+1:1;
			int nextMonthYear = (month==1)?year+1:year;
			int maxD = maxDate(nextMonthYear, nextMonth);
			Rectangle rm= new Rectangle((int)(w-bw*(maxD+2)),(int) (17*h), bw, b*2);
			try {
				drawString(rm, ""+nextMonth, 1, waFontTitle);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(int d = 1; d <= maxD; d++){
				Rectangle rt= new Rectangle((int)(w-bw*(maxD+2-d)),(int) (17*h), bw, b);
				try {
					drawString(rt, ""+d, 1, waFontTitle);
					rt.y -= rt.height;
//					drawString(rt, "日", 1, waFontTitle);
					drawString(rt, ClnUtils.getWeekName(nextMonthYear, nextMonth, d, 3), 1, waFontTitle);
					rt.y -= rt.height;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		for(int d = 1; d <= 16; d++){
			int day = (isLeft)?d:d+16;
			if(day > maxDate(year, month)) break;
			Rectangle r= new Rectangle(10,(int)(h*(16-d)),(int)w-20,(int)h);
			try {
				drawStrings13(r, dayStrings(year, month, day));
				drawMoon(r, ClnUtils.getMoonPhase(year, month, day));
				drawLine(r, 4, 1f);
				drawGrid24(r, 1f);
			} catch (IOException e) {
				
			}
		}
	}
	void drawGrid24(Rectangle r, float strokeWidth) throws IOException{
		contentStream.setLineWidth(strokeWidth);
		for(int h = 1; h < 24; h++){
			int l = (h%12==0)? 4: (h%6==0)? 3 :(h%3==0)?2 :1;
			contentStream.moveTo(r.x+h*r.width/24, r.y+l);
			contentStream.lineTo(r.x+h*r.width/24, r.y);
			contentStream.stroke();
		}
	}

}
