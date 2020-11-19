package src.main;

import java.util.HashMap;
import java.util.Map;

public class Hello {

    public static void main(String[] args) {
        System.out.println("Hello World"); 
        String s = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 1098;"
                    + "Increment: 199; Bidder: sth1;";
        String s1 = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 2000;"
                    + "Increment: 200; Bidder: sth2; df: dfdf ";
        String s2 = "SOLVersion: 1.1; Event: CLOSE;";                    

        processMessge(s);
        processMessge(s1);
        processMessge(s2);

        // Can't split so, in the end, the num of the array is just one. 
        String blank = " ";
        String[] arrBlank = blank.split(":");
        for (String i : arrBlank) {
            System.out.println(i);
        }
    }

    private static void processMessge(String msg) {
        AuctionEvent event = AuctionEvent.from(msg);
        // System.out.println(event.fields);

        String type = event.type();
        if ("CLOSE".equals(type)) {
            System.out.println("Auction Closed");
        } else if ("PRICE".equals(type)) {
            System.out.println(String.format("Auction Price is %d" 
                                    + " and Auction increment is %d ",
                                    event.currentPrice(), event.increment()));
        }
    }


    private static class AuctionEvent {
        public final Map<String, String> fields = new HashMap<>();

        static AuctionEvent from(String messageBody) {
            AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(messageBody)) {
                event.addField(field);
            }
            return event;
        }

        // ArrayIndexOutOfBoundsException
        private void addField(String field) {
            String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        private static String[] fieldsIn(String messageBody) {
            return messageBody.split(";");
        }


        String type() {
            return get("Event");
        }
        
        int currentPrice() {
            return getInt("CurrentPrice");
        }
        
        int increment() {
            return getInt("Increment");
        }

        private String bidder() {
            return get("Bidder");
        }
        
        private String get(String fieldName) {
            return fields.get(fieldName);
        }

        private int getInt(String fieldName) {
            return Integer.parseInt(get(fieldName));
        }


    }
}