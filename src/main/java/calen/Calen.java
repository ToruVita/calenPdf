package calen;

public class Calen {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Frame();
		int year = 2017;
		int month = 8;
		int day = 22;
//		int day = ClnUtils.shuu(year);
		System.out.println(ClnUtils.eto(year));
		System.out.println(ClnUtils.isHly(year, month, day));
		System.out.println(ClnUtils.check72(year, month, day));
		System.out.println(ClnUtils.julianDay(year, month, day));
		System.out.println(ClnUtils.longitude_sun(ClnUtils.julianDay(year, month, day)));
		double t = ClnUtils.julianDay(year, month, day);
		for(int d = 1; d < 30; d++){
			System.out.println(ClnUtils.isHly(year, month, d));
			System.out.println(ClnUtils.longitude_sun(ClnUtils.julianDay(year, month, d)));
//			System.out.println(d+":"+ClnUtils.check72(year, month, d));
//			System.out.println(ClnUtils.kou72[ClnUtils.check72(year, month, d)]);
		}
	}

}
