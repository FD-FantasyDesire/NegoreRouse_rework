package moflop.mods.negorerouse.named;

import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
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
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author 520
 * @updateDate 2020/02/13
 */
public class Protogenoi {
    String name = "moflop.slashblade.protogenoi";
    String materialName = "moflop.slashblade.chronosn";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 999);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/protogenoi");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/protogenoi");
        ItemSlashBlade.SpecialAttackType.set(tag, 111);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 50000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 50.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 8323327);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.ABSOLUTE_POWER);
        SpecialEffects.addEffect(customblade, NrSEs.BACK);
        customblade.addEnchantment(Enchantments.UNBREAKING, 10);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);


        ItemStack materialBlade = BladeUtils.findItemStack(NegoreRouse.MODID, materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 50000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"protogenoi"),
                blackblade,materialBlade,
                new Object[]{
                        " A ",
                        " S ",
                        " A ",
                        'A', new ItemStack(NrItems.SOUL_MIX),
                        'S', materialBlade });
        SlashBlade.addRecipe("protogenoi", recipe);
    }
}
