package net.fabricmc.example.mixin;


import net.fabricmc.example.GuiCounterSettings;
import net.fabricmc.example.GuiGoalManager;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.I18n;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class GuiInGameMenuMixin extends GuiScreen {

    @Inject(method = "initGui", at = @At("TAIL"), cancellable = true)
    private void registerMyButton(CallbackInfo ci){
        this.buttonList.add(new GuiButton(67, this.width / 2 - 100, this.height / 4 + 24 - 37,"Set Goal"));
    }
    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void myButtonclic(GuiButton par1GuiButton, CallbackInfo ci){
        if (par1GuiButton.id == 67){
            this.mc.displayGuiScreen(new GuiGoalManager(this));
        }
    }

}
