package net.Pandarix.betterarcheology.screen;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.item.ModItems;
import net.Pandarix.betterarcheology.util.MenuHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class IdentifyingMenu extends AbstractContainerMenu
{
    public final ArcheologyTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public IdentifyingMenu(int syncId, Inventory inventory, FriendlyByteBuf extraData)
    {
        this(syncId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(ArcheologyTableBlockEntity.PROPERTY_DELEGATES));
    }

    public IdentifyingMenu(int syncId, Inventory playerInventory, BlockEntity entity, ContainerData data)
    {
        super(ModMenuTypes.IDENTIFYING_MENU.get(), syncId);    //creates a new Instance of Screenhandler
        checkContainerSize(playerInventory, ArcheologyTableBlockEntity.INV_SIZE);
        blockEntity = (ArcheologyTableBlockEntity) entity;
        this.level = playerInventory.player.level();
        this.data = data;

        MenuHelper.createPlayerInventory(this, playerInventory);
        MenuHelper.createPlayerHotbar(this, playerInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler ->
        {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 20));
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 20));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 26, 48));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 134, 48));
        });

        addDataSlots(data);
    }

    public boolean isCrafting()
    {
        return data.get(0) > 0;
    }

    public int getScaledProgress()
    {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);                         // Maximum Progress, after reaching: progress done
        int progressArrowSize = 74;                                 // This is the width in pixels of your arrow

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex)
    {
        ItemStack newStack = ItemStack.EMPTY;   //defines an empty ItemStack, will be used to return the changed Item in the Slot
        Slot slot = this.slots.get(pIndex);    //the given InventorySlot

        //If the to-be-moved-slot has an Item inside
        if (slot != null && slot.hasItem())
        {
            ItemStack originalStack = slot.getItem();  //stores the Item in the to-be-moved-slot
            newStack = originalStack.copy();    //sets a new Stack to the given Item

            //BRUSHES
            if (originalStack.getItem() instanceof BrushItem)
            {
                if (isInInv(pIndex))
                {
                    //inventory -> slots
                    if (!this.moveItemStackTo(originalStack, this.slots.size() - 3, this.slots.size() - 2, true))
                    {
                        return ItemStack.EMPTY;
                    }
                    //slots -> inventory
                } else if (!this.moveItemStackTo(originalStack, 0, this.slots.size() - ArcheologyTableBlockEntity.INV_SIZE - 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            //ARTIFACTS
            if (originalStack.is(ModItems.UNIDENTIFIED_ARTIFACT.get()))
            {
                if (isInInv(pIndex))
                {
                    //inventory -> slots
                    if (!this.moveItemStackTo(originalStack, this.slots.size() - 2, this.slots.size() - 1, true))
                    {
                        return ItemStack.EMPTY;
                    }
                    //slots -> inventory
                } else if (!this.moveItemStackTo(originalStack, 0, this.slots.size() - ArcheologyTableBlockEntity.INV_SIZE - 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (!isInInv(pIndex))
            {
                if (!this.moveItemStackTo(originalStack, 0, this.slots.size() - ArcheologyTableBlockEntity.INV_SIZE - 1, false))
                {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty())
            {
                slot.setByPlayer(ItemStack.EMPTY);
            } else
            {
                slot.setChanged();
            }

            if (originalStack.getCount() == newStack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer)
    {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ARCHEOLOGY_TABLE.get());
    }

    private boolean isInInv(int invSlot)
    {
        return invSlot < this.slots.size() - ArcheologyTableBlockEntity.INV_SIZE - 1;
    }

}
