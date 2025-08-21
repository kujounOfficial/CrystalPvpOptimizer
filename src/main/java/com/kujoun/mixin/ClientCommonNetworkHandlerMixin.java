package com.kujoun.mixin;

import com.kujoun.KujounOptimizer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kujoun.KujounOptimizer.AnchorOptimizerEnabled;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {
    @Shadow
    @Final
    protected MinecraftClient client;

    @Inject(method = "sendPacket", at = @At("HEAD"))
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        if(packet instanceof PlayerInteractBlockC2SPacket) {
            if(AnchorOptimizerEnabled) {
                BlockPos pos = ((PlayerInteractBlockC2SPacket) packet).getBlockHitResult().getBlockPos();
                BlockState blockState = client.world.getBlockState(pos);
                if(blockState.getBlock() == Blocks.RESPAWN_ANCHOR) {
                    KujounOptimizer.LOGGER.info(String.valueOf(blockState.get(RespawnAnchorBlock.CHARGES)));
                    if(blockState.get(RespawnAnchorBlock.CHARGES) >= 5 && (client.player.getMainHandStack().getItem() == Items.GLOWSTONE || client.player.getOffHandStack().getItem() == Items.GLOWSTONE)) {
                        client.world.removeBlock(pos,false);
                    }
                    if(blockState.get(RespawnAnchorBlock.CHARGES) >= 1 && !(client.player.getMainHandStack().getItem() == Items.GLOWSTONE || client.player.getOffHandStack().getItem() == Items.GLOWSTONE)) {
                        client.world.removeBlock(pos,false);
                    }
                }
            }
        }
    }

}