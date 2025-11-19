package net.fabricmc.halfaheart;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiScreen;

public class GuiOverrideCounter extends GuiIngameMenu {
    private GuiScreen parentGuiScreen;
    AttemptCounterBase counter = new AttemptCounterBase();
    private String attemptcount = "Attempt Count: ";

    public GuiOverrideCounter( GuiCounterSettings parent) {
        this.parentGuiScreen = parent;
    }


        @Override
        public void initGui() {
            this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 4,"§aIncrease by 1" ));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 28, attemptcount + counter.getAttemptNumber() ));
            this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 52,"§cDecrease by 1" ));
            this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 76,"Done" ));

    }
    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0){
            int att = counter.getAttemptNumber();
            counter.SetAttemptOverride(att + 1);
            GuiButton button2 = (GuiButton) this.buttonList.get(1);
            button2.displayString = "Attempt Count: " + counter.getAttemptNumber();
            AttemptCounterBase.saveNumber(att + 1);
        }
        if (button.id == 2){
            int att = counter.getAttemptNumber();
            if (this.mc.theWorld != null) {
                if (counter.getAttemptNumber() > 1) {
                    counter.SetAttemptOverride(att - 1);
                    GuiButton button2 = (GuiButton) this.buttonList.get(1);
                    button2.displayString = "Attempt Count: " + counter.getAttemptNumber();
                    AttemptCounterBase.saveNumber(att - 1);
                }
            }
            else{
                if (counter.getAttemptNumber() > 0) {
                    counter.SetAttemptOverride(att - 1);
                    GuiButton button2 = (GuiButton) this.buttonList.get(1);
                    button2.displayString = "Attempt Count: " + counter.getAttemptNumber();
                    AttemptCounterBase.saveNumber(att - 1);
                }

            }
        }
        if (button.id == 3){
            this.mc.displayGuiScreen(this.parentGuiScreen);


        }

    }
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "§6Manual override", this.width / 2, 55, 16777215);
        for (int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            GuiButton var5 = (GuiButton) this.buttonList.get(var4);
            var5.drawButton(this.mc, par1, par2);
        }
    }
}
