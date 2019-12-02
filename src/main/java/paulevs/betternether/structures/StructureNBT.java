package paulevs.betternether.structures;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;

public class StructureNBT
{
	protected ResourceLocation location;
	protected Template template;
	protected static final PlacementSettings DEFAULT_SETTINGS = (
			new PlacementSettings())
			.setMirror(Mirror.NONE)
			.setRotation(Rotation.NONE)
			.setIgnoreEntities(false)
			.setChunk((ChunkPos) null)
			.setReplacedBlock((Block) null)
			.setIgnoreStructureBlock(false);
	
	public StructureNBT(String structure)
	{
		location = new ResourceLocation("betternether", structure);
		template = readTemplateFromJar(new ResourceLocation("betternether", structure));
	}
	
	protected StructureNBT(ResourceLocation location, Template template)
	{
		this.location = location;
		this.template = template;
	}
	
	public boolean generateCentered(World world, BlockPos pos, Random random)
	{
		return generateCentered(world, pos, Rotation.values()[random.nextInt(Rotation.values().length)]);
	}
	
	public boolean generateCentered(World world, BlockPos pos)
	{
		if(template == null)
		{
			System.out.println("No structure: " + location.toString());
			return false;
		}
		
		BlockPos blockpos2 = template.getSize();
		IBlockState iblockstate = world.getBlockState(pos);
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null)
				.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
		template.addBlocksToWorldChunk(world, pos.add(-blockpos2.getX() >> 1, 0, -blockpos2.getZ() >> 1), placementsettings);
		return true;
	}
	
	public boolean generateCentered(World world, BlockPos pos, Rotation rotation)
	{
		if(template == null)
		{
			System.out.println("No structure: " + location.toString());
			return false;
		}
		
		BlockPos blockpos2 = template.getSize().rotate(rotation);
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(rotation).setIgnoreEntities(false).setChunk((ChunkPos) null)
				.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
		pos = pos.add(-(blockpos2.getX() >> 1), 0, -(blockpos2.getZ() >> 1));
		pos = pos.add(blockpos2.getX() < 0 ? -1 : 0, 0, blockpos2.getZ() < 0 ? -1 : 0);
		template.addBlocksToWorldChunk(world, pos, placementsettings);
		return true;
	}
	
	private Template readTemplateFromJar(ResourceLocation resource)
    {
        String s = resource.getResourceDomain();
        String s1 = resource.getResourcePath();

        try
        {
        	InputStream inputstream = MinecraftServer.class.getResourceAsStream("/assets/" + s + "/structures/" + s1 + ".nbt");
            return readTemplateFromStream(inputstream);
        }
        catch (IOException e)
        {
			e.printStackTrace();
		}
        
        return null;
    }
	
	private Template readTemplateFromStream(InputStream stream) throws IOException
    {
        NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(stream);

        if (!nbttagcompound.hasKey("DataVersion", 99))
        {
            nbttagcompound.setInteger("DataVersion", 500);
        }

        Template template = new Template();
        template.read(nbttagcompound);
        
        return template;
    }
	
	public BlockPos getSize(Rotation rotation)
	{
		if (rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180)
			return template.getSize();
		else
		{
			BlockPos size = template.getSize();
			int x = size.getX();
			int z = size.getZ();
			return new BlockPos(z, size.getY(), x);
		}
	}
	
	public String getName()
	{
		return location.getResourcePath();
	}
}
