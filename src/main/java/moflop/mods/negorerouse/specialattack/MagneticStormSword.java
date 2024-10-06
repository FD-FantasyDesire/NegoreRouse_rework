package moflop.mods.negorerouse.specialattack;

import mods.flammpfeil.slashblade.entity.selector.EntitySelectorAttackable;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.ability.StylishRankManager;
import mods.flammpfeil.slashblade.entity.EntityWitherSword;
import mods.flammpfeil.slashblade.util.ReflectionAccessHelper;
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
public class MagneticStormSword extends SpecialAttackBase {
    @Override
    public String toString() {
        return "MagneticStormSword";
    }

    @Override
    public void doSpacialAttack(ItemStack stack, EntityPlayer player) {
        World world = player.world;

        NBTTagCompound tag = ItemSlashBlade.getItemTagCompound(stack);

        player.playSound(SoundEvents.BLOCK_ANVIL_USE,16.0f,0.5f);

        if(!world.isRemote){

            ItemSlashBlade blade = (ItemSlashBlade)stack.getItem();

            final int cost = -20;
            if(!ItemSlashBlade.ProudSoul.tryAdd(tag,cost,false)){
                ItemSlashBlade.damageItem(stack, 10, player);
            }

            int level = Math.max(1, EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack));
            float baseModif = blade.getBaseAttackModifiers(tag);
            float magicDamage = 1.0f + (baseModif/2.0f);
            int rank = StylishRankManager.getStylishRank(player);
            if(5 <= rank)
                magicDamage += ItemSlashBlade.AttackAmplifier.get(tag) * (0.25f + (level / 5.0f));

            int count = Math.max(player.experienceLevel,10);
            if (count>50){
                count = 50;
            }
            for(int i = 0; i < count;i++){

                if(!world.isRemote){
                    boolean isBurst = (i % 5 == 0);

                    EntityMagneticStormSword entityDrive = new EntityMagneticStormSword(world, player,magicDamage,90.0f);
                    if (entityDrive != null) {
                        entityDrive.setInterval(12+2*(i%5));
                        entityDrive.setLifeTime(100);
                        int color = isBurst ? 0x00FFFF : 0xFFFFFF;
                        entityDrive.setColor(color);
                        entityDrive.setBurst(isBurst);
                        world.spawnEntity(entityDrive);
                    }
                }
            }
        }
        ItemSlashBlade.setComboSequence(tag, ItemSlashBlade.ComboSequence.Kiriorosi);
    }
}