package net.fabricmc.example.mixin;

import net.fabricmc.example.AttemptCounterBase;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ScaledResolution;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class GuiInGameMixin {
    @Shadow @Final private Minecraft mc;

    @Inject(method = "renderGameOverlay", at = @At("HEAD"), cancellable = true)
    public void RenderCounter(float par1, boolean par2, int par3, int par4, CallbackInfo ci){
        if (!this.mc.gameSettings.showDebugInfo) {
            AttemptCounterBase counter = new AttemptCounterBase();
            FontRenderer var8 = this.mc.fontRenderer;
            this.mc.entityRenderer.setupOverlayRendering();
            GL11.glEnable(3042);
            GL11.glPushMatrix();
            int y = 0;
            int x = 217;
            String text = "Attempt " + counter.getAttemptNumber();
            var8.drawString(text, x, y, 300);
            GL11.glPopMatrix();
            GL11.glDisable(3042);
        }
    }
}
