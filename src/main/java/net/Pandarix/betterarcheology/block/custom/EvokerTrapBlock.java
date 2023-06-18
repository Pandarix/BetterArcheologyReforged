package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.core.Direction;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class EvokerTrapBlock extends HorizontalDirectionalBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty TRIGGERED = BooleanProperty.create("triggered");

    private static final int fangCooldown = 40; //cooldown used to prevent Fang-spamming
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active"); //active as long as fangs are spawning and for the duration of fangcooldown

    public EvokerTrapBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(TRIGGERED, false).with(ACTIVE, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean active = (Boolean) state.get(ACTIVE);

        //if the Block is receiving a redstone signal and is not already activated
        if (powered && !active) {
            //set self to active state and spawn fangs
            world.setBlockState(pos, state.with(ACTIVE, true));
            spawnFangs(state, world, pos, world.getRandom());
            //set cooldown for active-state to be reset
            world.scheduleBlockTick(pos, this, fangCooldown);
        } else if (!powered && active) {
            //world.setBlockState(pos, (BlockState) state.with(TRIGGERED, false), 4);
        }
    }

    //spawns 3 evoker fangs in a straight line in the direction the block is facing
    private  void spawnFangs(BlockState state, World world, BlockPos pos, Random random){
        if(world.isClient()){return;}

        int maxFangs = 3;

        //spawns fangs with individual positional increase depending on the direction the block is facing
        switch (state.get(FACING)) {
            case NORTH -> {
                for (int i = 0; i < maxFangs; ++i) {
                    world.spawnEntity(new EvokerFangsEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() - 0.5 - i * 1.5, (float) Math.toRadians(90), 0, null));
                }
            }
            case SOUTH -> {
                for (int i = 0; i < maxFangs; ++i) {
                    world.spawnEntity(new EvokerFangsEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 1.5 + i * 1.5, (float) Math.toRadians(90), 0, null));
                }
            }
            case EAST -> {
                for (int i = 0; i < maxFangs; ++i) {
                    world.spawnEntity(new EvokerFangsEntity(world, pos.getX() + 1.5 + i * 1.5, pos.getY(), pos.getZ() + 0.5, 0, 0, null));
                }
            }
            case WEST -> {
                for (int i = 0; i < maxFangs; ++i) {
                    world.spawnEntity(new EvokerFangsEntity(world, pos.getX() - 0.5 - i * 1.5, pos.getY(), pos.getZ() + 0.5, 0, 0, null));
                }
            }
            default -> {
                for (int i = 0; i < maxFangs; ++i) {
                    world.spawnEntity(new EvokerFangsEntity(world, pos.getX() + 0.5, pos.getY(), pos.getZ() - 0.5 - i * 1.5, 0, 0, null));
                }
            }
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, state.with(ACTIVE, false));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState) state.with(FACING, rotation.rotate((Direction) state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation((Direction) state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED, ACTIVE);
    }
}
