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
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	PGraphics pgp;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
			//System.out.println(feature.getProperties()+" "+feature.getId()+" "+feature.getLocation());
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			//System.out.println(route.getProperties());
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			//System.out.println(route.getProperties());
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
			//System.out.println(sl.getProperties());
			sl.setHidden(true);
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		
		map.addMarkers(airportList);

		pgp=createGraphics(800, 600);
	}
	
	public void draw() {
		background(0);
		map.draw();
		String name="",pop="";
		for (Marker m : airportList) 
		{
			AirportMarker marker = (AirportMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {

				 name ="City:"+ marker.getCity() + " ";
				 pop="Country:"+ marker.getCountry() + " ";
				
				
			}
		}
		  pgp.beginDraw();
		  pgp.background(255,0);
		  pgp.textSize(18);
		  pgp.fill(255);
		  pgp.stroke(0);
		  pgp.rectMode(PConstants.CORNER);
	      pgp.rect(mouseX+5, mouseY+5, Math.max(pgp.textWidth(name), pgp.textWidth(pop)) + 6, 39);
	      pgp.fill(63,25,253);
		  pgp.textAlign(PConstants.LEFT, PConstants.TOP);
	      pgp.text(name,mouseX+7, mouseY+5);
		  pgp.text(pop, mouseX+7, mouseY+23);
		  
		    
		  //pgp.ellipse(mouseX-120, mouseY-60, 60, 60);
		  pgp.endDraw();

		  image(pgp, 0, 0);


		    
			

	}
	

	
	/*@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
		//loop();
	}*/
	
	private void selectMarkerIfHover(List<Marker> markers)
	{
		// Abort if there's already a marker selected
		if (lastSelected != null) {
			return;
		}
		
		for (Marker m : markers) 
		{
			CommonMarker marker = (CommonMarker)m;
			if (marker.isInside(map,  mouseX, mouseY)) {
				lastSelected = marker;
				marker.setSelected(true);
				return;
			}
		}
	}
	
	@Override
	public void mouseClicked()
	{
		if (lastClicked != null) {
			unhideMarkers();
			lastClicked = null;
		}
		else if (lastClicked == null) 
		{
			checkAirportsForClick();
		}
	}
	
	private void checkAirportsForClick()
	{
		if (lastClicked != null) return;
		
		List<Integer> desti= new ArrayList<Integer>();
		
		// Loop over the airportList markers to see if one of them is selected
		for (Marker marker : airportList) {
			if (!marker.isHidden() && marker.isInside(map, mouseX, mouseY)) {
				lastClicked = (CommonMarker)marker;
				// Hide all the other airportLIst and hide
				for (Marker mhide : airportList) {
					if (mhide != lastClicked) {
						mhide.setHidden(true);
					}
				}
				
                 //int airportid=((AirportMarker) marker).getID();
				//System.out.print(((AirportMarker) marker).getID()+" ");
				for(Marker route : routeList) {
					
					int source = Integer.parseInt((String)route.getProperty("source"));

					int dest = Integer.parseInt((String)route.getProperty("destination"));
					//System.out.println(source);
					//desti.add(dest);
					
					if(!(((AirportMarker) marker).getID()==(source)) ) {  
						//System.out.println(marker.getLocation()+" "+source);
						route.setHidden(true);
					}
					if((((AirportMarker) marker).getID()==(source)) ) {  
						//System.out.println(marker.getLocation()+" "+source);
						desti.add(dest);
					}
				}
			}	
		}
		//System.out.println(desti);
		for(Marker marker: airportList)
		{
			for(Integer d:desti)
			{
				if(((AirportMarker) marker).getID()==d)
					marker.setHidden(false);
			}
		}
		
	}
	
	private void unhideMarkers() {
		for(Marker marker : airportList) {
			marker.setHidden(false);
		}
		for(Marker marker : routeList) {
			marker.setHidden(false);
		}
			
	}
	
	
}
