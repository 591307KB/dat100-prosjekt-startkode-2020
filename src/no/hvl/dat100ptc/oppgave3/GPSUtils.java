package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max;

		max = da[0];

		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}

		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];

		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}

		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitude = new double[gpspoints.length];
		for (int i = 0; i < latitude.length; i++) {
			latitude[i] = gpspoints[i].getLatitude();
		}
		return latitude;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitude = new double[gpspoints.length];
		for (int i = 0; i < longitude.length; i++) {
			longitude[i] = gpspoints[i].getLongitude();
		}
		return longitude;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		latitude1 = gpspoint1.getLatitude();
		latitude2 = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2 = gpspoint2.getLongitude();
		double lat1 = Math.toRadians(latitude1);
		double long1 = Math.toRadians(longitude1);
		double lat2 = Math.toRadians(latitude2);
		double long2 = Math.toRadians(longitude2);
		double deltalat = lat2 - lat1;
		double deltalong = long2 - long1;
		double a = pow(sin(deltalat / 2), 2) + ((cos(lat1) * cos(lat2)) * (pow(sin(deltalong / 2), 2)));
		double c = 2 * atan2(sqrt(a), sqrt(1 - a));
		d = R * c;
		return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		double m = distance(gpspoint1, gpspoint2);
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		speed = m / secs * 3.6;
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		int ss = secs % 60;
		int hh = secs / 60;
		int mm = hh % 60;
		hh /= 60;

		String hhstr = String.format("%02d", hh);
		String mmstr = String.format("%02d", mm);
		String ssstr = String.format("%02d", ss);
		timestr = String.format("  %s%s%s%s%s", hhstr, TIMESEP, mmstr, TIMESEP, ssstr);
		return timestr;

	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
	
		String str = "";
		d *= 100;
		d = Math.round(d);
		d /= 100;
		String temp = String.format("%.2f", d);
		for (int i = temp.length(); i < TEXTWIDTH; i++) {
			str += " ";
		}
		str += temp;
		return str;
	}
}
