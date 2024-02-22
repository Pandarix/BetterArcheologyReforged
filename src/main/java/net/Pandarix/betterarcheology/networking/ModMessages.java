package net.Pandarix.betterarcheology.networking;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class ModMessages
{
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id()
    {
        return packetId++;
    }

    public static void register()
    {
        SimpleChannel net = ChannelBuilder
                .named(new ResourceLocation(BetterArcheology.MOD_ID, "messages"))
                .networkProtocolVersion(1)
                .serverAcceptedVersions((status, version) -> true)
                .clientAcceptedVersions((status, version) -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message)
    {
        INSTANCE.send(message, PacketDistributor.SERVER.noArg());
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(message, PacketDistributor.PLAYER.with(player));
    }

    public static <MSG> void sendToClients(MSG message)
    {
        INSTANCE.send(message, PacketDistributor.ALL.noArg());
    }
}
