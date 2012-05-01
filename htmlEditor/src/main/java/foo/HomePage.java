package foo;

import javax.management.Attribute;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * Homepage
 */
public class HomePage extends WebPage {

	private static final long serialVersionUID = 1L;
	
	private int fontsize = 10;

	// TODO Add any page properties or variables here

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {

		final Label label = new Label("message",
				"If you see this message wicket is properly configured and running");

		Link<String> linkA = new Link<String>("linkA") {

			@Override
			public void onClick() {
				fontsize--;
				label.add(new AttributeModifier("style", "font-size:"+fontsize+"pt;"));
			}
		};
		add(linkA);

		Link<String> linkAA = new Link<String>("linkAA") {

			@Override
			public void onClick() {
				fontsize = 10;
				label.add(new AttributeModifier("style", "font-size:"+fontsize+"pt;"));
			}
		};
		add(linkAA);

		Link<String> linkAAA = new Link<String>("linkAAA") {

			@Override
			public void onClick() {
				fontsize++;
				label.add(new AttributeModifier("style", "font-size:"+fontsize+"pt;"));
			}
		};
		add(linkAAA);

		// Add the simplest type of label

		add(label);
		TextArea field = new TextArea("myTextArea", new Model(""));

		// TinyMCESettings mceSettings = new TinyMCESettings(Theme.advanced);
		// //mceSettings.addCustomSetting("readonly:true");
		// mceSettings.addCustomSetting("theme_advanced_resizing : true");
		// // mceSettings.addCustomSetting("theme_advanced_resizing : true");

		TinyMceBehavior tinyMceBehavior = new TinyMceBehavior();
		field.add(tinyMceBehavior);

		add(field);
	}
}
