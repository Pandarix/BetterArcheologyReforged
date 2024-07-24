package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.BetterArcheologyConfig;
import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.Pandarix.betterarcheology.util.FleeBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Creeper.class)
public class AddCreeperGoalsMixin
{
    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 2))
    private void injectMethod(GoalSelector instance, int pPriority, Goal pGoal)
    {
        if (BetterArcheologyConfig.fossilEffectsEnabled.get())
        {
            instance.addGoal(pPriority, pGoal); //add what would've been added anyway
            instance.addGoal(pPriority, new FleeBlockGoal<>((Creeper) (Object) this, FleeFromBlockEntity.class, 1.0, 1.2));
        }
    }
}
