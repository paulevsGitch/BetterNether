package paulevs.betternether.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import paulevs.betternether.blocks.materials.Materials;

public class BNLogStripable extends BNPillar {
	Block result;

	public BNLogStripable(Block source, Block result) {
		super(source);
		this.result = result;
	}

	public BNLogStripable(MaterialColor color, Block result) {
		super(Materials.makeWood(MaterialColor.TERRACOTTA_LIGHT_GREEN));
		this.result = result;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (player.getMainHandItem().getItem() instanceof AxeItem) {
			world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
			if (!world.isClientSide) {
				world.setBlock(pos, result.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 11);
				if (player != null && !player.isCreative()) {
					player.getMainHandItem().hurt(1, world.random, (ServerPlayer) player);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.FAIL;
	}
}
