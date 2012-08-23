package com.vialette.maxime.test.wicket6;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class CreateRSSFeedUsingRome {
    private static final DateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            String feedType = "rss_2.0";
            String fileName = "feed.xml";

            SyndFeed feed = new SyndFeedImpl();
            feed.setFeedType(feedType);

            feed.setTitle("Real's HowTo");
            feed.setLink("http://www.rgagnon.com/howto.html");
            feed.setDescription("Useful Java code examples");

            List entries = new ArrayList();
            SyndEntry entry;
            SyndContent description;

            entry = new SyndEntryImpl();
            entry.setTitle("Real's HowTo");
            entry.setLink("http://www.rgagnon.com/java-details/");
            entry.setPublishedDate(DATE_PARSER.parse("2004-06-08"));
            description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue("Cool java snippet!");
            entry.setDescription(description);
            entries.add(entry);

            feed.setEntries(entries);

            // Writer writer = new FileWriter(fileName);
            // SyndFeedOutput output = new SyndFeedOutput();
            // output.output(feed, writer);
            // writer.close();
            SyndFeedOutput output = new SyndFeedOutput();
            System.out.println(output.outputString(feed));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}