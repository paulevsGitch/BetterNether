package paulevs.betternether.registry;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockAccessor;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import paulevs.betternether.BetterNether;
import paulevs.betternether.blocks.materials.Materials;
import ru.bclib.api.BonemealAPI;
import ru.bclib.api.TagAPI;
import ru.bclib.blocks.BaseVineBlock;
import ru.bclib.blocks.SimpleLeavesBlock;
import ru.bclib.util.TagHelper;

public class NetherTags {
	public static final Tag<Block> SOUL_GROUND_BLOCK = TagAPI.makeCommonBlockTag( "soul_ground");
	public static final Tag<Block> NETHERRACK = TagAPI.makeCommonBlockTag("netherrack");
	public static final Tag<Block> MYCELIUM = TagAPI.makeCommonBlockTag("nether_mycelium");
	public static final Tag<Block> NYLIUM = BlockTags.NYLIUM;

	public static final Tag<Item> SOUL_GROUND_ITEM = TagAPI.makeCommonItemTag("soul_ground");

	public static void register() {
		BlocksRegistry.getPossibleBlocks().forEach(name -> {
			//TODO: maybe we should keep a list of references to all blocks
			Block block = Registry.BLOCK.get(new ResourceLocation(BetterNether.MOD_ID, name));
			BlockBehaviour.Properties properties = ((AbstractBlockAccessor) block).getSettings();
			Material material = ((AbstractBlockSettingsAccessor) properties).getMaterial();

			if (material.equals(Material.STONE) || material.equals(Material.METAL)) {
				TagHelper.addTag(TagAPI.MINEABLE_PICKAXE, block);
			}
			else if (material.equals(Material.WOOD) || material.equals(Material.NETHER_WOOD)) {
				TagHelper.addTag(TagAPI.MINEABLE_AXE, block);
			}
			else if (material.equals(Material.LEAVES) || material.equals(Material.PLANT) || material.equals(Material.WATER_PLANT)) {
				TagHelper.addTag(TagAPI.MINEABLE_HOE, block);
			}
			else if (material.equals(Material.SAND)) {
				TagHelper.addTag(TagAPI.MINEABLE_SHOVEL, block);
			}
		});
	}
}