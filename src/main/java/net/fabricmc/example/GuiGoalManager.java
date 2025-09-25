package net.fabricmc.example;

import net.minecraft.src.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;


public class GuiGoalManager extends GuiIngameMenu {
    private GuiScreen parentGuiScreen;

    public GuiGoalManager( GuiScreen parent) {
        this.parentGuiScreen = parent;
    }

    private String ReturnTrueFalse(boolean b){
        if (!b){
            return "§cFalse";
        }
        else return "§aTrue";

    }

    private GuiTextField goalField;
    private GuiButton doneBtn;
    GoalManager manager = new GoalManager();


    @Override
    public void initGui() {
        this.buttonList.add(this.doneBtn = (new GuiButton(0, this.width / 2 - 100, 120,"Done" )));
        this.buttonList.add(this.doneBtn = (new GuiButton(1, this.width / 2 - 100, 100,"Show Goal: " + ReturnTrueFalse(manager.getoverlayConfig()) )));
        this.goalField = (new GuiTextField(this.fontRenderer, this.width / 2 - 150, 60, 300, 20));
        this.goalField.setMaxStringLength(32767);
        this.goalField.setFocused(true);
        this.goalField.setText(" " + manager.GetGoal().trim());


    }
    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == 0) {
                manager.SetGoal(goalField.getText());
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (guiButton.id == 1){
                manager.setoverlayConfig(!manager.getoverlayConfig());
                guiButton.displayString = "Show Goal: " + ReturnTrueFalse(manager.getoverlayConfig());
            }
        }
    }
    @Override
    protected void keyTyped(char c, int i) {
            this.goalField.textboxKeyTyped(c, i);
            if (this.doneBtn.enabled) {
                if (i != 28 && i != 156) {
                    if (i == 1) {
                        this.actionPerformed(this.doneBtn);
                    }
                } else {
                    this.actionPerformed(this.doneBtn);
                }
            }




    }
    @Override
    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        this.goalField.mouseClicked(i, j, k);
    }
    @Override
    public void updateScreen() {
        this.goalField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Set Goal", this.width / 2, 20, 16777215);
        this.goalField.drawTextBox();
        for(int var4 = 0; var4 < this.buttonList.size(); ++var4) {
            GuiButton var5 = (GuiButton)this.buttonList.get(var4);
            var5.drawButton(this.mc, i, j);
        }
    }

}
