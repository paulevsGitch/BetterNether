package paulevs.betternether.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.config.Config;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.IStructureWorld;
import paulevs.betternether.structures.StructureBuilding;

public class BNWorldGenerator
{
	private static IStructureWorld[] globalStructuresLand;
	private static IStructureWorld[] globalStructuresLava;
	private static IStructureWorld[] globalStructuresCave;
	
	private static boolean hasCleaningPass;

	//private static float structueDensity = 1F / 64F;
	private static float oreDensity;
	
	private static final BlockState AIR = Blocks.AIR.getDefaultState();
	
	//private static CityStructureManager cityManager;
	private static Mutable popPos = new Mutable();
	
	private static NetherBiome[][][] biomeArray = new NetherBiome[8][64][8];
	private static BiomeMap map;
	private static int cacheTimer = 0;
	private static int sizeXZ;
	private static int sizeY;
	
	private static final List<BlockPos> LIST_FLOOR = new ArrayList<BlockPos>(1024);
	private static final List<BlockPos> LIST_WALL = new ArrayList<BlockPos>(1024);
	private static final List<BlockPos> LIST_CEIL = new ArrayList<BlockPos>(1024);
	
	public static void loadConfig()
	{
		hasCleaningPass = Config.getBoolean("generator_world", "cleaning_pass", true);
		oreDensity = Config.getFloat("generator_world", "cincinnasite_ore_density", 1F / 1024F);
		sizeXZ = Config.getInt("generator_world", "biome_size_xz", 128);
		sizeY = Config.getInt("generator_world", "biome_size_y", 32);
	}

	public static void init(IWorld world)
	{
		long seed = world.getSeed();
		map = new BiomeMap(seed, sizeXZ, sizeY);
		
		globalStructuresLand = new IStructureWorld[] {
				new StructureBuilding("altar_01", -1),
				new StructureBuilding("altar_02", -4),
				new StructureBuilding("altar_03", -3),
				new StructureBuilding("altar_04", -3),
				new StructureBuilding("altar_05", -2),
				new StructureBuilding("altar_06", -2),
				new StructureBuilding("portal_01", -4),
				new StructureBuilding("portal_02", -3),
				new StructureBuilding("garden_01", -3),
				new StructureBuilding("garden_02", -2),
				new StructureBuilding("pillar_01", -1),
				new StructureBuilding("respawn_point_01", -3),
				new StructureBuilding("respawn_point_02", -2)
		};
		
		globalStructuresLava = new IStructureWorld[] {};
		
		globalStructuresCave = new IStructureWorld[] {
				new StructureBuilding("room_01", -5),
		};
		
		/*if (ConfigLoader.hasCities())
		{
			cityManager = new CityStructureManager(seed);
			cityManager.load(world);
			cityManager.setDistance(ConfigLoader.getCityDistance());
		}*/
	}
	
	public static void clearCache()
	{
		if (cacheTimer > 512)
		{
			cacheTimer = 0;
			map.clearCache();
		}
	}
	
	/*public static void save(World world)
	{
		if (cityManager != null)
			cityManager.save(world);
	}*/
	
	private static void makeBiomeArray(int sx, int sz)
	{
		int wx, wy, wz;
		for (int x = 0; x < 8; x++)
		{
			wx = sx + (x << 1);
			for (int y = 0; y < 64; y++)
			{
				wy = (y << 1);
				for (int z = 0; z < 8; z++)
				{
					wz = sz + (z << 1);
					biomeArray[x][y][z] = getBiome(wx, wy, wz);
				}
			}
		}
		cacheTimer ++;
	}
	
	private static NetherBiome getBiomeLocal(int x, int y, int z, Random random)
	{
		x = (x + random.nextInt(4) - 2) >> 1;
		y = (y + random.nextInt(4) - 2) >> 1;
		z = (z + random.nextInt(4) - 2) >> 1;
		
		return biomeArray[clamp(x, 7)][clamp(y, 63)][clamp(z, 7)];
	}
	
