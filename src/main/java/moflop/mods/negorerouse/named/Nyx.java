package moflop.mods.negorerouse.named;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.entity.EntityBladeStand;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;
import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.NrRecipeBlade;
import moflop.mods.negorerouse.init.NrBlades;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.init.NrSEs;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import moflop.mods.negorerouse.utils.BladeUtils;
import moflop.mods.negorerouse.utils.WorldBladeStandCrafting;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Nyx {
    String name = "moflop.slashblade.nyx";
    String materialNameA = "moflop.slashblade.chronosn";
    @SubscribeEvent
    public void init(LoadEvent.InitEvent event){
        ItemStack customblade = new ItemStack(NrBlades.NR_BLADE,1,0);
        NBTTagCompound tag = new NBTTagCompound();
        customblade.setTagCompound(tag);

        ItemNrSlashBlade.CurrentItemName.set(tag, name);
        ItemNrSlashBlade.CustomMaxDamage.set(tag, 91);
        ItemNrSlashBlade.IsDefaultBewitched.set(tag, true);
        ItemNrSlashBlade.isNrBlade.set(tag, true);
        ItemSlashBlade.TextureName.set(tag, "named/negorerouse/nyx");
        ItemSlashBlade.ModelName.set(tag, "named/negorerouse/nyx");
        ItemSlashBlade.SpecialAttackType.set(tag, 109);
        ItemSlashBlade.StandbyRenderType.set(tag, 1);
        ItemSlashBlade.ProudSoul.set(tag, 10000);
        ItemSlashBlade.BaseAttackModifier.set(tag, 25.0F);
        ItemSlashBlade.SummonedSwordColor.set(tag, 0xDD00DD);
        SpecialEffects.addEffect(customblade, NrSEs.ORACLE);
        SpecialEffects.addEffect(customblade, NrSEs.PHANTOM);
        customblade.addEnchantment(Enchantments.UNBREAKING, 7);
        customblade.addEnchantment(Enchantments.POWER, 9);
        customblade.addEnchantment(Enchantments.SMITE, 10);
        customblade.addEnchantment(Enchantments.KNOCKBACK, 5);

        BladeUtils.registerCustomItemStack(name, customblade);
        BladeUtils.NrNamedBlades.add(name);

    }
    @SubscribeEvent
    public void postinit(LoadEvent.PostInitEvent event){
        SlashBladeHooks.EventBus.register(this);
    }

    @SubscribeEvent
    public void onBladeStandAttack(SlashBladeEvent.BladeStandAttack event){
        if(!event.entityBladeStand.hasBlade()) return;
        if(EntityBladeStand.getType(event.entityBladeStand) != EntityBladeStand.StandType.Single) return;
        if(!(event.damageSource.getTrueSource() instanceof EntityWither)) return;
        if(!event.damageSource.isExplosion()) return;
        if(!event.damageSource.getDamageType().equals("explosion.player")) return;

        ItemStack targetBlade = BladeUtils.findItemStack(NegoreRouse.MODID,materialNameA,1);

        if(!event.blade.getUnlocalizedName().equals(targetBlade.getUnlocalizedName())) return;

        ItemStack resultBlade = WorldBladeStandCrafting.crafting(event.blade,name);

        event.entityBladeStand.setBlade(resultBlade);
    }

}
