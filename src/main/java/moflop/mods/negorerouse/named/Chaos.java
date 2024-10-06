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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class Chaos {
    String name = "moflop.slashblade.chaos";
    String materialName = "moflop.slashblade.chronos";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 10000);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/chaos");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/chaos");
        ItemSlashBlade.SpecialAttackType.set(tag, 3);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 2000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 50.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0x663399);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        customblade.addEnchantment(Enchantments.SHARPNESS, 10);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

        ItemStack materialBlade = BladeUtils.findItemStack(NegoreRouse.MODID, materialName, 1);
        NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(materialBlade);
        ItemSlashBlade.ProudSoul.set(reqTag, 10000);
        ItemSlashBlade.KillCount.set(reqTag, 1000);
        ItemStack blackblade = BladeUtils.findItemStack(NegoreRouse.MODID, name, 1);
        IRecipe recipe = new NrRecipeBlade(new ResourceLocation(NegoreRouse.MODID,"chaos"),
                blackblade,materialBlade,
                new Object[]{
                        " AB",
                        "CDE",
                        "FAG",
                        'A', new ItemStack(NrItems.CHAOS),
                        'B', new ItemStack(Items.NETHER_STAR),
                        'C', new ItemStack(Blocks.LAPIS_BLOCK),
                        'D', materialBlade,
                        'E', new ItemStack(NrItems.CHRONOS),
                        'F', SlashBlade.findItemStack(SlashBlade.modid, SlashBlade.TinyBladeSoulStr , 1),
                        'G', new ItemStack(Blocks.GOLD_BLOCK)
                });
        SlashBlade.addRecipe("chaos", recipe);
    }
}