	private static NetherBiome getBiome(int x, int y, int z)
	{
		NetherBiome biome = map.getBiome(x, y, z);
		
		if (biome.hasEdge() || (biome.hasParrent() && biome.getParrentBiome().hasEdge()))
		{
			NetherBiome search = biome;
			if (biome.hasParrent())
				search = biome.getParrentBiome();
			int d = search.getEdgeSize();
			
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

	public static void generate(IWorld world, int cx, int cz, Random random)
	{
		NetherBiome biome;
		int sx = (cx << 4) | 8;
		int sz = (cz << 4) | 8;

		makeBiomeArray(sx, sz);
		
		//Structure Generator
		if (random.nextFloat() < 0.1)
		{
			BlockPos pos = new BlockPos(sx + random.nextInt(8), 32 + random.nextInt(120 - 32), sz + random.nextInt(8));
			while (world.getBlockState(pos).getBlock() != Blocks.AIR && pos.getY() > 32)
			{
				pos = pos.down();
			}
			int h = BlocksHelper.downRay(world, pos, pos.getY() - 5);
			pos = pos.down(h + 1);
			boolean terrain = true;
			for (int y = 1; y < 8; y++)
			{
				if (!world.isAir(pos.up(y)))
				{
					terrain = false;
					break;
				}
			}
			if (terrain)
			{
				if (BlocksHelper.isLava(world.getBlockState(pos)))
				{
					if (globalStructuresLava.length > 0)
						globalStructuresLava[random.nextInt(globalStructuresLava.length)].generateLava(world, pos.up(), random);
				}
				else if (globalStructuresLand.length > 0)
					globalStructuresLand[random.nextInt(globalStructuresLand.length)].generateSurface(world, pos.up(), random);
			}
			else if (globalStructuresCave.length > 0)
			{
				globalStructuresCave[random.nextInt(globalStructuresCave.length)].generateSubterrain(world, pos, random);
			}
		}

		// Total Populator
		for (int x = 0; x < 16; x++)
		{
			int wx = sx + x;
			for (int z = 0; z < 16; z++)
			{
				int wz = sz + z;
				for (int y = 5; y < 126; y++)
				{
					popPos.set(wx, y, wz);
					if (BlocksHelper.isNetherGround(world.getBlockState(popPos)))
					{
						biome = getBiomeLocal(x, y, z, random);
						
						if (world.isAir(popPos.up()))
							biome.genSurfColumn(world, popPos, random);

						if (((x + y + z) & 1) == 0)
						{
							// Ground Generation
							if (world.isAir(popPos.up()))
							{
								//biome.genFloorObjects(world, popPos.up(), random);
								LIST_FLOOR.add(new BlockPos(popPos.up()));
							}

							// Ceiling Generation
							else if (world.isAir(popPos.down()))
							{
								//biome.genCeilObjects(world, popPos.down(), random);
								LIST_CEIL.add(new BlockPos(popPos.down()));
							}

							// Wall Generation
							else// if (((x + y + z) & 1) == 0)
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

									boolean bDown = world.isAir(objPos.down());
									boolean bUp = world.isAir(objPos.up());

									if (bDown && bUp)
									{
										//biome.genWallObjects(world, objPos, random);
										LIST_WALL.add(new BlockPos(objPos));
									}
								}
							}
						}
						if (random.nextFloat() < oreDensity)
							spawnOre(BlocksRegister.BLOCK_CINCINNASITE_ORE.getDefaultState(), world, popPos, random);
					}
				}
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
		
		LIST_FLOOR.clear();
		LIST_WALL.clear();
		LIST_CEIL.clear();
	}
	
	public static void smoothChunk(IWorld world, int cx, int cz)
	{
		if (hasCleaningPass)
		{
			int wx = (cx << 4) | 8;
			int wz = (cz << 4) | 8;
			List<BlockPos> pos = new ArrayList<BlockPos>();
			BlockPos up;
			BlockPos down;
			BlockPos north;
			BlockPos south;
			BlockPos east;
			BlockPos west;
			for (int y = 32; y < 110; y++)
			{
				for (int x = 0; x < 16; x++)
					for (int z = 0; z < 16; z++)
					{
						popPos.set(x + wx, y, z + wz);
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
			for (BlockPos p : pos)
			{
				BlocksHelper.setWithoutUpdate(world, p, AIR);
				up = p.up();
				BlockState state = world.getBlockState(up);
				if (!state.getBlock().canPlaceAt(state, world, up))
					BlocksHelper.setWithoutUpdate(world, up, AIR);
			}
		}
		//if (cityManager != null)
		//	cityManager.generate(world, cx, cz);
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
	
	/*public static BlockPos getNearestCity(World world, int cx, int cz)
	{
		return cityManager.getNearestStructure(world, cx, cz);
	}*/
}
