package de.hdm.partnerboerse.client;

import java.util.HashMap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class EditSuchprofil extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);
  private LoadEigenschaften loadEigenschaften = new LoadEigenschaften();
  Suchprofil suchprofil = ClientSideSettings.getSuchprofil();

  Button deleteButton = new Button("Suchprofil löschen");
  Button safeButton = new Button("Suchprofil speichern");
  CreateWidget cw = new CreateWidget();

  @Override
  public void onLoad() {

    // Grid erstellen zur besseren Darstellung

    FlexTable SprofilGrid = new FlexTable();
    SprofilGrid.setStyleName("etable");
    this.add(SprofilGrid);

    // Spalte 2
    SprofilGrid.setWidget(0, 0, cw.getAlterLabel());
    SprofilGrid.setWidget(0, 1, cw.setAlterListBox());
    setRightWert(cw.getAlterListBox(), suchprofil.getAlter());


    // Spalte 4
    SprofilGrid.setWidget(1, 0, cw.getHcolorLabel());
    SprofilGrid.setWidget(1, 1, cw.setHcolorListBox());
    setRightWert(cw.getHcolorListBox(), suchprofil.getHaarFarbe());

    SprofilGrid.setWidget(2, 0, cw.getSpheightLabel());
    SprofilGrid.setWidget(2, 1, cw.setHeightListBox());
    setRightWert(cw.getHeightListBox(), (int) suchprofil.getKoerpergroesse());

    // Spalte 5
    SprofilGrid.setWidget(0, 2, cw.getSmokerLabel());
    SprofilGrid.setWidget(0, 3, cw.setSmokerListBox());
    String smokerString;
    if (suchprofil.isRaucher() == true)
      smokerString = "Ja";

    else
      smokerString = "Nein";
    setRightWert(cw.getSmokerListBox(), smokerString);

    // Spalte 6
    SprofilGrid.setWidget(1, 2, cw.getReligionLabel());
    SprofilGrid.setWidget(1, 3, cw.setReligionListBox());
    setRightWert(cw.getReligionListBox(), suchprofil.getReligion());

    // Spalte 7
    SprofilGrid.setWidget(2, 2, cw.getTitleLabel());
    SprofilGrid.setWidget(2, 3, cw.getTitleTextBox());
    cw.getTitleTextBox().setValue(suchprofil.getTitle());

    /**
     * Eventuell nicht nur Speichern sondern auch gleichzeitig suche? falls möglich
     */
    this.add(safeButton);

    safeButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        updateSuchprofilCallback();
      }

      /**
       * Daten des Suchprofils werden aus dem Formular ausgelesen
       * und das Suchprofil wird geupdatet in der DB
       */
      private void updateSuchprofilCallback() {

        Suchprofil search = getSuchprofilWerte();
        ClientValidation cv = new ClientValidation();
        
        if(cv.isSuchprofilValid(search)) {

        partnerAdmin.updateSuchprofil(search, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
            // TODO Auto-generated method stub

          }

          @Override
          public void onSuccess(Void result) {
            ListViewSuchProfil lvsp = new ListViewSuchProfil();

            HTMLPanel splistViewPanel =
                new HTMLPanel("<h3>" + "Hier können Sie ein Suchprofil erstellen!" + "</h3>");
            splistViewPanel.add(lvsp);

            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(splistViewPanel);

          }
        });
        } else {
          return;
        }
      }

    });

    Grid infoGrid = loadEigenschaften.loadEigen(suchprofil);
    this.add(infoGrid);

  }

  /**
   * Die Werte aus dem Formular werden in ein Suchprofil gespeichert
   * @return
   */
  private Suchprofil getSuchprofilWerte() {

    // TODO noch unvollständig
    suchprofil.setId(ClientSideSettings.getSuchprofil().getId());
    int alter = Integer.parseInt(cw.getAlterListBox().getSelectedValue());
    suchprofil.setAlter(alter);
    suchprofil.setHaarFarbe(cw.getHcolorListBox().getSelectedValue());
    float kgr = Float.parseFloat(cw.getHeightListBox().getSelectedValue());
    suchprofil.setKoerpergroesse(kgr);

    String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();
    switch (raucherSelectedValue) {
      case "Ja":
        suchprofil.setRaucher(true);
        break;
      case "Nein":
        suchprofil.setRaucher(false);
        break;
    }

    suchprofil.setReligion(cw.getReligionListBox().getSelectedValue());
    suchprofil.setTitle(cw.getTitleTextBox().getValue());

    return suchprofil;
  }

  private void setRightWert(ListBox lb, String string) {

    HashMap<Integer, String> hm = new HashMap<Integer, String>();

    for (int i = 0; i < lb.getItemCount(); i++) {
      hm.put(i, lb.getValue(i));
    }

    for (int o = 0; o < hm.size(); o++) {
      if (hm.get(o).equals(string)) {
        lb.setSelectedIndex(o);
      }
    }
  }

  private void setRightWert(ListBox lb, int string) {

    HashMap<Integer, String> hm = new HashMap<Integer, String>();

    for (int i = 0; i < lb.getItemCount(); i++) {
      hm.put(i, lb.getValue(i));
    }

    for (int o = 0; o < hm.size(); o++) {
      if (hm.get(o).equals(String.valueOf(string))) {
        lb.setSelectedIndex(o);
      }
    }
  }
}
