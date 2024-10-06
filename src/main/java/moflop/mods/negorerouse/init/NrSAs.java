package moflop.mods.negorerouse.init;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import moflop.mods.negorerouse.named.*;
import moflop.mods.negorerouse.specialattack.*;

public class NrSAs {
    public  NrSAs(){
            loadSAs();
    }
    public void loadSAs(){
        ItemSlashBlade.specialAttacks.put(107, new MagneticStormSword());
        ItemSlashBlade.specialAttacks.put(108, new BurningFireSA());
        ItemSlashBlade.specialAttacks.put(109, new DivineCrossSA());
        ItemSlashBlade.specialAttacks.put(110, new Zenith12th());
        ItemSlashBlade.specialAttacks.put(111, new CosmicLine());
        ItemSlashBlade.specialAttacks.put(112, new OverTheHorizon());

        ItemSlashBlade.specialAttacks.put(199, new ExcessBlade());
//            System.out.println("SA registed!");
    }
}
