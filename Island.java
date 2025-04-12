package island;

import java.util.Random;
import java.util.concurrent.*;

public class Island {
    private final int width;
    private final int height;
    private final Location[][] locations;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ExecutorService animalPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        this.locations = new Location[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                locations[i][j] = new Location();
            }
        }
        for (int i = 0; i < 10; i++) {
            int x = new Random().nextInt(height);
            int y = new Random().nextInt(width);
            locations[x][y].animals.add(new Horse());
        }
    }

    public void startSimulation() {
        scheduler.scheduleAtFixedRate(() -> {
            growPlants();
            runAnimals();
            printStatistics();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void growPlants() {
        for (Location[] row : locations) {
            for (Location loc : row) {
                loc.growPlants();
            }
        }
    }

    private void runAnimals() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int finalI = i;
                int finalJ = j;
                for (Animal animal : locations[i][j].animals) {
                    animalPool.submit(() -> {
                        animal.eat(locations[finalI][finalJ]);
                        animal.move(this, finalI, finalJ);
                        animal.reproduce(locations[finalI][finalJ]);
                    });
                }
            }
        }
    }

    private void printStatistics() {
        int totalAnimals = 0;
        int totalPlants = 0;
        for (Location[] row : locations) {
            for (Location loc : row) {
                totalAnimals += loc.animals.size();
                totalPlants += loc.plants.size();
            }
        }
        System.out.printf("Тварин: %d, Рослин: %d\n", totalAnimals, totalPlants);
    }

    public Location getLocation(int x, int y) {
        if (x >= 0 && y >= 0 && x < height && y < width) {
            return locations[x][y];
        }
        return null;
    }
}