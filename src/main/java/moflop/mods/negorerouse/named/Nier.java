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
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author 520
 * @updateDate 2020/02/13
 */
public class Nier {
    String name = "moflop.slashblade.nier";
    String materialName = "flammpfeil.slashblade.named.muramasa";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 20);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/nier");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/nier");
        ItemSlashBlade.SpecialAttackType.set(tag, 4);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.BaseAttackModifier.set(tag, 9.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 12632256);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, SpecialEffects.WitherEdge);
        customblade.addEnchantment(Enchantments.UNBREAKING, 1);
        customblade.addEnchantment(Enchantments.POWER, 8);
        customblade.addEnchantment(Enchantments.SMITE, 5);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);


        ItemStack materialBlade = BladeUtils.findItemStack("flammpfeil.slashblade", materialName, 1);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"nier"),
                blackblade,materialBlade,
                new Object[]{
                        "SA ",
                        "ABA",
                        " ZX",
                        'A', new ItemStack(NrItems.CHRONOS),
                        'S', new ItemStack(Items.ENDER_PEARL),
                        'Z', new ItemStack(Items.ENDER_EYE),
                        'X', new ItemStack(Items.EMERALD),
                        'B', materialBlade });
        SlashBlade.addRecipe("nier", recipe);
    }
}
