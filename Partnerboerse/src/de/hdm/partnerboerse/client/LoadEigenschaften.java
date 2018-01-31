package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import java.util.HashMap;
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

  Grid infoGrid = new Grid(2, 6);
  int row = 1;
  int column = 2;

  private Profil profil;
  private Suchprofil suchprofil;
  private ArrayList<Info> infoList = new ArrayList<>();
  private ArrayList<Eigenschaft> eigenschaftList = new ArrayList<>();
  private ClientValidation cv = new ClientValidation();

  
  public Grid loadEigen(Profil p) {

    this.profil = p;


    partnerAdmin.getAllEigenschaften(new EigenschaftenCallback());
    return infoGrid;
  }

  public Grid loadEigen(Suchprofil sp) {

    this.suchprofil = sp;


    partnerAdmin.getAllEigenschaften(new EigenschaftenCallback());
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

  class EigenschaftenCallback implements AsyncCallback<ArrayList<Eigenschaft>> {

    @Override
    public void onFailure(Throwable caught) {
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
            addDeleteButtonforSuchprofil(tb, eg);
            addSaveButtonForSuchprofil(tb, eg);
            getInfosOfSuchprofil(tb, eg);
          } else {
            addDeleteButtonforProfil(tb, eg);
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

            addDeleteButtonforSuchprofil(eg);
            addSaveButtonForSuchprofil(lb, eg);
          } else {
            addDeleteButtonforProfil(eg);
            addSaveButtonForProfil(lb, eg);
          }
          row++;
        }
        
        infoGrid.resize(infoGrid.getRowCount() + 1, infoGrid.getColumnCount());
      }

    }

  }

  private void addSaveButtonForProfil(final ListBox lb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(lb.getSelectedValue())) {
          partnerAdmin.createInfo(ClientSideSettings.getProfil(), lb.getSelectedValue(), eg,
              new AsyncCallback<Info>() {
  
                @Override
                public void onFailure(Throwable caught) {}
  
                @Override
                public void onSuccess(Info result) {
                  Window.alert("Die Info \"" + result.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
                }
  
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForProfil(final TextBox tb, final Eigenschaft eg) {
	  
	  	  
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(tb.getText())) {
          partnerAdmin.createInfo(profil, tb.getText(), eg, new AsyncCallback<Info>() {
    
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
                  + tb.getText() + eg.toString());
            }
    
            @Override
            public void onSuccess(Info result) {
              Window.alert("Die Info \"" + tb.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
            }
    
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForSuchprofil(final TextBox tb, final Eigenschaft eg) {
    Button safe = new Button("save");
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(tb.getText())) {
          partnerAdmin.createInfo(suchprofil, tb.getText(), eg, new AsyncCallback<Info>() {
  
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
                  + tb.getText() + eg.toString());
            }
  
            @Override
            public void onSuccess(Info result) {
              Window.alert("Die Info \"" + tb.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
            }
  
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForSuchprofil(final ListBox lb, final Eigenschaft eg) {
	  
	  final Button safe = new Button("save");
	  
	  for (Info i : infoList){
		  Window.alert("prüfung");
		  if (i.getEigenschaftId() == eg.getId()){
			  safe.setText("update");		  
			  }
	  }
    infoGrid.setWidget(row, column + 2, safe);
    safe.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(lb.getSelectedValue())) {      
          partnerAdmin.createInfo(suchprofil, lb.getSelectedValue(), eg, new AsyncCallback<Info>() {
  
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("hat nicht geklappt");
            }
  
            @Override
            public void onSuccess(Info result) {
              Window.alert("Die Info \"" + result.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
            }
  
          });
        } 
        else {
          return;
        }
      }

    });
  }

  private void addDeleteButtonforProfil(final TextBox tb, final Eigenschaft eg) {
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
            tb.setText("");
            
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
	          }


	        });
	      }

	    });
	  }
  
  private void addDeleteButtonforSuchprofil(final TextBox tb, final Eigenschaft eg) {
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
            tb.setText("");
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
	    	
	    	final HashMap<Integer, String> hm = new HashMap<Integer, String>();
	    	int counter = 0;
	    	
	        for (Auswahl a : result) {
	          if (a.getEigenschaftId() == eg.getId()) {
	            lb.addItem(a.getTitel());
	            hm.put(counter, a.getTitel());
		        counter++;
	          }
	          
	        }
	        
	        if (profil != null){
	        	
	        	partnerAdmin.findInfoOf(ClientSideSettings.getProfil(), new AsyncCallback<ArrayList<Info>>(){

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<Info> result) {
						for (Info i : result){
							for (int z = 0; z < hm.size(); z++){
								if (i.getText().equals(hm.get(z))){
									lb.setSelectedIndex(z);
								}
							
							}
						}
					}
		        	
		        });	        	
	        }
	        
	        if (suchprofil != null){
	        	partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>(){

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<Info> result) {
						for (Info i : result){
							for (int z = 0; z < hm.size(); z++){
							
								if (i.getText().equals(hm.get(z))){
									lb.setSelectedIndex(z);
								}
							}
						}
					}
	        		
	        	});
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

  private void getInfosOfSuchprofil(final TextBox tb, final Eigenschaft eigenschaft) {
    partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
        // TODO Auto-generated method stub
        
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {
        for (Info info : result) {
          if (info.getEigenschaftId() == eigenschaft.getId()) {
            tb.setText(info.getText());
          }
        }
        
      }});
    
  }
  
  class EigenschaftenReadCallback implements AsyncCallback<ArrayList<Eigenschaft>> {

    @Override
    public void onFailure(Throwable caught) {
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
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {

        infoList = result;
        createReadGridForEigenschaftInfo();

      }
    });
  }
  

  /**
   * Erstellt das Grid für alle Infos mit seinen zugehörigen
   * Eigenschaften
   */
  private void createReadGridForEigenschaftInfo() {

    for (Eigenschaft eigenschaft : eigenschaftList) {
      for (Info info : infoList) {
        if (info.getEigenschaftId() == eigenschaft.getId()) {
          infoGrid.setText(row, column, eigenschaft.getErlaeuterung());
          infoGrid.setText(row, column + 1, info.getText());
          infoGrid.resize(infoGrid.getRowCount() + 1, infoGrid.getColumnCount());
          row++;
        }
      }
    }
  }
}
