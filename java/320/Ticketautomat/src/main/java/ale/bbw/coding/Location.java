package ale.bbw.coding;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Location {
    // Type Declration - variable - init of the class
    HashMap<String, List<SimpleEntry<String, Double>>> locations = new HashMap<>();

    public Location() {
       this.initLocation();
    }
    private void initLocation () {
        List<SimpleEntry<String, Double>> zuerichList = new ArrayList<>();
        zuerichList.add(new SimpleEntry<>("Zürich", 20.00));
        locations.put("8000", zuerichList);

        List<SimpleEntry<String, Double>> buelachList = new ArrayList<>();
        buelachList.add(new SimpleEntry<>("Buealch", 15.00));
        locations.put("8180", buelachList);

        List<SimpleEntry<String, Double>> thalwilList = new ArrayList<>();
        thalwilList.add(new SimpleEntry<>("Tahlwil", 25.00));
        locations.put("8600", thalwilList);
    }

    public HashMap<String, List<SimpleEntry<String, Double>>> getLocations() {
        return this.locations;
    }
}