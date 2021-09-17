package paulevs.betternether.blocks.complex;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.blocks.BlockRubeusCone;
import paulevs.betternether.blocks.BlockRubeusSapling;
import paulevs.betternether.blocks.RubeusBark;
import paulevs.betternether.blocks.RubeusLog;
import ru.bclib.complexmaterials.entry.BlockEntry;

public class RubeusMaterial extends NetherWoodenMaterial {
	public final static String BLOCK_SAPLING = BLOCK_OPTIONAL_SAPLING;
	public final static String BLOCK_CONE = "cone";
	public RubeusMaterial() {
		super("rubeus", MaterialColor.COLOR_MAGENTA, MaterialColor.COLOR_MAGENTA);
	}
	
	@Override
	public RubeusMaterial init() {
		return (RubeusMaterial)super.init();
	}
	
	@Override
	protected void initDefault(FabricBlockSettings blockSettings, FabricItemSettings itemSettings) {
		super.initDefault(blockSettings, itemSettings);
		final Tag.Named<Block> tagBlockLog = getBlockTag(TAG_LOGS);
		final Tag.Named<Item> tagItemLog = getItemTag(TAG_LOGS);
		
		addBlockEntry(new BlockEntry(BLOCK_SAPLING, (complexMaterial, settings) -> {
			return new BlockRubeusSapling();
		}));
		
		addBlockEntry(new BlockEntry(BLOCK_CONE, (complexMaterial, settings) -> {
			return new BlockRubeusCone();
		}));
		
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_LOG, (complexMaterial, settings) -> {
				return new RubeusLog(woodColor, getStrippedLog());
			})
				.setBlockTags(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, tagBlockLog)
				.setItemTags(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, tagItemLog)
		);
		
		replaceOrAddBlockEntry(
			new BlockEntry(BLOCK_BARK, (complexMaterial, settings) -> {
				return new RubeusBark(woodColor, getStrippedBark());
			})
				.setBlockTags(BlockTags.LOGS, BlockTags.LOGS_THAT_BURN, tagBlockLog)
				.setItemTags(ItemTags.LOGS, ItemTags.LOGS_THAT_BURN, tagItemLog)
		);
	}
	
	public Block getCone(){
		return getBlock(BLOCK_CONE);
	}
	
	public Block getSapling(){
		return getBlock(BLOCK_SAPLING);
	}
}
