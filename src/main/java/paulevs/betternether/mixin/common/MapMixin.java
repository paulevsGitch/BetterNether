package paulevs.betternether.mixin.common;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
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
import net.minecraft.world.level.material.MaterialColor;
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
	private void customColors(Level world, Entity entity, MapItemSavedData state, CallbackInfo info) {
		if (world.dimensionType().hasCeiling() && world.dimension() == state.dimension && entity instanceof Player) {
			int i = 1 << state.scale;
			int j = state.x;
			int k = state.z;
			int l = Mth.floor(entity.getX() - (double) j) / i + 64;
			int m = Mth.floor(entity.getZ() - (double) k) / i + 64;
			int n = 128 / i;

			MapItemSavedData.HoldingPlayer playerUpdateTracker = state.getHoldingPlayer((Player) entity);
			++playerUpdateTracker.step;
			boolean bl = false;

			BlockState blockState = Blocks.BEDROCK.defaultBlockState();
			Blocks.BEDROCK.defaultBlockState();

			for (int o = l - n + 1; o < l + n; ++o) {
				if ((o & 15) == (playerUpdateTracker.step & 15) || bl) {
					bl = false;
					double d = 0.0D;

					for (int p = m - n - 1; p < m + n; ++p) {
						if (o >= 0 && p >= -1 && o < 128 && p < 128) {
							int q = o - l;
							int r = p - m;
							boolean bl2 = q * q + r * r > (n - 2) * (n - 2);
							int s = (j / i + o - 64) * i;
							int t = (k / i + p - 64) * i;
							Multiset<MaterialColor> multiset = LinkedHashMultiset.create();
							LevelChunk worldChunk = world.getChunkAt(new BlockPos(s, 0, t));
							if (!worldChunk.isEmpty()) {
								ChunkPos chunkPos = worldChunk.getPos();

								int u = chunkPos.getMinBlockX() + (s & 15);
								int v = chunkPos.getMinBlockZ() + (t & 15);

								int w = 0;
								double e = 0.0D;
								BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
								new BlockPos.MutableBlockPos();

								for (int x = 0; x < i; ++x) {
									for (int z = 0; z < i; ++z) {
										mutable.set(x + u, 127, z + v);

										blockState = Blocks.NETHERRACK.defaultBlockState();
										for (int y = 126; y > 0; y--) {
											mutable.setY(y);
											if (!world.isEmptyBlock(mutable) && world.isEmptyBlock(mutable.above())) {
												blockState = world.getBlockState(mutable);
												break;
											}
										}

										multiset.add(blockState.getMapColor(world, mutable));
									}
								}

								w /= i * i;
								double f = (e - d) * 4.0D / (double) (i + 4) + ((double) (o + p & 1) - 0.5D) * 0.4D;
								int ac = 1;
								if (f > 0.6D) {
									ac = 2;
								}

								if (f < -0.6D) {
									ac = 0;
								}

								MaterialColor materialColor = (MaterialColor) Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MaterialColor.NONE);

								if (materialColor == MaterialColor.WATER) {
									f = (double) w * 0.1D + (double) (o + p & 1) * 0.2D;
									ac = 1;
									if (f < 0.5D) {
										ac = 2;
									}

									if (f > 0.9D) {
										ac = 0;
									}
								}

								d = e;
								if (p >= 0 && q * q + r * r < n * n && (!bl2 || (o + p & 1) != 0)) {
									byte b = state.colors[o + p * 128];
									byte c = (byte) (materialColor.id * 4 + ac);
									if (b != c) {
										state.setColor(o, p, c);
										/*state.colors[o + p * 128] = c;
										state.markDirty(o, p);*/
										bl = true;
									}
								}
							}
						}
					}
				}
			}

			info.cancel();
		}
	}
}
