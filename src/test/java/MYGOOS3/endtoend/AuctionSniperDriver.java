package MYGOOS3.endtoend;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

import MYGOOS3.Main;
import MYGOOS3.myannotations.TellMe;

import static org.hamcrest.CoreMatchers.equalTo;

public class AuctionSniperDriver extends JFrameDriver {
    @SuppressWarnings("unchecked")
    public AuctionSniperDriver(int timeoutMillis) {
        super(new GesturePerformer(), JFrameDriver.topLevelFrame(
                named(Main.MAIN_WINDOW_NAME),
                showingOnScreen()),
                new AWTEventQueueProber(timeoutMillis, 100));
    }

    @TellMe
    @SuppressWarnings("unchecked")
    public void showsSniperStatus(String statusText) {
        new JLabelDriver(this, named(Main.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
    }
}

