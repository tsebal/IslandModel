package livestock.herbivores;

import island.Island;
import island.Location;
import livestock.Plant;

import java.util.List;
import java.util.Properties;

public class Caterpillar extends Herbivore {
    private static final float WEIGHT;
    private static final int BREED_FACTOR;

    private final Location location;
    private boolean isMoved;

    static {
        Properties appProp = Island.getAppProp();
        WEIGHT = Float.parseFloat(appProp.getProperty("CaterpillarWeight"));
        BREED_FACTOR = Integer.parseInt(appProp.getProperty("CaterpillarBreedFactor"));
    }

    public Caterpillar(Location location) {
        this.location = location;
    }

    @Override
    public boolean isMoved() {
        return isMoved;
    }

    @Override
    public void setIsMoved(boolean isMoved) {
        this.isMoved = isMoved;
    }

    @Override
    public float getWeight() {
        return WEIGHT;
    }

    @Override
    public void eat(List<Plant> plant) {
        if (!plant.isEmpty()) {
            plant.remove(0);
        } else {
            isDied();
        }
    }

    @Override
    public void move() {
        //Caterpillar can't move from location.
        isDied();
    }

    @Override
    public void moveDirection() {

    }

    @Override
    public void breed() {
        int locationCaterpillarPopulation = location.getPopulation().get("caterpillarPopulation");
        if (locationCaterpillarPopulation / BREED_FACTOR >= 2 &&
                locationCaterpillarPopulation < location.getMaxPopulation().get("maxCaterpillarPopulation")) {
            Caterpillar newCaterpillar = new Caterpillar(location);
            newCaterpillar.setIsMoved(true);
            location.animalArrive(newCaterpillar, "caterpillarPopulation");
        }
    }

    @Override
    public void isDied() {
        location.animalLeave(this, "caterpillarPopulation");
    }
}
