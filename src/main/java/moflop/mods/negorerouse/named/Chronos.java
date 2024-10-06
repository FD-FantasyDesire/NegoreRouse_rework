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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 520
 * @updateDate 2020/02/13
 */
public class Chronos {
    String name = "moflop.slashblade.chronos";
    String materialNameA = "moflop.slashblade.nier";
    String materialNameB = "flammpfeil.slashblade.named.yuzukitukumo";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 80);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/chronos");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/chronos");
        ItemSlashBlade.SpecialAttackType.set(tag, 7);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 10000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 15.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 3100495);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        customblade.addEnchantment(Enchantments.UNBREAKING, 3);
        customblade.addEnchantment(Enchantments.SHARPNESS, 5);
        customblade.addEnchantment(Enchantments.POWER, 10);
        customblade.addEnchantment(Enchantments.SMITE, 5);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);


        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack ingotSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "ingot_bladesoul", 1);

        ItemStack materialBladeA = BladeUtils.findItemStack(NegoreRouse.MODID, materialNameA, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBladeA);
        ItemSlashBlade.KillCount.set(reqTag, 1000);
        ItemSlashBlade.ProudSoul.set(reqTag, 2000);
        ItemStack materialBladeB = BladeUtils.findItemStack("flammpfeil.slashblade", materialNameB, 1);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        List<ItemStack> itemStackList = new ArrayList<>();
        itemStackList.add(materialBladeB);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"chronos"),
                blackblade,materialBladeA,
                new Object[]{
                        "ZX ",
                        "SBD",
                        " XH",
                        'Z', sphereSoul,
                        'X', new ItemStack(NrItems.CHRONOS),
                        'S', new ItemStack(Blocks.DIAMOND_BLOCK),
                        'H', ingotSoul,
                        'D', materialBladeB,
                        'B', materialBladeA });
        SlashBlade.addRecipe("chronos", recipe);
    }
}
