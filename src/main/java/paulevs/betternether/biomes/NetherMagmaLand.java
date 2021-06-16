package paulevs.betternether.biomes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.WorldAccess;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.decorations.StructureCrystal;
import paulevs.betternether.structures.decorations.StructureGeyser;
import paulevs.betternether.structures.plants.StructureGoldenVine;
import paulevs.betternether.structures.plants.StructureMagmaFlower;

import java.util.Random;

public class NetherMagmaLand extends NetherBiome {
	private static final Mutable POS = new Mutable();
	private static final boolean[] MASK;

	public NetherMagmaLand(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(248, 158, 68)
				.setLoop(SoundEvents.AMBIENT_NETHER_WASTES_LOOP)
				.setAdditions(SoundEvents.AMBIENT_NETHER_WASTES_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_NETHER_WASTES_MOOD));
		addStructure("geyser", new StructureGeyser(), StructureType.FLOOR, 0.1F, false);
		addStructure("obsidian_crystals", new StructureCrystal(), StructureType.FLOOR, 0.04F, true);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.4F, false);
		addStructure("golden_vine", new StructureGoldenVine(), StructureType.CEIL, 0.2F, true);
	}

	@Override
	public void genSurfColumn(WorldAccess world, BlockPos pos, Random random) {
		if (isMask(pos.getX(), pos.getZ())) {
			POS.set(pos);
			boolean magma = true;
			if (random.nextInt(4) == 0) {
				if (validWall(world, POS.down()) && validWall(world, POS.north()) && validWall(world, POS.south()) && validWall(world, POS.east()) && validWall(world, POS.west())) {
					BlocksHelper.setWithoutUpdate(world, POS, Blocks.LAVA.getDefaultState());
					magma = false;
				}
			}
			if (magma)
				for (int y = 0; y < random.nextInt(3) + 1; y++) {
				POS.setY(pos.getY() - y);
				if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
					BlocksHelper.setWithoutUpdate(world, POS, Blocks.MAGMA_BLOCK.getDefaultState());
				}
		}
		else
			super.genSurfColumn(world, pos, random);
	}

	protected boolean validWall(WorldAccess world, BlockPos pos) {
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
