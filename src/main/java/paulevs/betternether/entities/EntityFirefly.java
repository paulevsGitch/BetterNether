package paulevs.betternether.entities;

import java.util.Random;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.biomes.BiomeRegister;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.sounds.SoundRegister;
import paulevs.betternether.world.BNWorldGenerator;

@Optional.Interface(iface="elucent.albedo.lighting.ILightProvider", modid="albedo")
public class EntityFirefly extends EntityAmbientCreature implements IAnimals, ILightProvider
{
	private static final DataParameter<Byte> R = EntityDataManager.<Byte>createKey(EntityFirefly.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> G = EntityDataManager.<Byte>createKey(EntityFirefly.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> B = EntityDataManager.<Byte>createKey(EntityFirefly.class, DataSerializers.BYTE);
	private static final DataParameter<Float> SIZE = EntityDataManager.<Float>createKey(EntityFirefly.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> OFFSET = EntityDataManager.<Float>createKey(EntityFirefly.class, DataSerializers.FLOAT);
	
	private Random random;
	private boolean sitting;
	private boolean wantToSit;
	private float sitY;
	private float test;

	public EntityFirefly(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
		this.setNoGravity(true);
		this.isImmuneToFire = true;
		//@SideOnly(Side.CLIENT)
		//this.setRenderDistanceWeight(4);
		random = new Random();
	}
	
	//@SideOnly(Side.CLIENT)
	public boolean getCanSpawnHere()
    {
		NetherBiome biome = BNWorldGenerator.getBiome(this.getPosition());
		return biome == BiomeRegister.BIOME_GRASSLANDS || biome == BiomeRegister.BIOME_NETHER_JUNGLE;
    }
	
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(R, (byte) 255);
        this.dataManager.register(G, (byte) 255);
        this.dataManager.register(B, (byte) 255);
        this.dataManager.register(SIZE, 0.5F);
        this.dataManager.register(OFFSET, 0.77F);
    }

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) { }

	@Override
	protected void collideWithNearbyEntities() { }

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
	}

