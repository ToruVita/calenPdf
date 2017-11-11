package calen;

public class Moon {
	/**
	 * 月の名前
	 */
	private static String[] moonnameJ = { "朔/新月", "既朔", "三日月", "(夕月)", "(夕月)",
		"(夕月)", "弓張/上弦半月", "(宵月)", "九夜月", "十日月", "(宵月)", "(宵月)", "十三夜",
		"小望月", "十五夜/満月", "十六夜", "立待月", "居待月", "寝待月", "更待月", "(宵月)", "(宵月)",
		"二十三夜/下弦半月", "(有明月)", "(有明月)", "二十六夜", "(有明月)", "(有明月)", "(暁月)",
		"晦日月" };
	private static String[] moonnameE = { "New", "WAXING CRESCENT", "FIRST QUARTER",
		"WAXING GIBBOUS", "FULL", "WANING GIBBOUS", "THIRD QUARTER", "WANING CRESCENT" };

	static int age(int year, int month, int day) {
		int ma = (int) ((((year - 2009) % 19) * 11 + month + day) % 29.5);
		return ma;
	}

	public static String name(int year, int month, int day) {
		return moonnameJ[age(year, month, day)];
	}
	// http://www2.ed.oita-u.ac.jp/astro/student/taguchi/proj99/list.html
	public static double langitudeSun(double julian){
		double st = julian - 0.5;
		double en = julian + 0.5;
		double julianCentury = (julian - 2415020)/36525;
		return 	(279.69668 + (36000.76892 * julianCentury) - (0.0003025 * julianCentury*julianCentury))%360;
	}
	boolean sek12Flg(double julian){
		int s024 = (int) (langitudeSun(julian - 0.5) / 30);
		int s124 = (int) (langitudeSun(julian + 0.5) / 30);
		return !(s024 == s124);
	}
	static int pseudoMonth(double julian){
		int s124 = (int) (langitudeSun(julian + 0.5) / 30);
		return s124;
	}
	public static int getMonth(double julian){
		double wk = julian - getDateOfMonth(julian) + 1;
		wk++;
		while(getDateOfMonth(wk) > 1){
			wk++;
		}
		return (pseudoMonth(wk)+1)%12+1;
	}
	public static boolean langitudeMoonFlg(double julian){
		double j0 = langitudeMoon(julian - 0.5);
		double j10 = langitudeSun(julian - 0.5);
		double j1 = langitudeMoon(julian + 0.5);
		double j11 = langitudeSun(julian + 0.5);
		if((j0 - j10)*(j1-j11)<0 && (j0 - j10)<0 && j10<j11){
			return true;
		}else{
			return false;
		}
	}
	public static int getDateOfMonth(double julian){
		double wk = julian;
		int dy = 0;
		while(!langitudeMoonFlg(wk)){
			dy++;
			wk--;
		}
		return dy+1;
	}
	public static double langitudeMoon(double julian){
		double jc = (julian - 2415020)/36525;
		return (270.434164+(481267.8831*jc)-(0.001133*jc*jc)+(0.0000019*jc*jc*jc))%360;
	}
	public static int oldWeekDay(double julian){
		int index = getMonth(julian)-1+getDateOfMonth(julian)-1;
		return index % 6;
	}
}