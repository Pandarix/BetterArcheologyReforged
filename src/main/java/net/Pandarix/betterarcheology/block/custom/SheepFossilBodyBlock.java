package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SheepFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Block.box(0, 0, 2, 16, 8, 16),
            Direction.SOUTH, Block.box(0, 0, 0, 16, 8, 14),
            Direction.EAST, Block.box(2, 0, 0, 16, 8, 16),
            Direction.WEST, Block.box(0, 0, 0, 14, 8, 16));
    public SheepFossilBodyBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }
}
