package moflop.mods.negorerouse.item;

import mods.flammpfeil.slashblade.ItemSlashBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import moflop.mods.negorerouse.NegoreRouse;
import moflop.mods.negorerouse.creativetab.NrTabs;
import moflop.mods.negorerouse.init.NrItems;
import moflop.mods.negorerouse.utils.BladeUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static moflop.mods.negorerouse.utils.BladeUtils.*;
import static moflop.mods.negorerouse.utils.BladeUtils.getMcItemStack;
import static moflop.mods.negorerouse.utils.ItemUtils.NR_SOUL;

/**
 * @author Moflop,AbbyQAQ
 * @updateDate 2020/02/13
 */
public class ItemSoul extends NrItem {
    String name;

    public ItemSoul(String name){
        super("soul_" + name);
        this.setUnlocalizedName("nrSoul");
        this.name = name;
        this.setMaxStackSize(32);
        this.setCreativeTab(NrTabs.Nr_Item);
        NR_SOUL.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format(name));
        tooltip.add(I18n.format(name + ".SE"));
    }

    public static void craft(){
        ItemStack sphereSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "sphere_bladesoul", 1);
        ItemStack proudSoul = BladeUtils.findItemStack("flammpfeil.slashblade", "proudsoul", 1);
//        ItemStack materialBlade1 = BladeUtils.findItemStack("flammpfeil.slashblade", "flammpfeil.slashblade.named.doutanuki", 1);

        ResourceLocation chronos = new ResourceLocation(NegoreRouse.MODID,"soul_chronos");
        ResourceLocation erebus = new ResourceLocation(NegoreRouse.MODID,"soul_erebus");
        ResourceLocation soul_mix = new ResourceLocation(NegoreRouse.MODID,"soul_mix");
        ResourceLocation fate = new ResourceLocation(NegoreRouse.MODID,"soul_fate");
        ResourceLocation tartarus = new ResourceLocation(NegoreRouse.MODID,"soul_tartarus");
        ResourceLocation chaos = new ResourceLocation(NegoreRouse.MODID,"soul_chaos");

        GameRegistry.addShapedRecipe(chronos,chronos,
                new ItemStack(NrItems.CHRONOS), new Object[]{
                        "QWE",
                        "ASD",
                        "ZXC",
                        'Q', getMcItemStack("emerald_block"),
                        'W', sphereSoul,
                        'E', getMcItemStack("gold_ingot"),
                        'A', getMcItemStack("obsidian"),
                        'S', getMcItemStack("ender_eye"),
                        'D', getMcItemStack("golden_pickaxe"),
                        'Z', getMcItemStack("iron_block"),
                        'X', proudSoul,
                        'C', getMcItemStack("redstone_block")});

        GameRegistry.addShapedRecipe(erebus,erebus,
                new ItemStack(NrItems.EREBUS), new Object[]{
                        " W ",
                        "ASD",
                        " X ",
                        'W', sphereSoul,
                        'A', getMcItemStack("netherrack"),
                        'S', getMcItemStack("nether_brick_fence"),
                        'D', getMcItemStack("obsidian"),
                        'X', proudSoul});

        GameRegistry.addShapedRecipe(soul_mix,soul_mix,
                new ItemStack(NrItems.SOUL_MIX), new Object[]{
                        " XZ",
                        "DSD",
                        "ZD ",
                        'X', sphereSoul,
                        'Z', new ItemStack(NrItems.EREBUS),
                        'D', proudSoul,
                        'S', new ItemStack(NrItems.CHRONOS)});

        GameRegistry.addShapedRecipe(fate,fate,
                new ItemStack(NrItems.FATE), new Object[]{
                        "ABC",
                        "DEF",
                        "GHI",
                        'A', new ItemStack(Items.REDSTONE),
                        'B', new ItemStack(Blocks.OBSIDIAN),
                        'C', new ItemStack(Items.DIAMOND_HOE),
                        'D', new ItemStack(NrItems.CHRONOS),
                        'E', new ItemStack(Items.NETHER_STAR),
                        'F', new ItemStack(NrItems.SOUL_MIX),
                        'G', new ItemStack(Items.BLAZE_POWDER),
                        'H', new ItemStack(Blocks.LAPIS_BLOCK),
                        'I', new ItemStack(Blocks.GLOWSTONE)});

        GameRegistry.addShapedRecipe(tartarus,tartarus,
                new ItemStack(NrItems.TARTARUS), new Object[]{
                        "ABA",
                        "CDE",
                        "ABA",
                        'A',new ItemStack(Items.LAVA_BUCKET),
                        'B',new ItemStack(NrItems.EREBUS),
                        'C',new ItemStack(Blocks.SOUL_SAND),
                        'D',BladeUtils.findItemStack("flammpfeil.slashblade", "flammpfeil.slashblade.named.doutanuki", 1),
                        'E',new ItemStack(Items.NETHER_WART)});

        GameRegistry.addShapedRecipe(chaos,chaos,
                new ItemStack(NrItems.CHAOS), new Object[]{
                        "ABC",
                        "DEF",
                        "G H",
                        'A',new ItemStack(Blocks.TNT),
                        'B', new ItemStack(Blocks.GLASS_PANE),
                        'C', new ItemStack(Items.COMPASS),
                        'D', new ItemStack(Items.BLAZE_POWDER),
                        'E', new ItemStack(NrItems.CHRONOS),
                        'F', new ItemStack(Blocks.IRON_BLOCK),
                        'G', new ItemStack(Blocks.HOPPER),
                        'H', new ItemStack(Items.CAULDRON)});
    }

}
