package net.fabricmc.halfaheart.mixin;

import net.fabricmc.halfaheart.AttemptCounterBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(GuiGameOver.class)
public class GuiMixinDeath extends GuiScreen {
    @Shadow private int cooldownTimer;
    @Unique
    private boolean createClicked;
    @Inject(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/src/GuiGameOver;buttonList:Ljava/util/List;",ordinal = 4,shift = At.Shift.AFTER))
    public void initGui(CallbackInfo ci) {
        if (!MinecraftServer.getIsServer()) {
            this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120, "Get me a new World"));
        }
}

    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 0))
    private String DrawMyTextDeath(String string) {
        return "Quit";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 1))
    private String DrawMyTextDeath2(String string) {
        return "Quit";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 2))
    private String DrawMyTextDeath3(String string) {
        return "Keep Going";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 3))
    private String DrawMyTextDeath4(String string) {
        return "Quit";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/StatCollector;translateToLocal(Ljava/lang/String;)Ljava/lang/String;",ordinal = 0))
    private String DrawMyTextDeath5(String string) {
        return "Keep Going";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/StatCollector;translateToLocal(Ljava/lang/String;)Ljava/lang/String;",ordinal = 1))
    private String DrawMyTextDeath6(String string) {
        return "Keep Going";
    }
    @Redirect(method = "initGui", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/StatCollector;translateToLocal(Ljava/lang/String;)Ljava/lang/String;",ordinal = 2))
    private String DrawMyTextDeath7(String string) {
        return "Keep Going";
    }


    @Inject(method = "actionPerformed", at = @At("TAIL"), cancellable = true)
    private void ButtonClicked(GuiButton par1GuiButton, CallbackInfo ci){
        if (this.mc.thePlayer != null) {
        AttemptCounterBase counterBase = new AttemptCounterBase();
        if(par1GuiButton.id == 1){
            if (this.mc.theWorld.getWorldInfo().getGameType() != EnumGameType.CREATIVE & !this.mc.theWorld.getWorldInfo().areCommandsAllowed()) {
                if (counterBase.AddAttemptUponDeath) {
                    counterBase.AddAttempt();
                }
            }
        }
        if(par1GuiButton.id == 4) {
            GuiButton guiButton0 = (GuiButton)this.buttonList.get(0);
            GuiButton guiButton1 = (GuiButton)this.buttonList.get(1);
            GuiButton guiButton2 = (GuiButton)this.buttonList.get(2);
            guiButton0.enabled = false;
            guiButton1.enabled = false;
            guiButton2.enabled = false;
            par1GuiButton.enabled = false;
            if (counterBase.AddAttemptUponDeath) {
                if (this.mc.theWorld.getWorldInfo().getGameType() != EnumGameType.CREATIVE & !this.mc.theWorld.getWorldInfo().areCommandsAllowed()) {
                    counterBase.AddAttempt();
                }
            } else if (counterBase.AddAttemptUponNewWorldCreation) {
                if (this.mc.theWorld.getWorldInfo().getGameType() != EnumGameType.CREATIVE & !this.mc.theWorld.getWorldInfo().areCommandsAllowed()) {
                    counterBase.AddAttempt();
                }
            }
            this.mc.displayGuiScreen(null);
            if (this.createClicked) {
                return;
            }
            this.createClicked = true;
            long seed = new Random().nextLong();
            WorldSettings settings = new WorldSettings(seed, this.mc.theWorld.getWorldInfo().getGameType(), this.mc.theWorld.getWorldInfo().isMapFeaturesEnabled(), false, this.mc.theWorld.getWorldInfo().getTerrainType(), this.mc.theWorld.getWorldInfo().getDifficulty(), true);
            ISaveFormat var1 = this.mc.getSaveLoader();
            if (this.mc.theWorld.worldInfo.areCommandsAllowed() || this.mc.theWorld.getWorldInfo().getGameType() == EnumGameType.CREATIVE) {
                settings.enableCommands();
            }
            List saveList = null;
            try {
                saveList = var1.getSaveList();
            } catch (AnvilConverterException ignored) {
            }
            saveList.sort(null);
            String mostRecentWorld = updateWorldName(((SaveFormatComparator) saveList.get(0)).getDisplayName());
            try {
                if (MinecraftServer.getServer() != null) {
                    MinecraftServer.getServer().stopServer();
                    this.mc.loadWorld(null);
                }

                this.mc.launchIntegratedServer(this.CreateNaming(mostRecentWorld), mostRecentWorld.trim(), settings);
                this.mc.statFileWriter.readStat(StatList.createWorldStat, 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        }
    }
    @Unique
    private String CreateNaming(String Name) {
        String namenew = Name.trim();
        for (char var4 : ChatAllowedCharacters.allowedCharactersArray) {
            namenew = namenew.replace(var4, '_');
        }
        if (MathHelper.stringNullOrLengthZero(namenew)) {
            namenew = "World";
        }
        namenew = GuiCreateWorld.func_73913_a(this.mc.getSaveLoader(), namenew);
        return namenew;
    }

    @Unique
    private static String updateWorldName(String input) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        long largestvalue = Long.MIN_VALUE;
        int start = -1, end = -1;
        while (matcher.find()) {
            long number = Long.parseLong(matcher.group());
            if (number > largestvalue) {
                largestvalue = number;
                start = matcher.start();
                end = matcher.end();
            }
        }
        if (largestvalue == Long.MIN_VALUE) {
            return input;
        }
        long incrementedValue = largestvalue + (Long.signum(largestvalue));
        StringBuilder updatedString = new StringBuilder(input);
        updatedString.replace(start, end, String.valueOf(incrementedValue));
        return updatedString.toString();
    }
    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 0))
    private String DrawMyText(String string) {
        return "skill issue";
    }
    @Redirect(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/I18n;getString(Ljava/lang/String;)Ljava/lang/String;",ordinal = 1))
    private String DrawMyText2(String string) {
        return "skill issue";
    }
}
