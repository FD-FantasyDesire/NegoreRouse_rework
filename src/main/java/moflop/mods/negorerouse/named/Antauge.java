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


public class Antauge {
    String name = "moflop.slashblade.antauge";
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
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/antauge");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/antauge");
        ItemSlashBlade.SpecialAttackType.set(tag, 2);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 2000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 49.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0x00CC00);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.BACK);
        customblade.addEnchantment(Enchantments.SHARPNESS,7);
        customblade.addEnchantment(Enchantments.UNBREAKING,4);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT,5);
        customblade.addEnchantment(Enchantments.SMITE,8);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack ingotSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "ingot_bladesoul", 1);
        ItemStack tinySoul = BladeUtils.findItemStack("flammpfeil.slashblade", "tiny_bladesoul", 1);

        ItemStack materialBlade = BladeUtils.findItemStack("flammpfeil.slashblade", materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 15000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"antauge"),
                blackblade,materialBlade,
                new Object[]{
                        "ABC",
                        "DEF",
                        "GHI",
                        'A', sphereSoul,
                        'B', new ItemStack(NrItems.CHAOS),
                        'C', new ItemStack(Items.NETHER_STAR),
                        'D', new ItemStack(Blocks.GLOWSTONE),
                        'E', materialBlade,
                        'F', new ItemStack(Items.IRON_INGOT),
                        'G', new ItemStack(Items.EXPERIENCE_BOTTLE),
                        'H', new ItemStack(NrItems.HERCULES),
                        'I', tinySoul
                });
        SlashBlade.addRecipe("antauge", recipe);
    }
}
