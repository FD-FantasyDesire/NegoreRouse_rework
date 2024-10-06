package moflop.mods.negorerouse.init;

import mods.flammpfeil.slashblade.SlashBlade;
import moflop.mods.negorerouse.creativetab.NrTabs;
import moflop.mods.negorerouse.named.*;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import net.minecraft.item.Item;

/**
 * @author Cat
 * @updateDate 2020/02/14
 */
public class NrBlades {
    public static final Item NR_BLADE = new ItemNrSlashBlade(Item.ToolMaterial.IRON, 4.0f,"nrSlashBlade")
            .setMaxDamage(40)
            .setCreativeTab(NrTabs.Nr_Item);

    public NrBlades(){
        loadBlade();
    }

    public void loadBlade(){
        loadBlade(new Artemis());
        loadBlade(new Artemis_s());
        loadBlade(new Hercules());
        loadBlade(new Nier());
        loadBlade(new Chronos());
        loadBlade(new Erebus());
        loadBlade(new Chaos());
        loadBlade(new Antauge());
        loadBlade(new ChronosN());
        loadBlade(new Chronos_SY());
        loadBlade(new Chronos_AE());
        loadBlade(new Protogenoi());
        loadBlade(new Tartarus());
        loadBlade(new Nyx());
        loadBlade(new Ananke());
        loadBlade(new Deligun());
    }


    public void loadBlade(Object blade) {
        SlashBlade.InitEventBus.register(blade);
    }

}
