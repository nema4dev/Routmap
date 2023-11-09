package data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Place;
import org.jpl7.Query;
import org.jpl7.Term;

/**
 *
 * @author Nelson Navarro
 */
public class ExtractData {
    private final String prologFile;

    public ExtractData(String prologFile) {
        this.prologFile = prologFile;
    }

    private Query getQuery(String query) {
        Query consultQuery = new Query("consult('" + prologFile + "')");
        if (!consultQuery.hasSolution()) {
            System.out.println("Error de carga en datos.");
            return null;
        }
        return new Query(query);
    }
    
    public List<Place> getPlaces() {
        List<Place> places = new ArrayList<>();
        Query getPlacesQuery = getQuery("place(Place, Lat, Lon)");

        while (getPlacesQuery != null && getPlacesQuery.hasMoreSolutions()) {
            java.util.Map<String, Term> solution = getPlacesQuery.nextSolution();
            Term placeTerm = solution.get("Place");
            Term latTerm = solution.get("Lat");
            Term lonTerm = solution.get("Lon");

            if (placeTerm != null && latTerm != null && lonTerm != null) {
                String placeName = placeTerm.toString();
                double latitude = latTerm.doubleValue();
                double longitude = lonTerm.doubleValue();
                Place place = new Place(placeName, latitude, longitude);
                places.add(place);
            }
        }

        return places;
    }

    public Place getPlace(String placeName) {
        Query getPlacesQuery = getQuery("place(" + placeName + ", Lat, Lon)");
        if (getPlacesQuery != null && getPlacesQuery.hasSolution()) {
            java.util.Map<String, Term> solution = getPlacesQuery.oneSolution();
            Term latTerm = solution.get("Lat");
            Term lonTerm = solution.get("Lon");

            if (latTerm != null && lonTerm != null) {
                double latitude = latTerm.doubleValue();
                double longitude = lonTerm.doubleValue();
                return new Place(placeName, latitude, longitude);
            }
        }

        return null;
    }
}