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
public class Artemis {
    String name = "moflop.slashblade.artemis";
    String materialName = "flammpfeil.slashblade.named.muramasa";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 50);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/artemis");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/artemis");
        ItemSlashBlade.SpecialAttackType.set(tag, 6);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 20000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 14.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0x00FFFF);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        customblade.addEnchantment(Enchantments.UNBREAKING, 3);
        customblade.addEnchantment(Enchantments.POWER, 5);
        customblade.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 3);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack materialBlade = BladeUtils.findItemStack("flammpfeil.slashblade", materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 2000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"artemis"),
                blackblade,materialBlade,
                new Object[]{
                        " AS",
                        "ABA",
                        "ZA ",
                        'A', new ItemStack(NrItems.ARITEMIS),
                        'S', new ItemStack(Items.WATER_BUCKET),
                        'B', materialBlade,
                        'Z', new ItemStack(Blocks.DIAMOND_BLOCK) });
        SlashBlade.addRecipe("artemis", recipe);
    }
}
