
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * @author Toru
 * 
 */
public class ClnUtils {
	static String yearNameJ(int year){
		return "平成"+(year+29-2017)+"年";
	}
	private static String[] monthName = { "January", "February", "March", "April",
			"May", "June", "July", "August", "September", "October",
			"November", "December" };
	static String[] monthNameJ = { "睦月", "如月", "弥生", "卯月", "皐月", "水無月", "文月",
			"葉月", "長月", "神無月", "霜月", "師走" };
	static String[] weekNameS = { "Sun.", "Mon.", "Tue.", "Wed.", "Thu.",
			"Fri.", "Sat." };
	static String[] weekName = { "Sunday", "Monday", "Tuesday", "Wednesday",
			"Thursday", "Friday", "Saturday" };
	static String[] weekNameSJ = { "日", "月", "火", "水", "木", "金", "土" };
	static String[] weekNameJ = { "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日",
			"土曜日" };
	static String[] tideJ = { "大潮", "中潮", "小潮", "長潮", "若潮" };
	static String[] eto10 = { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
	static String[] eto12 = { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉",
			"戌", "亥" };
	static String[] sekki24 = { "春分", "清明", "穀雨", "立夏", "小満", "芒種", "夏至", "小暑",
			"大暑", "立秋", "処暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至",
			"小寒", "大寒", "立春", "雨水", "啓蟄" };
	static String[] kou72 = { "東風凍を解く", "うぐいす鳴く", "魚氷にあがる", "土が潤い起る",
			"霞始めてたなびく", "草木萌え動く", "巣籠もりの虫戸を開く", "桃始めて咲く", "菜虫蝶と化す", "雀始めて巣くう",
			"桜始めて開く", "雷声を出す", "燕来る", "雁水へ帰る", "虹始めて見る", "葭始めて生づ", "霜止み苗出づ",
			"牡丹花咲く", "蛙始めて鳴く", "みみず出づる", "竹筍生づ", "蚕起きて桑を食う", "紅花栄う", "麦秋至る",
			"蟷螂生づ", "腐草蛍となる", "梅の実黄ばむ", "乃東枯る", "菖蒲花咲く", "半夏生づ", "温風至る",
			"蓮始めて開く", "鷹技を習う", "桐始めて花を結ぶ", "土潤って蒸し暑し", "大雨時々降る", "涼風至る", "蜩鳴く",
			"濃霧昇降す", "綿の花しべ開く", "天地始めて寒し", "禾実る", "草露白し", "鶺鴒鳴く", "燕去る",
			"雷声を収む", "蟄虫戸を閉ざす", "水始めて涸る", "雁来る", "菊花開く", "蟋蟀戸にあり", "霜始めて降る",
			"小雨時々降る", "紅葉蔦黄ばむ", "椿開き始む", "地始めて凍る", "金盞花香し", "虹隠れて見えづ",
			"北風木の葉を払う", "橘始めて黄ばむ", "空寒く冬となる", "熊穴にこもる", "鮭魚群がる", "冬生じ夏枯る",
			"鹿角おつる", "雪下りて麦のびる", "芹栄う", "泉水温をふくむ", "雉始めて鳴く", "雉始めて鳴く",
			"蕗のとう花咲く", "水沢あつく堅し", "鶏とやにつく" };

	/**
	 * @param year1
	 *            変化元の年
	 * @param month1
	 *            変化元の月
	 * @param day
	 *            変化元の日
	 * @return ユリウス通日を返す。
	 */
	static double julianDay(int year1, int month1, int day) {
		int year = (month1 < 3) ? year1 - 1 : year1;
		int month = (month1 < 3) ? month1 + 12 : month1;
		return 2400000.5 + Math.floor(365.25 * year) + Math.floor(year / 400)
				- Math.floor(year / 100) + Math.floor(30.59 * (month - 2))
				+ day - 678912.0;
	}

	/**
	 * @param jd
	 *            ユリウス通日
	 * @return 年、月、日を配列で戻す。場合に拠っては時刻も
	 */
	static int[] dayJulian(double jd) {
		int z = (int) (jd + 0.5);
		double f = (jd + 0.5) - z; // JD に 0.5 足して Z に整数部を代入し、F に残りの端数を代入します。
		int a;
		if (z < 2299161) {
			a = z;
		} else {
			int x = (int) ((z - 1867216.25) / 36524.25);
			a = z + 1 + x - (int) (x / 4);
		}

		int b = a + 1524;
		int c = (int) ((b - 122.1) / 365.25);
		int d = (int) (365.25 * c);
		int e = (int) ((b - d) / 30.6001);

		int[] r = new int[4];
		r[2] = (int) (b - d - (int) (30.6001 * e) + f);

		if (e < 14) {
			r[1] = e - 1;
		} else {
			r[1] = e - 13;
		}
		if (r[1] > 2) {
			r[0] = c - 4716;
		} else {
			r[0] = c - 4715;
		}
		r[3] = (int) (f * 24);
		return r;
	}

	/**
	 * ツェラーの公式
	 * 
	 * @param yr
	 *            年
	 * @param mth
	 *            月
	 * @param day
	 *            日
	 * @return 曜日（日曜：０）
	 */
	static int zeller(int yr, int mth, int day) {
		int year = (mth < 3) ? yr - 1 : yr;
		int month = (mth < 3) ? mth + 12 : mth;
		return (year + (year / 4) - (year / 100) + (year / 400)
				+ ((13 * month + 8) / 5) + day) % 7;
	}

	/**
	 * 国民の祝日を返す。振替休日も。
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 祝祭日ならその名前を文字列で返す。平日は長さ０の文字列。
	 */
	static String isHly(int year, int month, int day) {
		if (month == 1 && day == 1)
			return "元旦";
		if (month == 1 && day == 2 && zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 1 && zeller(year, month, day) == 1 && day > 7 && day <= 15)
			return "成人の日";
		if (month == 2 && day == 11)
			return "建国記念の日";
		if (month == 2 && day == 12 && zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 3 && day == shun(year))
			return "春分の日";
		if (month == 3 && day == shun(year) + 1
				&& zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 4 && day == 29)
			return "昭和の日";
		if (month == 4 && day == 30 && zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 5 && day == 3)
			return "憲法記念日";
		if (month == 5 && day == 4)
			return "みどりの日";
		if (month == 5 && day == 5)
			return "こどもの日";
		if (month == 5 && day == 6 && zeller(year, month, day) < 4
				&& zeller(year, month, day) != 0)
			return "振替休日";
		if (month == 7 && zeller(year, month, day) == 1 && day > 15
				&& day <= 21)
			return "海の日";
		if (month == 8 && day == 11)
			return "山の日";
		if (month == 9 && zeller(year, month, day) == 1 && day > 15
				&& day <= 21)
			return "敬老の日";
		if (month == 9 && (day == 21 || day == 22)) {
			if (zeller(year, 9, day - 1) == 1 && shuu(year) == day + 1)
				return "国民の休日";
		}
		if (month == 9 && day == shuu(year))
			return "秋分の日";
		if (month == 9 && day == shuu(year) + 1
				&& zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 10 && zeller(year, month, day) == 1 && day > 7
				&& day <= 15)
			return "体育の日";
		if (month == 11 && day == 3)
			return "文化の日";
		if (month == 11 && day == 4 && zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 11 && day == 23)
			return "勤労感謝の日";
		if (month == 11 && day == 24 && zeller(year, month, day) == 1)
			return "振替休日";
		if (month == 12 && day == 23)
			return "天皇誕生日";
		if (month == 12 && day == 24 && zeller(year, month, day) == 1)
			return "振替休日";
		return "";
	}
	static String isHly(LocalDate dy) {
		return isHly(dy.getYear(), dy.getMonthValue(), dy.getDayOfMonth());
	}
	/**
	 * 春分の日の日付（日のみ）を返す。３月
	 * 
	 * @param year
	 *            年
	 * @return 日付
	 */
	static int shun(int year) {
		return (((242194 * (year - 1980)) + 20843100) / 1000000)
				- ((year - 1980) / 4);
	}

	/**
	 * 秋分の日の日付（日のみ）を返す。９月
	 * 
	 * @param year
	 *            年
	 * @return 日付
	 */
	static int shuu(int year) {
		return (((242194 * (year - 1980)) + 23248800) / 1000000)
				- ((year - 1980) / 4);
	}

	/**
	 * 月齢から潮の名前を返してみる
	 * 
	 * @param moonAge
	 *            月齢 int型
	 * @return 文字列
	 */
	static String getTide(int moonAge) {
		int i = -1;
		if (moonAge == 10 || moonAge == 25) {
			i = 3;
		} else if (moonAge == 10 || moonAge == 25) {
			i = 4;
		} else if ((moonAge >= 7 && moonAge <= 9)
				|| (moonAge >= 22 && moonAge <= 24)) {
			i = 2;
		} else if ((moonAge >= 14 && moonAge <= 17) || moonAge >= 29
				|| moonAge <= 2) {
			i = 0;
		} else {
			i = 1;
		}
		return tideJ[i];
	}

	/**
	 * 二十四節気、七十二候を求める。現在はファイルを検索している。ファイル作成の資料は「暦のページ」
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 二十四節気、七十二候を文字列で。【24】72
	 */
	static String dev72(int year, int month, int day) {
		String bf = "";
		try {
			File inputFile = new File("72.txt");
			FileReader in = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(in);

			String line;
			while ((line = br.readLine()) != null) {
				String[] tmp = line.split(",");
				int yearT = Integer.parseInt(tmp[3]);
				int monthT = Integer.parseInt(tmp[4]);
				int dayT = Integer.parseInt(tmp[5]);
				if (year == yearT && month == monthT && day == dayT) {
					if (tmp[0].length() > 0) {
						bf = "【" + tmp[0] + "】" + tmp[6];
					} else {
						bf = tmp[6];
					}
				}
			}

			br.close();
			in.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bf;
	}

	static String eto(int year) {
		return eto10[((year - 4) % 10)] + eto12[((year - 4) % 12)];
	}

	private static double normarize_angle(double angle) {
		return angle - 360.0 * Math.floor(angle / 360.0);
	}

	static int check72(int year, int month, int day) {
		return check72(julianDay(year, month, day));
	}

	static int check72(double tm) {
		double biggerOne = longitude_sun(hosei(tm + .5));
		int wk = (int) (Math.floor(biggerOne));
		int kou72 = 0;
		if (wk % 5 == 0) {
			kou72 = (wk / 5) + 1;
		} else {
			if (wk % 5 == 1
					&& longitude_sun(hosei(tm - .5)) < (double) (wk - 1)) {
				kou72 = ((wk - 1) / 5) + 1;
			}
		}
		return kou72;
	}

	static double hosei(double tm) {
		return (tm - Math.floor(tm) + tz + .5) / 36525.0
				+ (Math.floor(tm) - 2451545.0) / 36525.0;
	}

	static int getMoonPhase(int year, int month, int day) {
		return getMoonPhase(julianDay(year, month, day));
	}

	static double moonAge(int year, int month, int day) {
		return moonAge(julianDay(year, month, day));
	}

	static double moonAge(double tm) {
		double t = tm;
		for (; !saku(t); t--)
			;
		t = tm - realSaku(t);
		t = (t * 100 - (t * 100 - Math.floor(t * 100))) / 100.0;
		return (t);
	}

	static double realSaku(double tm) {
		double t = tm;
		for (double dlt = 0.5; dlt > 1.0 / 24 / 60 / 60; dlt /= 2) {
			if (saku(t + (dlt / 2), dlt)) {
				t += (dlt / 2);
			} else {
				t -= (dlt / 2);
			}
		}
		return t;
	}

	static boolean saku(double tm) {
		return saku(tm, 1.0);
	}

	static boolean saku(double tm, double dlt) {
		double preSun = normarize_angle(longitude_sun(hosei(tm - dlt / 2)));
		double preMoon = normarize_angle(longitude_moon(hosei(tm - dlt / 2)));
		double nxtSun = normarize_angle(longitude_sun(hosei(tm + dlt / 2)));
		double nxtMoon = normarize_angle(longitude_moon(hosei(tm + dlt / 2)));
		boolean flg = false;
		if ((nxtMoon - nxtSun) < (12.0 * dlt) && (nxtMoon - nxtSun) > 0) {
			if ((nxtMoon - nxtSun) * (preMoon - preSun) < 0) {
				flg = true;
			}
		} else if ((nxtMoon + 360.0 - nxtSun) < (12.0 * dlt)
				&& (nxtMoon + 360.0 - nxtSun) > 0) {
			flg = true;
		}
		return flg;
	}

	static int getMoonPhase(double tm) {
		double t = hosei(tm);
		int mp = (int) Math.floor(normarize_angle(longitude_moon(t)
				- longitude_sun(t)) / 360 * 28 + .5);
		if (mp == 28)
			mp = 0;
		return mp;
	}

	static double tz = (Calendar.getInstance().get(Calendar.DST_OFFSET) - Calendar
			.getInstance().get(Calendar.ZONE_OFFSET))
			/ (60.0 * 1000.0) / 1440.0; // タイムゾーンオフセット

	/**
	 * 太陽黄経の計算
	 * 要デバッグ：次のサイトを参照？http://astronomy.webcrow.jp/astrometry/sun_ecliptic_longitude.html
	 * @param t
	 *            ユリウス通日
	 * @return 太陽黄経(360度表示)
	 */
	static double longitude_sun(double t) {
		double k = Math.PI / 180.0;
		double ang, th;
		th = .0004 * Math.cos(k * normarize_angle(31557 * t + 161));
		th += .0004 * Math.cos(k * normarize_angle(29930 * t + 48));
		th += .0005 * Math.cos(k * normarize_angle(2281 * t + 221));
		th += .0005 * Math.cos(k * normarize_angle(155 * t + 118));
		th += .0006 * Math.cos(k * normarize_angle(33718 * t + 316));
		th += .0007 * Math.cos(k * normarize_angle(9038 * t + 64));
		th += .0007 * Math.cos(k * normarize_angle(3035 * t + 110));
		th += .0007 * Math.cos(k * normarize_angle(65929 * t + 45));
		th += .0013 * Math.cos(k * normarize_angle(22519 * t + 352));
		th += .0015 * Math.cos(k * normarize_angle(45038 * t + 254));
		th += .0018 * Math.cos(k * normarize_angle(445267 * t + 208));
		th += .0018 * Math.cos(k * normarize_angle(19 * t + 159));
		th += .0020 * Math.cos(k * normarize_angle(32964 * t + 158));
		th += .0200 * Math.cos(k * normarize_angle(71998.1 * t + 265.1));
		ang = normarize_angle(35999.05 * t + 267.52);
		th = th - .0048 * t * Math.cos(k * ang);
		th += 1.9147 * Math.cos(k * ang);
		ang = normarize_angle(36000.7695 * t);
		ang = normarize_angle(ang + 280.4659);
		th = normarize_angle(th + ang);
		return th;
	}

	/**
	 * 月黄経の計算
	 * 
	 * @param t
	 *            ユリウス通日
	 * @return 月黄経(360度表示)
	 */
	static double longitude_moon(double t) {
		double k = Math.PI / 180.0;
		double ang, th;
		th = .0003 * Math.cos(k * normarize_angle(2322131 * t + 191));
		th += .0003 * Math.cos(k * normarize_angle(4067 * t + 70));
		th += .0003 * Math.cos(k * normarize_angle(549197 * t + 220));
		th += .0003 * Math.cos(k * normarize_angle(1808933 * t + 58));
		th += .0003 * Math.cos(k * normarize_angle(349472 * t + 337));
		th += .0003 * Math.cos(k * normarize_angle(381404 * t + 354));
		th += .0003 * Math.cos(k * normarize_angle(958465 * t + 340));
		th += .0004 * Math.cos(k * normarize_angle(12006 * t + 187));
		th += .0004 * Math.cos(k * normarize_angle(39871 * t + 223));
		th += .0005 * Math.cos(k * normarize_angle(509131 * t + 242));
		th += .0005 * Math.cos(k * normarize_angle(1745069 * t + 24));
		th += .0005 * Math.cos(k * normarize_angle(1908795 * t + 90));
		th += .0006 * Math.cos(k * normarize_angle(2258267 * t + 156));
		th += .0006 * Math.cos(k * normarize_angle(111869 * t + 38));
		th += .0007 * Math.cos(k * normarize_angle(27864 * t + 127));
		th += .0007 * Math.cos(k * normarize_angle(485333 * t + 186));
		th += .0007 * Math.cos(k * normarize_angle(405201 * t + 50));
		th += .0007 * Math.cos(k * normarize_angle(790672 * t + 114));
		th += .0008 * Math.cos(k * normarize_angle(1403732 * t + 98));
		th += .0009 * Math.cos(k * normarize_angle(858602 * t + 129));
		th += .0011 * Math.cos(k * normarize_angle(1920802 * t + 186));
		th += .0012 * Math.cos(k * normarize_angle(1267871 * t + 249));
		th += .0016 * Math.cos(k * normarize_angle(1856938 * t + 152));
		th += .0018 * Math.cos(k * normarize_angle(401329 * t + 274));
		th += .0021 * Math.cos(k * normarize_angle(341337 * t + 16));
		th += .0021 * Math.cos(k * normarize_angle(71998 * t + 85));
		th += .0021 * Math.cos(k * normarize_angle(990397 * t + 357));
		th += .0022 * Math.cos(k * normarize_angle(818536 * t + 151));
		th += .0023 * Math.cos(k * normarize_angle(922466 * t + 163));
		th += .0024 * Math.cos(k * normarize_angle(99863 * t + 122));
		th += .0026 * Math.cos(k * normarize_angle(1379739 * t + 17));
		th += .0027 * Math.cos(k * normarize_angle(918399 * t + 182));
		th += .0028 * Math.cos(k * normarize_angle(1934 * t + 145));
		th += .0037 * Math.cos(k * normarize_angle(541062 * t + 259));
		th += .0038 * Math.cos(k * normarize_angle(1781068 * t + 21));
		th += .0040 * Math.cos(k * normarize_angle(133 * t + 29));
		th += .0040 * Math.cos(k * normarize_angle(1844932 * t + 56));
		th += .0040 * Math.cos(k * normarize_angle(1331734 * t + 283));
		th += .0050 * Math.cos(k * normarize_angle(481266 * t + 205));
		th += .0052 * Math.cos(k * normarize_angle(31932 * t + 107));
		th += .0068 * Math.cos(k * normarize_angle(926533 * t + 323));
		th += .0079 * Math.cos(k * normarize_angle(449334 * t + 188));
		th += .0085 * Math.cos(k * normarize_angle(826671 * t + 111));
		th += .0100 * Math.cos(k * normarize_angle(1431597 * t + 315));
		th += .0107 * Math.cos(k * normarize_angle(1303870 * t + 246));
		th += .0110 * Math.cos(k * normarize_angle(489205 * t + 142));
		th += .0125 * Math.cos(k * normarize_angle(1443603 * t + 52));
		th += .0154 * Math.cos(k * normarize_angle(75870 * t + 41));
		th += .0304 * Math.cos(k * normarize_angle(513197.9 * t + 222.5));
		th += .0347 * Math.cos(k * normarize_angle(445267.1 * t + 27.9));
		th += .0409 * Math.cos(k * normarize_angle(441199.8 * t + 47.4));
		th += .0458 * Math.cos(k * normarize_angle(854535.2 * t + 148.2));
		th += .0533 * Math.cos(k * normarize_angle(1367733.1 * t + 280.7));
		th += .0571 * Math.cos(k * normarize_angle(377336.3 * t + 13.2));
		th += .0588 * Math.cos(k * normarize_angle(63863.5 * t + 124.2));
		th += .1144 * Math.cos(k * normarize_angle(966404 * t + 276.5));
		th += .1851 * Math.cos(k * normarize_angle(35999.05 * t + 87.53));
		th += .2136 * Math.cos(k * normarize_angle(954397.74 * t + 179.93));
		th += .6583 * Math.cos(k * normarize_angle(890534.22 * t + 145.7));
		th += 1.2740 * Math.cos(k * normarize_angle(413335.35 * t + 10.74));
		th += 6.2888 * Math.cos(k * normarize_angle(477198.868 * t + 44.963));
		ang = normarize_angle(481267.8809 * t);
		ang = normarize_angle(ang + 218.3162);
		th = normarize_angle(th + ang);
		return th;
	}

	static double sunRise(int year, int month, int day, double ido, double keido) {
		double h1 = 3.0;
		double h2 = 9.0;
		for (double d1 = sunHeight(year, month, day, (h1 + h2) / 2.0, ido,
				keido); Math.abs(h1 - h2) > 1.0 / 60.0 / 60.0;) {
			if (d1 < 0) {
				h1 = (h1 + h2) / 2.0;
			} else {
				h2 = (h1 + h2) / 2.0;
			}
			d1 = sunHeight(year, month, day, (h1 + h2) / 2.0, ido, keido);
		}
		return (h1 + h2) / 2.0;
	}

	static double sunDawn(int year, int month, int day, double ido, double keido) {
		double h1 = 15.0;
		double h2 = 21.0;
		for (double d1 = sunHeight(year, month, day, (h1 + h2) / 2.0, ido,
				keido); Math.abs(h1 - h2) > 1.0 / 60.0 / 60.0;) {
			if (d1 > -1.7) {
				h1 = (h1 + h2) / 2.0;
			} else {
				h2 = (h1 + h2) / 2.0;
			}
			d1 = sunHeight(year, month, day, (h1 + h2) / 2.0, ido, keido);
		}
		return (h1 + h2) / 2.0;
	}

	static double sunHeight(int year, int month, int day, double jst,
			double ido, double keido) {
		double ked = keido / 180.0 * Math.PI;
		double id0 = ido / 180.0 * Math.PI;
		double t0 = 2.0 * Math.PI * (DaylyTide.serialy(year, month, day) - 1)
				/ 365.0;
		double d = 0.006918 - 0.399912 * Math.cos(t0) + 0.070257 * Math.sin(t0)
				- 0.006758 * Math.cos(2.0 * t0) + 0.000907 * Math.sin(2.0 * t0)
				- 0.002697 * Math.cos(3.0 * t0) + 0.001480 * Math.sin(3.0 * t0);
		double rrwk = 1.000110 + 0.034221 * Math.cos(t0) + 0.001280
				* Math.sin(t0) + 0.000719 * Math.cos(2.0 * t0) + 0.000077
				* Math.sin(2.0 * t0);
		double rr = Math.sqrt(1.0 / rrwk);
		double eq = 0.000075 + 0.001868 * Math.cos(t0) - 0.032077
				* Math.sin(t0) - 0.014615 * Math.cos(2.0 * t0) - 0.040849
				* Math.sin(2.0 * t0);
		double h = (jst - 12.0) * Math.PI / 12.0 + (0) + eq;
		double al = Math.asin(Math.sin(id0) * Math.sin(d) + Math.cos(id0)
				* Math.cos(d) * Math.cos(h));
		return al / Math.PI * 180.0;
	}

	static double sunHeight(int year, int month, int day, int hour, int min,
			int sec, double ido, double keido) {
		double jst = 1.0 * ((hour * 60 + min) * 60 + sec) / 3600.0;
		return sunHeight(year, month, day, jst, ido, keido);
	}


	public static String getWeekName(int year, int month, int day, int flg) {
		System.out.println(flg);
		if(flg==1){
			return weekNameS[zeller(year, month, day)];
		}else if(flg==2){
			return weekNameJ[zeller(year, month, day)];
		}else if(flg==3){
			return weekNameSJ[zeller(year, month, day)];
		}else{
			return weekName[zeller(year, month, day)];
		}
	}

	public static String getMonthName(int month) {
		return monthName[month];
	}
}
