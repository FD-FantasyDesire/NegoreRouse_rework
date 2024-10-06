package moflop.mods.negorerouse.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class ParticleUtils {

    public static void spawnParticle(EnumParticleTypes type, EntityPlayer player, int num, double rate)
    {
        // EntityBase.spawnExplodeParticle() とほぼ同じだが
        // こっちは、Y座標が固定

        World world = player.world;
        Random rand = player.getRNG();

        for (int i = 0; i < num; i++) {
            double xSpeed = rand.nextGaussian() * 0.02;
            double ySpeed = rand.nextGaussian() * 0.02;
            double zSpeed = rand.nextGaussian() * 0.02;

            double rx = rand.nextDouble();
//			double ry = rand.nextDouble();
            double rz = rand.nextDouble();

            world.spawnParticle(
                    type,
                    player.posX + ((rx*2 - 1)*player.width  - xSpeed * 10.0)*rate,
                    player.posY+1,
                    player.posZ + ((rz*2 - 1)*player.width  - zSpeed * 10.0)*rate,
                    xSpeed, ySpeed, zSpeed);
        }
    }
}
