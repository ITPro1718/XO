package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class LoadEigenschaften extends VerticalPanel {

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  Grid infoGrid = new Grid(10, 6);
  int row = 1;
  int column = 2;

  private Profil profil;
  private Suchprofil suchprofil;
  private ArrayList<Info> infoList = new ArrayList<>();
  private ArrayList<Eigenschaft> eigenschaftList = new ArrayList<>();


  public Grid loadEigen(Profil p) {

    this.profil = p;


    partnerAdmin.getAllEigenschaften(new eigenschaftenCallback());
    return infoGrid;
  }

  public Grid loadEigen(Suchprofil sp) {

    this.suchprofil = sp;


    partnerAdmin.getAllEigenschaften(new eigenschaftenCallback());
    return infoGrid;
  }

  public Grid loadEigenRead(Suchprofil sp) {

    this.suchprofil = sp;

    partnerAdmin.getAllEigenschaften(new EigenschaftenReadCallback());
    return infoGrid;
  }
  
  public Grid loadEigenRead(Profil p) {
    
    this.profil = p;
    
    partnerAdmin.getAllEigenschaften(new EigenschaftenReadCallback());
    return infoGrid;
  }

  class eigenschaftenCallback implements AsyncCallback<ArrayList<Eigenschaft>> {

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(ArrayList<Eigenschaft> result) {
      for (Eigenschaft e : result) {

        final Eigenschaft eg = e;

        if (eg.getIs_a().equals("freitext")) {
          TextBox tb = new TextBox();
          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, tb);
          if (suchprofil != null) {
            addDeleteButtonforSuchprofil(eg);
            addSaveButtonForSuchprofil(tb, eg);
          } else {
            addDeleteButtonforProfil(eg);
            addSaveButtonForProfil(tb, eg);
            getInfosOfUser(tb, eg);
          }
          row++;
        }

        if (eg.getIs_a().equals("auswahl")) {

          final ListBox lb = new ListBox();

          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, lb);

          getAuswahlen(lb, eg);
          if (suchprofil != null) {
            Window.alert(suchprofil.toString());
            addDeleteButtonforSuchprofil(eg);
            addSaveButtonForSuchprofil(lb, eg);
          } else {
            addDeleteButtonforProfil(eg);
            addSaveButtonForProfil(lb, eg);
          }
          row++;
        }
      }

    }

  }

  private void addSaveButtonForProfil(final ListBox lb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        // TODO: Suchprofil ID nehmen, und nicht das gespeicherte Profil
        partnerAdmin.createInfo(ClientSideSettings.getProfil(), lb.getSelectedValue(), eg,
            new AsyncCallback<Info>() {

              @Override
              public void onFailure(Throwable caught) {}

              @Override
              public void onSuccess(Info result) {}

            });
      }

    });
  }

  private void addSaveButtonForProfil(final TextBox tb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.createInfo(profil, tb.getText(), eg, new AsyncCallback<Info>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
                + tb.getText() + eg.toString());
          }

          @Override
          public void onSuccess(Info result) {
            Window.alert("Success");
          }

        });
      }

    });
  }

  private void addSaveButtonForSuchprofil(final TextBox tb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.createInfo(suchprofil, tb.getText(), eg, new AsyncCallback<Info>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
                + tb.getText() + eg.toString());
          }

          @Override
          public void onSuccess(Info result) {
            Window.alert("Success");
          }

        });
      }

    });
  }

  private void addSaveButtonForSuchprofil(final ListBox lb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.createInfo(suchprofil, lb.getSelectedValue(), eg, new AsyncCallback<Info>() {

          @Override
          public void onFailure(Throwable caught) {
            Window.alert("hat nicht geklappt");
          }

          @Override
          public void onSuccess(Info result) {
            Window.alert("hat geklappt");
          }

        });
      }

    });
  }

  private void addDeleteButtonforProfil(final Eigenschaft eg) {
    Button del = new Button("delete");
    infoGrid.setWidget(row, column + 3, del);
    del.addClickHandler(new ClickHandler() {


      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.deleteInfoOfEigenschaft(eg, profil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(Void result) {
            Window.alert("Info erfolgreich gelöscht");
          }


        });
      }

    });
  }

  private void addDeleteButtonforSuchprofil(final Eigenschaft eg) {
    Button del = new Button("delete");
    infoGrid.setWidget(row, column + 3, del);
    del.addClickHandler(new ClickHandler() {


      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.deleteInfoOfEigenschaft(eg, suchprofil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(Void result) {
            Window.alert("Info erfolgreich gelöscht");
          }


        });
      }

    });
  }

  private void getAuswahlen(final ListBox lb, final Eigenschaft eg) {
    partnerAdmin.getAuswahl(new AsyncCallback<ArrayList<Auswahl>>() {

      @Override
      public void onFailure(Throwable caught) {}

      @Override
      public void onSuccess(ArrayList<Auswahl> result) {
        for (Auswahl a : result) {
          if (a.getEigenschaftId() == eg.getId()) {
            lb.addItem(a.getTitel());
          }
        }
      }
    });
  }

  private void getInfosOfUser(final TextBox tb, final Eigenschaft eig) {
    partnerAdmin.findInfoOf(profil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {}

      @Override
      public void onSuccess(ArrayList<Info> result) {
        for (Info i : result) {
          if (i.getEigenschaftId() == eig.getId()) {
            tb.setText(i.getText());
          }
        }
      }

    });
  }

  class EigenschaftenReadCallback implements AsyncCallback<ArrayList<Eigenschaft>> {

    @Override
    public void onFailure(Throwable caught) {
      // TODO Auto-generated method stub

    }

    @Override
    public void onSuccess(ArrayList<Eigenschaft> result) {

      eigenschaftList = result;
      
      if(profil == null && suchprofil != null) {
        getInfosOfSuchprofil(result);
        
      }else if(profil != null && suchprofil == null) {
        getInfosOfProfil(result);
        
      }

    }

  }

  private void getInfosOfSuchprofil(final ArrayList<Eigenschaft> eigenschaftResult) {

    partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onSuccess(ArrayList<Info> infoResult) {

        infoList = infoResult;
        createReadGridForEigenschaftInfo();

      }
    });

  }

  private void getInfosOfProfil(final ArrayList<Eigenschaft> eigenschaftResult) {

    partnerAdmin.findInfoOf(profil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub

      }

      @Override
      public void onSuccess(ArrayList<Info> result) {

        infoList = result;
        createReadGridForEigenschaftInfo();

      }
    });
  }

  private void createReadGridForEigenschaftInfo() {

    for (Eigenschaft eigenschaft : eigenschaftList) {
      infoGrid.setText(row, column, eigenschaft.getErlaeuterung());

      for (Info info : infoList) {
        if (info.getEigenschaftId() == eigenschaft.getId()) {
          infoGrid.setText(row, column + 1, info.getText());
        }
      }
      row++;
    }


  }

}
