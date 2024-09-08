package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.BetterArcheology;
import net.Pandarix.betterarcheology.util.ServerPlayerHelper;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public class LootVaseBlock extends Block
{
    private static final VoxelShape SHAPE = Block.box(3, 0, 3, 13, 14, 13);
    //advancement id for granting the advancement in onBreak, condition of advancement is "impossible" and needs to be executed here
    ResourceLocation ADVANCEMENT_ID = BetterArcheology.createResource("loot_vase_broken");

    public LootVaseBlock(BlockBehaviour.Properties settings)
    {
        super(settings);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        try
        {
            Optional<Holder.Reference<Enchantment>> SILK_TOUCH = level.registryAccess().asGetterLookup().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH);
            boolean hasSilkTouch = SILK_TOUCH.isPresent() && EnchantmentHelper.getItemEnchantmentLevel(SILK_TOUCH.get(), player.getMainHandItem()) > 0;

            if (!level.isClientSide())
            {
                //if the players is not in creative and doesn't silk-touch the vase
                if (!player.isCreative() && !hasSilkTouch)
                {
                    //spawn xpOrbs
                    Entity xpOrb = new ExperienceOrb(level, pos.getX(), pos.getY(), pos.getZ(), 4);
                    level.addFreshEntity(xpOrb);
                }

                //gets the AdvancementLoader of the ServerPlayer and grants him the
                // custom criteria called "criteria"
                // will not get executed when advancement is already granted
                AdvancementHolder advancement = level.getServer().getAdvancements().get(ADVANCEMENT_ID);
                if (advancement != null)
                {
                    ServerPlayerHelper.getServerPlayer(player).getAdvancements().award(advancement, "criteria");
                }
            }
            if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !hasSilkTouch)
            {
                //4% chance of spawning a silverfish when breaking a loot vase
                if (level.getRandom().nextInt(25) == 1)
                {
                    spawnSilverFish(level, pos);
                }
            }
        } catch (Exception e)
        {
            BetterArcheology.LOGGER.error("Error in breaking LootVase Block! : ", e);
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    //Similar code that also gets executed when InfestedBlock is brocke to spawn a SilverFish
    private static void spawnSilverFish(Level level, BlockPos pos)
    {
        Silverfish silverfishEntity = (Silverfish) EntityType.SILVERFISH.create(level);
        if (silverfishEntity != null)
        {
            silverfishEntity.moveTo((double) pos.getX() + 0.5, (double) pos.getY(), (double) pos.getZ() + 0.5, 0.0F, 0.0F);
            level.addFreshEntity(silverfishEntity);
            silverfishEntity.spawnAnim();
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return SHAPE;
    }
}
