package MYGOOS3.endtoend;

import static org.hamcrest.Matchers.equalTo;
// import static org.hamcrest.CoreMatchers.equalTo; CoreMatchers is a more abbreviated version of Matchers. 
import static org.junit.Assert.assertThat;

import org.hamcrest.Matcher;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import MYGOOS3.Main;
import MYGOOS3.myannotations.TellMe;

public class FakeAuctionServer {

	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";

	private final String itemId;
	private final XMPPConnection connection;
	private Chat currentChat;

	private final SingleMessageListener messageListener = new SingleMessageListener();

	public FakeAuctionServer(String itemId) {
		this.itemId = itemId;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}

	public void startSellingItem() throws XMPPException {
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemId), AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener((chat, createdLocally) -> {
			currentChat = chat;
			chat.addMessageListener(messageListener);
		});
	}

	public void hasReceivedJoinRequestFromSniper(String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(Main.JOIN_COMMAND_FORMAT));
	}
	
	public void hasReceivedBid(int bid, String sniperId) throws InterruptedException {
		receivesAMessageMatching(sniperId, equalTo(String.format(Main.BID_COMMAND_FORMAT, bid)));
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage("SOLVersion: 1.1; Event: CLOSE;");
	}

	public void stop() {
		connection.disconnect();
	}

	public String getItemId() {
		return itemId;
	}

	
	public void reportPrice(int price, int increment, String bidder)
    throws XMPPException {
        currentChat.sendMessage(
            String.format("SOLVersion: 1.1; Event: PRICE; "
            + "CurrentPrice: %d; Increment: %d; Bidder: %s;",
			price, increment, bidder));

    }

	@TellMe
	private void receivesAMessageMatching(String sniperId, Matcher<? super String> messageMatcher)
			throws InterruptedException {
		messageListener.receivesAMessage(messageMatcher);
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
	}

	public void sendInvalidMEssageContaining(String brokenMessage) throws XMPPException {
		currentChat.sendMessage(brokenMessage);
	}
	

}
