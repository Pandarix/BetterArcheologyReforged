package net.Pandarix.betterarcheology.block.custom;

import com.google.common.collect.ImmutableMap;
import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;

public class OcelotFossilBlock extends FossilBaseWithEntityBlock
{
    //Map of hitboxes for every direction the model can be facing
    private static final Map<Direction, VoxelShape> OCELOT_SHAPES_FOR_DIRECTION = ImmutableMap.of(
            Direction.NORTH, Shapes.or(
                    Block.box(5.5, 0, 0, 11.5, 9.5, 17.75),
                    Block.box(6, 5, -7, 11, 10, 1)),
            Direction.SOUTH, Shapes.or(
                    Block.box(5.5, 0, -1.75, 11.5, 9.5, 16),
                    Block.box(6, 5, 15, 11, 10, 23)),
            Direction.EAST, Shapes.or(
                    Block.box(-1.25, 0, 5, 16.5, 9.5, 11),
                    Block.box(15.5, 5, 5.5, 23.5, 10, 10.5)),
            Direction.WEST, Shapes.or(
                    Block.box(0.5, 0, 5, 18.25, 9.5, 11),
                    Block.box(-6.5, 5, 5.5, 1.5, 10, 10.5)));

    public OcelotFossilBlock(BlockBehaviour.Properties settings)
    {
        super(settings);
    }

    @Override
    @ParametersAreNonnullByDefault
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FleeFromBlockEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return OCELOT_SHAPES_FOR_DIRECTION.get(pState.getValue(FACING));
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> component, TooltipFlag flag)
    {
        component.add(Component.translatable("block.betterarcheology.ocelot_fossil_tooltip").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, context, component, flag);
    }
}
