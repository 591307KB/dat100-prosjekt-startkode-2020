package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;

	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);

		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon));

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {

		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		double ystep = MAPYSIZE / (Math.abs(maxlat - minlat));

		return ystep;

	}

	public void showRouteMap(int ybase) {

		int xpos, ypos;
		int xpos1 = (int) ((gpspoints[0].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints)))
				* xstep());
		int ypos1 = (int) ((gpspoints[0].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints))) * ystep());
		for (int i = 1; i < gpspoints.length; i++) {
			if (gpspoints[i].getElevation() > gpspoints[i - 1].getElevation())
				setColor(255, 0, 0);
			else if (gpspoints[i].getElevation() == gpspoints[i - 1].getElevation())
				setColor(0, 0, 255);
			else if (gpspoints[i].getElevation() < gpspoints[i - 1].getElevation())
				setColor(0, 255, 0);
			xpos = (int) ((gpspoints[i - 1].getLongitude() - GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints)))
					* xstep());
			ypos = (int) ((gpspoints[i - 1].getLatitude() - GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints)))
					* ystep());
			drawLine(MARGIN + xpos1, ybase - ypos1, MARGIN + xpos, ybase - ypos);
			xpos1 = xpos;
			ypos1 = ypos;
			fillCircle(xpos + MARGIN, ybase - ypos, 3);

		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;
		String tim = "Total time:      " + GPSUtils.formatTime(gpscomputer.totalTime());
		String dis = "Total distance:  " + GPSUtils.formatDouble(gpscomputer.totalDistance()) + " m";
		String ele = "Total elevation: " + GPSUtils.formatDouble(gpscomputer.totalElevation()) + " m";
		String max = "Max speed:       " + GPSUtils.formatDouble(gpscomputer.maxSpeed()) + " km/h";
		String avg = "Average speed:   " + GPSUtils.formatDouble(gpscomputer.averageSpeed()) + " km/h";
		String nrg = "Energy:          " + GPSUtils.formatDouble(gpscomputer.totalKcal(80.0)) + " kcal";

		setColor(0, 0, 0);
		setFont("Courier", 12);
		drawString(tim, TEXTDISTANCE,TEXTDISTANCE);
		drawString(dis, TEXTDISTANCE,TEXTDISTANCE*2);
		drawString(ele, TEXTDISTANCE,TEXTDISTANCE*3);
		drawString(max, TEXTDISTANCE,TEXTDISTANCE*4);
		drawString(avg, TEXTDISTANCE,TEXTDISTANCE*5);
		drawString(nrg, TEXTDISTANCE,TEXTDISTANCE*6);
	}

}
