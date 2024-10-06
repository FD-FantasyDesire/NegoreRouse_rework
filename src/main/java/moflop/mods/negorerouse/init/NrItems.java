package moflop.mods.negorerouse.init;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.client.model.BladeModel;
import mods.flammpfeil.slashblade.event.ModelRegister;
import mods.flammpfeil.slashblade.tileentity.DummyTileEntity;
import moflop.mods.negorerouse.creativetab.NrTabs;
import moflop.mods.negorerouse.item.ItemSoul;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @author AbbyQAQ
 * @updateDate 2020/02/13
 */
public class NrItems {
    public static final Item ARITEMIS = new ItemSoul("aritemis");
    public static final Item HERCULES = new ItemSoul("hercules");
    public static final Item CHRONOS = new ItemSoul("chronos");
    public static final Item EREBUS = new ItemSoul("erebus");
    public static final Item SOUL_MIX = new ItemSoul("soulMix");
    public static final Item FATE = new ItemSoul("fate");
    public static final Item TARTARUS = new ItemSoul("tartarus");
    public static final Item CHAOS = new ItemSoul("chaos");

}
