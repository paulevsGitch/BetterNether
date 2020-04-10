package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.DecoratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import paulevs.betternether.BetterNether;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureCaves;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.world.structures.CityFeature;

public class BNWorldGenerator
{
	private static boolean hasCleaningPass;
	private static boolean hasFixPass;

	private static float oreDensity;
	private static float structureDensity;
	
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	
	private static Mutable popPos = new Mutable();
	
	private static final NetherBiome[][][] BIOMES = new NetherBiome[4][32][4];
	private static BiomeMap map;
	private static int sizeXZ;
	private static int sizeY;
	
	private static final List<BlockPos> LIST_FLOOR = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_WALL = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_CEIL = new ArrayList<BlockPos>(4096);
	private static final List<BlockPos> LIST_LAVA = new ArrayList<BlockPos>(1024);
	
	private static StructureCaves caves;
	private static NetherBiome biome;

	public static final StructureFeature<DefaultFeatureConfig> CITY = Registry.register(
			Registry.STRUCTURE_FEATURE,
			new Identifier(BetterNether.MOD_ID, "nether_city"),
			new CityFeature(DefaultFeatureConfig::deserialize)
			);
	
	public static void onModInit()
	{
		hasCleaningPass = Config.getBoolean("generator_world", "terrain_cleaning_pass", true);
		hasFixPass = Config.getBoolean("generator_world", "world_fixing_pass", true);
		
		oreDensity = Config.getFloat("generator_world", "cincinnasite_ore_density", 1F / 1024F);
		structureDensity = Config.getFloat("generator_world", "structures_density", 1F / 32F);
		sizeXZ = Config.getInt("generator_world", "biome_size_xz", 200);
		sizeY = Config.getInt("generator_world", "biome_size_y", 40);
		
		if (Config.getBoolean("generator_world", "generate_cities", true))
		{
			Biomes.NETHER.addStructureFeature(CITY.configure(FeatureConfig.DEFAULT));
			Biomes.NETHER.addFeature(GenerationStep.Feature.RAW_GENERATION, CITY.configure(FeatureConfig.DEFAULT).createDecoratedFeature(Decorator.NOPE.configure(DecoratorConfig.DEFAULT)));
			Feature.STRUCTURES.put("nether_city", CITY);
		}
	}

	public static void init(IWorld world)
	{
		if (map == null)
		{
			long seed = world.getSeed();
			map = new BiomeMap(seed, sizeXZ, sizeY);
			caves = new StructureCaves(seed);
		}
	}
	
	public static void clearCache()
	{
		map.clearCache();
	}
	
	private static void makeBiomeArray(int sx, int sz)
	{
		int wx, wy, wz;
		for (int x = 0; x < 4; x++)
		{
			wx = sx + (x << 2);
			for (int y = 0; y < 32; y++)
			{
				wy = (y << 2);
				for (int z = 0; z < 4; z++)
				{
					wz = sz + (z << 2);
					BIOMES[x][y][z] = getBiome(wx, wy, wz);
				}
			}
		}
	}
	
	private static NetherBiome getBiomeLocal(int x, int y, int z, Random random)
	{
		int px = (x + random.nextInt(5) - 2) >> 2;
		int py = (y + random.nextInt(5) - 2) >> 2;
		int pz = (z + random.nextInt(5) - 2) >> 2;
		return BIOMES[clamp(px, 3)][clamp(py, 31)][clamp(pz, 3)];
	}
	
	public static NetherBiome getBiome(int x, int y, int z)
	{
		NetherBiome biome = map.getBiome(x, y, z);
		
		if (biome.hasEdge() || (biome.hasParrent() && biome.getParrentBiome().hasEdge()))
		{
			NetherBiome search = biome;
			if (biome.hasParrent())
				search = biome.getParrentBiome();
			int d = (int) Math.ceil(search.getEdgeSize() / 4F) * 4;
			
			boolean edge = !search.isSame(map.getBiome(x + d, y, z));
			edge = edge || !search.isSame(map.getBiome(x - d, y, z));
			edge = edge || !search.isSame(map.getBiome(x, y + d, z));
			edge = edge || !search.isSame(map.getBiome(x, y - d, z));
			edge = edge || !search.isSame(map.getBiome(x, y, z + d));
			edge = edge || !search.isSame(map.getBiome(x, y, z - d));
			edge = edge || !search.isSame(map.getBiome(x - d, y - d, z - d));
			edge = edge || !search.isSame(map.getBiome(x + d, y - d, z - d));
			edge = edge || !search.isSame(map.getBiome(x - d, y - d, z + d));
			edge = edge || !search.isSame(map.getBiome(x + d, y - d, z + d));
			edge = edge || !search.isSame(map.getBiome(x - d, y + d, z - d));
			edge = edge || !search.isSame(map.getBiome(x + d, y + d, z - d));
			edge = edge || !search.isSame(map.getBiome(x - d, y + d, z + d));
			edge = edge || !search.isSame(map.getBiome(x + d, y + d, z + d));
			
			if (edge)
			{
				biome = search.getEdge();
			}
		}
		
		return biome;
	}
	
