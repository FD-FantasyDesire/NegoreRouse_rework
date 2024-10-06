package moflop.mods.negorerouse.named;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.capability.BladeCapabilityProvider;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.NrRecipeBlade;
import moflop.mods.negorerouse.init.NrBlades;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.init.NrSEs;
import moflop.mods.negorerouse.item.NrItem;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import moflop.mods.negorerouse.specialeffects.NrSpecialEffects;
import moflop.mods.negorerouse.utils.BladeUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class Artemis_s {
    String name = "moflop.slashblade.artemis_s";
    String materialName = "moflop.slashblade.artemis";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 1024);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/artemiss");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/artemiss");
        ItemSlashBlade.SpecialAttackType.set(tag, 107);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 2000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 20.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0xFF0000);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.CLEAR);
        customblade.addEnchantment(Enchantments.UNBREAKING, 4);
        customblade.addEnchantment(Enchantments.POWER, 7);
        customblade.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 1);
        customblade.addEnchantment(Enchantments.BANE_OF_ARTHROPODS,3);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT,3);
        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack materialBlade = BladeUtils.findItemStack(NegoreRouse.MODID, materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 20000);
        ItemSlashBlade.RepairCount.set(reqTag,5);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"artemis_s"),
                blackblade,materialBlade,
                new Object[]{
                        "ASA",
                        "LBZ",
                        "ASA",
                        'A', new ItemStack(NrItems.ARITEMIS),
                        'S', new ItemStack(Items.NETHER_STAR),
                        'L', new ItemStack(Items.CLOCK),
                        'B', materialBlade,
                        'Z', new ItemStack(Blocks.DAYLIGHT_DETECTOR)}
        );
        SlashBlade.addRecipe("artemis_s", recipe);
    }
}
