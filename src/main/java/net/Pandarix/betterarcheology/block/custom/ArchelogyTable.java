package net.Pandarix.betterarcheology.block.custom;

import net.Pandarix.betterarcheology.block.entity.ArcheologyTableBlockEntity;
import net.Pandarix.betterarcheology.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class ArchelogyTable extends BaseEntityBlock {
    //indicates if the table is currently "crafting" the identified artifact
    //triggers particle creation
    public static final BooleanProperty DUSTING = BooleanProperty.create("dusting");

    public ArchelogyTable(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(DUSTING, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(DUSTING);
    }

    /* BLOCK ENTITY STUFF */
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    //Drops Items present in the table at the time of destruction
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof ArcheologyTableBlockEntity) {
                ((ArcheologyTableBlockEntity) blockEntity).drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, moved);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof ArcheologyTableBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (ArcheologyTableBlockEntity) entity, pPos);
            } else {
                throw new IllegalStateException("Container Provider Missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    // creates ArcheologyTableBlockEntity for each ArcheologyTable
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ArcheologyTableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ARCHEOLOGY_TABLE.get(), ArcheologyTableBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isClientSide() && pState.getValue(DUSTING)) {
            addDustParticles(pLevel, pPos, pRandom);
        }
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    public void addDustParticles(Level pLevel, BlockPos pos, RandomSource random) {
        if (random.nextBoolean()) {
            return;
        } //create particles half of the time
        int i = random.nextIntBetweenInclusive(1, 3); //number of particles to be created

        BlockParticleOption blockStateParticleEffect = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SAND.defaultBlockState());

        for (int j = 0; j < i; ++j) {
            //centering Block position
            //setting base velocity to 3 and multiplying it with rand double with random sign
            //that way particles can spread in every direction by chance
            pLevel.addParticle(blockStateParticleEffect,
                    pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1),
                    0.0,
                    3.0 * random.nextDouble() * (random.nextBoolean() ? 1 : -1));
        }
    }
};

