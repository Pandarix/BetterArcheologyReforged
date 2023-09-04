package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class CreeperFossilBodyBlock extends FossilBaseBodyBlock {
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.box(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.box(3, 0, 1.5, 13, 6.5, 6.5)),
            Direction.SOUTH, Shapes.or(
                    Block.box(3.5, 5.25, 5.5, 12.5, 17.25, 10.5),
                    Block.box(3, 0, 9.5, 13, 6.5, 14.5),
                    Block.box(3, 0, 1.5, 13, 6.5, 6.5)),
            Direction.WEST, Shapes.or(
                    Block.box(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.box(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.box(9.5, 0, 3, 14.5, 6.5, 13)),
            Direction.EAST, Shapes.or(
                    Block.box(5.5, 5.25, 3.5, 10.5, 17.25, 12.5),
                    Block.box(1.5, 0, 3, 6.5, 6.5, 13),
                    Block.box(9.5, 0, 3, 14.5, 6.5, 13)));

    public CreeperFossilBodyBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPES_FOR_DIRECTION.get(blockState.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag) {
        components.add(Component.translatable("block.betterarcheology.creeper_fossil_body_tooltip").withStyle(ChatFormatting.GRAY).append(Component.translatable("block.betterarcheology.fossil_body_set").withStyle(ChatFormatting.BLUE)));
        super.appendHoverText(stack, blockGetter, components, tooltipFlag);
    }
}
