package net.Pandarix.betterarcheology.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class SusBlockEntity extends BrushableBlockEntity
{
    public SusBlockEntity(BlockPos pos, BlockState state)
    {
        super(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean m_345532_(BlockState blockState)
    {
        return ModBlockEntities.SUSBLOCK.get().isValid(blockState) || super.m_345532_(blockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType()
    {
        return ModBlockEntities.SUSBLOCK.get();
    }
}