	@Override
    public void onUpdate()
    {
    	super.onUpdate();
    	if (this.dead && this.hasNoGravity())
    		this.setNoGravity(false);
    	if (sitting)
    	{
    		this.posY = sitY;
    	}
    }

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		//offset = 1.3F - (Float) this.dataManager.get(SIZE) * 1.06F;
		//System.out.println(offset);
		if (wantToSit)
		{
			if (sitting)
			{
				this.motionX = 0;
				this.motionY = 0;
				this.motionZ = 0;
				if (random.nextInt(320) == 0)
				{
					wantToSit = false;
					//write("Not want to sit");
				}
			}
			else
			{
				this.motionX *= 0.5;
				this.motionY = -0.02;
				this.motionZ *= 0.5;
				BlockPos under = new BlockPos(this).add(0, - 0.5, 0);
				if (this.world.getBlockState(under).isSideSolid(world, under, EnumFacing.UP))
				{
					this.motionX = 0;
					this.motionY = 0;
					this.motionZ = 0;
					//this.posY = Math.floor(this.posY - 0.1) + this.world.getBlockState(under).getCollisionBoundingBox(this.world, under).maxY - 1;
					//this.posY = under.getY();
					sitY = under.getY() + (float) this.world.getBlockState(under).getCollisionBoundingBox(this.world, under).maxY;
					//write("Sitting now, Y: " + this.posY);
					sitting = true;
				}
			}
		}
		else
		{
			if (sitting)
			{
				this.motionY = 0.05;
				this.motionX = 0.05 - random.nextDouble() * 0.1;
				this.motionZ = 0.05 - random.nextDouble() * 0.1;
				//write("Not sitting");
				sitting = false;
			}
			else
			{
				if (random.nextInt(3) == 0)
				{
					this.motionX += 0.01 - random.nextDouble() * 0.02;
					this.motionY += 0.02 - random.nextDouble() * 0.04;
					this.motionZ += 0.01 - random.nextDouble() * 0.02;
					// Don't fly to high
					BlockPos under = new BlockPos(this).down(8);
					if (this.world.getBlockState(under).getBlock() == Blocks.AIR
					&& this.world.getBlockState(under.up(7)).getBlock() == Blocks.AIR)
					{
						this.motionY = -Math.abs(this.motionY);
					}
					double l = motionX * motionX + motionY * motionY + motionZ * motionZ;
					if (l > 0)
					{
						l = Math.sqrt(l);
						this.motionX /= l;
						this.motionY /= l;
						this.motionZ /= l;
					}
					else
					{
						switch (random.nextInt(6))
						{
						case 0:
							this.motionX = 1;
							this.motionY = 0;
							this.motionZ = 0;
							break;
						case 1:
							this.motionX = -1;
							this.motionY = 0;
							this.motionZ = 0;
							break;
						case 2:
							this.motionY = 1;
							this.motionX = 0;
							this.motionZ = 0;
							break;
						case 3:
							this.motionY = -1;
							this.motionX = 0;
							this.motionZ = 0;
							break;
						case 4:
							this.motionZ = 1;
							this.motionX = 0;
							this.motionY = 0;
							break;
						default:
							this.motionZ = -1;
							this.motionX = 0;
							this.motionY = 0;
							break;
						}
					}
					this.motionX *= 0.05;
					this.motionY *= 0.02;
					this.motionZ *= 0.05;
					float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
					float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
					this.moveForward = 0.5F;
					this.rotationYaw += f1;
				}
				if (random.nextInt(320) == 0)
				{
					wantToSit = true;
					//write("Want to sit");
				}
			}
		}
	}

	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    @Override
    public float getBrightness()
    {
        return 1.0F;
    }
    
    @SideOnly(Side.CLIENT)
    public void bindColor()
    {
    	GL11.glColor3ub(
    			(Byte) this.dataManager.get(R),
    			(Byte) this.dataManager.get(G),
    			(Byte) this.dataManager.get(B));
    }
    
    @SideOnly(Side.CLIENT)
    public void transform()
    {
    	GlStateManager.translate(0, (Float) this.dataManager.get(OFFSET), 0);
    	GlStateManager.scale(
    			(Float) this.dataManager.get(SIZE),
    			(Float) this.dataManager.get(SIZE),
    			(Float) this.dataManager.get(SIZE));
    }
    
    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
    	super.writeEntityToNBT(compound);
    	compound.setByte("R", ((Byte) this.dataManager.get(R)));
    	compound.setByte("G", ((Byte) this.dataManager.get(G)));
    	compound.setByte("B", ((Byte) this.dataManager.get(B)));
    	compound.setFloat("Size", (Float) this.dataManager.get(SIZE));
    }
    
    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
    	super.readEntityFromNBT(compound);
    	this.dataManager.set(R, compound.getByte("R"));
    	this.dataManager.set(G, compound.getByte("G"));
    	this.dataManager.set(B, compound.getByte("B"));
    	this.dataManager.set(SIZE, compound.getFloat("Size"));
    	updateOffset();
    }

	@Override
	public Light provideLight()
	{
		return Light
    			.builder()
    			.pos(this)
    			.color(
    			(float) (((Byte) this.dataManager.get(R)) & 255) / 255F,
				(float) (((Byte) this.dataManager.get(G)) & 255) / 255F,
				(float) (((Byte) this.dataManager.get(B)) & 255) / 255F).radius(2F).build();
	}
	
	protected boolean canTriggerWalking()
    {
        return false;
    }

    public void fall(float distance, float damageMultiplier)
    {
    }

    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
    {
    }
    
    @Nullable
    public SoundEvent getAmbientSound()
    {
        return this.sitting ? null : SoundRegister.FLY_SOUND;
    }
    
    protected float getSoundVolume()
    {
        return 0.3F + random.nextFloat() * 0.2F;
    }
    
    protected float getSoundPitch()
    {
        return random.nextFloat() * 0.4F + 0.8F;
    }
    
    private void updateOffset()
    {
    	//offset = 1.3F - (Float) this.dataManager.get(SIZE) * 1.06F;
    	this.dataManager.set(OFFSET, 1.3F - (Float) this.dataManager.get(SIZE) * 1.06F);
    }
    
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
		this.dataManager.set(R, (byte) (128 | random.nextInt(128)));
		this.dataManager.set(G, (byte) (128 | random.nextInt(128)));
		this.dataManager.set(B, (byte) (128 | random.nextInt(128)));
		this.dataManager.set(SIZE, 0.25F + random.nextFloat() * 0.25F);
		updateOffset();
        return livingdata;
    }
}
