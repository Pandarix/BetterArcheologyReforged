package net.Pandarix.betterarcheology.event;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.block.entity.client.ArcheologyTableBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.SusBlockEntityRenderer;
import net.Pandarix.betterarcheology.block.entity.client.VillagerFossilBlockEntityRenderer;
import net.Pandarix.betterarcheology.entity.ModEntityTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

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
            event.registerEntityRenderer(ModEntityTypes.BOMB_ENTITY.get(), ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event){
            //RENDERING
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROTTEN_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROTTEN_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VILLAGER_FOSSIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VILLAGER_FOSSIL_BODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL_BODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.OCELOT_FOSSIL_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL_BODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SHEEP_FOSSIL_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHICKEN_FOSSIL_BODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL_BODY.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CREEPER_FOSSIL_HEAD.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.GROWTH_TOTEM.get(), RenderType.cutout());
        }
    }
}
