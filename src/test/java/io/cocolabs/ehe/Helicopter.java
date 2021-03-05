package io.cocolabs.ehe;

import zombie.iso.Vector2;

public class Helicopter {

    private final Vector2 position;

    public Helicopter() {
        this.position = new Vector2(1, 1);
    }

    public Helicopter(Vector2 position) {
        this.position = position;
    }

    public void moveToPosition(Vector2 destination) {

        Vector2 cloned = position.clone();
        cloned.aimAt(destination);

        cloned.normalize();
        cloned.setLength(0.5F);

        position.x += cloned.x;
        position.y += cloned.y;
    }

    public float getDistanceTo(Vector2 target) {
        return position.distanceTo(target);
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
