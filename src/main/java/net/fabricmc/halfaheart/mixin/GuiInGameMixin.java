package net.fabricmc.halfaheart.mixin;

import net.fabricmc.halfaheart.AttemptCounterBase;
import net.fabricmc.halfaheart.GoalManager;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.Minecraft;
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
    public void RenderCounters(float par1, boolean par2, int par3, int par4, CallbackInfo ci){
        if ((this.mc.gameSettings.debugScreenState & 1) == 0) {
            AttemptCounterBase counter = new AttemptCounterBase();
            if (counter.getoverlayConfig()) {
                FontRenderer renderer = this.mc.fontRenderer;
                this.mc.entityRenderer.setupOverlayRendering();
                int y = 1;
                int x = 217;
                String text = "Attempt " + counter.getAttemptNumber();
                renderer.drawString(text, x - 1, y, 0);
                renderer.drawString(text, x + 1, y, 0);
                renderer.drawString(text, x, y + 1, 0);
                renderer.drawString(text, x, y - 1, 0);
                renderer.drawString(text, x, y, 0xFFFFFF);
            }
            GoalManager manager = new GoalManager();
            if (manager.getoverlayConfig()) {
                FontRenderer renderer = this.mc.fontRenderer;
                this.mc.entityRenderer.setupOverlayRendering();
                int y = 1;
                int x = 1;
                String text = "Goal: " + manager.GetGoal().trim();
                renderer.drawString(text, x - 1, y, 0);
                renderer.drawString(text, x + 1, y, 0);
                renderer.drawString(text, x, y + 1, 0);
                renderer.drawString(text, x, y - 1, 0);
                renderer.drawString(text, x, y, 0xFFFFFF);
            }


        }
    }
}
