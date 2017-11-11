package calen;
class DaylyTide {
	String port = "";
	int year = 2010;
	int month = 1;
	int day = 1;
	static String str = "宇野,3430,13357,140,14950,32830,28630,3290,4320,19230,17050,20690,29850,23610,15580,23750,9690,23100,21050,21300,38970,2860,32800,24260,14060,30230,31390,5490,32290,60,33710,33580,34810,34570,34770,24120,950,1320,31580,27530,23350,29050,1930,4280,1770,140,70,230,210,420,70,2290,250,130,10,830,100,3110,60,90,140,210,80,100,510,1000,350,90,6730,180,460,90,2140,60,700,190,60,70,90,40,90,50,90,100,0,,";
	double[] datas;

	public DaylyTide(int year, int month, int day, String port) {
		super();
		this.month = month;
		this.day = day;
		this.year = year;
		if (!port.equals(str.split(",")[0])) {
			if (port.split(",").length > 80) {
				str = port;
			} else {
				this.port = port;
			}
		}
		this.port = port;
		init(72);
	}

	void init(int l) {
		datas = new double[l];
		double[][] dd = this.getVLandF(year, month, day);
		for (int i = 0; i < datas.length; i++) {
			double hour = (double) i * 24.0 / datas.length;
			datas[i] = v(dd[2], dd[1], dd[0], tdata(), hour);
		}
	}

	double getTide(int year, int month, int day, int hour) {
		double[][] dd = this.getVLandF(year, month, day);
		return v(dd[2], dd[1], dd[0], tdata(), hour);
	}

	/**
	 * 特定日付の潮の高さを配列で戻す
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param dev
	 *            分割個数
	 * @return
	 */
	static double[] getTides(int year, int month, int day, int dev) {
		double[] datas = new double[dev];
		double[][] dd = getVLandF(year, month, day);
		for (int i = 0; i < datas.length; i++) {
			double hour = (double) i * 24.0 / datas.length;
			datas[i] = v(dd[2], dd[1], dd[0], tdata(), hour);
		}
		return datas;
	}

	static double[] tdata() {
		String[] ss = str.split(",");
		double[] tt = new double[84];
		for (int i = 0; i < 84; i++) {
			tt[i] = Integer.parseInt(ss[i + 1]);
		}
		return tt;
	}

	static double lng() {
		String[] ss = str.split(",");
		return Double.parseDouble(ss[2]) / 100;
		// return 133.57;
	}

	static double v(double[] f, double[] vl, double[] ags, double[] tdata,
			double hour) {
		double dr = 0.0174532925199433;
		double tl = tdata()[2];
		for (int j = 0; j < 40; j++) {
			double a = (vl[j] + ags[j] * hour - tdata()[3 + j] / 100.0) * dr;
			tl += f[j] * tdata()[3 + 40 + j] / 100.0 * Math.cos(a);
		}
		return tl;
	}

