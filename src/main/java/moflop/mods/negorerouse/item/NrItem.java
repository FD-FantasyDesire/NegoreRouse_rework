package moflop.mods.negorerouse.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
public class NrItem extends Item {
    public NrItem(String name){
        super();
        this.setRegistryName(name);
        ForgeRegistries.ITEMS.register(this);
    }
}
