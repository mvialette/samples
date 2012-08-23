//package com.vialette.maxime.test.wicket6;
//
//import java.io.IOException;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.wicket.request.cycle.RequestCycle;
//import org.jdom.Document;
//import org.jdom.ProcessingInstruction;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;
//
//import com.sun.syndication.feed.synd.SyndFeed;
//import com.sun.syndication.io.FeedException;
//import com.sun.syndication.io.WireFeedOutput;
//
//public class RSSRequestTarget implements IRequestTarget {
//
//    private SyndFeed feed;
//
//    public RSSRequestTarget(SyndFeed feed) {
//        this.feed = feed;
//    }
//
//    public void respond(RequestCycle requestCycle) {
//        requestCycle.getResponse().setCharacterEncoding("UTF-8");
//        requestCycle.getResponse().setContentType("text/xml; charset=UTF-8");
//        try {
//            Writer writer = new OutputStreamWriter(requestCycle.getResponse().getOutputStream());
//            WireFeedOutput feedOutput = new WireFeedOutput();
//            Document doc = feedOutput.outputJDom(this.feed.createWireFeed());
//
//            // create the XSL processing instruction
//            Map<String, String> xsl = new HashMap<String, String>();
//            xsl.put("href", "http://myserver.com/xsl/rss.xsl");
//            xsl.put("type", "text/xsl");
//            xsl.put("media", "screen");
//            ProcessingInstruction pXsl = new ProcessingInstruction("xml-stylesheet", xsl);
//            doc.addContent(0, pXsl);
//            // <pre> // create the CSS processing instruction
//            Map<String, String> css = new HashMap<String, String>();
//            css.put("href", "http://myserver.com/css/rss.css");
//            css.put("type", "text/css");
//            css.put("media", "screen");
//            ProcessingInstruction pCss = new ProcessingInstruction("xml-stylesheet", css);
//            doc.addContent(1, pCss);
//            // </pre>
//            // response
//            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
//            outputter.output(doc, writer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (FeedException e) {
//            e.printStackTrace();
//        }
//        // if (WicketApplication.get().getId() == null) {
//        // WicketApplication.get().bind();
//        // }
//    }
// }