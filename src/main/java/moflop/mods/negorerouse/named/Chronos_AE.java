package moflop.mods.negorerouse.named;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.NrRecipeBlade;
import moflop.mods.negorerouse.init.NrBlades;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.init.NrSEs;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import moflop.mods.negorerouse.utils.BladeUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.tools.cmd.Spec;


public class Chronos_AE {
    String name = "moflop.slashblade.chronos_ae";
    String materialName = null;
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 9999);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/aeon");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/aeon");
        ItemSlashBlade.SpecialAttackType.set(tag, 199);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 20000);
        ItemSlashBlade.BaseAttackModifier.set(tag, (float) (Integer.MAX_VALUE-10));
        ItemSlashBlade.SummonedSwordColor.set(tag, 0xCCCC00);
        SpecialEffects.addEffect(customblade, NrSEs.ETERNITY);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.ABSOLUTE_POWER);
        customblade.addEnchantment(Enchantments.UNBREAKING, 5);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack materialBlade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 10000);
        ItemSlashBlade.KillCount.set(reqTag, 10000);
        ItemSlashBlade.RepairCount.set(reqTag, 10000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"chronos_ae"),
                blackblade,materialBlade,
                new Object[]{
                        "ABC",
                        "DEF",
                        "GHI",
                        'A', new ItemStack(NrItems.ARITEMIS),
                        'B', new ItemStack(NrItems.CHRONOS),
                        'C', new ItemStack(NrItems.HERCULES),
                        'D', new ItemStack(NrItems.CHAOS),
                        'E', materialBlade,
                        'F', new ItemStack(NrItems.EREBUS),
                        'G', new ItemStack(NrItems.TARTARUS),
                        'H', new ItemStack(NrItems.FATE),
                        'I', new ItemStack(NrItems.SOUL_MIX)
                });
        SlashBlade.addRecipe("chronos_ae", recipe);
    }
}
