package paulevs.betternether.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.Tag.Named;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import paulevs.betternether.registry.NetherBlocks;
import ru.bclib.api.TagAPI;
import ru.bclib.interfaces.TagProvider;

import java.util.List;

public class BlockVeinedSand extends BlockBase implements TagProvider {
	public BlockVeinedSand() {
		super(FabricBlockSettings.of(Material.SAND)
				.mapColor(MaterialColor.COLOR_BROWN)
				.sounds(SoundType.SAND)
				.strength(0.5F, 0.5F));
		this.setDropItself(false);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		if (world.getBlockState(pos.above()).getBlock() == NetherBlocks.SOUL_VEIN)
			return state;
		else
			return Blocks.SOUL_SAND.defaultBlockState();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
		return new ItemStack(Blocks.SOUL_SAND);
	}
	
	@Override
	public void addTags(List<Named<Block>> blockTags, List<Named<Item>> itemTags) {
		//TODO: enable after BCLIb 1.1.4
		//blockTags.add(TagAPI.BLOCK_SOUL_GROUND);
	}
}
