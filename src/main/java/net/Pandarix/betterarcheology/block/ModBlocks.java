package net.Pandarix.betterarcheology.block;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.block.custom.*;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

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
    public static final RegistryObject<Block> SUSPICIOUS_RED_SAND = BLOCKS.register("suspicious_red_sand", () -> new SusBlock(Blocks.RED_SAND, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_SAND), SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED));

    public static final RegistryObject<Block> SUSPICIOUS_DIRT = BLOCKS.register("suspicious_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_GRAVEL), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    //---------FOSSILIFEROUS BLOCKS-----------//
    public static final RegistryObject<Block> FOSSILIFEROUS_DIRT = BLOCKS.register("fossiliferous_dirt", () -> new SusBlock(Blocks.DIRT, BlockBehaviour.Properties.copy(Blocks.SUSPICIOUS_GRAVEL), SoundEvents.BRUSH_GRAVEL, SoundEvents.BRUSH_GRAVEL_COMPLETED));

    //-------------FOSSILS---------------//
    //Villager
    public static final RegistryObject<Block> VILLAGER_FOSSIL = BLOCKS.register("villager_fossil", () -> new VillagerFossilBlock(BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).lightLevel(
            (state) -> {
        return state.getValue(VillagerFossilBlock.INVENTORY_LUMINANCE);
    })));

    public static final RegistryObject<Block> VILLAGER_FOSSIL_HEAD = BLOCKS.register("villager_fossil_head", () -> new VillagerFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> VILLAGER_FOSSIL_BODY = BLOCKS.register("villager_fossil_body", () -> new VillagerFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Ocelot
    public static final RegistryObject<Block> OCELOT_FOSSIL = BLOCKS.register("ocelot_fossil", () -> new OcelotFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> OCELOT_FOSSIL_HEAD = BLOCKS.register("ocelot_fossil_head", () -> new OcelotFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> OCELOT_FOSSIL_BODY = BLOCKS.register("ocelot_fossil_body", () -> new OcelotFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistryObject<Block> SHEEP_FOSSIL = BLOCKS.register("sheep_fossil", () -> new SheepFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> SHEEP_FOSSIL_HEAD = BLOCKS.register("sheep_fossil_head", () -> new SheepFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> SHEEP_FOSSIL_BODY = BLOCKS.register("sheep_fossil_body", () -> new SheepFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Sheep
    public static final RegistryObject<Block> CHICKEN_FOSSIL = BLOCKS.register("chicken_fossil", () -> new ChickenFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CHICKEN_FOSSIL_HEAD = BLOCKS.register("chicken_fossil_head", () -> new ChickenFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CHICKEN_FOSSIL_BODY = BLOCKS.register("chicken_fossil_body", () -> new ChickenFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //Creeper
    public static final RegistryObject<Block> CREEPER_FOSSIL = BLOCKS.register("creeper_fossil", () -> new CreeperFossilBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CREEPER_FOSSIL_HEAD = BLOCKS.register("creeper_fossil_head", () -> new CreeperFossilHeadBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    public static final RegistryObject<Block> CREEPER_FOSSIL_BODY = BLOCKS.register("creeper_fossil_body", () -> new CreeperFossilBodyBlock(BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL).sound(SoundType.BONE_BLOCK)));

    //-----------ROTTEN WOOD-------------//
    public static final WoodType ROTTEN_WOOD_TYPE = registerWoodType("rotten_wood");
    public static final BlockSetType ROTTEN_WOOD_BLOCKSET = registerBlockSetType("rotten_wood");

    public static final RegistryObject<Block> ROTTEN_LOG = BLOCKS.register("rotten_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_PLANKS = BLOCKS.register("rotten_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_SLAB = BLOCKS.register("rotten_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_STAIRS = BLOCKS.register("rotten_stairs", () -> new StairBlock(ROTTEN_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_FENCE = BLOCKS.register("rotten_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE).sound(SoundType.STEM)));

    public static final RegistryObject<Block> ROTTEN_FENCE_GATE = BLOCKS.register("rotten_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE).sound(SoundType.STEM), ROTTEN_WOOD_TYPE));

    public static final RegistryObject<Block> ROTTEN_TRAPDOOR = BLOCKS.register("rotten_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR).sound(SoundType.STEM), ROTTEN_WOOD_BLOCKSET));

    public static final RegistryObject<Block> ROTTEN_DOOR = BLOCKS.register("rotten_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR).sound(SoundType.STEM), ROTTEN_WOOD_BLOCKSET));

    //-------------MUD Brick Stuff----------------//
    public static final RegistryObject<Block> INFESTED_MUD_BRICKS = BLOCKS.register("infested_mud_bricks", () -> new InfestedMudBricks(Blocks.MUD_BRICKS, BlockBehaviour.Properties.copy(Blocks.INFESTED_STONE_BRICKS)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICKS = BLOCKS.register("cracked_mud_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.INFESTED_STONE_BRICKS)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICK_SLAB = BLOCKS.register("cracked_mud_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB)));

    public static final RegistryObject<Block> CRACKED_MUD_BRICK_STAIRS = BLOCKS.register("cracked_mud_brick_stairs", () -> new StairBlock(CRACKED_MUD_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB)));

    //-----------------OTHER--------------------//
    public static final RegistryObject<Block> ARCHEOLOGY_TABLE = BLOCKS.register("archeology_table", () -> new ArchelogyTable(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static final RegistryObject<Block> LOOT_VASE = BLOCKS.register("loot_vase", () -> new LootVaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> VASE = BLOCKS.register("vase", () -> new VaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> LOOT_VASE_CREEPER = BLOCKS.register("loot_vase_creeper", () -> new LootVaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> VASE_CREEPER = BLOCKS.register("vase_creeper", () -> new VaseBlock(BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).sound(SoundType.DECORATED_POT)));

    public static final RegistryObject<Block> EVOKER_TRAP = BLOCKS.register("evoker_trap", () -> new EvokerTrapBlock(BlockBehaviour.Properties.copy(Blocks.STONE).strength(25f)));

    //REGISTERING--------------------------------------------------------------------------//
    private static WoodType registerWoodType(String id) {
        return WoodType.register(new WoodType("betterarcheology." + id, new BlockSetType(id)));
    }

    private static BlockSetType registerBlockSetType(String id) {
        return BlockSetType.register(new BlockSetType(id));
    }

    //LOGGER-----------------------------------------------------------------------------//
    public static void registerModBlocks() {
        BetterArcheology.LOGGER.info("Registering Blocks from " + BetterArcheology.MOD_ID);
    }
}