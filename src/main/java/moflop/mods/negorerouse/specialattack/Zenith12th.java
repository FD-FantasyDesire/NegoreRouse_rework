package moflop.mods.negorerouse.specialattack;

import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
import moflop.mods.negorerouse.entity.EntityLightningPhantomSword;
import moflop.mods.negorerouse.entity.EntityMagneticStormSword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;

import java.util.List;

/**
 * Created by Furia on 15/06/21.
 */
public class Zenith12th extends SpecialAttackBase {
    @Override
    public String toString() {
        return "Zenith12th";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        player.playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER,16.0f,2.0f);

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

            for(int i = 0; i < 12;i++){
                boolean isBurst = true;
                double radius = 1.0f;
                Vec3d playerPos = player.getPositionVector();
                float yaw = player.rotationYaw;
                Vec3d lookVec = new Vec3d(
                            -Math.sin(Math.toRadians(yaw)),
                            0,
                            Math.cos(Math.toRadians(yaw))
                ).normalize();
                Vec3d upVec = new Vec3d(0, 1, 0);
                Vec3d rightVec = lookVec.crossProduct(upVec).normalize();
                Vec3d forwardVec = rightVec.crossProduct(lookVec).normalize();
                EntityLightningPhantomSword entityDrive = new EntityLightningPhantomSword(world, player,magicDamage,90.0f);
                if (entityDrive != null) {
                    double angle = 2 * Math.PI * i / 12;
                    double xOffset = radius * Math.cos(angle);
                    double yOffset = radius * Math.sin(angle);
                    Vec3d circlePoint = playerPos.add(rightVec.scale(xOffset)).add(forwardVec.scale(yOffset));
                    entityDrive.setPosition(circlePoint.x, circlePoint.y + player.getEyeHeight(), circlePoint.z);
                    entityDrive.setInterval(4+i*2);
                    entityDrive.setLifeTime(100);
                    entityDrive.setRoll(i*-30f+90f);
                    entityDrive.setScale(1.0f);
                    entityDrive.setColor(0xFFCF00);
                    entityDrive.setBurst(isBurst);
                    world.spawnEntity(entityDrive);
                }
            }
        }
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi);
    }
}