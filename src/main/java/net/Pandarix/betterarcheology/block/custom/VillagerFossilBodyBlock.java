package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.swing.text.html.BlockView;
import java.util.Map;

public class VillagerFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Block.createCuboidShape(0, 0, 8, 16, 12, 15),
            Direction.SOUTH, Block.createCuboidShape(0, 0, 1, 16, 12, 8),
            Direction.EAST, Block.createCuboidShape(1, 0, 0, 8, 12, 16),
            Direction.WEST, Block.createCuboidShape(8, 0, 0, 15, 12, 16));

    public VillagerFossilBodyBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_FOR_DIRECTION.get(state.getValue(FACING));
    }
}
