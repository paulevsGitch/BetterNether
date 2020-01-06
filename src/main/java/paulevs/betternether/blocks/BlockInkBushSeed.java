package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;

public class BlockInkBushSeed extends BlockCommonSapling
{
	public BlockInkBushSeed()
	{
		super(BlocksRegister.BLOCK_INK_BUSH, MaterialColor.RED);
	}
}
