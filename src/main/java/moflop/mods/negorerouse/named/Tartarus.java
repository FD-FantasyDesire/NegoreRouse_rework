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

public class Tartarus {
    String name = "moflop.slashblade.tartarus";
    String materialNameA = "moflop.slashblade.erebus";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 91);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/tartarus");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/tartarus");
        ItemSlashBlade.SpecialAttackType.set(tag, 108);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 10000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 25.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0xFF0000);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.REVERSE_POWER);
        SpecialEffects.addEffect(customblade, NrSEs.DEADLOCK);
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
        ItemSlashBlade.ProudSoul.set(reqTag, 5000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"tartarus"),
                blackblade,materialBladeA,
                new Object[]{
                        "ABC",
                        "DEF",
                        "GBH",
                        'A', new ItemStack(Items.GHAST_TEAR),
                        'B', new ItemStack(NrItems.TARTARUS),
                        'C', new ItemStack(Blocks.OBSIDIAN),
                        'D', new ItemStack(Blocks.NETHERRACK),
                        'E', materialBladeA,
                        'F', new ItemStack(Items.NETHER_WART),
                        'G', new ItemStack(Items.SKULL,1,1),
                        'H', new ItemStack(Items.BLAZE_ROD)
                });
        SlashBlade.addRecipe("tartarus", recipe);

    }
}
