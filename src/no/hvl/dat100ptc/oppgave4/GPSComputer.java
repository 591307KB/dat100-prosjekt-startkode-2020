package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	public double totalDistance() {

		double distance = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i - 1], gpspoints[i]);
		}
		return distance;
	}
	
	
	public double totalElevation() {

		double elevation = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			if (gpspoints[i - 1].getElevation() < gpspoints[i].getElevation())
				elevation += gpspoints[i].getElevation() - gpspoints[i - 1].getElevation();
		}
		return elevation;

	}

	public int totalTime() {

		int time = 0;
		time = gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
		return time;

	}

	public double[] speeds() {

		double[] avg = new double[gpspoints.length - 1];
		for (int i = 1; i < gpspoints.length; i++) {
			avg[i - 1] = GPSUtils.speed(gpspoints[i - 1], gpspoints[i]);
		}
		return avg;

	}

	public double maxSpeed() {

		double maxspeed = 0;

		maxspeed = GPSUtils.findMax(speeds());
		return maxspeed;

	}

	public double averageSpeed() {

		double average = 0;

		average = totalDistance() / totalTime() * 3.6;

		return average;

	}

	public static double MS = 2.236936;

	public double kcal(double weight, int secs, double speed) {

		double kcal;
		double met = 0;
		double speedmph = speed * 0.62;
		double sec = (double) secs;

		if (speedmph < 10)
			met = 4.0;
		else if (speedmph >= 10 && speedmph < 12)
			met = 6.0;
		else if (speedmph >= 12 && speedmph < 14)
			met = 8.0;
		else if (speedmph >= 14 && speedmph < 16)
			met = 10.0;
		else if (speedmph >= 16 && speedmph < 20)
			met = 12.0;
		else if (speedmph >= 20)
			met = 16.0;

		kcal = weight * met * (sec / 60 / 60);
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		for (int i = 1; i < gpspoints.length; i++) {
			totalkcal += kcal(weight, gpspoints[i].getTime() - gpspoints[i - 1].getTime(),
					GPSUtils.speed(gpspoints[i - 1], gpspoints[i]));
		}
		return totalkcal;

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {
		
		String tim = "Total time:      " + GPSUtils.formatTime(totalTime());
		String dis = "Total distance:  " + GPSUtils.formatDouble(totalDistance()) + " m";
		String ele = "Total elevation: " + GPSUtils.formatDouble(totalElevation()) + " m";
		String max = "Max speed:       " + GPSUtils.formatDouble(maxSpeed()) + " km/h";
		String avg = "Average speed:   " + GPSUtils.formatDouble(averageSpeed()) + " km/h";
		String nrg = "Energy:          " + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal";

		System.out.println("==============================================");
		System.out.println(tim + "\n" + dis + "\n" + ele + "\n" + max + "\n" + avg + "\n" + nrg);
	}

}
