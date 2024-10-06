package moflop.mods.negorerouse.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.util.ResourceLocationRaw;
import moflop.mods.negorerouse.NegoreRouse;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * @author Cat
 * @updateDate 2020/02/14
 */
public class BladeUtils {
    public static Map<ResourceLocationRaw, ItemStack> nrBladeRegistry = Maps.newHashMap();
    public static List<String> NrNamedBlades = Lists.newArrayList();

    public static void registerCustomItemStack(String name, ItemStack stack){
        nrBladeRegistry.put(new ResourceLocationRaw(NegoreRouse.MODID, name),stack);
    }

    static public ItemStack findItemStack(String modid, String name, int count){
        ResourceLocationRaw key = new ResourceLocationRaw(modid, name);
        ItemStack stack = ItemStack.EMPTY;

        if(nrBladeRegistry.containsKey(key)) {
            stack = nrBladeRegistry.get(key).copy();

        }else if(SlashBlade.BladeRegistry.containsKey(key)){
            stack = SlashBlade.BladeRegistry.get(key).copy();
        }else{
            Item item = Item.REGISTRY.getObject(key);
            if (item != null){
                stack = new ItemStack(item);
            }
        }

        if(!stack.isEmpty()) {
            stack.setCount(count);
        }

        return stack;
    }

    public static ItemStack getCustomBlade(String modid,String name){
        return BladeUtils.findItemStack(modid, name, 1);
    }
    public static ItemStack getCustomBlade(String key){
        String modid;
        String name;
        {
            String str[] = key.split(":",2);
            if(str.length == 2){
                modid = str[0];
                name = str[1];
            }else{
                modid = NegoreRouse.MODID;
                name = key;
            }
        }

        return getCustomBlade(modid,name);
    }

    public static ItemStack getMcItemStack(String name){
        ResourceLocationRaw key = new ResourceLocationRaw("minecraft", name);
        Item item = Item.REGISTRY.getObject(key);
        ItemStack stack = ItemStack.EMPTY;
        if (item != null){
            stack = new ItemStack(item);
        }
        return stack;
    }

}
