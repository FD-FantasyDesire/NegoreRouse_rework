package moflop.mods.negorerouse.specialeffects.event;

import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade.ComboSequence;
import mods.flammpfeil.slashblade.specialeffect.SpecialEffects;
import mods.flammpfeil.slashblade.util.SlashBladeEvent;
import mods.flammpfeil.slashblade.util.SlashBladeHooks;

import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.init.NrSEs;
import moflop.mods.negorerouse.named.item.ItemNrSlashBlade;
import moflop.mods.negorerouse.utils.BladeUtils;
import moflop.mods.negorerouse.utils.ParticleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * @author Cat,TennouboshiUzume
 * @updateDate 2024/08/09
 */
public class NrSEEvent {

    public NrSEEvent(){
        SlashBladeHooks.EventBus.register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

//    永恒之枪即死
    @SubscribeEvent
    public void InstKillEffectEvent(SlashBladeEvent.ImpactEffectEvent event){

        if(!useBlade(event.sequence)) return;
        if(!SpecialEffects.isPlayer(event.user)) return;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.chronos_sy",1).getUnlocalizedName())) return;

        EntityPlayer player = (EntityPlayer) event.user;
        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.INSTKILL)){
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }
        DamageSource ds = new EntityDamageSource("directMagic",player).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode();
        event.target.attackEntityFrom(ds, Integer.MAX_VALUE);
        blade.setItemDamage(blade.getItemDamage()+1);
//        ItemSlashBlade.damageItem(blade,9999999,player);
//        player.onEnchantmentCritical(event.target);
//        event.target.setDead();
    }

    /** se:清曜 */
    @SubscribeEvent
    public void ClearEffectEvent(SlashBladeEvent.ImpactEffectEvent event){
//        if(!useBlade(event.sequence)) return;
        if(!SpecialEffects.isPlayer(event.user)) return;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.artemis_s",1).getUnlocalizedName())) return;
        EntityPlayer player = (EntityPlayer) event.user;
        EntityLivingBase target = event.target;
        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.CLEAR)){
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }
        World world = player.world;
        Random random = player.getRNG();
        if (!world.isDaytime()||player.dimension != DimensionType.OVERWORLD.getId()){
            target.world.newExplosion(player, target.posX, target.posY+target.height/2, target.posZ,1.5f,false,false);
            if (target instanceof EntityLivingBase) {
                ((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20 * 5, 5));
            }
            for (int i=0;i<40;i++){
                ((WorldServer)target.world).spawnParticle(EnumParticleTypes.VILLAGER_HAPPY,
                        true,
                        target.posX,
                        target.posY + target.height/2 + i*0.5f + random.nextGaussian()*0.1,
                        target.posZ,
                        1,  0D, 0D, 0D,0D);
            }
            ((WorldServer)target.world).spawnParticle(EnumParticleTypes.TOTEM,
                    true,
                    target.posX,
                    target.posY + target.height/2,
                    target.posZ,
                    20,  0D, 0D, 0D,0.5);
            blade.setItemDamage(blade.getItemDamage() - 50);
        }else {
            ((WorldServer)target.world).spawnParticle(EnumParticleTypes.BARRIER,
                    true,
                    target.posX,
                    target.posY + target.height/2,
                    target.posZ,
                    1, 0, 0, 0, 0.1);
            blade.setItemDamage(blade.getItemDamage() + 10);
        }
    }
    /** se:禁锢 */
    @SubscribeEvent
    public void DeadlockEffectEvent(SlashBladeEvent.ImpactEffectEvent event){
//        if(!useBlade(event.sequence)) return;
        if(!SpecialEffects.isPlayer(event.user)) return;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.tartarus",1).getUnlocalizedName())) return;
        EntityPlayer player = (EntityPlayer) event.user;
        EntityLivingBase target = event.target;
        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.DEADLOCK)){
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }
        World world = player.world;
        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,20*5,45));
        ((WorldServer)target.world).spawnParticle(EnumParticleTypes.FLAME,
                false,
                target.posX,
                target.posY + target.height/2,
                target.posZ,
                40, 0, 0, 0, 0.1);
    }

    /** se:烔侧 */
    @SubscribeEvent
    public void FateEffectEvent(SlashBladeEvent.ImpactEffectEvent event) {
        if (!SpecialEffects.isPlayer(event.user)) return;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.ananke",1).getUnlocalizedName())) return;
        EntityPlayer player = (EntityPlayer) event.user;
        EntityLivingBase target = event.target;
        World world = player.world;

        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.FATE)) {
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }

        if (event.target.getRNG().nextInt(5) == 0) {
            world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY + target.height/2,target.posZ,true));
            target.attackEntityFrom(DamageSource.LIGHTNING_BOLT,5);
            ((WorldServer)target.world).spawnParticle(EnumParticleTypes.END_ROD,
                    false,
                    target.posX,
                    target.posY + target.height/2,
                    target.posZ,
                    60, 0, 0, 0, 0.2);
        }
    }

    /** se:幻影 */
    @SubscribeEvent
    public void PhantomEffectEvent(SlashBladeEvent.OnUpdateEvent event) {
        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.nyx",1).getUnlocalizedName())) return;
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;
        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.PHANTOM)) {
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }
        if (player.getActivePotionEffect(MobEffects.NIGHT_VISION) == null){
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,20 * 300,0));
        }
    }

    /** se:永恒朦胧 */
    @SubscribeEvent
    public void EternityEffectEvent(SlashBladeEvent.ImpactEffectEvent event) {
//        if (!useBlade(event.sequence)) return;
        if (!SpecialEffects.isPlayer(event.user)) return;
        ItemStack blade = event.blade;
        if (!(blade.getItem() instanceof ItemNrSlashBlade)) return;
        if (!event.blade.getUnlocalizedName().equals(BladeUtils.findItemStack(NegoreRouse.MODID,"moflop.slashblade.chronos_ae",1).getUnlocalizedName())) return;
        EntityPlayer player = (EntityPlayer) event.user;
        EntityLivingBase target = event.target;
        World world = player.world;
        switch (SpecialEffects.isEffective(player, event.blade, NrSEs.ETERNITY)) {
            case None:
                return;
            case Effective:
                break;
            case NonEffective:
                return;
        }
        world.addWeatherEffect(new EntityLightningBolt(world,target.posX,target.posY,target.posZ,true));
        target.setHealth(target.getHealth()/2 + 1);
        ((WorldServer)target.world).spawnParticle(EnumParticleTypes.EXPLOSION_HUGE,
                false,
                target.posX,
                target.posY + target.height/2,
                target.posZ,
                1, 0, 0, 0, 0.2);
        if(target.getHealth()<5){
            target.setHealth(0);
            target.attackEntityFrom(DamageSource.OUT_OF_WORLD,10);
        }
    }

    /** se:神谕 */
    @SubscribeEvent
    public void oracleUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.ORACLE)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                break;
            /** 达到所需等级 */
            case Effective:
                break;
        }
        player.addPotionEffect(new PotionEffect(MobEffects.LUCK,20 * 5,0));
        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,20 * 1,0));

    }

    /** se:爆裂性 */
    @SubscribeEvent
    public void burstDriveUpdate(SlashBladeEvent.OnUpdateEvent event) {
        if (SpecialEffects.isPlayer(event.entity)) {
            EntityPlayer player = (EntityPlayer)event.entity;
            ItemStack blade = event.blade;
            NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
            if (!ItemSlashBlade.IsBroken.get(tag)) {
                switch(SpecialEffects.isEffective(player, event.blade, NrSEs.BURST_DRIVE)) {
                    case None:
                        return;
                    case NonEffective:
                        return;
                    case Effective:
                        double d0 = player.getRNG().nextGaussian() * 0.02D;
                        double d1 = player.getRNG().nextGaussian() * 0.02D;
                        double d2 = player.getRNG().nextGaussian() * 0.02D;
                        double d3 = 10.0D;
                        event.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, player.posX + (double)(player.getRNG().nextFloat() * player.width * 2.0F) - (double)player.width - d0 * d3, player.posY, player.posZ + (double)(player.getRNG().nextFloat() * player.width * 2.0F) - (double)player.width - d2 * d3, d0, d1, d2);
                    default:
                        ComboSequence seq = ItemSlashBlade.getComboSequence(tag);
                        if (this.useBlade(seq)) {
                            PotionEffect haste = player.getActivePotionEffect(MobEffects.SPEED);
                            int check = haste != null ? (haste.getAmplifier() != 1 ? 3 : 4) : 2;
                            if (player.swingProgressInt == check) {
                                this.doAddAttack(event.blade, player, seq);
                            }
                        }
                }
            }
        }
    }
    public void doAddAttack(ItemStack stack, EntityPlayer player, ComboSequence setCombo) {
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);
        World world = player.world;
        if (!ItemSlashBlade.ProudSoul.tryAdd(tag, -2, false)) {
            stack.setItemDamage(stack.getMaxDamage() + 1);
        } else {
            if (!world.isRemote) {
                float baseModif = ((ItemSlashBlade)stack.getItem()).getBaseAttackModifiers(tag);
                int level = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                float magicDamage = baseModif + (float)level;
                int rank = StylishRankManager.getStylishRank(player);
                if (5 <= rank) {
                    magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.5F + (float)level / 5.0F);
                }

                EntityDrive entityDrive = new EntityDrive(world, player, magicDamage, false, 90.0F - setCombo.swingDirection);
                if (entityDrive != null) {
                    entityDrive.setInitialSpeed(1.5F);
                    entityDrive.setLifeTime(10);
                    world.spawnEntity(entityDrive);
                }
            }

        }
    }

    /** se:负向神力 */
    @SubscribeEvent
    public void reversePowerUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.REVERSE_POWER)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                return;
            /** 达到所需等级 */
            case Effective:
                break;
        }

        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 3, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 10, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20 * 7, 0));
        if (player.experienceLevel<= 100){
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * 2, 0));
        }
        player.addPotionEffect(new PotionEffect(MobEffects.UNLUCK,20 * 5,0));
        if (player.world.rand.nextFloat() <= 0.2F){
            player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH,20 * 1,0));
        }
    }

    /** se:绝对神力 */
    @SubscribeEvent
    public void absolutePowerUpdate(SlashBladeEvent.OnUpdateEvent event) {

        if(!SpecialEffects.isPlayer(event.entity)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(event.blade);
        if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

        switch (SpecialEffects.isEffective(player,event.blade, NrSEs.ABSOLUTE_POWER)){
            /** 任何时候可触发 */
            case None:
                return;
            /** 未达到所需等级 */
            case NonEffective:
                return;
            /** 达到所需等级 */
            case Effective:
                break;
        }

        player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20 * 5, 0));
        player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20 * 10, 0));

    }

    /** se:回溯 */
    @SubscribeEvent
    public void back(LivingDeathEvent event) {
        if (event.getSource().getImmediateSource() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getSource().getImmediateSource();
            if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSlashBlade){
                ItemStack blade = player.getHeldItem(EnumHand.MAIN_HAND);
                NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(blade);
                if(!useBlade(ItemSlashBlade.getComboSequence(tag))) return;

                switch (SpecialEffects.isEffective(player,blade, NrSEs.BACK)){
                    /** 任何时候可触发 */
                    case None:
                        return;
                    /** 未达到所需等级 */
                    case NonEffective:
                        return;
                    /** 达到所需等级 */
                    case Effective:
                        break;
                }
                blade.setItemDamage(blade.getItemDamage() + 1);
            }
        }


    }

    private boolean useBlade(ComboSequence sequence){
        if(sequence.useScabbard) return false;
        if(sequence == ItemSlashBlade.ComboSequence.None) return false;
        if(sequence == ItemSlashBlade.ComboSequence.Noutou) return false;
        return true;
    }



}
