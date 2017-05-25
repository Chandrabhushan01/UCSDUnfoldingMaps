package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class TitleOverImage extends PApplet {
	

	//create a buffer to draw boxes to
	PGraphics pg;

	public void setup(PGraphics buffer) {
	  size(100, 100);

	  buffer = createGraphics(100, 100);
	}

	

	//draw boxes to buffer
	

	public void draw() {

	  
	}

}
