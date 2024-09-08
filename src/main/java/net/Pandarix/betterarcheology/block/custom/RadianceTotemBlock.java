package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.Pandarix.betterarcheology.block.entity.RadianceTotemBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class RadianceTotemBlock extends FossilBaseWithEntityBlock
{
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty SELECTOR = IntegerProperty.create("selector", 0, 3);

    protected static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.box(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
    protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.box(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

    public RadianceTotemBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(SELECTOR, 0));
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type)
    {
        return createTickerHelper(type, ModBlockEntities.RADIANCE_TOTEM.get(), RadianceTotemBlockEntity::tick);
    }

    @Override
    @NotNull
    @ParametersAreNonnullByDefault
    public RenderShape getRenderShape(BlockState pState)
    {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new RadianceTotemBlockEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.animateTick(pState, pLevel, pPos, pRandom);
        if (pRandom.nextBoolean() && pLevel.isClientSide())
        {
            pLevel.addParticle(ParticleTypes.GLOW,
                    pPos.getCenter().x() + randomDirectionModifier(pRandom, 3),
                    pPos.getCenter().y() - 0.25 + randomDirectionModifier(pRandom, 5),
                    pPos.getCenter().z() + randomDirectionModifier(pRandom, 3), 0, -4, 0);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    @NotNull
    public InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if (!pState.is(this))
        {
            return InteractionResult.PASS;
        }

        // if feature is disabled, notify the user and skip
        if (!BetterArcheologyConfig.radianceTotemEnabled.get() || !BetterArcheologyConfig.totemsEnabled.get())
        {
            if (pLevel.isClientSide())
            {
                pPlayer.displayClientMessage(Component.translatableWithFallback("config.notify.disabled", "This feature has been disabled in the config!"), true);
            }
            return InteractionResult.PASS;
        }
        BlockState newState = pState.cycle(SELECTOR);
        pLevel.setBlock(pPos, newState, 3);

        if (pLevel.isClientSide())
        {
            pPlayer .displayClientMessage(Component.translatable("block.betterarcheology.radiance_totem_message_" + newState.getValue(SELECTOR)).withStyle(ChatFormatting.GREEN), true);
            pLevel.playLocalSound(pPos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.5f, 0.5f, false);
            RandomSource random = pLevel.getRandom();
            for (int i = 0; i <= 10; i++)
            {
                pLevel.addParticle(ParticleTypes.GLOW,
                        pPos.getCenter().x() + randomDirectionModifier(random, 3),
                        pPos.getCenter().y() - 0.25 + randomDirectionModifier(random, 5),
                        pPos.getCenter().z() + randomDirectionModifier(random, 3), 0, -4, 0);
            }
        }
        return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isRandomlyTicking(BlockState pState)
    {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private static float randomDirectionModifier(RandomSource pRandom, int pReduce)
    {
        return ((pRandom.nextFloat() / pReduce) * pRandom.nextIntBetweenInclusive(-1, 1));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltip, TooltipFlag pTooltipFlag)
    {
        super.appendHoverText(pStack, pContext, pTooltip, pTooltipFlag);
        pTooltip.add(Component.translatable("block.betterarcheology.radiance_totem_tooltip").withStyle(ChatFormatting.DARK_GREEN));
    }

    @javax.annotation.Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());

        for (Direction direction : pContext.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.canSurvive(pContext.getLevel(), pContext.getClickedPos()))
                {
                    return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
                }
            }
        }

        return null;
    }

    @ParametersAreNonnullByDefault
    @NotNull
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        return pState.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(HANGING, WATERLOGGED, SELECTOR);
    }

    @ParametersAreNonnullByDefault
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos)
    {
        Direction direction = getConnectedDirection(pState).getOpposite();
        return Block.canSupportCenter(pLevel, pPos.relative(direction), direction.getOpposite());
    }

    protected static Direction getConnectedDirection(BlockState pState)
    {
        return pState.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    @ParametersAreNonnullByDefault
    @NotNull
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos)
    {
        if (pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return getConnectedDirection(pState).getOpposite() == pDirection && !pState.canSurvive(pLevel, pPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @NotNull
    public FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @ParametersAreNonnullByDefault
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType)
    {
        return false;
    }
}
