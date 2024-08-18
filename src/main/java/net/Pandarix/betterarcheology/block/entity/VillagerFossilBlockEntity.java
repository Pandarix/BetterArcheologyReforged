package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.block.custom.VillagerFossilBlock;
import net.Pandarix.betterarcheology.screen.FossilInventoryMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VillagerFossilBlockEntity extends BlockEntity implements MenuProvider
{
    private final ItemStackHandler itemStackHandler = new ItemStackHandler(1)
    {
        @Override
        protected void onContentsChanged(int slot)
        {
            setChanged();
            if (level != null && !level.isClientSide())
            {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private LazyOptional<IItemHandler> lazyOptional = LazyOptional.empty();

    public VillagerFossilBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.VILLAGER_FOSSIL.get(), pos, state);
    }

    //ITEMHANDLER stuff----------------------------------------------------------------------------------//
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == ForgeCapabilities.ITEM_HANDLER)
        {
            return lazyOptional.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        lazyOptional = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps()
    {
        super.invalidateCaps();
        lazyOptional.invalidate();
    }

    //Reading & writing----------------------------------------------------------------------------------//
    @Override
    protected void saveAdditional(CompoundTag nbt)
    {
        nbt.put("inventory", itemStackHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt)
    {
        super.load(nbt);
        itemStackHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops()
    {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for (int i = 0; i < itemStackHandler.getSlots(); i++)
        {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.betterarcheology.villager_fossil");
    }

    /*
    Still From Forge
    //update luminance of block based on the luminance of the item given when it would be in its placed state
    @Override
    public void onClose(Player player) {
        ImplementedInventory.super.onClose(player);
        int luminance = Block.byItem(this.getInventoryContents().getItem()).defaultBlockState().getLightEmission();
        player.level().setBlock(this.getBlockPos(), level.getBlockState(this.getBlockPos()).setValue(VillagerFossilBlock.INVENTORY_LUMINANCE, luminance), 3);
    }
     */


    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer)
    {
        return new FossilInventoryMenu(pContainerId, pPlayerInventory, this);
    }

    public ItemStack getInventoryContents()
    {
        return itemStackHandler.getStackInSlot(0);
    }

    public void setInventory(List<ItemStack> inventory)
    {
        for (int i = 0; i < inventory.size(); i++)
        {
            itemStackHandler.setStackInSlot(i, inventory.get(i));

            if (this.level != null)
            {
                int luminance = Block.byItem(this.getInventoryContents().getItem()).defaultBlockState().getLightEmission();
                this.level.setBlock(this.getBlockPos(), level.getBlockState(this.getBlockPos()).setValue(VillagerFossilBlock.INVENTORY_LUMINANCE, luminance), 3);
            }
        }
    }

    public static <E extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, E e)
    {
        if (level.isClientSide())
        {
            return;
        }
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        if (this.level != null)
        {
            int luminance = Block.byItem(this.getInventoryContents().getItem()).defaultBlockState().getLightEmission();
            this.level.setBlock(this.getBlockPos(), level.getBlockState(this.getBlockPos()).setValue(VillagerFossilBlock.INVENTORY_LUMINANCE, luminance), 3);
        }
    }

    /*Synchronization to the client*/
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag()
    {
        return this.saveWithoutMetadata();
    }
}
