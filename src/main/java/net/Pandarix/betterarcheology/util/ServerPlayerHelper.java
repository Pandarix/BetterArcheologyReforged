package net.Pandarix.betterarcheology.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class ServerPlayerHelper
{
    public static ServerPlayer getServerPlayer(Player player)
    {
        if (player instanceof ServerPlayer)
        {
            return (ServerPlayer) player;
        } else
        {
            return null;
        }
    }
}
