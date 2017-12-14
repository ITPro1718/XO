package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Editor implements EntryPoint {
  private VerticalPanel mainPanel = new VerticalPanel();
  private FlexTable profilFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private TextBox newSymbolTextBox = new TextBox();
  private Button addProfilButton = new Button("Add");
  private Label lastUpdatedLabel = new Label();

  @Override
  public void onModuleLoad() {

    // Create table for Profil
    profilFlexTable.setText(0, 0, "Vorname");
    profilFlexTable.setText(0, 1, "Nachname");
    profilFlexTable.setText(0, 2, "Haarfarbe");
    profilFlexTable.setText(0, 3, "Körpergröße");
    profilFlexTable.setText(0, 4, "Raucher");
    profilFlexTable.setText(0, 5, "Religion");
    profilFlexTable.setText(0, 6, "Geburtsdatum");
    profilFlexTable.setText(0, 7, "Passwort");
    profilFlexTable.setText(0, 8, "Email");
    
    // Assemble Add Stock panel.
    addPanel.add(newSymbolTextBox);
    addPanel.add(addProfilButton);

    // Assemble Main panel.
    mainPanel.add(profilFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);

    // Associate the Main panel with the HTML host page.
    RootPanel.get("profile").add(mainPanel);

    // Move cursor focus to the input box.
    newSymbolTextBox.setFocus(true);

    /*
     * Neues Button Widget erzeugen und eine Beschriftung festlegen.
     */
    final Button findProfilButton = new Button("Finde Profile");
    VerticalPanel navPanel = new VerticalPanel();
    
    RootPanel.get("profile").add(navPanel);
    navPanel.add(findProfilButton);
    
    findProfilButton.addClickHandler(new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {
        // TODO Auto-generated method stub
        
      }
    });

  }



}
