package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.html.BlockView;
import java.util.Map;
import java.util.stream.Stream;

public class OcelotFossilBlock extends FossilBaseWithEntityBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> OCELOT_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Stream.of(
                    Block.createCuboidShape(5.5, 0, 0, 11.5, 9.5, 17.75),
                    Block.createCuboidShape(6, 5, -7, 11, 10, 1)).reduce(VoxelShapes::union).get(),
            Direction.SOUTH, Stream.of(
                    Block.createCuboidShape(5.5, 0, -1.75, 11.5, 9.5, 16),
                    Block.createCuboidShape(6, 5, 15, 11, 10, 23)).reduce(VoxelShapes::union).get(),
            Direction.EAST, Stream.of(
                    Block.createCuboidShape(-1.25, 0, 5, 16.5, 9.5, 11),
                    Block.createCuboidShape(15.5, 5, 5.5, 23.5, 10, 10.5)).reduce(VoxelShapes::union).get(),
            Direction.WEST, Stream.of(
                    Block.createCuboidShape(0.5, 0, 5, 18.25, 9.5, 11),
                    Block.createCuboidShape(-6.5, 5, 5.5, 1.5, 10, 10.5)).reduce(VoxelShapes::union).get());

    public OcelotFossilBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FleeFromBlockEntity(pos, state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OCELOT_SHAPES_FOR_DIRECTION.get(state.getValue(FACING));
    }
}
