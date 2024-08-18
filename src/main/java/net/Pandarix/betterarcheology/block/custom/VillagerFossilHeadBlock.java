package net.Pandarix.betterarcheology.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VillagerFossilHeadBlock extends FossilBaseHeadBlock
{
    private static final VoxelShape VILLAGER_HEAD_SHAPE = Block.box(3, 0, 3, 13, 10, 13);

    public VillagerFossilHeadBlock(BlockBehaviour.Properties settings)
    {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return VILLAGER_HEAD_SHAPE;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter blockGetter, List<Component> components, TooltipFlag tooltipFlag)
    {
        components.add(Component.translatable("block.betterarcheology.villager_fossil_head_tooltip").withStyle(ChatFormatting.GRAY).append(Component.translatable("block.betterarcheology.fossil_head_set").withStyle(ChatFormatting.BLUE)));
        super.appendHoverText(stack, blockGetter, components, tooltipFlag);
    }
}
