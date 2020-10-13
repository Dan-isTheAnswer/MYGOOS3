package MYGOOS3.endtoend;

import org.junit.Test;

public class AuctionSniperEndToEndTest {

    private ApplicationRunner application = new ApplicationRunner();
    private FakeAuctionServer auction = new FakeAuctionServer();

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() {

        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper();
        
        auction.announceClosed();
        application.showsSniperHasLostAuction();

    }

    
}