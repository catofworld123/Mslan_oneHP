package net.fabricmc.example.mixin;

import net.fabricmc.example.GuiCounterSettings;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiVideoSettings.class)
public class GuiVideoSettingsMixin extends GuiScreen {
    @Inject(method = "initGui", at = @At("TAIL"))
    public void initGui(CallbackInfo ci){

        this.buttonList.add(new GuiSmallButton(300,this.width / 2 + 5  , this.height / 7 + 160, "Attempt Counter Settings"));
    }
    @Inject(method = "actionPerformed",at = @At("TAIL"))
    public void onButtonclicked(GuiButton par1button, CallbackInfo ci){
        if (par1button.id == 300){
            this.mc.displayGuiScreen(new GuiCounterSettings(this));
        }
    }
}
