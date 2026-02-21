package net.fabricmc.halfaheart.mixin;

import api.world.difficulty.Difficulty;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiCreateWorld;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin extends GuiScreen {
    @Shadow private boolean lockDifficulty;
    @Shadow private int difficultyID;
    @Shadow private GuiButton buttonAllowCommands;
    @Shadow private GuiButton buttonCustomize;
    @Shadow private GuiButton moreWorldOptions;
    @Shadow private boolean createClicked;
    @Shadow private boolean commandsAllowed;
    @Shadow private String gameMode;
    @Unique boolean onlyOnce = true;

    @Inject(method = "updateButtonText", at = @At("HEAD"))
    private void SwitchHostile(CallbackInfo ci){
        if(this.difficultyID != 4 && onlyOnce){
            this.difficultyID = 4;
            onlyOnce = false;
        }
    }

    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiCreateWorld;updateButtonText()V", ordinal = 8))
    private void LockHostile(GuiButton par1GuiButton, CallbackInfo ci){
        if(this.difficultyID == 3){
            this.difficultyID = 0;
        }
        else if (this.difficultyID == 0){
            this.difficultyID = 4;
        }
        else this.difficultyID = 4;

    }
    @Inject(method = "actionPerformed", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/GuiCreateWorld;updateButtonText()V", ordinal = 9))
    private void alwaysLockedDifficulty(CallbackInfo ci){
        this.lockDifficulty = true;
    }

    @Inject(method = "func_82288_a", at = @At("HEAD"))
    public void disableBottomButton(CallbackInfo ci){
        this.moreWorldOptions.enabled = false;
    }

    @Redirect(method = "updateButtonText", at = @At(value = "INVOKE", target = "Lapi/world/difficulty/Difficulty;getLocalizedName()Ljava/lang/String;"))
    private String SetMyCustomDifficultyName(Difficulty difficulty){
        if(difficulty.index == 4) {
            return "Half a heart";
        }
        if(difficulty.index != 4){
            return "Half a heart (casual)";

        }
        return difficulty.getLocalizedName();
    }



    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 10))
    private String DrawMyText(String string){
        if (this.difficultyID == 4) {
            return "Harder. Stronger. Better. Funner.";
        }
        if (this.difficultyID != 4)
        {
            return "<<We have NM NoHit at home>>";
        }
        return "ERROR";
    }
    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 11))
    private String DrawMyText2ndLine(String string){
        if (this.difficultyID == 4 || this.difficultyID != 1) {
            return "You know the drill.";
        }
        return "ERROR";
    }
    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 12))
    private String DrawMyText3rdLine(String string){
        if (this.difficultyID == 4) {
            return "Good luck.                                                  Mslan was here :)";
        }
        if (this.difficultyID != 4)
        {
            return "";
        }
        return "ERROR";
    }






}