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
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class Ananke {
    String name = "moflop.slashblade.ananke";
    String materialName = "moflop.slashblade.nyx";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 1000);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/ananke");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/nyx");
        ItemSlashBlade.SpecialAttackType.set(tag, 110);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 2000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 36.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0xFFBB00);
        SpecialEffects.addEffect(customblade, NrSEs.ABSOLUTE_POWER);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.FATE);
        customblade.addEnchantment(Enchantments.UNBREAKING, 5);
        customblade.addEnchantment(Enchantments.POWER, 9);
        customblade.addEnchantment(Enchantments.FIRE_ASPECT,3);
        customblade.addEnchantment(Enchantments.SMITE,10);
        customblade.addEnchantment(Enchantments.KNOCKBACK,2);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack materialBlade = BladeUtils.findItemStack(NegoreRouse.MODID, materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 40000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);

        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"nyx"),
                blackblade,materialBlade,
                new Object[]{
                        "SOD",
                        "NAN",
                        "GBS",
                        'A', materialBlade,
                        'O', new ItemStack(Blocks.OBSIDIAN),
                        'D', new ItemStack(Items.DIAMOND_SWORD),
                        'G', new ItemStack(Items.GOLDEN_SWORD),
                        'S', new ItemStack(NrItems.SOUL_MIX),
                        'N', new ItemStack(NrItems.FATE),
                        'B', new ItemStack(Blocks.GOLD_BLOCK)}
        );
        SlashBlade.addRecipe("artemis", recipe);
    }
}
