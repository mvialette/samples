package com.vialette.maxime.test.wicket_sample;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see com.vialette.maxime.test.wicket_sample.Start#main(String[])
 */
public class WicketApplication extends MyWebApplicationSpring3
 {

    @Override
    protected void init() {
        super.init();

        mountPage("/home", HomePage.class);
        mountPage("/admin", AdminPage.class);
        mountPage("/second", SecondPage.class);
        mountPage("/three", ThreePage.class);
    }
}
