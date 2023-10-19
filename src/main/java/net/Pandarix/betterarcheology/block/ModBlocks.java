package net.Pandarix.betterarcheology.block;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.*;
import net.Pandarix.betterarcheology.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    //ITEM ENTRIES-------------------------------------------------------------------------//
    /*
    TODO: Add Items like this:
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.copy(BLocks.BLOCK)), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
    oder
    public static final Block NAME = registerBlock("name", new Block(FabricBlockSettings.of(Material.MATERIAL).otherSettings()), ModItemGroup.BETTER_ARCHEOLOGY_ITEMGROUP);
     */

    // Create a Deferred Register to hold Blocks which will all be registered under the "BetterArcheology" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterArcheology.MOD_ID);

    //-----------SUS VARIANTS-------------//
    public static final RegistryObject<Block> SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", () -> new SusBlock(Blocks.RED_SAND, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_SAND), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));

    public static final RegistryObject<Block> SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_GRAVEL), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    //---------FOSSILIFEROUS BLOCKS-----------//
    public static final RegistryObject<Block> FOSSILIFEROUS_DIRT = registerBlock("fossiliferous_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_GRAVEL), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    public static final RegistryObject<Block> CHISELED_BONE_BLOCK = registerBlock("chiseled_bone_block",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(0.3F).instrument(NoteBlockInstrument.XYLOPHONE).sound(SoundType.BONE_BLOCK)));

    //-------------FOSSILS---------------//
    //Villager
    public static final RegistryObject<Block> VILLAGER_FOSSIL = registerRareBlock("villager_fossil", () -> new VillagerFossilBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel(
            (state) -> {
                return state.getValue(VillagerFossilBlock.INVENTORY_LUMINANCE);
            })));

    public static final RegistryObject<Block> VILLAGER_FOSSIL_HEAD = registerRareBlock("villager_fossil_head", () -> new VillagerFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> VILLAGER_FOSSIL_BODY = registerRareBlock("villager_fossil_body", () -> new VillagerFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Ocelot
    public static final RegistryObject<Block> OCELOT_FOSSIL = registerRareBlock("ocelot_fossil", () -> new OcelotFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> OCELOT_FOSSIL_HEAD = registerRareBlock("ocelot_fossil_head", () -> new OcelotFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> OCELOT_FOSSIL_BODY = registerRareBlock("ocelot_fossil_body", () -> new OcelotFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistryObject<Block> SHEEP_FOSSIL = registerRareBlock("sheep_fossil", () -> new SheepFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> SHEEP_FOSSIL_HEAD = registerRareBlock("sheep_fossil_head", () -> new SheepFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> SHEEP_FOSSIL_BODY = registerRareBlock("sheep_fossil_body", () -> new SheepFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistryObject<Block> CHICKEN_FOSSIL = registerRareBlock("chicken_fossil", () -> new ChickenFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CHICKEN_FOSSIL_HEAD = registerRareBlock("chicken_fossil_head", () -> new ChickenFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CHICKEN_FOSSIL_BODY = registerRareBlock("chicken_fossil_body", () -> new ChickenFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Creeper
    public static final RegistryObject<Block> CREEPER_FOSSIL = registerRareBlock("creeper_fossil", () -> new CreeperFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CREEPER_FOSSIL_HEAD = registerRareBlock("creeper_fossil_head", () -> new CreeperFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CREEPER_FOSSIL_BODY = registerRareBlock("creeper_fossil_body", () -> new CreeperFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //-----------ROTTEN WOOD-------------//
    public static final WoodType ROTTEN_WOOD_TYPE = registerWoodType("rotten_wood");
    public static final BlockSetType ROTTEN_WOOD_BLOCKSET = registerBlockSetType("rotten_wood");

    public static final RegistryObject<Block> ROTTEN_LOG = registerBlock("rotten_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_PLANKS = registerBlock("rotten_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_SLAB = registerBlock("rotten_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_STAIRS = registerBlock("rotten_stairs", () -> new StairBlock(ROTTEN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_FENCE = registerBlock("rotten_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_FENCE_GATE = registerBlock("rotten_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(SoundType.STEM), ROTTEN_WOOD_TYPE));

    public static final RegistryObject<Block> ROTTEN_TRAPDOOR = registerBlock("rotten_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(SoundType.STEM), ROTTEN_WOOD_BLOCKSET));

    public static final RegistryObject<Block> ROTTEN_DOOR = registerBlock("rotten_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(SoundType.STEM), ROTTEN_WOOD_BLOCKSET));

    public static final RegistryObject<Block> ROTTEN_PRESSURE_PLATE = registerBlock("rotten_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE).sound(SoundType.STEM), ROTTEN_WOOD_BLOCKSET));

    //-------------MUD Brick Stuff----------------//
    public static final RegistryObject<Block> INFESTED_MUD_BRICKS = registerBlock("infested_mud_bricks", () -> new InfestedBlock(Blocks.MUD_BRICKS, BlockBehaviour.Properties.copy(Blocks.INFESTED_STONE_BRICKS)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICKS = registerBlock("cracked_mud_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.INFESTED_STONE_BRICKS)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICK_SLAB = registerBlock("cracked_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICK_STAIRS = registerBlock("cracked_mud_brick_stairs", () -> new StairBlock(CRACKED_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB)));

    //-----------------OTHER--------------------//
    public static final RegistryObject<Block> ARCHEOLOGY_TABLE = registerBlock("archeology_table", () -> new ArchelogyTable(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static final RegistryObject<Block> LOOT_VASE = registerBlock("loot_vase", () -> new LootVaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> VASE = registerBlock("vase", () -> new VaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> LOOT_VASE_CREEPER = registerBlock("loot_vase_creeper", () -> new LootVaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> VASE_CREEPER = registerBlock("vase_creeper", () -> new VaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> LOOT_VASE_GREEN = registerBlock("loot_vase_green", () -> new LootVaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> VASE_GREEN = registerBlock("vase_green", () -> new VaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> EVOKER_TRAP = registerBlock("evoker_trap", () -> new EvokerTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(25f)));

    //REGISTERING--------------------------------------------------------------------------//
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, null);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerRareBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, Rarity.UNCOMMON);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, Rarity rarity) {
        return rarity != null ?
                ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(rarity))) :
                ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static WoodType registerWoodType(String id) {
        return WoodType.register(new WoodType("betterarcheology." + id, new BlockSetType(id)));
    }

    private static BlockSetType registerBlockSetType(String id) {
        return BlockSetType.register(new BlockSetType(id));
    }
}