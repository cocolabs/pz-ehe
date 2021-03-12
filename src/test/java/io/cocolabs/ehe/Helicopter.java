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

    public Vector2 setUpMovement(Vector2 destination) {
    /**
     * Normalizes, lengthens, and aims a Vector2 to another Vector2
     * @param destination {@code Vector2}
     * @return normalized and lenghtened {@code Vector2} aimed at destination
     */

        //Call this method aimAndMoveTo
        //First you use aimAt then you use setLength
        //lengthen-ing is basically moving no?

        //Vector2s in essence are infinite lines (linear equations)
        //lengthening cuts this line to the smallest possible amount which still maintains an angle
        //otherwise it would be a "point" - that's why there is a lengthen within normalize

        Vector2 movement = position.clone();
        movement.aimAt(destination);

        movement.normalize();
        movement.setLength(speed);

        return movement;
    }

    public void moveStep(Vector2 movement, Vector2 destination) {
    /**
     * Move Helicopter along {@code Vector2} movement; destination is used to track travel distance
     * @param movement
     * @param destination
     */

        float lastDistance = position.distanceTo(destination);

        position.x += movement.x;
        position.y += movement.y;

        distanceTraveled += lastDistance - position.distanceTo(destination);
    }

    public void moveDampen(Vector2 movement, Vector2 destination) {
    /**
     * Slows down rate of movement along {@code Vector2} movement the closer it gets to {@code Vector2} destination
     * @param movement
     * @param destination
     */

        movement.x *= Math.max(0.1f,((destination.x - position.x)/destination.x));
        movement.y *= Math.max(0.1f,((destination.y - position.y)/destination.y));
    }

    /**
     * Utilizes: setVectorAndAim, dampenVectorMovement, stepAlongVector to move Helicopter towards {@code Vector2} destination
     * @param destination
     */
    public void moveToPosition(Vector2 destination) {

        Vector2 movement = this.setUpMovement(destination);

        moveDampen(movement,destination);

        moveStep(movement, destination);
    }

    public float getDistanceTo(Vector2 target) {
        return position.distanceTo(target);
    }

    //region Getters and Setters

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() { return this.speed; }

    public float getDistanceTraveled() {
        return distanceTraveled;
    }

    public float getPositionX() { return this.position.x; }

    public float getPositionY() { return this.position.y; }
    //endregion

    @Override
    public String toString() {
        return String.format("%s (T: %f)", position.toString(), distanceTraveled);
    }
}
