package de.hdm.partnerboerse.client.editor.eigenschaften;

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

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.ClientValidation;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

/**
 * Diese Klasse lädt alle Eigenschaften dynamisch aus der Datenbank für ein Profil oder Suchprofil in
 * einen Grid (infoGrid).
 * Wenn ein (Such)-Profil Infos hat, werden diese in die Textfelder geladen bzw. die ListBoxen
 * auf den richtigen Wert gesetzt.
 * @author Burghardt, Grundermann
 *
 */
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

  /**
   * Lädt alle Eigenschaften für ein Profil zur Bearbeitung
   * @param p
   * @return
   */
  public Grid loadEigen(Profil p) {

    this.profil = p;

    partnerAdmin.getAllEigenschaften(new EigenschaftenCallback());
    return infoGrid;
  }

  /**
   * Lädt alle Eigenschaften für ein Suchprofil zur Bearbeitung
   * @param sp
   * @return
   */
  public Grid loadEigen(Suchprofil sp) {

    this.suchprofil = sp;


    partnerAdmin.getAllEigenschaften(new EigenschaftenCallback());
    return infoGrid;
  }

  /**
   * Lädt alle Eigenschaften für ein Profil im Lesemodus
   * @param sp
   * @return
   */
  public Grid loadEigenRead(Suchprofil sp) {

    this.suchprofil = sp;

    partnerAdmin.getAllEigenschaften(new EigenschaftenReadCallback());
    return infoGrid;
  }
  
  /**
   * Lädt alle Eigenschaften für ein Suchprofil im Lesemodus
   * @param p
   * @return
   */
  public Grid loadEigenRead(Profil p) {
    
    this.profil = p;
    
    partnerAdmin.getAllEigenschaften(new EigenschaftenReadCallback());
    return infoGrid;
  }

  /**
   * Callback, welcher alle Eigenschaften aus der Datenbank lädt und je nach Eigenschaft 
   * (Freitext oder Auswahl) entweder eine ListBox oder TextBox erstellt und die Infos
   * in die Felder lädt.
   */
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

        /**
         * Wenn die Eigenschaft ein Freitext ist, wird eine TextBox erstellt und hinzugefügt
         */
        if (eg.getIs_a().equals("freitext")) {
          TextBox tb = new TextBox();
          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, tb);
          
          /**
           * Prüft, ob es sich um ein Profil oder Suchprofil handelt und fügt je nach dem
           * den Lösch-Button, Speicher-Button und die gespeicherte Info hinzu.
           */
          if (suchprofil != null) {
            addDeleteButtonforSuchprofil(saveButton, deleteButton, tb, eg);
            addSaveButtonForSuchprofil(saveButton, deleteButton, tb, eg);
            getInfosOfSuchprofil(saveButton, deleteButton, tb, eg);
          } else {
            addDeleteButtonforProfil(saveButton, deleteButton, tb, eg);
            addSaveButtonForProfil(saveButton, deleteButton, tb, eg);
            getInfosOfUser(saveButton, deleteButton ,tb, eg);
          }
          
          // Zählt die Reihe um 1 hoch, um die nächste Eigenschaft in die nächste Reihe zu schreiben
          row++;
        }
        
        /**
         * Wenn die Eigenschaft eine Auswahl ist, wird eine ListBox erstellt und hinzugefügt
         */
        if (eg.getIs_a().equals("auswahl")) {

          final ListBox lb = new ListBox();
          infoGrid.setText(row, column, eg.getErlaeuterung());
          infoGrid.setWidget(row, column + 1, lb);

          /**
           * Lädt die Auswahlen aus der Auswahltabelle in die ListBox
           */
          getAuswahlen(saveButton, deleteButton, lb, eg);
          
          /**
           * Prüft, ob es sich um ein Profil oder Suchprofil handelt und fügt je nach dem
           * den Lösch-Button, Speicher-Button und die gespeicherte Info hinzu.
           */
          if (suchprofil != null) {
            addDeleteButtonforSuchprofil(saveButton, deleteButton, eg);
            addSaveButtonForSuchprofil(saveButton, deleteButton, lb, eg);
          } else {
            addDeleteButtonforProfil(saveButton, deleteButton, eg);
            addSaveButtonForProfil(saveButton, deleteButton, lb, eg);
          }
          
          // Zählt die Reihe um 1 hoch, um die nächste Eigenschaft in die nächste Reihe zu schreiben
          row++;
        }
        
        /**
         * Macht den Grid um eine Reihe größer
         */
        infoGrid.resize(infoGrid.getRowCount() + 1, infoGrid.getColumnCount());
      }

    }

  }

  /**
   * Fügt dem Grid einen saveButton hinzu, welcher onClick die ListBox ausliest und im Callback an den Server zurückgibt
   * @param saveButton
   * @param deleteButton
   * @param lb
   * @param eg
   */
  private void addSaveButtonForProfil(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(lb.getSelectedValue())) {
        	
          /**
           * Callback, welcher eine Info für ein Profil speichert
           */
          partnerAdmin.createInfo(ClientSideSettings.getProfil(), lb.getSelectedValue(), eg,
              new AsyncCallback<Info>() {
  
                @Override
                public void onFailure(Throwable caught) {}
  
                @Override
                public void onSuccess(Info result) {
                	// Setzt den saveButton auf updaten und graut den deleteButton aus
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
  
  /**
   * Fügt dem Grid einen saveButton hinzu, welcher onClick die ListBox ausliest und im Callback an den Server zurückgibt
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eg
   */
  private void addSaveButtonForProfil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {
	  
    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(tb.getText())) {
        	
          /**
           * Callback, welcher eine Info für ein Profil speichert
           */
          partnerAdmin.createInfo(profil, tb.getText(), eg, new AsyncCallback<Info>() {
    
	            @Override
	            public void onFailure(Throwable caught) {
	              Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
	                  + tb.getText() + eg.toString());
	            }
	    
	            @Override
	            public void onSuccess(Info result) {
	            	// Setzt den saveButton auf updaten und graut den deleteButton aus
	            	deleteButton.setEnabled(true);
	            	saveButton.setText("updaten");
	            	Window.alert("Die Info \"" + tb.getText() + "\" wurde zur Eigenschaft \"" + eg.getErlaeuterung() + "\" gespeichert.");
	            }
    
          });
        } else {
          return;
        }
      }

    });
  }

  /**
   * Fügt dem Grid einen saveButton hinzu, welcher onClick die ListBox ausliest und im Callback an den Server zurückgibt
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eg
   */
  private void addSaveButtonForSuchprofil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eg) {

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(tb.getText())) {
        	
        	/**
             * Callback, welcher eine Info für ein Profil speichert
             */
	        partnerAdmin.createInfo(suchprofil, tb.getText(), eg, new AsyncCallback<Info>() {
	  
	            @Override
	            public void onFailure(Throwable caught) {
	              Window.alert("Hier ist der fail " + ClientSideSettings.getProfil().toString()
	                  + tb.getText() + eg.toString());
	            }
	  
	            @Override
	            public void onSuccess(Info result) {
	            	// Setzt den saveButton auf updaten und graut den deleteButton aus
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

  /**
   * Fügt dem Grid einen saveButton hinzu, welcher onClick die ListBox ausliest und im Callback an den Server zurückgibt
   * @param saveButton
   * @param deleteButton
   * @param lb
   * @param eg
   */
  private void addSaveButtonForSuchprofil(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {
	  

    infoGrid.setWidget(row, column + 2, saveButton);
    saveButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        if(cv.isInfoValid(lb.getSelectedValue())) { 
          /**
           * Callback, welcher eine Info für ein Profil speichert
           */
          partnerAdmin.createInfo(suchprofil, lb.getSelectedValue(), eg, new AsyncCallback<Info>() {
  
            @Override
            public void onFailure(Throwable caught) {
              Window.alert("hat nicht geklappt");
            }
  
            @Override
            public void onSuccess(Info result) {
            	// Setzt den saveButton auf updaten und graut den deleteButton aus
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

  /**
   * Fügt dem Grid einen deleteButton hinzu, welcher dann eine Info über einen Callback löscht.
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eg
   */
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
  
  /**
   * Fügt dem Grid einen deleteButton hinzu, welcher dann eine Info über einen Callback löscht.
   * @param saveButton
   * @param deleteButton
   * @param eg
   */
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

  /**
   * Fügt dem Grid einen deleteButton hinzu, welcher dann eine Info über einen Callback löscht.
   * @param saveButton
   * @param deleteButton
   * @param eg
   */
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
  
  /**
   * Fügt dem Grid einen deleteButton hinzu, welcher dann eine Info über einen Callback löscht.
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eg
   */
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

  /**
   * Fügt alle Auswahl-Eigenschaftsobjekte die Einträge der Auswahl Tabelle der übergebenen ListBox hinzu
   * @param saveButton
   * @param deleteButton
   * @param lb
   * @param eg
   */
  private void getAuswahlen(final Button saveButton, final Button deleteButton, final ListBox lb, final Eigenschaft eg) {
	  
	  
	    partnerAdmin.getAuswahl(new AsyncCallback<ArrayList<Auswahl>>() {
	
	      @Override
	      public void onFailure(Throwable caught) {}
	
	      @Override
	      public void onSuccess(ArrayList<Auswahl> result) {
	    	deleteButton.setEnabled(false);
	    	
	    	/**
	    	 * Speichert eine Auswahloption in einer HashMap, um später den Abgleich mit der vom
	    	 * User angelegten Info zu ermöglichen
	    	 */
	    	final HashMap<Integer, String> hm = new HashMap<Integer, String>();
	    	int counter = 0;

	        for (Auswahl a : result) {
	          if (a.getEigenschaftId() == eg.getId()) {
	            lb.addItem(a.getTitel());
	            hm.put(counter, a.getTitel());
		        counter++;
	          }
	          
	        }
	        
	        /**
	         * Prüft, die Infos von einem Suchprofil oder Profil geladen werden müssen.
	         */
	        if (profil != null){
	        	
	        	partnerAdmin.findInfoOf(ClientSideSettings.getProfil(), new AsyncCallback<ArrayList<Info>>(){

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<Info> result) {
						/**
						 * Prüft, ob ein User bereits eine Info zu der zugehörigen AuswahlEigenschaft gespeichert hatte
						 * und setzt ggf. den richtigen Wert
						 */
						for (Info i : result){
							for (int z = 0; z < hm.size(); z++){
								if (i.getText().equals(hm.get(z))){
									lb.setSelectedIndex(z);
									deleteButton.setEnabled(true);
									saveButton.setText("updaten");
									break;
								}						
							}
							/**
							 * Falls keine passende Info gefunden wurde, wird der speichern Button auf
							 * "speichern" gesetzt und der deleteButton ausgegraut.
							 */
							if (saveButton.getText().equals("speichern")){
								deleteButton.setEnabled(false);
							}
						}
					}
		        	
		        });	        	
	        }
	        
	        /**
	         * Prüft, die Infos von einem Suchprofil oder Profil geladen werden müssen.
	         */
	        if (suchprofil != null){
	        	partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>(){

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(ArrayList<Info> result) {
						
						/**
						 * Prüft, ob ein User bereits eine Info zu der zugehörigen AuswahlEigenschaft gespeichert hatte
						 * und setzt ggf. den richtigen Wert
						 */
						for (Info i : result){
							for (int z = 0; z < hm.size(); z++){
							
								if (i.getText().equals(hm.get(z))){
									lb.setSelectedIndex(z);
									saveButton.setText("updaten");
									deleteButton.setEnabled(true);
									break;
								}
							}
							/**
							 * Falls keine passende Info gefunden wurde, wird der speichern Button auf
							 * "speichern" gesetzt und der deleteButton ausgegraut.
							 */
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

  /**
   * Holt sich die Infos eines Users und lädt diese dann in die zugehörige TextBox
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eig
   */
  private void getInfosOfUser(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eig) {
    
	  partnerAdmin.findInfoOf(profil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
    	  Window.alert("hier bin ich gelandet");
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {
       /**
		* Prüft, ob ein User bereits eine Info zu der zugehörigen AuswahlEigenschaft gespeichert hatte
		* und setzt ggf. den richtigen Wert
		*/
        for (Info i : result) {
          if (i.getEigenschaftId() == eig.getId()) {
            tb.setText(i.getText());
            saveButton.setText("updaten");
          }
        }
        /**
         * Wenn keine Info gefunden wurde, wird des Löschen Button ausgegraut
         */
        if (saveButton.getText().equals("speichern")){
        	deleteButton.setEnabled(false);        
        	}
      }

    });
  }

  /**
   * Holt sich die Infos eines Suchprofils und lädt diese dann in die zugehörige TextBox
   * @param saveButton
   * @param deleteButton
   * @param tb
   * @param eig
   */
  private void getInfosOfSuchprofil(final Button saveButton, final Button deleteButton, final TextBox tb, final Eigenschaft eigenschaft) {
    partnerAdmin.findInfoOf(suchprofil, new AsyncCallback<ArrayList<Info>>() {

      @Override
      public void onFailure(Throwable caught) {
      }

      @Override
      public void onSuccess(ArrayList<Info> result) {
       /**
  		* Prüft, ob ein User bereits eine Info zu der zugehörigen AuswahlEigenschaft gespeichert hatte
  		* und setzt ggf. den richtigen Wert
  		*/
        for (Info info : result) {
          if (info.getEigenschaftId() == eigenschaft.getId()) {
            tb.setText(info.getText());
            saveButton.setText("updaten");
          }
        }
        /**
         * Wenn keine Info gefunden wurde, wird des Löschen Button ausgegraut
         */
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

  /**
   * Holt sich alle Infos eines Suchprofils um dann einen ReadGrid zu erstellen
   * @param eigenschaftResult
   */
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

  /**
   * Holt sich alle Infos eines Profils um dann einen ReadGrid zu erstellen
   * @param eigenschaftResult
   */
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
