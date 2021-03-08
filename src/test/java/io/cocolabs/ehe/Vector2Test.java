package io.cocolabs.ehe;

import org.junit.jupiter.api.Test;
import zombie.iso.Vector2;

import java.util.Random;

class Vector2Test {

    private static final Random RAND = new Random();

    @Test
    void shouldHomeToTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
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
        for (; normalHelicopter.getDistanceTo(player) > 1; stepsWithDefaultSpeed++) {
            normalHelicopter.moveToPosition(player);
        }
        String format = "Normal helicopter took %d steps to reach target\n";
        System.out.printf(format, stepsWithDefaultSpeed);

        Helicopter fasterHelicopter = new Helicopter(new Vector2(1, 1), 2.0f);

        int stepsWithFastSpeed = 0;
        for (; fasterHelicopter.getDistanceTo(player) > 1; stepsWithFastSpeed++) {
            fasterHelicopter.moveToPosition(player);
        }
        System.out.printf(format, stepsWithFastSpeed);
    }

    @Test
    void shouldHomeToMovingTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = getRandomVector(75, 250);
        Helicopter helicopter = new Helicopter();

        float startPosDist = helicopter.getDistanceTo(player);
        System.out.println("\n" + "startPosDist:" + startPosDist + "\n");

        int[][] array = new int[][] {
                new int[] { 20, 30 },
                new int[] { 35, 40 },
                new int[] { 60, 90 },
        };

        int turns = 0;
        for (int i = 0; helicopter.getDistanceTo(player) > helicopter.getDistanceTraveled(); i++)
        {
            if (turns < 3 && (helicopter.getDistanceTo(player) > (startPosDist/(turns+1))) )
            {
                player.x = array[turns][0];
                player.y = array[turns][1];
                turns++;

                System.out.println("--player_pos: x:" + player.x + " y:" + player.x + " heli 2 player:" + helicopter.getDistanceTo(player));
            }
            helicopter.moveToPosition(player);
            System.out.println("i:" + i + ' ' + helicopter);
        }
    }

    private static Vector2 getRandomVector(int min, int max) {
        return new Vector2(RAND.nextInt(max) + min, RAND.nextInt(max) + min);
    }
}
