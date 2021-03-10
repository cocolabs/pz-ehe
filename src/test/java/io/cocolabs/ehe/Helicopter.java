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

        Vector2 movement = position.clone();
        movement.aimAt(destination);

        movement.normalize();
        movement.setLength(speed);

        movement.x *= Math.max(0.1f,((destination.x - position.x)/destination.x));
        movement.y *= Math.max(0.1f,((destination.y - position.y)/destination.y));

        float lastDistance = position.distanceTo(destination);

        position.x += movement.x;
        position.y += movement.y;

        distanceTraveled += lastDistance - position.distanceTo(destination);
    }

    public void moveDampen(Vector2 movement, Vector2 destination) {

        movement.x *= Math.max(0.1f,((destination.x - position.x)/destination.x));
        movement.y *= Math.max(0.1f,((destination.y - position.y)/destination.y));
    }
    public float getDistanceTo(Vector2 target) {
        return position.distanceTo(target);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() { return this.speed; }

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    public float getPositionX() { return this.position.x; }

    public float getPositionY() { return this.position.y; }

    @Override
    public String toString() {
        return String.format("%s (T: %f)", position.toString(), distanceTraveled);
    }
}
