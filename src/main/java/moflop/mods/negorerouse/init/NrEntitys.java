package moflop.mods.negorerouse.init;

import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import static moflop.mods.negorerouse.NegoreRouse.MODID;

public class NrEntitys {
    public NrEntitys(){
        loadEntity();
    }

    public void loadEntity(){
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "entitydriveex"),
                EntityDriveEx.class,
                "EntityDriveEx",
                1,
                NegoreRouse.instance,
                64,
                1,
                false
        );
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "entityphantomswordexbase"),
                EntityPhantomSwordExBase.class,
                "EntityPhantomSwordexbase",
                2,
                NegoreRouse.instance,
                64,
                1,
                false
        );
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "entityphantomswordex"),
                EntityPhantomSwordEx.class,
                "EntityPhantomSwordEx",
                3,
                NegoreRouse.instance,
                64,
                1,
                false
        );
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "magneticstormsword"),
                EntityMagneticStormSword.class,
                "MagneticStormSword",
                4,
                NegoreRouse.instance,
                64,
                1,
                false
        );
        EntityRegistry.registerModEntity(
                new ResourceLocation(MODID, "lightningphantomsword"),
                EntityLightningPhantomSword.class,
                "LightningPhantomSword",
                5,
                NegoreRouse.instance,
                64,
                1,
                false
        );
    }
}
