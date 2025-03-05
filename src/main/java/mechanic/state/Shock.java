package mechanic.state;

import mechanic.interfaces.Switchable;

public class Shock implements Switchable {

    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void switchOn() {
        this.isActive = true;
    }

    @Override
    public void switchOff() {
        this.isActive = false;
    }
}
