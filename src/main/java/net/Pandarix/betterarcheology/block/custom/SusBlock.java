package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.block.entity.SusBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class SusBlock extends BrushableBlock
{
    public SusBlock(Block baseBlock, Properties settings, SoundEvent brushingSound, SoundEvent brushingCompleteSound)
    {
        super(baseBlock, settings, brushingSound, brushingCompleteSound);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new SusBlockEntity(pos, state);
    }
}
