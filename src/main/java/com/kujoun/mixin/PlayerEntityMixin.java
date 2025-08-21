package com.kujoun.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kujoun.KujounOptimizer.CrystalOptimizerEnabled;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract void tickMovement();

    @Inject(method = "attack", at = @At("RETURN"), cancellable = true)
    private void attack(Entity target, CallbackInfo ci) {
        if(target instanceof EndCrystalEntity) {
            if(CrystalOptimizerEnabled) {
                target.remove(Entity.RemovalReason.KILLED);
                target.emitGameEvent(GameEvent.ENTITY_DIE);
            }
        }
    }
}
