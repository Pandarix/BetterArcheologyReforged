package net.Pandarix.betterarcheology.event;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = BetterArcheology.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.ARCHEOLOGY_TABLE.get(),
                    ArcheologyTableBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.SUSBLOCK.get(),
                    SusBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(ModBlockEntities.VILLAGER_FOSSIL.get(),
                    VillagerFossilBlockEntityRenderer::new);
        }
    }
}
