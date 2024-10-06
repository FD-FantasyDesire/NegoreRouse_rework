package moflop.mods.negorerouse;

import moflop.mods.negorerouse.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Moflop
 * @updateDate 2020/02/12
 */
@Mod(
        modid = NegoreRouse.MODID,
        name = NegoreRouse.NAME,
        version = NegoreRouse.VER,
        dependencies = NegoreRouse.DEP,
        acceptedMinecraftVersions = "[1.12.2]"
)
public class NegoreRouse {
    public static final String MODID = "negorerouse";
    public static final String NAME = "NegroeRouse";
    public static final String VER = "r3";/** 版本号 */
    public static final String DEP = "required-after:flammpfeil.slashblade@[mc1.12-r30,);";/** 依赖拔刀剑版本 */
    public static final String[] AUTHOR = {"Moflop","AbbyQAQ","X_Big_Bean","JSC_Luciela","JSC_FSGRKV","Cat","520","TennouboshiUzume"};/** 制作者名单[排名不分先后] */
// 2024
    public static Logger logger = LogManager.getLogger(MODID);

    @Instance(NegoreRouse.MODID)
    public static NegoreRouse instance;

    @SidedProxy(
            clientSide = "moflop.mods.negorerouse.client.ClientProxy",
            serverSide = "moflop.mods.negorerouse.common.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