	static double[][] getVLandF(int year, int month, int day) {
		double dr = 0.0174532925199433;
		double rd = 57.29577951308232;
		double zt = 135.0;
		int[] nc = { 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 6, 6 };
		double[] ags = { 0.0410686, 0.0821373, 0.5443747, 1.0158958, 1.0980331,
				13.3986609, 13.4715145, 13.9430356, 14.0251729, 14.4920521,
				14.9178647, 14.9589314, 15.0000000, 15.0410686, 15.0821353,
				15.1232059, 15.5854433, 16.0569644, 16.1391017, 27.8953548,
				27.9682084, 28.4397295, 28.5125831, 28.9019669, 28.9841042,
				29.4556253, 29.5284789, 29.9589333, 30.0000000, 30.0410667,
				30.0821373, 31.0158958, 42.9271398, 43.4761563, 44.0251729,
				45.0410686, 57.9682084, 58.9841042, 86.9523127, 87.9682084 };
		double vl[] = new double[40]; /* 天文引数 */
		double f[] = new double[40]; /* 天文因数 */
		double v[] = new double[40];
		double u[] = new double[40];
		double f0[] = new double[10];
		double u0[] = new double[10];

		int tz = serialy(year, month, day) + (int) fix((year + 3) / 4) - 500;

		double ty = year - 2000;
		double s = rnd36(211.728 + rnd36(129.38471 * ty)
				+ rnd36(13.176396 * tz));
		double h = rnd36(279.974 + rnd36(-0.23871 * ty) + rnd36(0.985647 * tz));
		double p = rnd36(83.298 + rnd36(40.66229 * ty) + rnd36(0.111404 * tz));
		double n = rnd36(125.071 + rnd36(-19.32812 * ty)
				+ rnd36(-0.052954 * tz));

		v[0] = h;
		v[1] = 2 * h;
		v[2] = s - p;
		v[3] = 2 * s - 2 * h;
		v[4] = 2 * s;
		v[5] = -3 * s + h + p + 270;
		v[6] = -3 * s + 3 * h - p + 270;
		v[7] = -2 * s + h + 270;
		v[8] = -2 * s + 3 * h - 270;
		v[9] = -s + h + 90;
		v[10] = -2 * h + 192;
		v[11] = -h + 270;
		v[12] = 180;
		v[13] = h + 90;
		v[14] = 2 * h + 168;
		v[15] = 3 * h + 90;
		v[16] = s + h - p + 90;
		v[17] = 2 * s - h - 270;
		v[18] = 2 * s + h + 90;
		v[19] = -4 * s + 2 * h + 2 * p;
		v[20] = -4 * s + 4 * h;
		v[21] = -3 * s + 2 * h + p;
		v[22] = -3 * s + 4 * h - p;
		v[23] = -2 * s + 180;
		v[24] = -2 * s + 2 * h;
		v[25] = -s + p + 180;
		v[26] = -s + 2 * h - p + 180;
		v[27] = -h + 282;
		v[28] = 0;
		v[29] = h + 258;
		v[30] = 2 * h;
		v[31] = 2 * s - 2 * h;
		v[32] = -4 * s + 3 * h + 270;
		v[33] = -3 * s + 3 * h + 180;
		v[34] = -2 * s + 3 * h + 90;
		v[35] = h + 90;
		v[36] = -4 * s + 4 * h;
		v[37] = -2 * s + 2 * h;
		v[38] = -6 * s + 6 * h;
		v[39] = -4 * s + 4 * h;

		double n1 = Math.sin(n * dr);
		double n2 = Math.sin(rnd36(n * 2) * dr);
		double n3 = Math.sin(rnd36(n * 3) * dr);
		u0[0] = 0;
		u0[1] = -23.74 * n1 + 2.68 * n2 - 0.38 * n3;
		u0[2] = 10.80 * n1 - 1.34 * n2 + 0.19 * n3;
		u0[3] = -8.86 * n1 + 0.68 * n2 - 0.07 * n3;
		u0[4] = -12.94 * n1 + 1.34 * n2 - 0.19 * n3;
		u0[5] = -36.68 * n1 + 4.02 * n2 - 0.57 * n3;
		u0[6] = -2.14 * n1;
		u0[7] = -17.74 * n1 + 0.68 * n2 - 0.04 * n3;
		double cu = 1 - 0.2505 * Math.cos(p * 2 * dr) - 0.1102
				* Math.cos((p * 2 - n) * dr) - 0.0156
				* Math.cos((p * 2 - n * 2) * dr) - 0.0370 * Math.cos(n * dr);
		double su = -0.2505 * Math.sin(p * 2 * dr) - 0.1102
				* Math.sin((p * 2 - n) * dr) - 0.0156
				* Math.sin((p * 2 - n * 2) * dr) - 0.0370 * Math.sin(n * dr);
		u0[8] = Math.atan2(su, cu) * rd;
		cu = 2 * Math.cos(p * dr) + 0.4 * Math.cos((p - n) * dr);
		su = Math.sin(p * dr) + 0.2 * Math.cos((p - n) * dr);
		u0[9] = Math.atan2(su, cu) * rd;

		u[0] = 0;
		u[1] = 0;
		u[2] = 0;
		u[3] = -u0[6];
		u[4] = u0[1];
		u[5] = u0[2];
		u[6] = u0[2];
		u[7] = u0[2];
		u[8] = u0[6];
		u[9] = u0[9];
		u[10] = 0;
		u[11] = 0;
		u[12] = 0;
		u[13] = u0[3];
		u[14] = 0;
		u[15] = 0;
		u[16] = u0[4];
		u[17] = -u0[2];
		u[18] = u0[5];
		u[19] = u0[6];
		u[20] = u0[6];
		u[21] = u0[6];
		u[22] = u0[6];
		u[23] = u0[2];
		u[24] = u0[6];
		u[25] = u0[6];
		u[26] = u0[8];
		u[27] = 0;
		u[28] = 0;
		u[29] = 0;
		u[30] = u0[7];
		u[31] = -u0[6];
		u[32] = u0[6] + u0[2];
		u[33] = u0[6] * 1.5;
		u[34] = u0[6] + u0[3];
		u[35] = u0[3];
		u[36] = u0[6] * 2;
		u[37] = u0[6];
		u[38] = u0[6] * 3;
		u[39] = u0[6] * 2;

		for (int i = 0; i < 40; i++) {
			v[i] = rnd36(v[i] + u[i]);
			vl[i] = rnd36(v[i] + lng() * nc[i] - ags[i] * zt / 15);
		}

		n1 = Math.cos(n * dr);
		n2 = Math.cos(rnd36(n * 2) * dr);
		n3 = Math.cos(rnd36(n * 3) * dr);
		f0[0] = 1.0000 - 0.1300 * n1 + 0.0013 * n2;
		f0[1] = 1.0429 + 0.4135 * n1 - 0.0040 * n2;
		f0[2] = 1.0089 + 0.1871 * n1 - 0.0147 * n2 + 0.0014 * n3;
		f0[3] = 1.0060 + 0.1150 * n1 - 0.0088 * n2 + 0.0006 * n3;
		f0[4] = 1.0129 + 0.1676 * n1 - 0.0170 * n2 + 0.0016 * n3;
		f0[5] = 1.1027 + 0.6504 * n1 + 0.0317 * n2 - 0.0014 * n3;
		f0[6] = 1.0004 - 0.0373 * n1 + 0.0002 * n2;
		f0[7] = 1.0241 + 0.2863 * n1 + 0.0083 * n2 - 0.0015 * n3;
		cu = 1 - 0.2505 * Math.cos(p * 2 * dr) - 0.1102
				* Math.cos((p * 2 - n) * dr) - 0.0156
				* Math.cos((p * 2 - n * 2) * dr) - 0.0370 * Math.cos(n * dr);
		su = -0.2505 * Math.sin(p * 2 * dr) - 0.1102
				* Math.sin((p * 2 - n) * dr) - 0.0156
				* Math.sin((p * 2 - n * 2) * dr) - 0.0370 * Math.sin(n * dr);
		double arg = Math.atan2(su, cu) * rd;
		f0[8] = su / Math.sin(arg * dr);
		cu = 2 * Math.cos(p * dr) + 0.4 * Math.cos((p - n) * dr);
		su = Math.sin(p * dr) + 0.2 * Math.cos((p - n) * dr);
		arg = Math.atan2(su, cu) * rd;
		f0[9] = cu / Math.cos(arg * dr);

		f[0] = 1;
		f[1] = 1;
		f[2] = f0[0];
		f[3] = f0[6];
		f[4] = f0[1];
		f[5] = f0[2];
		f[6] = f0[2];
		f[7] = f0[2];
		f[8] = f0[6];
		f[9] = f0[9];
		f[10] = 1;
		f[11] = 1;
		f[12] = 1;
		f[13] = f0[3];
		f[14] = 1;
		f[15] = 1;
		f[16] = f0[4];
		f[17] = f0[2];
		f[18] = f0[5];
		f[19] = f0[6];
		f[20] = f0[6];
		f[21] = f0[6];
		f[22] = f0[6];
		f[23] = f0[2];
		f[24] = f0[6];
		f[25] = f0[6];
		f[26] = f0[8];
		f[27] = 1;
		f[28] = 1;
		f[29] = 1;
		f[30] = f0[7];
		f[31] = f0[6];
		f[32] = f0[6] * f0[2];
		f[33] = Math.pow(f0[6], 1.5);
		f[34] = f0[6] * f0[3];
		f[35] = f0[3];
		f[36] = Math.pow(f0[6], 2);
		f[37] = f0[6];
		f[38] = Math.pow(f0[6], 3);
		f[39] = Math.pow(f0[6], 2);
		double[][] wk = new double[3][40];
		wk[0] = ags;
		wk[1] = vl;
		wk[2] = f;
		return wk;
	}

	static int serialy(int year, int month, int day) {
		int[][] tuki_tbl = {
				{ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
				{ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
		int d = 0;
		for (int i = 1; i < month; i++)
			d += tuki_tbl[uruu_chk(year)][i - 1];
		d += day - 1;
		return d;
	}

	static int uruu_chk(int yyyy) {
		if (0 != yyyy % 4) {
			return 0;
		}
		if (0 != yyyy % 100) {
			return 1;
		}
		if (0 == yyyy % 400) {
			return 1;
		}
		return 0;
	}

	static double fix(double x) {
		if (x >= 0)
			return Math.floor(Math.abs(x));
		else
			return -Math.floor(Math.abs(x));
	}

	static double rnd36(double x) {
		return x - Math.floor(x / 360) * 360;
	}

	double rnd18(double x) {
		return x - fix((x + sgn(x) * 180) / 360) * 360;
	}

	int sgn(double x) {
		if (x < 0)
			return -1;
		else if (x > 0)
			return 1;
		else
			return 0;
	}

}