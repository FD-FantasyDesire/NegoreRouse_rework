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

public class BurningFireSA extends SpecialAttackBase {
    @Override
    public String toString() {
        return "BurningFireSA";
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

        ParticleUtils.spawnParticle(EnumParticleTypes.FLAME,player,30,2);
        player.playSound(SoundEvents.ENTITY_BLAZE_BURN,1.0f,1.5f);

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

            int count = Math.max(player.experienceLevel/5,1);
            if (count>5){
                count = 5;
            }
            Vec3d pos = new Vec3d(player.getPosition());
            for (int i = 0; i < count*5; i++) {
                EntityDriveEx entity = new EntityDriveEx(world, player, magicDamage);
                entity.setLifeTime(40);
                entity.setColor(0xFF0000);
                entity.setMultiHit(false);

                float yaw;
                float pitch = player.rotationPitch;
                float distance = 1.0f; // 距离玩家前方1.5单位处生成

                switch (i%5) {
                    case 0:
                        yaw = player.rotationYaw - 20.0f;
                        break;
                    case 1:
                        yaw = player.rotationYaw - 10.0f;
                        break;
                    case 2:
                        yaw = player.rotationYaw;
                        break;
                    case 3:
                        yaw = player.rotationYaw + 10.0f;
                        break;
                    case 4:
                        yaw = player.rotationYaw + 20.0f;
                        break;
                    default:
                        yaw = player.rotationYaw;
                        break;
                }
                int f = i/5;

                Vec3d lookVec = player.getLookVec();
                lookVec.rotateYaw(yaw);
                Vec3d spawnpos = player.getLookVec().addVector(player.posX,player.posY+player.eyeHeight,player.posZ);
                entity.setInitialPosition(spawnpos.x, spawnpos.y, spawnpos.z, yaw, pitch,0,0.3f+0.05f*f);
                entity.setInterval(5+f*5);
                entity.setLifeTime(100);
                entity.setIsOverWall(true);
                entity.setParticle(EnumParticleTypes.LAVA);
                entity.setPosition(
                        spawnpos.x,
                        spawnpos.y,
                        spawnpos.z);
                if (entity != null) {
                    world.spawnEntity(entity);
                }
            }
        }

        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriage);
    }
}
