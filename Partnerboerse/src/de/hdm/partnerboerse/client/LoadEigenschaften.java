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
        final Button saveButton = new Button("speichern");
        final Button deleteButton = new Button("löschen");

        if (eg.getIs_a().equals("freitext")) {
          TextBox tb = new TextBox();
          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, tb);
          if (suchprofil != null) {
            addDeleteButtonforSuchprofil(saveButton, deleteButton, tb, eg);
            addSaveButtonForSuchprofil(saveButton, deleteButton, tb, eg);
            getInfosOfSuchprofil(saveButton, deleteButton, tb, eg);
          } else {
            addDeleteButtonforProfil(saveButton, deleteButton, tb, eg);
            addSaveButtonForProfil(saveButton, deleteButton, tb, eg);
            getInfosOfUser(saveButton, deleteButton ,tb, eg);
          }
          row++;
        }

        if (eg.getIs_a().equals("auswahl")) {

          final ListBox lb = new ListBox();

          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, lb);

          getAuswahlen(saveButton, deleteButton, lb, eg);
          if (suchprofil != null) {
            addDeleteButtonforSuchprofil(saveButton, deleteButton, eg);
            addSaveButtonForSuchprofil(saveButton, deleteButton, lb, eg);
          } else {
            addDeleteButtonforProfil(saveButton, deleteButton, eg);
            addSaveButtonForProfil(saveButton, deleteButton, lb, eg);
          }
          row++;
        }
        
        infoGrid.resize(infoGrid.getRowCount() + 1, infoGrid.getColumnCount());
      }

    }

  }

  private void addSaveButtonForProfil(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(lb.getSelectedValue())) {
          partnerAdmin.createInfo(ClientSideSettings.getProfil(), lb.getSelectedValue(), eg,
              new AsyncCallback<Info>() {
  
                @Override
                public void onFailure(Throwable caught) {}
  
                @Override
                public void onSuccess(Info result) {
                	saveButton.setText("updaten");
                	deleteButton.setEnabled(true);
                  Window.alert("Die Info \"" + result.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
                }
  
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForProfil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {
	  
    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

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
              deleteButton.setEnabled(true);
              saveButton.setText("update");
              Window.alert("Die Info \"" + tb.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
            }
    
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForSuchprofil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

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
              deleteButton.setEnabled(true);
              saveButton.setText("updaten");
            }
  
          });
        } else {
          return;
        }
      }

    });
  }

  private void addSaveButtonForSuchprofil(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {
	  

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

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
              deleteButton.setEnabled(true);
              saveButton.setText("updaten");
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

  private void addDeleteButtonforProfil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 3, deleteButton);
    deleteButton.addClickHandler(new ClickHandler() {


      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.deleteInfoOfEigenschaft(eg, profil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(Void result) {
            tb.setText("");
            saveButton.setText("speichern");
            deleteButton.setEnabled(false);
          }


        });
      }

    });
  }
  
  private void addDeleteButtonforProfil(final Button saveButton, final Button deleteButton, final Eigenschaft eg) {

	    infoGrid.setWidget(row, column + 3, deleteButton);
	    deleteButton.addClickHandler(new ClickHandler() {


	      @Override
	      public void onClick(ClickEvent event) {

	        partnerAdmin.deleteInfoOfEigenschaft(eg, profil, new AsyncCallback<Void>() {

	          @Override
	          public void onFailure(Throwable caught) {

	          }

	          @Override
	          public void onSuccess(Void result) {
	        	  saveButton.setText("speichern");
	        	  deleteButton.setEnabled(false);
	          }


	        });
	      }

	    });
	  }

  private void addDeleteButtonforSuchprofil(final Button saveButton, final Button deleteButton, final Eigenschaft eg) {

	    infoGrid.setWidget(row, column + 3, deleteButton);
	    deleteButton.addClickHandler(new ClickHandler() {


	      @Override
	      public void onClick(ClickEvent event) {

	        partnerAdmin.deleteInfoOfEigenschaft(eg, suchprofil, new AsyncCallback<Void>() {

	          @Override
	          public void onFailure(Throwable caught) {

	          }

	          @Override
	          public void onSuccess(Void result) {
	        	  deleteButton.setEnabled(false);
	        	  saveButton.setText("speichern");
	          }


	        });
	      }

	    });
	  }
  
  private void addDeleteButtonforSuchprofil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 3, deleteButton);
    deleteButton.addClickHandler(new ClickHandler() {


      @Override
      public void onClick(ClickEvent event) {

        partnerAdmin.deleteInfoOfEigenschaft(eg, suchprofil, new AsyncCallback<Void>() {

          @Override
          public void onFailure(Throwable caught) {
          }

          @Override
          public void onSuccess(Void result) {
            tb.setText("");
            deleteButton.setEnabled(false);
            saveButton.setText("speichern");
          }
        });
      }
    });
  }

  private void getAuswahlen(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {
	  
	  
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
									deleteButton.setEnabled(true);
									saveButton.setText("updaten");
									break;
								}						
							}
							if (saveButton.getText().equals("speichern")){
								deleteButton.setEnabled(false);
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
									saveButton.setText("updaten");
									deleteButton.setEnabled(true);
									break;
								}
							}
							if (saveButton.getText().equals("speichern")){
								deleteButton.setEnabled(false);
							}
						}
					}
	        		
	        	});
	        } 
	        
	      }
	    });
	  }

  private void getInfosOfUser(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eig) {
    
	  partnerAdmin.findInfoOf(profil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
    	  Window.alert("hier bin ich gelandet");
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {
        for (Info i : result) {
          if (i.getEigenschaftId() == eig.getId()) {
            tb.setText(i.getText());
            saveButton.setText("updaten");
          }
        }
        if (saveButton.getText().equals("speichern")){
        	deleteButton.setEnabled(false);        
        	}
      }

    });
  }

  private void getInfosOfSuchprofil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eigenschaft) {
    partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {
        for (Info info : result) {
          if (info.getEigenschaftId() == eigenschaft.getId()) {
            tb.setText(info.getText());
            saveButton.setText("updaten");
          }
        }
        if (saveButton.getText().equals("speichern")){
        	deleteButton.setEnabled(false);        
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
