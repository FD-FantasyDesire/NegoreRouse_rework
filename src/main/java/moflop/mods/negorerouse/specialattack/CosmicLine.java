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

public class CosmicLine extends SpecialAttackBase {
    @Override
    public String toString() {
        return "CosmicLine";
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

        ParticleUtils.spawnParticle(EnumParticleTypes.PORTAL,player,150,3);
        player.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL,1.0f,0.5f);

        // 服务器端执行逻辑
        if(!world.isRemote){

            final int cost = -20;
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

            int count = Math.max(1+player.experienceLevel/5*2,1);
            if (count>11){
                count = 11;
            }
            Vec3d pos = new Vec3d(player.getPosition());

            int centerIndex = count / 2;

            for (int i=0;i<count;i++){
                float rotateYaw = (i-centerIndex)*15f;
                pos.rotateYaw(rotateYaw);
                EntityDriveEx entityDrive = new EntityDriveEx(world,player,magicDamage);
                entityDrive.setInitialPosition(pos.x,pos.y,pos.z,player.rotationYaw+rotateYaw,player.rotationPitch,0,0.3f);
                entityDrive.setPosition(
                        player.getLookVec().x+player.posX,
                        player.getLookVec().y+player.posY+player.eyeHeight,
                        player.getLookVec().z+player.posZ
                );
                entityDrive.setColor(0xCF00CF);
                entityDrive.setLifeTime(20*5);
                entityDrive.setParticle(EnumParticleTypes.END_ROD);
                world.spawnEntity(entityDrive);
            }
            for (int i=0;i<count;i++){
                float rotateYaw = (i-centerIndex)*15f;
                pos.rotateYaw(rotateYaw);
                EntityDriveEx entityDrive = new EntityDriveEx(world,player,magicDamage);
                entityDrive.setInitialPosition(pos.x,pos.y,pos.z,player.rotationYaw+rotateYaw,player.rotationPitch,90,0.3f);
                entityDrive.setPosition(
                        player.getLookVec().x+player.posX,
                        player.getLookVec().y+player.posY+player.eyeHeight,
                        player.getLookVec().z+player.posZ
                );
                entityDrive.setColor(0xCF00CF);
                entityDrive.setLifeTime(20*5);
                entityDrive.setParticle(EnumParticleTypes.SPELL_WITCH);
                world.spawnEntity(entityDrive);
            }
        }

        // 设置武器的连击序列
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.SlashEdge);
    }
}
