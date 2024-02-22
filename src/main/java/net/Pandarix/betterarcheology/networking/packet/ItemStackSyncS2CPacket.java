package net.Pandarix.betterarcheology.networking.packet;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ItemStackSyncS2CPacket
{
    private final ItemStackHandler itemStackHandler;
    private final BlockPos pos;

    public ItemStackSyncS2CPacket(ItemStackHandler itemStackHandler, BlockPos pos)
    {
        this.itemStackHandler = itemStackHandler;
        this.pos = pos;
    }

    public ItemStackSyncS2CPacket(FriendlyByteBuf buf)
    {
        List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
        itemStackHandler = new ItemStackHandler(collection.size());
        for (int i = 0; i < collection.size(); i++)
        {
            itemStackHandler.insertItem(i, collection.get(i), false);
        }

        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        Collection<ItemStack> list = new ArrayList<>();
        for (int i = 0; i < itemStackHandler.getSlots(); i++)
        {
            list.add(itemStackHandler.getStackInSlot(i));
        }

        buf.writeCollection(list, FriendlyByteBuf::writeItem);
        buf.writeBlockPos(pos);
    }

    public boolean handle(CustomPayloadEvent.Context context)
    {
        context.enqueueWork(() ->
        {
            if ( Minecraft.getInstance().level != null && Minecraft.getInstance().level.getBlockEntity(pos) instanceof ArcheologyTableBlockEntity blockEntity)
            {
                blockEntity.setHandler(this.itemStackHandler);
            }
        });
        return true;
    }
}