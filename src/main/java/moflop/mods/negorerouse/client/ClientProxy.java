package moflop.mods.negorerouse.client;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import moflop.mods.negorerouse.client.render.entity.*;
import moflop.mods.negorerouse.common.CommonProxy;
import moflop.mods.negorerouse.entity.*;
import moflop.mods.negorerouse.utils.ItemUtils;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraft.client.renderer.entity.Render;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        new ItemUtils();

        RenderingRegistry.registerEntityRenderingHandler(
                EntityDriveEx.class,
                new IRenderFactory<EntityDriveEx>() {
                    @Override
                    public Render<? super EntityDriveEx> createRenderFor(RenderManager manager)
                    {
                        return new RenderDriveEx(manager);
                    }
                });
        RenderingRegistry.registerEntityRenderingHandler(
                EntityPhantomSwordExBase.class,
                new IRenderFactory<EntityPhantomSwordExBase>() {
                    @Override
                    public Render<? super EntityPhantomSwordExBase> createRenderFor(RenderManager manager)
                    {
                        return new RenderPhantomSwordExBase(manager);
                    }
                });
        RenderingRegistry.registerEntityRenderingHandler(
                EntityPhantomSwordEx.class,
                new IRenderFactory<EntityPhantomSwordEx>() {
                    @Override
                    public Render<? super EntityPhantomSwordEx> createRenderFor(RenderManager manager)
                    {
                        return new RenderPhantomSwordEx(manager);
                    }
                });
}


    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }

}
