package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LootVaseBlock extends Block {
    private static final VoxelShape SHAPE = Block.createCuboidShape(3, 0, 3, 13, 14, 13);
    //advancement id for granting the advancement in onBreak, condition of advancement is "impossible" and needs to be executed here
    Identifier ADVANCEMENT_ID = new Identifier(BetterArcheology.MOD_ID, "loot_vase_broken");
    public LootVaseBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient()) {
            //if the players is not in creative and doesn't silk-touch the vase
            if (!player.isCreative() && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, player.getMainHandStack()) <= 0) {
                //spawn xpOrbs
                Entity xpOrb = new ExperienceOrbEntity(world, pos.getX(), pos.getY(), pos.getZ(), 4);
                world.spawnEntity(xpOrb);
            }
            ServerPlayerHelper.getServerPlayer(player).getAdvancementTracker().grantCriterion(world.getServer().getAdvancementLoader().get(ADVANCEMENT_ID), "criteria"); //gets the AdvancementLoader of the ServerPlayer and grants him the custom criteria called "criteria"
            // will not get executed when advancement is already granted
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, tool, dropExperience);
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, tool) == 0) {
            //4% chance of spawning a silverfish when breaking a loot vase
            if(world.getRandom().nextInt(25) == 1){
                spawnSilverFish(world, pos);
            }
        }
    }

    //Similar code that also gets executed when InfestedBlock is brocke to spawn a SilverFish
    private static void spawnSilverFish(ServerWorld world, BlockPos pos){
        SilverfishEntity silverfishEntity = (SilverfishEntity)EntityType.SILVERFISH.create(world);
        if (silverfishEntity != null) {
            silverfishEntity.refreshPositionAndAngles((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, 0.0F, 0.0F);
            world.spawnEntity(silverfishEntity);
            silverfishEntity.playSpawnEffects();
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
