package net.Pandarix.betterarcheology.world.processor;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

@AutoRegister(BetterArcheology.MOD_ID)
public class StructureProcessorTypes
{
    @AutoRegister("waterlogfix_processor")
    public static StructureProcessorType<WaterlogFixProcessor> WATERLOGFIX_PROCESSOR = () -> WaterlogFixProcessor.CODEC;
}
