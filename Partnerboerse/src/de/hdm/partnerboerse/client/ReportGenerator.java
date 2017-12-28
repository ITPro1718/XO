package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReportGenerator implements EntryPoint{

	@Override
	public void onModuleLoad() {

	      DockPanel dockPanel = new DockPanel();
	      dockPanel.setStyleName("dockpanel");
	      dockPanel.setSpacing(4);
	      dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

	      // Add text all around
	      dockPanel.add(new HTML("This is the first north component."), 
	      DockPanel.NORTH);
	      dockPanel.add(new HTML("This is the first south component."), 
	      DockPanel.SOUTH);
	      dockPanel.add(new HTML("This is the east component."), 
	      DockPanel.EAST);
	      dockPanel.add(new HTML("This is the west component."), 
	      DockPanel.WEST);
	      dockPanel.add(new HTML("This is the second north component."), 
	      DockPanel.NORTH);
	      dockPanel.add(new HTML("This is the second south component"), 
	      DockPanel.SOUTH);

	      // Add scrollable text in the center
	      HTML contents = new HTML("This is a ScrollPanel contained"
	         +" at the center of a DockPanel. "
	         +" small in order to see the nifty scroll bars!");
	      ScrollPanel scroller = new ScrollPanel(contents);
	      scroller.setSize("400px", "100px");
	      dockPanel.add(scroller, DockPanel.CENTER);


	      VerticalPanel vPanel = new VerticalPanel();
	      vPanel.add(dockPanel);

	      // Add the widgets to the root panel.
	      RootPanel.get("mwrapREP").add(vPanel);
		
	}
}
