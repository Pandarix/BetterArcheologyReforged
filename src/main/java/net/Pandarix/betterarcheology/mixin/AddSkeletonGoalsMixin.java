package net.Pandarix.betterarcheology.mixin;

import net.Pandarix.betterarcheology.block.entity.FleeFromBlockEntity;
import net.Pandarix.betterarcheology.block.entity.SkeletonFleeFromBlockEntity;
import net.Pandarix.betterarcheology.util.FleeBlockGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeleton.class)
public class AddSkeletonGoalsMixin {
    @Redirect(method = "registerGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V", ordinal = 3))
    private void injectMethod(GoalSelector instance, int priority, Goal goal){
        instance.addGoal(priority, goal); //add what would've been added anyway
        instance.addGoal(priority, new FleeBlockGoal<>((AbstractSkeleton) (Object) this, SkeletonFleeFromBlockEntity.class, 1.0, 1.2));
    }
}
