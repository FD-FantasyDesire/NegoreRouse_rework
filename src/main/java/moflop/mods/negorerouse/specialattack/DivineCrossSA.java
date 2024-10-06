package moflop.mods.negorerouse.specialattack;

import ibxm.Player;
import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import moflop.mods.negorerouse.entity.EntityDriveEx;
import moflop.mods.negorerouse.utils.ParticleUtils;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Furia on 14/05/27.
 */
public class DivineCrossSA extends SpecialAttackBase {
    @Override
    public String toString() {
        return "DivineCrossSA";
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

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        ParticleUtils.spawnParticle(EnumParticleTypes.END_ROD,player,50,1);
        player.playSound(SoundEvents.BLOCK_PORTAL_TRAVEL,1.0f,1.5f);

        if(!world.isRemote){

            final int cost = -200;
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

            for(int i = 0; i <=1;i++){
                boolean rolls = (i%2 == 0);
                EntityDriveEx entityDrive = new EntityDriveEx(world, player, magicDamage);
                entityDrive.setInitialPosition(
                        player.posX,
                        player.posY+player.eyeHeight,
                        player.posZ,
                        player.rotationYaw,
                        player.rotationPitch,
                        rolls ? 0f: 90f,
                        0.5f
                );
                entityDrive.setColor(rolls ? 0xFF00FF : 0xFFFFFF);
                entityDrive.setScale(10.0f);
                entityDrive.setLifeTime(200);
                entityDrive.setMultiHit(true);
                entityDrive.setIsOverWall(true);
                entityDrive.setParticle(EnumParticleTypes.EXPLOSION_LARGE);
                entityDrive.setRoll(rolls ? 0.0f : 90.0f);
                if (entityDrive != null) {
                    world.spawnEntity(entityDrive);
                }
            }
            world.setWorldTime(world.getWorldTime()+12000);;
        }
//        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriage);
//        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.AerialRave);
        ItemSlashBlade.setComboSequence(tag,ItemSlashBlade.ComboSequence.SlashEdge);
    }
}
