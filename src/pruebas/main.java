package pruebas;

import java.util.Date;
import java.util.TimeZone;

import Utils.Utils;
import entities.Dia;

public class main {

	public static void main(String[] args) {
//		Date d = new Date();
//		System.out.println(d);
//		System.out.println(d.getTimezoneOffset());
//		System.out.println(TimeZone.getDefault());
		
		String date = "Wed Oct 17 09:55:25 ART 2015";
		String date2 = "Fri Oct 16 09:55:25 ART 2015";
		Date a =Utils.convertDateStringToDate(date);
		Date b =Utils.convertDateStringToDate(date2);
		System.out.println("inicio:");
		System.out.println("diferencia: "+Utils.getTime((a.getTime()-b.getTime())/1000));
		
	}

}
