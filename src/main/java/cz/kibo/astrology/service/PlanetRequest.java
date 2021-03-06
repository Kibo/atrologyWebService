package cz.kibo.astrology.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.kibo.api.astrology.domain.Coordinates;

/**
 * Helper class to get Object from JSON String.
 * 
 * @author Tomas Jurman tomasjurman@gmail.com
 */
public class PlanetRequest extends ARequest{
	
	private List<String> planets;
	
	private JSONObject data;
	private boolean hasError = false;
	private StringBuilder errors = new StringBuilder();
	
	private LocalDateTime event;	
	private Coordinates coords;
	private String zodiac;
		
	/**
	 * Creates Object from JSON string
	 * 
	 * @param body JSON string
	 */
	public PlanetRequest(String body) {
		
		this.data = new JSONObject( body );
		
		if( !isValid( this.data ) ) {			
			return;
		}	
					
		this.event  = super.parseEvent(this.data);
		this.coords = super.parseTopo( this.data );
		this.zodiac = super.parseZodiac( this.data );
									
		// planets
		this.planets = new ArrayList<String>();			
		JSONArray planetsData = this.data.getJSONArray("planets");
		for (int i = 0, ln = planetsData.length() ; i < ln ; i++) {
			this.planets.add( planetsData.get(i).toString() );
		}					
	}
		
	public List<String> getPlanets() {
		return planets;
	}
	
	public LocalDateTime getEvent() {
		return event;
	}
		
	public Coordinates getCoordinates() {
		return coords;
	}
	
	public String getZodiac() {
		return this.zodiac;
	}

	public boolean hasError() {
		return this.hasError;
	}
	
	public String errors() {
		return errors.toString();
	}
		
	private boolean isValid( JSONObject body ) {
		
		this.hasError = super.commonValidator(body, this.errors);
											
		if( !this.data.has("planets")) {
			this.errors.append("Property planets not found.");
			this.hasError = true;
		}
					
		return !this.hasError;		
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + ((planets == null) ? 0 : planets.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlanetRequest other = (PlanetRequest) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (planets == null) {
			if (other.planets != null)
				return false;
		} else if (!planets.equals(other.planets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlanetRequest [data=" + data + "]";
	}
}
