package paulevs.betternether.mixin.common;

import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.NetworkSyncedItem;
import net.minecraft.item.map.MapState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FilledMapItem.class)
public abstract class MapMixin extends NetworkSyncedItem {
	public MapMixin(Settings settings) {
		super(settings);
	}

	@Shadow
	private BlockState getFluidStateIfVisible(World world, BlockState state, BlockPos pos) {
		return state;
	}

	@Inject(method = "updateColors", at = @At(value = "HEAD"), cancellable = true)
	private void customColors(World world, Entity entity, MapState state, CallbackInfo info) {
		if (world.getDimension().hasCeiling() && world.getRegistryKey() == state.dimension && entity instanceof PlayerEntity) {
			int i = 1 << state.scale;
			int j = state.centerX;
			int k = state.centerZ;
			int l = MathHelper.floor(entity.getX() - (double) j) / i + 64;
			int m = MathHelper.floor(entity.getZ() - (double) k) / i + 64;
			int n = 128 / i;

			MapState.PlayerUpdateTracker playerUpdateTracker = state.getPlayerSyncData((PlayerEntity) entity);
			++playerUpdateTracker.field_131;
			boolean bl = false;

			BlockState blockState = Blocks.BEDROCK.getDefaultState();
			Blocks.BEDROCK.getDefaultState();

			for (int o = l - n + 1; o < l + n; ++o) {
				if ((o & 15) == (playerUpdateTracker.field_131 & 15) || bl) {
					bl = false;
					double d = 0.0D;

					for (int p = m - n - 1; p < m + n; ++p) {
						if (o >= 0 && p >= -1 && o < 128 && p < 128) {
							int q = o - l;
							int r = p - m;
							boolean bl2 = q * q + r * r > (n - 2) * (n - 2);
							int s = (j / i + o - 64) * i;
							int t = (k / i + p - 64) * i;
							Multiset<MapColor> multiset = LinkedHashMultiset.create();
							WorldChunk worldChunk = world.getWorldChunk(new BlockPos(s, 0, t));
							if (!worldChunk.isEmpty()) {
								ChunkPos chunkPos = worldChunk.getPos();

								int u = chunkPos.getStartX() + (s & 15);
								int v = chunkPos.getStartZ() + (t & 15);

								int w = 0;
								double e = 0.0D;
								BlockPos.Mutable mutable = new BlockPos.Mutable();
								new BlockPos.Mutable();

								for (int x = 0; x < i; ++x) {
									for (int z = 0; z < i; ++z) {
										mutable.set(x + u, 127, z + v);

										blockState = Blocks.NETHERRACK.getDefaultState();
										for (int y = 126; y > 0; y--) {
											mutable.setY(y);
											if (!world.isAir(mutable) && world.isAir(mutable.up())) {
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

								MapColor materialColor = (MapColor) Iterables.getFirst(Multisets.copyHighestCountFirst(multiset), MapColor.CLEAR);

								if (materialColor == MapColor.WATER_BLUE) {
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
