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
public class Erebus {
    String name = "moflop.slashblade.erebus";
    String materialNameA = "moflop.slashblade.chronos";
    String materialNameB = "flammpfeil.slashblade.named.sabigatana";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 160);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/erebus");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/erebus");
        ItemSlashBlade.SpecialAttackType.set(tag, 6);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 5000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 20.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 16777215);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.REVERSE_POWER);
        SpecialEffects.addEffect(customblade, SpecialEffects.WitherEdge);
        customblade.addEnchantment(Enchantments.UNBREAKING, 2);
        customblade.addEnchantment(Enchantments.SHARPNESS, 5);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT, 3);
        customblade.addEnchantment(Enchantments.SMITE, 5);
        customblade.addEnchantment(Enchantments.LOOTING, 3);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);


        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack ingotSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "ingot_bladesoul", 1);

        ItemStack materialBladeA = BladeUtils.findItemStack(NegoreRouse.MODID, materialNameA, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBladeA);
        ItemSlashBlade.ProudSoul.set(reqTag, 5000);
        ItemStack materialBladeB = BladeUtils.findItemStack("flammpfeil.slashblade", materialNameB, 1);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"erebus"),
                blackblade,materialBladeA,
                new Object[]{
                        "ZX ",
                        "SBD",
                        " XH",
                        'Z', sphereSoul,
                        'X', new ItemStack(NrItems.EREBUS),
                        'S', new ItemStack(Blocks.OBSIDIAN),
                        'H', ingotSoul,
                        'D', materialBladeB,
                        'B', materialBladeA });
        SlashBlade.addRecipe("erebus", recipe);
    }
}
