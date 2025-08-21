package com.kujoun.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.kujoun.KujounOptimizer.AnchorOptimizerEnabled;
import static com.kujoun.KujounOptimizer.CrystalOptimizerEnabled;


@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen{
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgets", at = @At("HEAD"))
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.of("Crystal Optimizer: " + (CrystalOptimizerEnabled ? "ON" : "OFF")), (button) -> {
            CrystalOptimizerEnabled = !CrystalOptimizerEnabled;
            button.setMessage(Text.of("Crystal Optimizer: " + (CrystalOptimizerEnabled ? "ON" : "OFF")));
        }).dimensions(5, 5, 150,20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.of("Anchor Optimizer: " + (AnchorOptimizerEnabled ? "ON" : "OFF")), (button) -> {
            AnchorOptimizerEnabled = !AnchorOptimizerEnabled;
            button.setMessage(Text.of("Anchor Optimizer: " + (AnchorOptimizerEnabled ? "ON" : "OFF")));
        }).dimensions(5, 28, 150,20).build());
    }
}
