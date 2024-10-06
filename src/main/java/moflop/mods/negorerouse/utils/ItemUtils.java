package moflop.mods.negorerouse.utils;

import com.google.common.collect.Lists;
import mods.flammpfeil.slashblade.client.model.BladeModel;
import mods.flammpfeil.slashblade.tileentity.DummyTileEntity;
import moflop.mods.negorerouse.init.NrBlades;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemUtils {
    public static final ModelResourceLocation modelLoc = new ModelResourceLocation("flammpfeil.slashblade:model/named/blade.obj");
    public static final List<Item> NR_SOUL = Lists.newArrayList();
    public static final List<Item> NR_BLADE = Lists.newArrayList();

    public ItemUtils(){
        MinecraftForge.EVENT_BUS.register(this);
        for (Item soul : NR_SOUL){
            registerRender(soul);
        }
        for (Item blade : NR_BLADE){
            registerBlade(blade);
        }
    }

    public void registerBlade(Item blade){
        ModelLoader.setCustomModelResourceLocation(blade, 0, modelLoc);
        ForgeHooksClient.registerTESRItemStack(blade, 0, DummyTileEntity.class);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item)
    {
        ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, model);
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event){
        event.getModelRegistry().putObject(modelLoc, new BladeModel());
    }
}
