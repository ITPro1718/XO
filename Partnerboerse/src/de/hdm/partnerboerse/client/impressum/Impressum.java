package de.hdm.partnerboerse.client.impressum;

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

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.login.LoginInfo;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class Impressum extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();
		
	
	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	@Override
	public void onLoad() {
		
		
		Grid press = new Grid(21,2);
		press.addStyleName("presstab");
		this.add(press);
		
		press.setText(0, 0, "Angaben gemäß § 5 TMG");		
		press.setText(1, 0, "Anschrift");
		press.setText(1, 1, "Hochschule der Medien");
		press.setText(2, 1, "Nobelstraße 10");
		press.setText(3, 1, "7000 Stuttgart");
		
		press.setText(5, 0, "Vertreten durch:");		
		press.setText(5, 1, "Evelyn Hettmann");
		press.setText(6, 1, "Simon Burghardt");
		press.setText(7, 1, "Maurice Frank");
		press.setText(8, 1, "Siam Gundermann");
		press.setText(9, 1, "Adnan Bicaj");
		press.setText(10, 1, "Sanja Mikulic");
		
		press.setText(12, 0, "Kontakt: ");
		press.setText(12, 1, "ab199@hdm-stuttgart.de ");
		press.setText(13, 1, "0711 123 456");
		
		press.setText(15, 0, "Haftungsausschluss: ");
		press.setText(15, 1, "Die Inhalte unserer Seiten wurden mit größter Sorgfalt erstellt. "
				+ "Für die Richtigkeit, Vollständigkeit und Aktualität der Inhalte können wir jedoch keine Gewähr übernehmen."
				+ " Als Diensteanbieter sind wir gemäß § 7 Abs.1 TMG für eigene Inhalte auf diesen Seiten nach den allgemeinen Gesetzen verantwortlich. "
				+ "Nach §§ 8 bis 10 TMG sind wir als Diensteanbieter jedoch nicht verpflichtet, "
				+ "übermittelte oder gespeicherte fremde Informationen zu überwachen oder nach Umständen zu forschen,"
				+ " die auf eine rechtswidrige Tätigkeit hinweisen. Verpflichtungen zur Entfernung oder Sperrung der Nutzung von Informationen "
				+ "nach den allgemeinen Gesetzen bleiben hiervon unberührt. Eine diesbezügliche Haftung ist jedoch erst ab dem Zeitpunkt "
				+ "der Kenntnis einer konkreten Rechtsverletzung möglich. Bei Bekanntwerden von entsprechenden Rechtsverletzungen werden wir diese Inhalte umgehend entfernen. ");
		
		press.setText(17, 0, "Haftung für Links: ");
		press.setText(17, 1, "Unser Angebot enthält Links zu externen Webseiten Dritter, auf deren Inhalte wir keinen Einfluss haben. "
				+ "Deshalb können wir für diese fremden Inhalte auch keine Gewähr übernehmen. "
				+ "Für die Inhalte der verlinkten Seiten ist stets der jeweilige Anbieter oder Betreiber der Seiten verantwortlich. "
				+ "Die verlinkten Seiten wurden zum Zeitpunkt der Verlinkung auf mögliche Rechtsverstöße überprüft. "
				+ "Rechtswidrige Inhalte waren zum Zeitpunkt der Verlinkung nicht erkennbar. Eine permanente inhaltliche Kontrolle der verlinkten Seiten ist "
				+ "jedoch ohne konkrete Anhaltspunkte einer Rechtsverletzung nicht zumutbar. "
				+ "Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Links umgehend entfernen. ");
		
		press.setText(19, 0, "Urheberrecht: ");
		press.setText(19, 1, "Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten unterliegen dem deutschen Urheberrecht. "
				+ "Die Vervielfältigung, Bearbeitung, Verbreitung und jede Art der Verwertung außerhalb der Grenzen des Urheberrechtes bedürfen der schriftlichen Zustimmung des jeweiligen Autors "
				+ "bzw. Erstellers. Downloads und Kopien dieser Seite sind nur für den privaten, nicht kommerziellen Gebrauch gestattet. "
				+ "Soweit die Inhalte auf dieser Seite nicht vom Betreiber erstellt wurden, werden die Urheberrechte Dritter beachtet. "
				+ "Insbesondere werden Inhalte Dritter als solche gekennzeichnet. Sollten Sie trotzdem auf eine Urheberrechtsverletzung aufmerksam werden, "
				+ "bitten wir um einen entsprechenden Hinweis. Bei Bekanntwerden von Rechtsverletzungen werden wir derartige Inhalte umgehend entfernen. ");
		
		RootPanel.get("contwrap").add(press);

	}
}
