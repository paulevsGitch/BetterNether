package paulevs.betternether.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNetherrack;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import paulevs.betternether.BetterNether;
import paulevs.betternether.world.BNWorldGenerator;

public class BlockEggPlant extends Block
{
	private static final AxisAlignedBB EGG_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);
	private static final Potion potion = Potion.getPotionById(19);

	public BlockEggPlant()
	{
		super(Material.PLANTS, MapColor.WHITE_STAINED_HARDENED_CLAY);
		this.setCreativeTab(BetterNether.BN_TAB);
		this.setHardness(1.0F);
		this.setRegistryName("egg_plant");
		this.setUnlocalizedName("egg_plant");
		this.setSoundType(SoundType.PLANT);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return EGG_AABB;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
		worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX() + rand.nextDouble(), pos.getY() + 0.4, pos.getZ() + rand.nextDouble(), 0.46, 0.28, 0.55);
    }
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (BNWorldGenerator.enableMobDamage && entityIn instanceof EntityLiving && !((EntityLiving) entityIn).isPotionActive(potion))
		{
			((EntityLiving) entityIn).addPotionEffect(new PotionEffect(potion, 100, 3));
		}
		else if (BNWorldGenerator.enablePlayerDamage && entityIn instanceof EntityPlayer && !((EntityPlayer) entityIn).isPotionActive(potion))
			((EntityPlayer) entityIn).addPotionEffect(new PotionEffect(potion, 100, 3));
	}
	
	@Override
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
		Block under = worldIn.getBlockState(pos.down()).getBlock();
		return under instanceof BlockNetherrack || under == Blocks.SOUL_SAND;
    }
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!canPlaceBlockAt(worldIn, pos))
			worldIn.destroyBlock(pos, true);
	}
}
