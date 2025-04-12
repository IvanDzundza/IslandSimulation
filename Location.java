package island;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Location {
    public final List<Animal> animals = new CopyOnWriteArrayList<>();
    public final List<Plant> plants = new CopyOnWriteArrayList<>();

    public void growPlants() {
        plants.add(new Plant());
    }
}