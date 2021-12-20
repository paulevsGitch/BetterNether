package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.decorations.StructureCrystal;
import paulevs.betternether.world.structures.decorations.StructureGeyser;
import paulevs.betternether.world.structures.plants.StructureGoldenVine;
import paulevs.betternether.world.structures.plants.StructureMagmaFlower;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class NetherMagmaLand extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(248, 158, 68)
				   .loop(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
				   .additions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE());
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherMagmaLand::new;
		}
	}

	private static final boolean[] MASK;
	
	public NetherMagmaLand(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
	}
	
	@Override
	protected void onInit(){
		addStructure("geyser", new StructureGeyser(), StructureType.FLOOR, 0.1F, false);
		addStructure("obsidian_crystals", new StructureCrystal(), StructureType.FLOOR, 0.04F, true);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.4F, false);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.2F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		if (isMask(pos.getX(), pos.getZ())) {
			final MutableBlockPos POS = new MutableBlockPos();
			POS.set(pos);
			boolean magma = true;
			if (random.nextInt(4) == 0) {
				if (validWall(world, POS.below()) && validWall(world, POS.north()) && validWall(world, POS.south()) && validWall(world, POS.east()) && validWall(world, POS.west())) {
					BlocksHelper.setWithoutUpdate(world, POS, Blocks.LAVA.defaultBlockState());
					magma = false;
				}
			}
			if (magma)
				for (int y = 0; y < random.nextInt(3) + 1; y++) {
				POS.setY(pos.getY() - y);
				if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
					BlocksHelper.setWithoutUpdate(world, POS, Blocks.MAGMA_BLOCK.defaultBlockState());
				}
		}
		else
			super.genSurfColumn(world, pos, random);
	}

	protected boolean validWall(LevelAccessor world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isLava(state) || BlocksHelper.isNetherGroundMagma(state);
	}

	protected boolean isMask(int x, int z) {
		x &= 15;
		z &= 15;
		return MASK[(x << 4) | z];
	}

	static {
		MASK = new boolean[] {
				false, true, false, false, false, false, false, true, false, false, false, false, true, true, false, false,
				false, false, false, false, false, false, false, true, false, false, false, false, true, true, false, false,
				true, true, true, false, false, false, true, true, false, false, false, true, false, false, true, true,
				true, false, false, true, true, true, true, false, true, true, true, true, false, false, false, true,
				true, false, false, true, true, true, false, false, false, false, false, true, false, false, false, false,
				true, false, false, false, true, false, false, false, false, false, false, false, true, false, false, false,
				false, false, false, false, true, false, false, false, false, false, false, false, true, true, true, true,
				true, false, false, false, true, true, true, true, false, false, false, true, true, false, true, true,
				true, true, true, true, true, false, false, true, true, false, true, true, false, false, false, true,
				false, false, true, false, false, false, false, false, true, true, true, false, false, false, false, true,
				false, false, true, false, false, false, false, false, false, true, false, false, false, false, true, false,
				false, false, true, true, false, false, false, false, false, true, false, false, false, false, true, false,
				true, false, false, false, true, false, false, false, false, true, false, false, false, false, true, false,
				true, true, false, false, true, false, false, false, true, true, true, true, true, true, false, true,
				false, true, true, true, true, true, true, true, false, false, false, false, true, false, false, false,
				false, true, true, false, false, false, true, false, false, false, false, false, true, true, false, false
		};
	}
}
