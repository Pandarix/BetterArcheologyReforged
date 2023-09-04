package net.Pandarix.betterarcheology.screen;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.item.ModItems;
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

public class IdentifyingMenu extends AbstractContainerMenu {
    public final ArcheologyTableBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public IdentifyingMenu(int syncId, Inventory inventory, FriendlyByteBuf extraData) {
        this(syncId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(ArcheologyTableBlockEntity.PROPERTY_DELEGATES));
    }

    public IdentifyingMenu(int syncId, Inventory playerInventory, BlockEntity entity, ContainerData data) {
        super(ModMenuTypes.IDENTIFYING_MENU.get(), syncId);    //creates a new Instance of Screenhandler
        checkContainerSize(playerInventory, ArcheologyTableBlockEntity.INV_SIZE);
        blockEntity = (ArcheologyTableBlockEntity) entity;
        this.level = playerInventory.player.level();
        this.data = data;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 20));
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 20));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 26, 48));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 134, 48));
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);                         // Maximum Progress, after reaching: progress done
        int progressArrowSize = 74;                                 // This is the width in pixels of your arrow //TODO: Edit correct size

        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack newStack = ItemStack.EMPTY;   //defines an empty ItemStack, will be used to return the changed Item in the Slot
        Slot slot = this.slots.get(pIndex);    //the given InventorySlot

        //If the to-be-moved-slot has an Item inside
        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();  //stores the Item in the to-be-moved-slot
            newStack = originalStack.copy();    //sets a new Stack to the given Item

            //BRUSHES
            if (originalStack.getItem() instanceof BrushItem) {
                if (isInInv(pIndex)) {
                    //inventory -> slots
                    if (!this.moveItemStackTo(originalStack, 0, this.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                    //slots -> inventory
                } else if (!this.moveItemStackTo(originalStack, 0, ArcheologyTableBlockEntity.INV_SIZE, false)) {
                    return ItemStack.EMPTY;
                }
            }

            //ARTIFACTS
            if (originalStack.is(ModItems.UNIDENTIFIED_ARTIFACT.get())) {
                if (isInInv(pIndex)) {
                    //inventory -> slots
                    if (!this.moveItemStackTo(originalStack, 1, this.slots.size(), true)) {
                        return ItemStack.EMPTY;
                    }
                    //slots -> inventory
                } else if (!this.moveItemStackTo(originalStack, 1, ArcheologyTableBlockEntity.INV_SIZE, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (isInInv(pIndex)) {
                if (!this.moveItemStackTo(originalStack, ArcheologyTableBlockEntity.INV_SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                slot.setChanged();
                return ItemStack.EMPTY;
            }

            //If the to-be-moved-Itemstack is empty, replace the ItemStack with "Empty-Item"
            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            slot.setChanged();
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ARCHEOLOGY_TABLE.get());
    }

    private boolean isInInv(int invSlot) {
        return invSlot < ArcheologyTableBlockEntity.INV_SIZE;
    }

    //Helper Method to add Players Inventoryslots to Screen
    private void addPlayerInventory(Inventory playerInventory) {
        /*Adding Slots of Main Inventory
                COL:    COL:    COL:    ...
        ROW:    slot    slot    slot
        ROW:    slot    slot    slot
        ...
        */

        //MainSize is the number of Slots besides the Armor and Offhand
        //HotbarSize is the number of Slots in the Hotbar, which incidentally is the number of slots per Column
        int hotbarSize = Inventory.DEFAULT_DISTANCE_LIMIT + 1;
        int inventorySize = Inventory.INVENTORY_SIZE - hotbarSize;    //Because Main includes the Hotbar Slots, we have to subtract them to get the raw Inventory size
        int inventoryRows = inventorySize / hotbarSize;    //All Slots : Slots per Column = Number of Rows to draw
        int inventoryColumns = hotbarSize;

        //For every Row in the Inventory
        for (int i = 0; i < inventoryRows; ++i) {
            //Add a slot for every Column in the Row
            for (int l = 0; l < inventoryColumns; ++l) {
                //Numbers are Minecrafts pre-defined offsets due to the textures
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    //Helper Method to add Players HotBarSlots to Screen
    private void addPlayerHotbar(Inventory playerInventory) {
        //Adds a new Slot to the Screen for every Slot in the Players Hotbar
        for (int i = 0; i < Inventory.DEFAULT_DISTANCE_LIMIT + 1; ++i) {
            //Numbers are Minecrafts pre-defined offsets due to the textures
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
