package paulevs.betternether.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.blocks.BlockStalagnateBowl.EnumFood;
import paulevs.betternether.config.ConfigLoader;

public class ItemsRegister
{
    public static Item BLACK_APPLE;
    public static Item CINCINNASITE;
    public static Item CINCINNASITE_PICKAXE;
    public static Item CINCINNASITE_PICKAXE_DIAMOND;
    public static Item STALAGNATE_BOWL;
    public static Item STALAGNATE_BOWL_WART;
    public static Item STALAGNATE_BOWL_MUSHROOM;
    public static Item STALAGNATE_BOWL_APPLE;
    public static Item CINCINNASITE_AXE;
    public static Item CINCINNASITE_AXE_DIAMOND;
    
    private static List<Item> render = new ArrayList<Item>();

    public static void register()
    {
		BLACK_APPLE = registerItem(new ItemBlackApple());
		CINCINNASITE = registerItem(new ItemCincinnasite());
		CINCINNASITE_PICKAXE = registerItem(new BNItemPickaxe("cincinnasite_pickaxe", ToolMaterial.IRON, 512, 4F));
		CINCINNASITE_PICKAXE_DIAMOND = registerItem(new BNItemPickaxe("cincinnasite_pickaxe_diamond", ToolMaterial.DIAMOND, 2048, 1.5F));
		STALAGNATE_BOWL = registerItem(new ItemBowlEmpty("stalagnate_bowl"));
		STALAGNATE_BOWL_WART = registerItem(new ItemBowlFood("stalagnate_bowl_wart", 4, EnumFood.WART));
		STALAGNATE_BOWL_MUSHROOM = registerItem(new ItemBowlFood("stalagnate_bowl_mushroom", 6, EnumFood.MUSHROOM));
		STALAGNATE_BOWL_APPLE = registerItem(new ItemBowlFood("stalagnate_bowl_apple", 8, EnumFood.APPLE));
		CINCINNASITE_AXE = registerItem(new BNItemAxe("cincinnasite_axe", ToolMaterial.IRON, 512, 4F));
		CINCINNASITE_AXE_DIAMOND = registerItem(new BNItemAxe("cincinnasite_axe_diamond", ToolMaterial.DIAMOND, 2048, 1.5F));
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        for (Item item : render)
        	registerModel(item);
    }

    private static Item registerItem(Item item)
    {
    	if (ConfigLoader.mustInitItem())
    	{
    		ForgeRegistries.ITEMS.register(item);
    		render.add(item);
    		return item;
    	}
    	else
    		return Items.AIR;
    }

    @SideOnly(Side.CLIENT)
    private static void registerModel(Item item)
    {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
