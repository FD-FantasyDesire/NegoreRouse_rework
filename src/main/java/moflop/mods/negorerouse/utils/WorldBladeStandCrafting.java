package moflop.mods.negorerouse.utils;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class WorldBladeStandCrafting {
    public static ItemStack crafting(ItemStack targetBlade,String name){
        ItemStack resultBlade = BladeUtils.getCustomBlade(name);
//      获取参与合成的刀的参数
        NBTTagCompound oldTag = ItemSlashBlade.getItemTagCompound(targetBlade);
        NBTTagCompound newTag = ItemSlashBlade.getItemTagCompound(resultBlade);

        int killCount = ItemSlashBlade.KillCount.get(oldTag);
        int refine = ItemSlashBlade.RepairCount.get(oldTag);
        int proudSoul = ItemSlashBlade.ProudSoul.get(oldTag);


        if (oldTag.hasUniqueId("Owner")) {
            newTag.setUniqueId("Owner", oldTag.getUniqueId("Owner"));
        }

        if (oldTag.hasKey("adjustX")) {
            newTag.setFloat("adjustX", oldTag.getFloat("adjustX"));
        }

        if (oldTag.hasKey("adjustY")) {
            newTag.setFloat("adjustY", oldTag.getFloat("adjustY"));
        }

        if (oldTag.hasKey("adjustZ")) {
            newTag.setFloat("adjustZ", oldTag.getFloat("adjustZ"));
        }

        ItemSlashBlade.KillCount.set(newTag,killCount);
        ItemSlashBlade.ProudSoul.set(newTag,proudSoul);
        ItemSlashBlade.RepairCount.set(newTag,refine);
//      合并附魔
        EnchantmentTransfer.mergeEnchantments(targetBlade,resultBlade);
        return resultBlade;
    }
}
