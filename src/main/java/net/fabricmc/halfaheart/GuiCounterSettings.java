package net.fabricmc.halfaheart;

import net.minecraft.src.*;

public class GuiCounterSettings extends GuiIngameMenu {
    private GuiScreen parentGuiScreen;

    public GuiCounterSettings(GuiScreen guiScreen) {
        this.parentGuiScreen = guiScreen;
    }

    private String ReturnTrueFalse(boolean b){
        if (!b){
            return "§cFalse";
        }
        else return "§aTrue";

    }

    AttemptCounterBase counter = new AttemptCounterBase();
    private String dodeathcount = "Count Up On Death: " +  ReturnTrueFalse(counter.AddAttemptUponDeath);
    private String docreateworldcount = "Count Up On World Creation: " + ReturnTrueFalse(counter.AddAttemptUponNewWorldCreation);
    private String overridedeathcount = "§dOverride Death count";
    private String resetDeathCount = "§cReset Attempt Count";
    private String enableOverlay = "Overlay: " + ReturnTrueFalse(counter.getoverlayConfig());

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 4, resetDeathCount));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 28, overridedeathcount));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 52, dodeathcount));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 76, docreateworldcount));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 148, "Done"));
        this.buttonList.add(new GuiButton(5,this.width / 2 - 100, this.height / 4 + 124,enableOverlay));
        if (counter.getAttemptNumber() == 0) {
            GuiButton button = (GuiButton) this.buttonList.get(0);
            button.enabled = false;
        }
        if (this.mc.theWorld != null){
            if (counter.getAttemptNumber() == 1) {
                GuiButton button = (GuiButton) this.buttonList.get(0);
                button.enabled = false;
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 4) {
            this.mc.displayGuiScreen(this.parentGuiScreen);
        }
        if (button.id == 0) {

            counter.ResetAttemptCounter();
            overridedeathcount = "§dOverride Death count";
            GuiButton button2 = (GuiButton) this.buttonList.get(0);
            GuiButton button3 = (GuiButton) this.buttonList.get(1);
            if (this.mc.theWorld != null){
                counter.SetAttemptOverride(1);
                button2.enabled = false;
                overridedeathcount = "§dOverride Death count";
                button3.displayString = overridedeathcount;
                this.drawCenteredString(this.fontRenderer, "Current Attempt: " + counter.getAttemptNumber(), this.width / 2, 50, 16777215);
            }
            else {
                button2.enabled = false;
              button3.displayString = overridedeathcount;
                this.drawCenteredString(this.fontRenderer, "Current Attempt: " + counter.getAttemptNumber(), this.width / 2, 50, 16777215);
            }
        }
        if (button.id == 2){

            counter.AddAttemptUponDeath = !counter.AddAttemptUponDeath;
            AttemptCounterBase.saveBooleans(counter.AddAttemptUponNewWorldCreation,counter.AddAttemptUponDeath);
            dodeathcount = "Count Up On Death: " + ReturnTrueFalse( counter.AddAttemptUponDeath);
            button.displayString = dodeathcount;


        }
        if (button.id == 3){
            counter.AddAttemptUponNewWorldCreation = !counter.AddAttemptUponNewWorldCreation;
            AttemptCounterBase.saveBooleans(counter.AddAttemptUponNewWorldCreation,counter.AddAttemptUponDeath);
            docreateworldcount = "Count Up On World Creation: " + ReturnTrueFalse( counter.AddAttemptUponNewWorldCreation);
            button.displayString = docreateworldcount;
        }
        if (button.id == 1){
            this.mc.displayGuiScreen(new GuiOverrideCounter(this));
        }
        if (button.id == 5){
            counter.setoverlayConfig(!counter.getoverlayConfig());
            enableOverlay = "Overlay: " + ReturnTrueFalse(counter.getoverlayConfig());
            button.displayString = enableOverlay;
        }

    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        GuiButton button3 = (GuiButton) this.buttonList.get(1);
        button3.displayString ="§dOverride Death count";
        this.drawCenteredString(this.fontRenderer, "Attempt Counter Settings", this.width / 2, 40, 16777215);
        this.drawCenteredString(this.fontRenderer, "Current Attempt Count: " + counter.getAttemptNumber(), this.width / 2, 55, 16777215);
        for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            GuiButton var5 = (GuiButton) this.buttonList.get(var4);
            var5.drawButton(this.mc, par1, par2);
        }
    }





}