	private static int clamp(int x, int max)
	{
		return x < 0 ? 0 : x > max ? max : x;
	}

	public static void populate(IWorld world, int sx, int sz, Random random)
	{
		makeBiomeArray(sx, sz);
		
		//Structure Generator
		if (random.nextFloat() < structureDensity)
		{
			popPos.set(sx + random.nextInt(16), 32 + random.nextInt(120 - 32), sz + random.nextInt(16));
			StructureType type = StructureType.FLOOR;
			boolean isAir =  world.getBlockState(popPos).isAir();
			boolean airUp = world.getBlockState(popPos.up()).isAir() && world.getBlockState(popPos.up(3)).isAir();
			boolean airDown = world.getBlockState(popPos.down()).isAir() && world.getBlockState(popPos.down(3)).isAir();
			NetherBiome biome = getBiomeLocal(popPos.getX() - sx, popPos.getY(), popPos.getZ() - sz, random);
			if (!isAir && !airUp && !airDown)
				type = StructureType.UNDER;
			else
			{
				if (!biome.hasCeilStructures() || random.nextBoolean()) // Floor
				{
					while (world.getBlockState(popPos.down()).isAir() && popPos.getY() > 1)
					{
						popPos.setY(popPos.getY() - 1);
					}
				}
				else // Ceil
				{
					while (!BlocksHelper.isNetherGroundMagma(world.getBlockState(popPos.up())) && popPos.getY() < 127)
					{
						popPos.setY(popPos.getY() + 1);
					}
					type = StructureType.CEIL;
				}
			}
			biome = getBiomeLocal(popPos.getX() - sx, popPos.getY(), popPos.getZ() - sz, random);
			if (world.isAir(popPos))
			{
				if (type == StructureType.FLOOR)
				{
					BlockState down = world.getBlockState(popPos.down());
					if (BlocksHelper.isLava(down))
					{
						biome.genLavaBuildings(world, popPos, random);
					}
					else if (BlocksHelper.isNetherGroundMagma(down))
						biome.genFloorBuildings(world, popPos, random);
				}
				else if (type == StructureType.CEIL)
				{
					BlockState up = world.getBlockState(popPos.up());
					if (BlocksHelper.isNetherGroundMagma(up))
					{
						biome.genCeilBuildings(world, popPos, random);
					}
				}
			}
			else
				biome.genUnderBuildings(world, popPos, random);
		}
		
		LIST_LAVA.clear();
		LIST_FLOOR.clear();
		LIST_WALL.clear();
		LIST_CEIL.clear();
		
		int ex = sx + 16;
		int ez = sz + 16;

		// Total Populator
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				
				for (int y = 5; y < 126; y++)
				{
					if (caves.isInCave(x, y, z))
						continue;
					
					popPos.set(wx, y, wz);
					BlockState state = world.getBlockState(popPos);
					boolean lava = BlocksHelper.isLava(state);
					if (lava || BlocksHelper.isNetherGroundMagma(state))
					{
						biome = getBiomeLocal(x, y, z, random);
						
						if (!lava && world.isAir(popPos.up()))
							biome.genSurfColumn(world, popPos, random);

						if (((x + y + z) & 1) == 0)
						{
							// Ground Generation
							if (world.isAir(popPos.up()))
							{
								if (lava)
									LIST_LAVA.add(popPos.up());
								else
									LIST_FLOOR.add(new BlockPos(popPos.up()));
							}

							// Ceiling Generation
							else if (world.isAir(popPos.down()))
							{
								LIST_CEIL.add(new BlockPos(popPos.down()));
							}

							// Wall Generation
							else
							{
								boolean bNorth = world.isAir(popPos.north());
								boolean bSouth = world.isAir(popPos.south());
								boolean bEast = world.isAir(popPos.east());
								boolean bWest = world.isAir(popPos.west());
								if (bNorth || bSouth || bEast || bWest)
								{
									BlockPos objPos = null;
									if (bNorth)
										objPos = popPos.north();
									else if (bSouth)
										objPos = popPos.south();
									else if (bEast)
										objPos = popPos.east();
									else
										objPos = popPos.west();

									if ((popPos.getX() >= sx) && (popPos.getX() < ex) && (popPos.getZ() >= sz) && (popPos.getZ() < ez))
									{
										boolean bDown = world.isAir(objPos.down());
										boolean bUp = world.isAir(objPos.up());
	
										if (bDown && bUp)
										{
											LIST_WALL.add(new BlockPos(objPos));
										}
									}
								}
							}
						}
						if (random.nextFloat() < oreDensity)
							spawnOre(BlocksRegistry.CINCINNASITE_ORE.getDefaultState(), world, popPos, random);
					}
				}
			}
			
		}
		
		for (BlockPos pos: LIST_LAVA)
		{
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				biome.genLavaObjects(world, pos, random);
			}
		}
		
		for (BlockPos pos: LIST_FLOOR)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				biome.genFloorObjects(world, pos, random);
			}
		
		for (BlockPos pos: LIST_WALL)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				biome.genWallObjects(world, pos, random);
			}
		
		for (BlockPos pos: LIST_CEIL)
			if (world.isAir(pos))
			{
				biome = getBiomeLocal(pos.getX() - sx, pos.getY(), pos.getZ() - sz, random);
				biome.genCeilObjects(world, pos, random);
			}
	}
	
	public static void prePopulate(IWorld world, int cx, int cz)
	{
		int wx = (cx << 4);
		int wz = (cz << 4);
		
		popPos.set(wx, 0, wz);
		caves.generate(world, popPos, world.getRandom());
		
		if (hasCleaningPass)
		{
			List<BlockPos> pos = new ArrayList<BlockPos>();
			BlockPos up;
			BlockPos down;
			BlockPos north;
			BlockPos south;
			BlockPos east;
			BlockPos west;
			for (int y = 32; y < 110; y++)
			{
				popPos.setY(y);
				for (int x = 0; x < 16; x++)
				{
					popPos.setX(x + wx);
					for (int z = 0; z < 16; z++)
					{
						popPos.setZ(z + wz);
						if (canReplace(world, popPos))
						{
							up = popPos.up();
							down = popPos.down();
							north = popPos.north();
							south = popPos.south();
							east = popPos.east();
							west = popPos.west();
							if (world.isAir(north) && world.isAir(south))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(east) && world.isAir(west))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(up) && world.isAir(down))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.north().east().down()) && world.isAir(popPos.south().west().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.south().east().down()) && world.isAir(popPos.north().west().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.north().west().down()) && world.isAir(popPos.south().east().up()))
								pos.add(new BlockPos(popPos));
							else if (world.isAir(popPos.south().west().down()) && world.isAir(popPos.north().east().up()))
								pos.add(new BlockPos(popPos));
						}
					}
				}
			}
			for (BlockPos p : pos)
			{
				BlocksHelper.setWithoutUpdate(world, p, AIR);
				up = p.up();
				BlockState state = world.getBlockState(up);
				if (!state.getBlock().canPlaceAt(state, world, up))
					BlocksHelper.setWithoutUpdate(world, up, AIR);
			}
		}
	}
	
	private static boolean canReplace(IWorld world, BlockPos pos)
	{
		return BlocksHelper.isNetherGround(world.getBlockState(pos));
	}
	
	private static void spawnOre(BlockState state, IWorld world, BlockPos pos, Random random)
	{
		for (int i = 0; i < 6 + random.nextInt(11); i++)
		{
			BlockPos local = pos.add(random.nextInt(3), random.nextInt(3), random.nextInt(3));
			if (BlocksHelper.isNetherrack(world.getBlockState(local)))
			{
				BlocksHelper.setWithoutUpdate(world, local, state);
			}
		}
	}
	
	public static void cleaningPass(IWorld world, int sx, int sz)
	{
		if (hasFixPass)
		{
			fixBlocks(world, sx, 30, sz, sx + 15, 110, sz + 15);
		}
	}
	
	private static void fixBlocks(IWorld world, int x1, int y1, int z1, int x2, int y2, int z2)
	{
		for (int y = y1; y <= y2; y++)
		{
			popPos.setY(y);
			for (int x = x1; x <= x2; x++)
			{
				popPos.setX(x);
				for (int z = z1; z <= z2; z++)
				{
					popPos.setZ(z);
					
					BlockState state = world.getBlockState(popPos);
					
					if (!state.canPlaceAt(world, popPos))
					{
						BlocksHelper.setWithoutUpdate(world, popPos, AIR);
						continue;
					}
					
					if (!state.isOpaque() && world.getBlockState(popPos.up()).getBlock() == Blocks.NETHER_BRICKS)
					{
						BlocksHelper.setWithoutUpdate(world, popPos, Blocks.NETHER_BRICKS.getDefaultState());
						continue;
					}
					
					if (BlocksHelper.isLava(state) && world.isAir(popPos.up()) && world.isAir(popPos.down()))
					{
						BlocksHelper.setWithoutUpdate(world, popPos, AIR);
						continue;
					}
				}
			}
		}
	}
}
