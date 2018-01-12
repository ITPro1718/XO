package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class EditKontaktsperre extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);
  FlexTable kontaktsperreGrid = new FlexTable();

  /**
   * Aufbau Kontaktsperrenliste mit Editierfunktion
   */

  public void onLoad() {
    HTML kontaktsperre = new HTML("<h3>" + "Kontaktsperrenliste" + "</h3>");
    kontaktsperre.addStyleName("kswrap");

    kontaktsperreGrid.setStyleName("kstable");
    this.add(kontaktsperreGrid);

    Button deleteButton = new Button("Profil entsperren");

    // Zeile 1
    kontaktsperreGrid.setText(0, 0, "Vorname");
    kontaktsperreGrid.setText(0, 1, "Nachname");
    kontaktsperreGrid.setText(0, 2, "E-Mail");
    kontaktsperreGrid.setWidget(0, 3, deleteButton);
    
    loadKontaktsperreFromServer();

  }

  private void loadKontaktsperreFromServer() {
    // TODO Auto-generated method stub
    
  }


}
