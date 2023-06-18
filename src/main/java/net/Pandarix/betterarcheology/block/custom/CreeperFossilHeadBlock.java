package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.swing.text.html.BlockView;

public class CreeperFossilHeadBlock extends FossilBaseHeadBlock {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 8, 13);

    public CreeperFossilHeadBlock(Properties settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
