package moflop.mods.negorerouse;

import com.google.common.collect.Lists;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.TagPropertyAccessor;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Moflop
 * @updateDate 2020/02/18
 */
public class NrRecipeBlade extends ShapedOreRecipe {
    ItemStack inheritBlade = ItemStack.EMPTY;
    List<ItemStack> materialBlade = Lists.newArrayList();
    List<ItemStack> determineBlade = Lists.newArrayList();

    public NrRecipeBlade(ResourceLocation group, ItemStack result,ItemStack inheritBlade, Object... recipe) {
        this(group, result, inheritBlade, Lists.newArrayList(), recipe);
    }

    /**
     *
     * @param result 最后得到的刀]
     * @param inheritBlade 被继承的拔刀
     * @param materialBlade 所需拔刀
     * @param recipe 合成
     */
    public NrRecipeBlade(ResourceLocation group, ItemStack result,ItemStack inheritBlade, List<ItemStack> materialBlade, Object... recipe) {
        super(group, result, recipe);
        this.inheritBlade = inheritBlade;
        this.materialBlade = materialBlade;
        if (!inheritBlade.isEmpty()){
            this.materialBlade.add(inheritBlade);
        }
    }


    /**
     * @param access 比较的NBT类型
     * @param material 被比较物
     * @param compare 比较物
     * @return 用于比较NBT数值大小,如果compare大于等于material返回true
     */
    public boolean compare(TagPropertyAccessor.TagPropertyInteger access, NBTTagCompound material, NBTTagCompound compare){
        return access.get(material) <= access.get(compare);
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {

        if (materialBlade.size() > 9){
            materialBlade.clear();
            materialBlade.add(inheritBlade);
        }
        boolean result = super.matches(inv, world);

        if (result && materialBlade.size() != 0){
            for (ItemStack materialBlade : materialBlade){
                if (!materialBlade.isEmpty()){
                    for(int idx = 0; idx < inv.getSizeInventory(); idx++){
                        ItemStack curIs = inv.getStackInSlot(idx);
                        if (!curIs.isEmpty() && curIs.getItem() instanceof ItemSlashBlade && curIs.hasTagCompound() && !determineBlade.contains(curIs)){
                            NBTTagCompound material = ItemSlashBlade.getItemTagCompound(materialBlade);
                            NBTTagCompound blade = ItemSlashBlade.getItemTagCompound(curIs);

                            Map<Enchantment,Integer> oldItemEnchants = EnchantmentHelper.getEnchantments(materialBlade);
                            for(Map.Entry<Enchantment,Integer> enchant: oldItemEnchants.entrySet())
                            {
                                int level = EnchantmentHelper.getEnchantmentLevel(enchant.getKey(),curIs);
                                if(level < enchant.getValue()){
                                    return false;
                                }
                            }

                            boolean determine = !curIs.getUnlocalizedName().equals(materialBlade.getUnlocalizedName()) ||
                                    !ItemSlashBladeNamed.CurrentItemName.get(blade).equals(ItemSlashBladeNamed.CurrentItemName.get(material)) ||
                                    !compare(ItemSlashBlade.ProudSoul, material, blade) || !compare(ItemSlashBlade.ProudSoul, material, blade) ||
                                    !compare(ItemSlashBlade.RepairCount, material, blade);
                            if(determine){
                                return false;
                            }
                            determineBlade.add(curIs);
                            break;

                        }
                    }
                }
            }
        }

        return result;
    }

    public ItemStack getCraftingResult(InventoryCrafting var1) {
        ItemStack result = super.getCraftingResult(var1);

        for(int idx = 0; idx < var1.getSizeInventory(); ++idx) {
            ItemStack curIs = var1.getStackInSlot(idx);
            if (!curIs.isEmpty() && curIs.getItem() instanceof ItemSlashBlade && curIs.hasTagCompound()) {
                NBTTagCompound oldTag = curIs.getTagCompound();
                oldTag = oldTag.copy();
                NBTTagCompound reqTag = ItemSlashBlade.getItemTagCompound(this.inheritBlade);
                if (ItemSlashBladeNamed.CurrentItemName.get(oldTag).equals(ItemSlashBladeNamed.CurrentItemName.get(reqTag))) {
                    NBTTagCompound newTag = ItemSlashBlade.getItemTagCompound(result);
                    if (ItemSlashBladeNamed.CurrentItemName.exists(newTag)) {
                        String key = ItemSlashBladeNamed.CurrentItemName.get(newTag);
                        ItemStack tmp = SlashBlade.getCustomBlade(key);
                        if (!tmp.isEmpty()) {
                            result = tmp;
                        }
                    }

                    newTag = ItemSlashBlade.getItemTagCompound(result);
                    ItemSlashBlade.KillCount.set(newTag, ItemSlashBlade.KillCount.get(oldTag));
                    ItemSlashBlade.ProudSoul.set(newTag, ItemSlashBlade.ProudSoul.get(oldTag));
                    ItemSlashBlade.RepairCount.set(newTag, ItemSlashBlade.RepairCount.get(oldTag));
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

                    Map<Enchantment, Integer> newItemEnchants = EnchantmentHelper.getEnchantments(result);
                    Map<Enchantment, Integer> oldItemEnchants = EnchantmentHelper.getEnchantments(curIs);
                    Iterator var9 = oldItemEnchants.keySet().iterator();

                    while(var9.hasNext()) {
                        Enchantment enchantIndex = (Enchantment)var9.next();
                        Enchantment enchantment = enchantIndex;
                        int destLevel = newItemEnchants.containsKey(enchantIndex) ? (Integer)newItemEnchants.get(enchantIndex) : 0;
                        int srcLevel = (Integer)oldItemEnchants.get(enchantIndex);
                        srcLevel = Math.max(srcLevel, destLevel);
                        boolean canApplyFlag = enchantIndex.canApply(result);
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

                    EnchantmentHelper.setEnchantments(newItemEnchants, result);
                }
            }
        }

        return result;
    }
}
