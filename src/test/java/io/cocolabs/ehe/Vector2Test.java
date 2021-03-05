package io.cocolabs.ehe;

import org.junit.jupiter.api.Test;
import zombie.GameTime;
import zombie.iso.IsoUtils;
import zombie.iso.Vector2;

class Vector2Test {

    @Test
    void shouldHomeToTargetPosition() {

        Vector2 helicopter = new Vector2(1, 1);
        Vector2 player = new Vector2(100, 250);

        while(helicopter.distanceTo(player) > 1)
        {
            Vector2 cloned = helicopter.clone();
            cloned.aimAt(player);

            cloned.normalize();
            cloned.setLength(0.5F);

            helicopter.x += cloned.x;
            helicopter.y += cloned.y;

            System.out.println(helicopter);
        }
    }
}
