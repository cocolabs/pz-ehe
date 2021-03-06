package io.cocolabs.ehe;

import zombie.iso.Vector2;

public class Helicopter {

    private final Vector2 position;
    private float speed;
    private float distanceTraveled;

    public Helicopter() {
        this.position = new Vector2(1, 1);
        this.speed = 1.0f;
    }

    public Helicopter(Vector2 position, float speed) {
        this.position = position;
        this.speed = speed;
    }

    public void moveToPosition(Vector2 destination) {

        Vector2 cloned = position.clone();
        cloned.aimAt(destination);

        cloned.normalize();
        cloned.setLength(speed);

        float lastDistance = cloned.distanceTo(destination);

        position.x += cloned.x;
        position.y += cloned.y;

        distanceTraveled += lastDistance - position.distanceTo(destination);
    }

    public float getDistanceTo(Vector2 target) {
        return position.distanceTo(target);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    @Override
    public String toString() {
        return String.format("%s (T: %f)", position.toString(), distanceTraveled);
    }
}
