package io.cocolabs.ehe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import zombie.iso.Vector2;

import java.util.Random;

class Vector2Test {

    private static final Random RAND = new Random();
    private final Helicopter helicopter = new Helicopter();

    @Test
    void shouldHomeToTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = getRandomVector(75, 250);

        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

        for (int i = 0; helicopter.getDistanceTo(player) > 1; i++)
        {
            helicopter.moveToPosition(player, null, true);
            System.out.println("i:" + i + ' ' + helicopter);
            Assertions.assertTrue(i < 4855);
        }
    }

    @Test
    void shouldHomeToTargetPositionWithSpeed() {

        Vector2 player = getRandomVector(75, 250);

        int stepsWithDefaultSpeed = 0;
        for (; helicopter.getDistanceTo(player) > 1; stepsWithDefaultSpeed++)
        {
            helicopter.moveToPosition(player, null, true);
            Assertions.assertTrue(stepsWithDefaultSpeed < 4855);
        }
        String format = "Helicopter (speed: %f) took %d steps to reach target\n";
        System.out.printf(format, helicopter.getSpeed(), stepsWithDefaultSpeed);


        Helicopter fasterHelicopter = new Helicopter(new Vector2(1, 1), 4f);

        int stepsWithFastSpeed = 0;
        for (; fasterHelicopter.getDistanceTo(player) > 1; stepsWithFastSpeed++)
        {
            fasterHelicopter.moveToPosition(player, null, true);
            Assertions.assertTrue(stepsWithFastSpeed < 4855);
        }
        System.out.printf(format, fasterHelicopter.getSpeed(), stepsWithFastSpeed);

    }

    @Test
    void shouldHomeToMovingTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = new Vector2(25, 25);
        helicopter.setSpeed(10);

        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

        float startPosDist = helicopter.getDistanceTo(player);
        System.out.println("startPosDist:" + startPosDist + "\n");

        int turnsLeft = 3;
        for (int i = 0; helicopter.getDistanceTo(player) > 1; i++)
        {
            turnsLeft = movePlayerInTurns(startPosDist, turnsLeft, player);
            helicopter.moveToPosition(player, null, true);
            System.out.println("i:" + i + ' ' + helicopter);
            Assertions.assertTrue(i < 2173);
        }
    }

    @Test
    void shouldPassOverMovingTargetPosition() {

        //while these are vectors they are being utilized as coord pairs
        Vector2 player = new Vector2(25, 25);
        helicopter.setSpeed(25);

        Vector2 movement = null;
        boolean heliPassedPlayer = false;
        System.out.println("\n" + "player: x:" + player.getX() + " y:" + player.getY());

        float startPosDist = helicopter.getDistanceTo(player);
        System.out.println("startPosDist:" + startPosDist + "\n");

        int turnsLeft = 3;
        for (int i = 0; helicopter.isInBounds(); i++)
        {
            // bounce the player around
            turnsLeft = movePlayerInTurns(startPosDist, turnsLeft, player);

            //Checks if the heli comes with in a range of 10 before no longer aiming at the player
            if (!heliPassedPlayer)
            {
                movement = helicopter.setVectorAndAim(player, true);
                if (helicopter.getDistanceTo(player) < 10 )
                {
                    heliPassedPlayer = true;
                    System.out.println("\nhelicopter flew over player at: x:" + helicopter.getPositionX() + " y:" + helicopter.getPositionY()+"\n");
                }
            }
            helicopter.moveToPosition(player, movement, false);
            System.out.println("i:" + i + ' ' + helicopter);
        }
        System.out.println("\nend: x:" + helicopter.getPositionX() + " y:" + helicopter.getPositionY());
        Assertions.assertTrue(heliPassedPlayer);
    }

    @Test
    void testIsHelicopterInBounds() {

        // test if x is out of bounds (minimum)
        helicopter.position.x = -1;
        helicopter.position.y = -1;
        Assertions.assertFalse(helicopter.isInBounds());

        // test if y is out of bounds (maximum)
        helicopter.position.x = 1501;
        helicopter.position.y = 1501;
        Assertions.assertFalse(helicopter.isInBounds());

        // test if x is in bounds (minimum)
        helicopter.position.x = 0;
        helicopter.position.y = 0;
        Assertions.assertTrue(helicopter.isInBounds());

        // test if y is in of bounds (maximum)
        helicopter.position.x = 1500;
        helicopter.position.y = 1500;
        Assertions.assertTrue(helicopter.isInBounds());
    }

    /**
     * Moves player in given amount of turns based on helicopter distance to player in relation to starting point
     * @param startPosDist distance between {@code Vector2} player and {@code Vector2} Helicopter starting point
     * @param turns amount of times player will move
     * @return turns left
     */
    private int movePlayerInTurns(float startPosDist, int turns, Vector2 player) {

        int[][] array = new int[][] {
                new int[] { 120, 130 },
                new int[] { 35, 40 },
                new int[] { 160, 190 },
        };
        //max and min multi-purposes turnsInitial and turnsCurrent to move the player
        //Movements are total distance divided by turnsInitial * turnsLeft,
        //Example: turnsInitial is 3, the player will move at the following points 0%, 33.33~% and 66.66~% of the trip
        float max = (startPosDist/3)*(turns);
        float min = (startPosDist/3)*(turns-1);

        //Move player while in-between specific range of distance
        if (turns > 0 && helicopter.getDistanceTo(player) >= min && helicopter.getDistanceTo(player) <= max )
        {
            player.x = array[turns-1][0];
            player.y = array[turns-1][1];
            System.out.println("[turn: "+turns+"] player moved to: x:" + player.x + " y:" + player.y + " heli-to-player:" + helicopter.getDistanceTo(player));
            turns--;
        }
        return turns;
    }

    private static Vector2 getRandomVector(int x, int y) {
        return new Vector2(RAND.nextInt(x) + 1, RAND.nextInt(y) + 1);
    }
}
