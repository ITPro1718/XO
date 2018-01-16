package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class CreateSuchprofil extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  Button deleteButton = new Button("Suchprofil löschen");
  Button safeButton = new Button("Suchprofil speichern");
  VerticalPanel panel = new VerticalPanel();
  ListBox bdayListBox = new ListBox();
  ListBox hcolorListBox = new ListBox();
  ListBox heightListBox = new ListBox();
  ListBox religionListBox = new ListBox();
  ListBox smokerListBox = new ListBox();
  TextBox title = new TextBox();

  @Override
  public void onLoad() {

    Label bdayLabel = new Label("Alter: ");
    Label hcolorLabel = new Label("Haarfarbe: ");
    Label heightLabel = new Label("Größe: ");
    Label smokerLabel = new Label("Raucher: ");
    Label religionLabel = new Label("Religion: ");
    Label titleLabel = new Label("title des Suchprofils");

    /**
     * DropDown in Profil genauso wie bei Suchprofil
     */
    bdayListBox.addItem("20", "20");
    bdayListBox.addItem("30", "30");
    bdayListBox.addItem("40", "40");
    bdayListBox.addItem("50", "50");
    bdayListBox.addItem("60", "60");

    hcolorListBox.addItem("braun", "braun");
    hcolorListBox.addItem("blond", "blond");
    hcolorListBox.addItem("schwarz", "schwarz");
    hcolorListBox.addItem("rot", "rot");
    hcolorListBox.addItem("andere", "andere");

    heightListBox.addItem("1.50", "1.50");
    heightListBox.addItem("1.60", "1.60");
    heightListBox.addItem("1.70", "1.70");
    heightListBox.addItem("1.80", "1.80");
    heightListBox.addItem("1.90", "1.90");
    heightListBox.addItem("2.00", "2.00");

    religionListBox.addItem("katholisch", "katholisch");
    religionListBox.addItem("evangelisch", "evangelisch");
    religionListBox.addItem("moslem", "moslem");
    religionListBox.addItem("buddhist", "buddhist");
    religionListBox.addItem("hindu", "hindu");
    religionListBox.addItem("atheist", "atheist");
    religionListBox.addItem("andere", "andereRel+");

    smokerListBox.addItem("Ja", "YSmoker");
    smokerListBox.addItem("Nein", "NSmoker");

    // Grid erstellen zur besseren Darstellung

    FlexTable SprofilGrid = new FlexTable();
    SprofilGrid.setStyleName("etable");
    this.add(SprofilGrid);

    // Spalte 2
    SprofilGrid.setWidget(0, 0, bdayLabel);
    SprofilGrid.setWidget(0, 1, bdayListBox);

    // Spalte 4
    SprofilGrid.setWidget(1, 0, hcolorLabel);
    SprofilGrid.setWidget(1, 1, hcolorListBox);

    SprofilGrid.setWidget(2, 0, heightLabel);
    SprofilGrid.setWidget(2, 1, heightListBox);

    // Spalte 5
    SprofilGrid.setWidget(0, 2, smokerLabel);
    SprofilGrid.setWidget(0, 3, smokerListBox);

    // Spalte 6
    SprofilGrid.setWidget(1, 2, religionLabel);
    SprofilGrid.setWidget(1, 3, religionListBox);

    // Spalte 7
    SprofilGrid.setWidget(2, 2, titleLabel);
    SprofilGrid.setWidget(2, 3, title);

    this.add(safeButton);

    safeButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        createSuchprofilCallback();
      }

      private void createSuchprofilCallback() {

        Profil source = new Profil();
        source.setId(2);

        Suchprofil search = getSuchprofilWerte();

        partnerAdmin.createSuchprofil(source, search.getTitle(), search.getHaarFarbe(),
            (float) search.getKoerpergroesse(), search.isRaucher(), search.getReligion(),
            search.getAlter(), new AsyncCallback<Void>() {

              @Override
              public void onFailure(Throwable caught) {

            }

              @Override
              public void onSuccess(Void result) {
                ListViewSuchProfil lvsp = new ListViewSuchProfil();

                HTMLPanel splistViewPanel =
                    new HTMLPanel("<h3>" + "Hier können sie ein Suchprofil erstellen!" + "</h3>");
                splistViewPanel.add(lvsp);

                RootPanel.get("contwrap").clear();
                RootPanel.get("contwrap").add(splistViewPanel);
              }

            });

      }

    });

  }

  private Suchprofil getSuchprofilWerte() {

    Suchprofil s = new Suchprofil();
    s.setId(1);
    int alter = Integer.parseInt(bdayListBox.getSelectedValue());
    s.setAlter(alter);
    s.setHaarFarbe(hcolorListBox.getSelectedValue());
    float kgr = Float.parseFloat(heightListBox.getSelectedValue());
    s.setKoerpergroesse(kgr);

    String raucherSelectedValue = smokerListBox.getSelectedValue();
    switch (raucherSelectedValue) {
      case "YSmoker":
        s.setRaucher(true);
        break;
      case "NSmoker":
        s.setRaucher(false);
        break;
      default:
        break;
    }

    s.setReligion(religionListBox.getSelectedValue());
    s.setTitle(title.getValue());

    return s;
  }
}
