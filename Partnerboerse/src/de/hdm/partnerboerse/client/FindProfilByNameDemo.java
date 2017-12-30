package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class FindProfilByNameDemo extends Showcase {
	/**
	 * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	 * Methode zu erstellen ist.
	 * 
	 * @see Showcase#getHeadlineText()
	 */
	@Override
	protected String getHeadlineText() {
		return "Profil";
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie
	 * ist eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 */
	protected void run() {
		this.append("Profil nach Namen finden");

		PartnerboerseAdministrationAsync profilverwaltung = ClientSideSettings.getProfilVerwaltung();

		profilverwaltung.getProfilByName("Gundermann", new ProfilAusgebenCallback(this));
	}

	class ProfilAusgebenCallback implements AsyncCallback<ArrayList<Profil>> {
		private Showcase showcase = null;

		public ProfilAusgebenCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			this.showcase.append("Fehler bei der Abfrage " + caught.getMessage());

		}

		@Override
		public void onSuccess(ArrayList<Profil> result) {
			if (result != null) {
				for (Profil c : result) {
					if (c != null) {
						// Kundennummer und Name ausgeben
						this.showcase.append("Profil #" + c.getId() + ": " + c.getNachname() + ", " + c.getVorname());
					}

				}
				if (result.size() == 1)
					this.showcase.append("1 Element erhalten");
				else
					this.showcase.append(result.size() + " Elemente erhalten");

			} else {
				ClientSideSettings.getLogger().info("result == null");
			}
		}
	}
}
