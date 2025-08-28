package net.fabricmc.example.mixin;

import btw.util.hardcorespawn.HardcoreSpawnUtils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.SharedMonsterAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HardcoreSpawnUtils.class)
public class HardcoreSpawnUtilsMixin {
    @Inject(method = "handleHardcoreSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/FoodStats;setFoodLevel(I)V", shift = At.Shift.AFTER))
    private static void handleHardcoreSpawnMixin(MinecraftServer server, EntityPlayerMP oldPlayer, EntityPlayerMP newPlayer, CallbackInfo ci) {
        newPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(1.0f);
        newPlayer.setHealth(1.0f);
    }
}