package net.fabricmc.example.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
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
    @Unique
    private boolean createClicked;
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (!MinecraftServer.getIsServer()) {
            this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 120, "Next Attempt"));
        }
        if (this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
            if (this.mc.isIntegratedServerRunning()) {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, "Quit"));
            } else {
                this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96, "deathScreen.leaveServer"));
            }

        } else {
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72, "Keep going"));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96, "Quit"));
            if (this.mc.getSession() == null) {
                ((GuiButton)this.buttonList.get(1)).enabled = false;
            } else {
                long timeOfLastSpawnAssignment = this.mc.thePlayer.getTimeOfLastSpawnAssignment();
                GuiButton respawnButton = (GuiButton)this.buttonList.get(0);
                if (!this.mc.theWorld.getDifficulty().hasHardcoreSpawn()) {
                    respawnButton.displayString = "IDK";
                } else if (this.mc.theWorld.getWorldTime() - timeOfLastSpawnAssignment < 10800L && timeOfLastSpawnAssignment != 0L) {
                    respawnButton.displayString = "Keep going";
                } else {
                    respawnButton.displayString = "I dont like this world";
                }
            }
        }

}
    @Inject(method = "actionPerformed", at = @At("TAIL"), cancellable = true)
    private void Button(GuiButton par1GuiButton, CallbackInfo ci){
        if(par1GuiButton.id == 4){
            this.mc.displayGuiScreen(null);
            if (this.createClicked) {
                return;
            }
            this.createClicked = true;
            long seed = new Random().nextLong();
            WorldSettings settings = new WorldSettings(seed, this.mc.theWorld.getWorldInfo().getGameType(), this.mc.theWorld.getWorldInfo().isMapFeaturesEnabled(), false, this.mc.theWorld.getWorldInfo().getTerrainType(), this.mc.theWorld.getWorldInfo().getDifficulty(),true);
            ISaveFormat var1 = this.mc.getSaveLoader();
            if(this.mc.theWorld.worldInfo.areCommandsAllowed() || this.mc.theWorld.getWorldInfo().getGameType() == EnumGameType.CREATIVE){
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
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawGradientRect(0, 0, this.width, this.height, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        boolean var4 = this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled();
        String var5 = var4 ? "skill issue" : "skill issue";
        this.drawCenteredString(this.fontRenderer, var5, this.width / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();
        if (var4) {
            this.drawCenteredString(this.fontRenderer, I18n.getString("deathScreen.hardcoreInfo"), this.width / 2, 144, 16777215);
        }

        this.drawCenteredString(this.fontRenderer, I18n.getString("deathScreen.score") + ": " + EnumChatFormatting.YELLOW + this.mc.thePlayer.getScore(), this.width / 2, 100, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
