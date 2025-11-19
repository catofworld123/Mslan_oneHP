package btw.community.halfaheart;

import btw.AddonHandler;
import btw.BTWAddon;

public class HalfAHeartAddon extends BTWAddon {
    private static HalfAHeartAddon instance;

    public HalfAHeartAddon() {
        super();
    }

    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " Version " + this.getVersionString() + " Initializing...");
        AddonHandler.logMessage("Hello BTW NoHit World!");
    }
}