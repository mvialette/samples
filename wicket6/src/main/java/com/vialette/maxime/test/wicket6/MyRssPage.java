package com.vialette.maxime.test.wicket6;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupType;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;

public class MyRssPage extends WebPage {

    public MyRssPage() {
        setStatelessHint(true);
        // SyndFeed feed = new SyndFeedImpl();
        // getRequestCycle().setRequestTarget(new RSSRequestTarget(feed));
    }




    @Override
    protected void onRender() {
        try {
            PrintWriter pw = new PrintWriter(
                    ((HttpServletResponse) getResponse().getContainerResponse()).getOutputStream());
            pw.write("<root><a>aaa</a><b>bbbb</b></root>");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public MarkupType getMarkupType() {
        return new MarkupType("xml", MarkupType.XML_MIME);
    }

    @Override
    public Markup getAssociatedMarkup() {
        return Markup.NO_MARKUP;
    }

    @Override
    protected void configureResponse(WebResponse response) {
        super.configureResponse(response);
        response.setContentType("text/xml");
    }
}
