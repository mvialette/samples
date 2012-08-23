package com.vialette.maxime.test.wicket6;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.protocol.http.WebApplication;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.vialette.maxime.test.wicket6.Start#main(String[])
 */
public class WicketApplication extends WebApplication
 {

    public static WebSocketEventSystem eventSystem;
    public static String lastMessagePost;
    public static List<String> messagesAyantEtePostes = new ArrayList<String>();

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
	@Override
	public Class<HomePage> getHomePage()
	{
		return HomePage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
        mountPage("rss", MyRssPage.class);

        eventSystem = new WebSocketEventSystem();
		// add your configuration here
	}

    public static WebSocketEventSystem getEventSystem() {
        return eventSystem;
    }

    public void setEventSystem(WebSocketEventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }
}
