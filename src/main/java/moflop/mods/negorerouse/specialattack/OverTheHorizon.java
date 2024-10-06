package moflop.mods.negorerouse.specialattack;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import moflop.mods.negorerouse.entity.EntityDriveEx;
import moflop.mods.negorerouse.utils.ParticleUtils;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import net.minecraft.world.World;

public class OverTheHorizon extends SpecialAttackBase {
    @Override
    public String toString() {
        return "OverTheHorizon";
    }

    /**
     * 执行特殊攻击的方法
     *
     * @param stack  当前使用的武器
     * @param player 进行攻击的玩家
     */
    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        // 获取武器的NBT数据
        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        ParticleUtils.spawnParticle(EnumParticleTypes.SMOKE_LARGE,player,30,2);
        player.playSound(SoundEvents.ENTITY_WITHER_DEATH,16.0f,0.5f);

        // 服务器端执行逻辑
        if(!world.isRemote){

            final int cost = -200;
            // 消耗 Proud Souls 或者损坏物品
            if(!ItemSlashBlade.ProudSoul.tryAdd(tag,cost,false)){
                ItemSlashBlade.damageItem(stack, 10, player);
            }

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            int level = Math.max(1, EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack));
            float baseModif = blade.getBaseAttackModifiers(tag);
            float magicDamage = 1.0f + (baseModif/2.0f);
            int rank = StylishRankManager.getStylishRank(player);
            if(5 <= rank)
                magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.25f + (level / 5.0f));

            int count = Math.max(player.experienceLevel/5,2);
            if (count>10){
                count = 10;
            }
            EntityDriveEx entityDrive = new EntityDriveEx(world,player,magicDamage);
            entityDrive.setLifeTime(100);
            entityDrive.setScale(count);
            entityDrive.setInitialPosition(player.posX+player.getLookVec().x,
                    player.posY+player.getLookVec().y,
                    player.posZ+player.getLookVec().z,
                    player.rotationYaw,
                    player.rotationPitch,
                    90f,0.3f);
            entityDrive.setColor(0xFFFFFF);
            entityDrive.setInterval(5);
            entityDrive.setIsOverWall(true);
            entityDrive.setMultiHit(true);
            world.spawnEntity(entityDrive);
        }

        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriage);
    }
}
