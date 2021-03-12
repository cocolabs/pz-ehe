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

        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

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
        String format = "Helicopter (speed: %f) took %d steps to reach target\n";
        System.out.printf(format, normalHelicopter.getSpeed(), stepsWithDefaultSpeed);

        Helicopter fasterHelicopter = new Helicopter(new Vector2(1, 1), 4f);

        int stepsWithFastSpeed = 0;
        for (; fasterHelicopter.getDistanceTo(player) > 1; stepsWithFastSpeed++) {
            fasterHelicopter.moveToPosition(player);
        }
        System.out.printf(format, fasterHelicopter.getSpeed(), stepsWithFastSpeed);
    }

    @Test
    void shouldHomeToMovingTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = getRandomVector(15, 25);
        Helicopter helicopter = new Helicopter();
        helicopter.setSpeed(25);

        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

        float startPosDist = helicopter.getDistanceTo(player);
        System.out.println("startPosDist:" + startPosDist + "\n");

        int[][] array = new int[][] {
                new int[] { 120, 130 },
                new int[] { 35, 40 },
                new int[] { 160, 190 },
        };

        int turnsInitial = 3;
        int turnsLeft = turnsInitial;

        for (int i = 0; helicopter.getDistanceTo(player) > 1; i++) {

            //max and min multi-purposes turnsInitial and turnsCurrent to move the player
            //movements are total distance divided by turnsInitial * turnsLeft,
            //Example: turnsInitial is 3, the player will move at the following points 0%, 33.33~% and 66.66~% of the trip
            float max = (startPosDist/turnsInitial)*(turnsLeft);
            float min = (startPosDist/turnsInitial)*(turnsLeft-1);

            //Move player while in-between specific range of distance
            if (turnsLeft > 0 && helicopter.getDistanceTo(player) >= min && helicopter.getDistanceTo(player) <= max ) {
                player.x = array[turnsLeft-1][0];
                player.y = array[turnsLeft-1][1];
                System.out.println("[turn: "+turnsLeft+"] player moved to: x:" + player.x + " y:" + player.y + " heli-to-player:" + helicopter.getDistanceTo(player));
                turnsLeft--;
            }
            helicopter.moveToPosition(player);
            System.out.println("i:" + i + ' ' + helicopter);
        }
    }

    @Test
    void shouldPassOverMovingTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = getRandomVector(15, 25);
        Helicopter helicopter = new Helicopter();
        helicopter.setSpeed(25);

        Vector2 movement = helicopter.setVectorAndAim(player);

        boolean HeliPassedPlayer = false;
        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

        float startPosDist = helicopter.getDistanceTo(player);
        System.out.println("startPosDist:" + startPosDist + "\n");

        int[][] array = new int[][] {
                new int[] { 120, 130 },
                new int[] { 35, 40 },
                new int[] { 160, 190 },
        };

        int turnsInitial = 3;
        int turnsLeft = turnsInitial;

        for (int i = 0; helicopter.getPositionX() < 1500 && helicopter.getPositionY() < 1500
                && helicopter.getPositionX() > 0 && helicopter.getPositionY() > 0 ; i++) {

            //max and min multi-purposes turnsInitial and turnsCurrent to move the player
            //movements are total distance divided by turnsInitial * turnsLeft,
            //Example: turnsInitial is 3, the player will move at the following points 0%, 33.33~% and 66.66~% of the trip
            float max = (startPosDist/turnsInitial)*(turnsLeft);
            float min = (startPosDist/turnsInitial)*(turnsLeft-1);

            //Move player while in-between specific range of distance
            if (turnsLeft > 0 && helicopter.getDistanceTo(player) >= min && helicopter.getDistanceTo(player) <= max ) {
                player.x = array[turnsLeft-1][0];
                player.y = array[turnsLeft-1][1];
                System.out.println("[turn: "+turnsLeft+"] player moved to: x:" + player.x + " y:" + player.y + " heli-to-player:" + helicopter.getDistanceTo(player));
                turnsLeft--;
            }

            //Checks if the heli comes with in a range of 10 before no longer aiming at the player
            if (!HeliPassedPlayer) {
                movement = helicopter.setVectorAndAim(player);
                helicopter.dampenVectorMovement(movement, player);
                if (helicopter.getDistanceTo(player) < 10 ) {
                    HeliPassedPlayer = true;
                    System.out.println("\nhelicopter flew over player at: x:" + helicopter.getPositionX() + " y:" + helicopter.getPositionY()+"\n");
                }
            }
            helicopter.stepAlongVector(movement, player);
            System.out.println("i:" + i + ' ' + helicopter);

        }
        System.out.println("\nheli end: x:" + helicopter.getPositionX() + " y:" + helicopter.getPositionY());
    }

    private static Vector2 getRandomVector(int min, int max) {
        return new Vector2(RAND.nextInt(max) + min, RAND.nextInt(max) + min);
    }
}
