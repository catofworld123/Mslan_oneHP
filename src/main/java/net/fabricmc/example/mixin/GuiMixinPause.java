package net.fabricmc.example.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameMenu.class)
public class GuiMixinPause {

    @Redirect(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/Minecraft;displayGuiScreen(Lnet/minecraft/src/GuiScreen;)V", ordinal = 5))
    private void punishPlayer(Minecraft instance, GuiScreen var3){
        instance.displayGuiScreen((GuiScreen)null);
        instance.setIngameFocus();
        instance.sndManager.resumeAllSounds();
        instance.thePlayer.playSound("ambient.weather.thunder", 10000.0F, 0.8F + instance.thePlayer.rand.nextFloat() * 0.2F);

    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;", ordinal = 4))
    private String displayOpenToLan(String string) {
        return "Cheat?";
    }
}