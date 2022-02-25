package paulevs.betternether.mixin.common;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multisets;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ComplexItem;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.MaterialColor.Brightness;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapItem.class)
public abstract class MapMixin extends ComplexItem {
	public MapMixin(Properties settings) {
		super(settings);
	}

	@Shadow
	private BlockState getCorrectStateForFluidBlock(Level world, BlockState state, BlockPos pos) {
		return state;
	}

	@Inject(method = "update", at = @At(value = "HEAD"), cancellable = true)
	private void customColors(Level level, Entity entity, MapItemSavedData state, CallbackInfo info) {
		//Code derived and adapted from Vanilla Minecraft Code in net.minecraft.world.item.MapItem.update
		if (level.dimensionType().hasCeiling() && level.dimension() == state.dimension && entity instanceof Player) {
			BlockPos.MutableBlockPos POS2 = new BlockPos.MutableBlockPos();
			final BlockPos.MutableBlockPos POS = new BlockPos.MutableBlockPos();
			
			int scale = 1 << state.scale;
			int sx = state.x;
			int sz = state.z;
			int px = Mth.floor(entity.getX() - (double)sx) / scale + 64;
			int py = Mth.floor(entity.getZ() - (double)sz) / scale + 64;
			int stepWidth = 128 / scale;
			if (level.dimensionType().hasCeiling()) {
				stepWidth /= 2;
			}
			MapItemSavedData.HoldingPlayer holdingPlayer = state.getHoldingPlayer((Player)entity);
			++holdingPlayer.step;
			boolean bl = false;
			for (int xx = px - stepWidth + 1; xx < px + stepWidth; ++xx) {
				if ((xx & 0xF) != (holdingPlayer.step & 0xF) && !bl) continue;
				bl = false;
				double d = 0.0;
				for (int yy = py - stepWidth - 1; yy < py + stepWidth; ++yy) {
					double y;
					
					if (xx < 0 || yy < -1 || xx >= 128 || yy >= 128) continue;
					int dx = xx - px;
					int dy = yy - py;
					boolean bl2 = dx * dx + dy * dy > (stepWidth - 2) * (stepWidth - 2);
					int x = (sx / scale + xx - 64) * scale;
					int z = (sz / scale + yy - 64) * scale;
					LinkedHashMultiset<MaterialColor> multiset = LinkedHashMultiset.create();
					LevelChunk levelChunk = level.getChunkAt(new BlockPos(x, 0, z));
					if (levelChunk.isEmpty()) continue;
					ChunkPos chunkPos = levelChunk.getPos();
					int cx = x & 0xF;
					int cz = z & 0xF;
					int w = 0;
					double height = 0.0;
					
					//we do not want the special ceiling code for the nether
//					if (level.dimensionType().hasCeiling()) {
//
//					}
					
					for (int bx = 0; bx < scale; ++bx) {
						for (int bz = 0; bz < scale; ++bz) {
							BlockState blockState;
							int testY = levelChunk.getHeight(Heightmap.Types.WORLD_SURFACE, bx + cx, bz + cz) + 1;
							if (testY > level.getMinBuildHeight() + 1) {
								
								//make sure we get under the nether ceiling and find the first "AIR" block
								do {
									POS.set(chunkPos.getMinBlockX() + bx + cx, --testY, chunkPos.getMinBlockZ() + bz + cz);
									blockState = levelChunk.getBlockState(POS);
								} while (blockState.is(Blocks.BEDROCK) || blockState.getMapColor(level, POS) != MaterialColor.NONE && testY > level.getMinBuildHeight());
								
								do {
									POS.set(chunkPos.getMinBlockX() + bx + cx, --testY, chunkPos.getMinBlockZ() + bz + cz);
								} while ((blockState = levelChunk.getBlockState(POS)).getMapColor(level, POS) == MaterialColor.NONE && testY > level.getMinBuildHeight());
								if (testY > level.getMinBuildHeight() && !blockState.getFluidState()
																				 .isEmpty()) {
									BlockState blockState2;
									int ab = testY - 1;
									POS2.set(POS);
									do {
										POS2.setY(ab--);
										blockState2 = levelChunk.getBlockState( POS2);
										++w;
									} while (ab > level.getMinBuildHeight() && !blockState2.getFluidState()
																						   .isEmpty());
									blockState = this.getCorrectStateForFluidBlock(level, blockState, (BlockPos) POS);
								}
							}
							else {
								blockState = Blocks.BEDROCK.defaultBlockState();
							}
							state.checkBanners(level, chunkPos.getMinBlockX() + bx + cx, chunkPos.getMinBlockZ() + bz + cz);
							height += (double) testY / (double) (scale * scale);
							multiset.add(blockState.getMapColor(level, (BlockPos) POS));
						}
					}
					
					
					MaterialColor mc = Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MaterialColor.NONE);
					Brightness br = mc == MaterialColor.WATER ? ((y = (double) (w /= scale * scale) * 0.1 + (double) (xx + yy & 1) * 0.2) < 0.5 ? MaterialColor.Brightness.HIGH : (y > 0.9 ? MaterialColor.Brightness.LOW : MaterialColor.Brightness.NORMAL)) : ((y = (height - d) * 4.0 / (double) (scale + 4) + ((double) (xx + yy & 1) - 0.5) * 0.4) > 0.6 ? MaterialColor.Brightness.HIGH : (y < -0.6 ? MaterialColor.Brightness.LOW : MaterialColor.Brightness.NORMAL));
					d = height;
					if (yy < 0 || dx * dx + dy * dy >= stepWidth * stepWidth || bl2 && (xx + yy & 1) == 0) continue;
					bl |= state.updateColor(xx, yy, mc.getPackedId(br));
					
				}
			}

			info.cancel();
		}
	}
}
