package com.kujoun;

import net.fabricmc.api.ModInitializer;

import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KujounOptimizer implements ModInitializer {
	public static final String MOD_ID = "kujounoptimizer";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean CrystalOptimizerEnabled = false;
	public static boolean AnchorOptimizerEnabled = false;

	@Override
	public void onInitialize() {
	}
}