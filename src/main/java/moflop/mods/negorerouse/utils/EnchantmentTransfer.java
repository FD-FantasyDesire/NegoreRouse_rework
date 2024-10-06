package moflop.mods.negorerouse.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EnchantmentTransfer {

    /**
     * 将 sourceItem 和 targetItem 上的附魔合并，并将最高等级的附魔应用到 targetItem 上
     * @param sourceItem 源物品，附魔将从这个物品上获取并与目标物品的附魔合并
     * @param targetItem 目标物品，附魔将转移到这个物品上
     */
    public static void mergeEnchantments(ItemStack sourceItem, ItemStack targetItem) {
        Map<Enchantment, Integer> newItemEnchants = EnchantmentHelper.getEnchantments(targetItem);
        Map<Enchantment, Integer> oldItemEnchants = EnchantmentHelper.getEnchantments(sourceItem);
        Iterator var9 = oldItemEnchants.keySet().iterator();

        while(var9.hasNext()) {
            Enchantment enchantIndex = (Enchantment)var9.next();
            Enchantment enchantment = enchantIndex;
            int destLevel = newItemEnchants.containsKey(enchantIndex) ? (Integer)newItemEnchants.get(enchantIndex) : 0;
            int srcLevel = (Integer)oldItemEnchants.get(enchantIndex);
            srcLevel = Math.max(srcLevel, destLevel);
            boolean canApplyFlag = enchantIndex.canApply(targetItem);
            if (canApplyFlag) {
                Iterator var15 = newItemEnchants.keySet().iterator();

                while(var15.hasNext()) {
                    Enchantment curEnchantIndex = (Enchantment)var15.next();
                    if (curEnchantIndex != enchantIndex && !enchantment.isCompatibleWith(curEnchantIndex)) {
                        canApplyFlag = false;
                        break;
                    }
                }

                if (canApplyFlag) {
                    newItemEnchants.put(enchantIndex, srcLevel);
                }
            }
        }

        EnchantmentHelper.setEnchantments(newItemEnchants, targetItem);
    }
}
