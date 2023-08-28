package net.Pandarix.betterarcheology.block.entity;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.networking.ModMessages;
import net.Pandarix.betterarcheology.networking.packet.ItemStackSyncS2CPacket;
import net.Pandarix.betterarcheology.item.ModItems;
import net.Pandarix.betterarcheology.screen.IdentifyingMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static net.Pandarix.betterarcheology.block.custom.ArchelogyTable.DUSTING;

public class ArcheologyTableBlockEntity extends BlockEntity implements MenuProvider {

    //default inventory size of the archeology table,
    public static final int INV_SIZE = 3;
    //default number of Properties of ArcheologyTableBlockEntity
    public static final int PROPERTY_DELEGATES = 2;

    //count of custom slots inside the table
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final String translationKey = "archeology_table";   //used in getDisplayName using getTranslationKey

    //synchronises Ints between server and client
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 72;

    //loottable for crafting results
    private static final ResourceLocation CRAFTING_LOOT = new ResourceLocation(BetterArcheology.MOD_ID, "identifying_loot");

    public ArcheologyTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ARCHEOLOGY_TABLE.get(), pos, state);

        //getter und setter für PropertyDelegate based on index (progress, maxProgress)
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ArcheologyTableBlockEntity.this.progress;
                    case 1 -> ArcheologyTableBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ArcheologyTableBlockEntity.this.progress = value;
                    case 1 -> ArcheologyTableBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public net.minecraft.network.chat.Component getDisplayName() {
        return Component.translatable(new ResourceLocation(BetterArcheology.MOD_ID, translationKey).toLanguageKey());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER){
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops(){
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new IdentifyingMenu(id, inventory, this, this.data);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public static void tick(Level world, BlockPos blockPos, BlockState blockState, ArcheologyTableBlockEntity entity) {
        //don't do anything clientside
        if (world.isClientSide()) {
            return;
        }

        //if the entity has a recipe inside:
        if (hasRecipe(entity)) {
            //play sound every 10th tick
            if (entity.progress % 10 == 0) {
                world.playSound(null, entity.worldPosition, SoundEvents.BRUSH_GENERIC, SoundSource.BLOCKS, 0.25f, 1f);
            }

            //if the recipe is inside, et self state to dusting
            world.setBlock(blockPos, blockState.setValue(DUSTING, true), 3);
            entity.progress++; //increase progress
            setChanged(world, blockPos, blockState);
            //if crafting progress is bigger or as big as the maxProgress, then craft the Item, else reset the timer
            if (entity.progress >= entity.maxProgress) {
                entity.craftItem();
            }
        } else {
            world.setBlock(blockPos, blockState.setValue(DUSTING, false), 3);
            entity.resetProgress();
            setChanged(world, blockPos, blockState);
        }
    }

    private void craftItem() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for (int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        //if there is an unidentified artifact in the input slot and the output slot is empty:
        if (hasRecipe(this) && this.itemHandler.getStackInSlot(2).isEmpty()) {
            //remove input from slot
            ItemStack stack = this.itemHandler.getStackInSlot(1);
            stack.shrink(1);
            this.itemHandler.setStackInSlot(1, stack);
            ItemStack brush = this.itemHandler.getStackInSlot(0);
            int newDamage = brush.getDamageValue() + 1; //calculate new Damage Value the item would have

            //if the item is supposed to break or the durability is smaller than zero
            if (newDamage > brush.getMaxDamage()) {
                ItemStack brushStack = this.itemHandler.getStackInSlot(0);
                brushStack.shrink(1);
                this.itemHandler.setStackInSlot(0, brushStack);   //remove the Item
                assert this.level != null;
                if (!this.level.isClientSide()) {
                    //play break sound
                    this.level.playSound(null, this.worldPosition, SoundEvents.ITEM_BREAK, SoundSource.BLOCKS, 0.25f, 1f);
                }
            } else {
                //if not, set the damage to the calculated damage above
                brush.setDamageValue(newDamage);
            }

            //if on server
            if (!this.level.isClientSide()) {
                //play sound after crafting
                this.level.playSound(null, this.worldPosition, SoundEvents.BRUSH_SAND_COMPLETED, SoundSource.BLOCKS, 0.5f, 1f);
            }
            this.itemHandler.setStackInSlot(2, generateCraftingLoot(this, this.level)); //set crafted output in the output slot
            this.resetProgress(); //resets crafting progress
            this.setChanged();
        }

    }

    private ItemStack generateCraftingLoot(BlockEntity entity, Level level) {
        //if on server and there is a Server(World)
        if (level != null && !level.isClientSide() && level.getServer() != null) {
            //gets loot-table based on .json loot
            LootTable lootTable = level.getServer().getLootData().getLootTable(CRAFTING_LOOT);
            //parameters for determining loot such as luck, origin and position
            LootParams lootparams = (new LootParams.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.getBlockPos())).withLuck(0).create(LootContextParamSets.CHEST);

            //returns ArrayList of ItemStacks that get generated by the LootTable
            ObjectArrayList<ItemStack> objectArrayList = lootTable.getRandomItems(lootparams, level.random.nextLong());

            //return first LootTable entry as crafting output
            if (objectArrayList.size() == 0) {
                return ItemStack.EMPTY;
            }
            if (objectArrayList.size() == 1) {
                return objectArrayList.get(0);
            }
        }
        return ItemStack.EMPTY;
    }

    private static boolean hasRecipe(ArcheologyTableBlockEntity entity) {
        //recipe type can be configured here
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());

        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasShardInFirstSlot = entity.itemHandler.getStackInSlot(1).is(ModItems.UNIDENTIFIED_ARTIFACT.get());                     //Input
        Item itemInSlot0 = entity.itemHandler.getStackInSlot(0).getItem();
        boolean hasBrushInSlot = itemInSlot0 == ModItems.IRON_BRUSH.get() ||
                itemInSlot0 == ModItems.DIAMOND_BRUSH.get() ||
                itemInSlot0 == Items.BRUSH;
        return hasShardInFirstSlot && hasBrushInSlot && canInsertAmountIntoOutputSlot(entity.itemHandler) && canInsertItemIntoOutputSlot(entity.itemHandler, entity.itemHandler.getStackInSlot(2).getItem());
    }

    public boolean canExtract(int slot, ItemStack stack, Direction side) {
        //only extract on the bottom
        return side == Direction.DOWN && slot == 2;
    }

    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side) {
        //no insertion into the output slot
        if (side == Direction.DOWN) {
            return false;
        }
        //if the top is targeted and the item is a Brush, insert
        if (side == Direction.UP) {
            return slot == 0 && stack.getItem() instanceof BrushItem;
        }
        //for the sides: if it is an unidentified artifact
        return slot == 1 && stack.is(ModItems.UNIDENTIFIED_ARTIFACT.get());
    }

    private static boolean canInsertItemIntoOutputSlot(ItemStackHandler handler, Item output) {
        return handler.getStackInSlot(2).getItem() == output || handler.getStackInSlot(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(ItemStackHandler handler) {
        return handler.getStackInSlot(2).getMaxStackSize() > handler.getStackInSlot(2).getCount();
    }

    public List<ItemStack> getInventoryContents() {
        return Arrays.asList(itemHandler.getStackInSlot(0), itemHandler.getStackInSlot(1), itemHandler.getStackInSlot(2));
    }

    /*
    Forge stuff
    @Override
    public void markDirty() {
        if (world != null && !world.isClient()) {
            PacketByteBuf data = PacketByteBufs.create();
            data.writeInt(inventory.size());
            for (ItemStack itemStack : inventory) {
                data.writeItemStack(itemStack);
            }
            data.writeBlockPos(getPos());

            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, getPos())) {
                ServerPlayNetworking.send(player, ModMessages.ITEM_SYNC, data);
            }
        }

        super.markDirty();
    }
    */
}
