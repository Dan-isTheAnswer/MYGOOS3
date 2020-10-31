package MYGOOS3;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {
    private static final int ARG_HOSTNAME = 0; // Who is the auction's host?
    private static final int ARG_USERNAME = 1;
    private static final int ARG_PASSWORD = 2;
    private static final int ARG_ITEM_ID = 3;

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
	public static final Object JOIN_COMMAND_FORMAT = "";
	public static final String BID_COMMAND_FORMAT = "";

    private MainWindow ui;

    private Chat notToBeGCd;

    // where are these used in??
    public static final String STATUS_JOINING = "joining";
    public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_NAME = "sniper status";

    public Main() throws InvocationTargetException, InterruptedException {
        startUserInterface();
    }

    private void startUserInterface() throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> ui = new MainWindow());
    }

    public static void main(String... args) throws InvocationTargetException, InterruptedException, XMPPException {
        Main main = new Main();
        XMPPConnection connection = connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
        main.joinAuction(connection, args[ARG_ITEM_ID]);
    }

    private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);
        return connection;
    }

    private void joinAuction(XMPPConnection connection, String itemId) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(auctionId(itemId, connection), (c, message) -> SwingUtilities.invokeLater(() -> ui.showStatus(Main.STATUS_LOST)));
        // instead of MainWindow.STATUS_LOST;
        notToBeGCd = chat;
        chat.sendMessage(new Message());
    }

    private String auctionId(String itemId, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemId, connection.getServiceName());
    }

}

// createChat is not working ...