package io.cocolabs.ehe;

import org.junit.jupiter.api.Test;
import zombie.GameTime;
import zombie.iso.IsoUtils;
import zombie.iso.Vector2;

import java.util.Random;

class Vector2Test {

    private static final Random RAND = new Random();

    @Test
    void shouldHomeToTargetPosition() {

        Vector2 player = getRandomVector(75, 250);
        Helicopter helicopter = new Helicopter();

        for (int i = 0; helicopter.getDistanceTo(player) > 1; i++)
        {
            helicopter.moveToPosition(player);
            System.out.println("i:" + i + ' ' + helicopter);
        }
    }

    @Test
    void shouldHomeToTargetPositionWithSpeed() {
        Vector2 player = getRandomVector(75, 250);
        Helicopter normalHelicopter = new Helicopter();

        int stepsWithDefaultSpeed = 0;
        for (;normalHelicopter.getDistanceTo(player) > 1; stepsWithDefaultSpeed++) {
            normalHelicopter.moveToPosition(player);
        }
        String format = "Normal helicopter took %d steps to reach target";
        System.out.printf(format, stepsWithDefaultSpeed);

        Helicopter fasterHelicopter = new Helicopter(new Vector2(1, 1), 2.0f);

        int stepsWithFastSpeed = 0;
        for (;fasterHelicopter.getDistanceTo(player) > 1; stepsWithFastSpeed++) {
            fasterHelicopter.moveToPosition(player);
        }
        System.out.printf(format, stepsWithFastSpeed);

    @Test
    void shouldHomeToMovingTargetPosition() {

        Vector2 helicopter = new Vector2(1, 1);
        Vector2 player = getRandomVector(75, 250);

        for (int i = 0; helicopter.distanceTo(player) > 1; i++)
        {
            Vector2 cloned = helicopter.clone();
            cloned.aimAt(player);

            cloned.normalize();
            cloned.setLength(0.5F);

            helicopter.x += cloned.x;
            helicopter.y += cloned.y;

            System.out.println("i:" + i + ' ' + helicopter);
        }
    }

    private static Vector2 getRandomVector(int min, int max) {
        return new Vector2(RAND.nextInt(max) + min, RAND.nextInt(max) + min);
    }
}
