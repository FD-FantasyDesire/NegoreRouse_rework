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
public class Hercules {
    String name = "moflop.slashblade.hercules";
    String materialName = "moflop.slashblade.artemis";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 60);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/hercules");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/hercules");
        ItemSlashBlade.SpecialAttackType.set(tag, 6);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.KillCount.set(tag, 1000);
        ItemSlashBlade.ProudSoul.set(tag, 2000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 17.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 16711680);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.BURST_DRIVE);
        customblade.addEnchantment(Enchantments.UNBREAKING, 3);
        customblade.addEnchantment(Enchantments.POWER, 5);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT, 2);
        customblade.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
        customblade.addEnchantment(Enchantments.SMITE, 3);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);


        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);

        ItemStack materialBlade = BladeUtils.findItemStack(NegoreRouse.MODID, materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.KillCount.set(reqTag, 1000);
        ItemSlashBlade.ProudSoul.set(reqTag, 2000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"hercules"),
                blackblade,materialBlade,
                new Object[]{
                        " AS",
                        "ABA",
                        "ZA ",
                        'A', new ItemStack(NrItems.HERCULES),
                        'S', sphereSoul,
                        'Z', new ItemStack(Items.LAVA_BUCKET),
                        'B', materialBlade });
        SlashBlade.addRecipe("hercules", recipe);
    }
}
