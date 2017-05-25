package module6;

import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;

import processing.core.PConstants;
import processing.core.PFont;

import processing.core.PApplet;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	private int id;
	public static int TRI_SIZE = 5;
	PApplet pa;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	    id= Integer.parseInt(city.getId());
	}
	
	/*void setup()
	{
        //size(800,600);
	}
	*/
	

	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(11);
		pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String name = "City:"+getCity() + " ";
		String pop="country:"+getCountry() + " ";
		//TitleOverImage im=new TitleOverImage();         
		// trying to draw title on top of other markers but can't understand how
		//pg=PApplet.createGraphics(100,100);
		/*pa =new PApplet();
         pg=pa.createGraphics(100, 100);
		pg.beginDraw();
		  pg.background(255,0);
		  pg.noFill();
		  pg.stroke(255);
		  pg.ellipse(pa.mouseX-120, pa.mouseY-60, 60, 60);
		  pg.endDraw();
		  pg.image(pg, 120, 60);

			pg.textSize(12);

			pg.textAlign(PConstants.LEFT, PConstants.TOP);
			pg.text(name, x+3, y-TRI_SIZE-33);
			pg.text(pop, x+3, y - TRI_SIZE -18);*/
		/*pg.pushStyle();
		
		pg.fill(255, 255, 255);
		
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-TRI_SIZE-39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
		pg.fill(0, 0, 0);
		
		pg.popStyle();*/
		


		
		
		
	}
	
	/*public void drawText() {
		  //translate(width/2,height/2);
		  textAlign(LEFT, CENTER);
		  fill(255, 255, 255);
		  textSize(12);
		  text("RED: ", width/2, height/2);
		}
	public void drawBox() {

		 pgp.beginDraw();
		 pgp.rectMode(CENTER);

		  pgp.translate(width/2, height/2);
		  
		  pgp.fill(255, 255, 255);
		  pgp.rect(0, 0, 100, 100);


		  pgp.endDraw();
		}*/
	
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	public int getID()
	{
		return id;
	}
	

	
}
