package island;

import java.util.Iterator;
import java.util.Random;

public class Horse extends Animal {
    public Horse() {
        weight = 400;
        maxCountPerCell = 20;
        speed = 4;
        foodNeeded = 60;
        icon = "🐎";
    }

    @Override
    public void eat(Location location) {
        Iterator<Plant> iterator = location.plants.iterator();
        double eaten = 0;
        while (iterator.hasNext() && eaten < foodNeeded) {
            iterator.next();
            iterator.remove();
            eaten += Plant.WEIGHT;
        }
        if (eaten < foodNeeded) isAlive = false;
    }

    @Override
    public void move(Island island, int x, int y) {
        Random random = new Random();
        int dx = random.nextInt(speed * 2 + 1) - speed;
        int dy = random.nextInt(speed * 2 + 1) - speed;
        Location newLoc = island.getLocation(x + dx, y + dy);
        if (newLoc != null && newLoc.animals.stream().filter(a -> a instanceof Horse).count() < maxCountPerCell) {
            island.getLocation(x, y).animals.remove(this);
            newLoc.animals.add(this);
        }
    }

    @Override
    public void reproduce(Location location) {
        long count = location.animals.stream().filter(a -> a instanceof Horse).count();
        if (count >= 2 && count < maxCountPerCell) {
            location.animals.add(new Horse());
        }
    }
}