
package moflop.mods.negorerouse.specialattack;

import mods.flammpfeil.slashblade.entity.EntityDrive;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.specialattack.SpecialAttackBase;
import moflop.mods.negorerouse.entity.EntityDriveEx;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Furia on 14/05/27.
 */
public class ExcessBlade extends SpecialAttackBase {

    static public String AttackType = StylishRankManager.AttackTypes.registerAttackType("OverSlash", 0.5F);

    @Override
    public String toString() {
        return "ExcessBlade";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;
        Random random = new Random();

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        player.playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, 16.0F, 0.5F);

        if(!world.isRemote){

            final int cost = -200;
            if(!ItemSlashBlade.ProudSoul.tryAdd(tag,cost,false)){
                ItemSlashBlade.damageItem(stack, 10, player);
            }

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            {
                AxisAlignedBB bb = player.getEntityBoundingBox();
                bb = bb.grow(20.0f, 20.0f, 20.0f);

                List<Entity> list = world.getEntitiesInAABBexcluding(player, bb, EntitySelectorAttackable.getInstance());

                for(Entity curEntity : list){
                    if(stack.isEmpty()) break;
                    StylishRankManager.setNextAttackType(player, StylishRankManager.AttackTypes.CircleSlash);
                    blade.attackTargetEntity(stack, curEntity, player, true);
                    player.onCriticalHit(curEntity);
                }
            }

            int level = Math.max(1, EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack));
            float baseModif = blade.getBaseAttackModifiers(tag);
            float magicDamage = 1.0f + (baseModif/2.0f);
            int rank = StylishRankManager.getStylishRank(player);
            if(5 <= rank)
                magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.25f + (level / 5.0f));

            int count = Math.max(player.experienceLevel/2,24);
            if (count > 180){
                count = 180;
            }

            for(int i = 0; i < count;i++){
                float yaw = 720f / count * i;
                float pitch = (float) (random.nextGaussian() * 30);
                float roll = (float) (random.nextGaussian() * 60);
                int color = random.nextInt(16777216);
                {
                    EntityDriveEx entityDrive = new EntityDriveEx(world, player, magicDamage);
                    entityDrive.setInitialPosition(player.posX,
                            player.posY + (double) player.getEyeHeight() / 2D,
                            player.posZ,
                            player.rotationYaw + yaw /*+ (entityDrive.getRand().nextFloat() - 0.5f) * 60*/,
                            pitch,
                            roll,
                            0.5f
                    );
                    entityDrive.setInterval(5);
                    entityDrive.setLifeTime(60);
                    entityDrive.setScale(2.0f);
                    entityDrive.setParticle(EnumParticleTypes.ENCHANTMENT_TABLE);
                    entityDrive.setIsOverWall(true);
                    entityDrive.setMultiHit(true);
                    entityDrive.setColor(color);
                    if (entityDrive != null) {
                        world.spawnEntity(entityDrive);
                    }
                }
//                {
//                    EntityDriveEx entityDrive = new EntityDriveEx(world, player, magicDamage);
//                    entityDrive.setInitialPosition(player.posX,
//                            player.posY + (double) player.getEyeHeight() / 2D,
//                            player.posZ,
//                            player.rotationYaw + yaw /*+ (entityDrive.getRand().nextFloat() - 0.5f) * 60*/,
//                            pitch,
//                            roll + 90f,
//                            0.5f
//                    );//(entityDrive.getRand().nextFloat() - 0.5f) * 60);
//                    entityDrive.setInterval(5);
//                    entityDrive.setLifeTime(60);
//                    entityDrive.setScale(2.0f);
//                    entityDrive.setIsOverWall(true);
//                    entityDrive.setMultiHit(true);
//                    entityDrive.setColor(color);
//                    if (entityDrive != null) {
//                        world.spawnEntity(entityDrive);
//                    }
//                }
            }
        }
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Battou);
    }

//不再使用，直接使用000000到FFFFFF的随机数
private final static int[] colorArray={
            0xFF0000,//red
            0xFFA500,//orange
            0xFFFF00,//yellow
            0x00FF00,//green
            0x00FFFF,//aqua
            0x0000FF,//blue
            0x800080//purple
    };
}