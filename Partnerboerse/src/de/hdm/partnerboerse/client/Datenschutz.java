package de.hdm.partnerboerse.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class Datenschutz extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();
	
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	@Override
	public void onLoad() {
		
		
		HTML datasec = new HTML("Die Nutzung unserer Webseite ist in der Regel ohne Angabe personenbezogener Daten möglich. "
				+ "Soweit auf unseren Seiten personenbezogene Daten (beispielsweise Name, Anschrift oder eMail-Adressen) erhoben werden, erfolgt dies, "
				+ "soweit möglich, stets auf freiwilliger Basis. Diese Daten werden ohne Ihre ausdrückliche Zustimmung nicht an Dritte weitergegeben. "
				+ "Wir weisen darauf hin, dass die Datenübertragung im Internet (z.B. bei der Kommunikation per E-Mail) Sicherheitslücken aufweisen kann. "
				+ "Ein lückenloser Schutz der Daten vor dem Zugriff durch Dritte ist nicht möglich. "
				+ "Der Nutzung von im Rahmen der Impressumspflicht veröffentlichten Kontaktdaten durch Dritte zur Übersendung von nicht ausdrücklich "
				+ "angeforderter Werbung und Informationsmaterialien wird hiermit ausdrücklich widersprochen. "
				+ "Die Betreiber der Seiten behalten sich ausdrücklich rechtliche Schritte im Falle der unverlangten Zusendung von Werbeinformationen, etwa durch Spam-Mails, vor.");
		datasec.addStyleName("datasec");
		
		RootPanel.get("contwrap").add(datasec);


	}


	
}
