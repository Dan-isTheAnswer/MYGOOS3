package MYGOOS3.endtoend;

import static MYGOOS3.endtoend.FakeAuctionServer.XMPP_HOSTNAME;

import MYGOOS3.Main;

public class ApplicationRunner {
    // used in endtoendtest.java later 
    // public static final String SNIPER_XMPP_ID = "sniper@localhost/Auction";

    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PWD = "sniper";
    
    private AuctionSniperDriver driver;


	public void startBiddingIn(FakeAuctionServer auction) {
        Thread thread = new Thread("Test Application") {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PWD, auction.getItemId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus(Main.STATUS_JOINING);

	}

	public void showsSniperHasLostAuction() {
        driver.showsSniperStatus(Main.STATUS_LOST);
	}

    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
