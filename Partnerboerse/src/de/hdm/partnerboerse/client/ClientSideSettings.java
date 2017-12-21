package de.hdm.partnerboerse.client;

import java.util.logging.Logger;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.partnerboerse.shared.CommonSettings;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.ReportGeneratorServiceAsync;

public class ClientSideSettings extends CommonSettings {

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>PartnerboerseAdministration</code>.
	 */

	private static PartnerboerseAdministrationAsync profilverwaltung = null;

	/**
	 * Remote Service Proxy zur Verbindungsaufnahme mit dem Server-seitgen
	 * Dienst namens <code>ReportGenerator</code>.
	 */
	private static ReportGeneratorServiceAsync reportGeneratorService = null;

	/**
	 * Name des Client-seitigen Loggers.
	 */
	private static final String LOGGER_NAME = "PartnerboerseProjekt Web Client";

	/**
	 * Instanz des Client-seitigen Loggers.
	 */
	private static final Logger log = Logger.getLogger(LOGGER_NAME);

	/**
	 * @return die Logger-Instanz für die Server-Seite
	 */
	public static Logger getLogger() {
		return log;
	}

	/**
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * PartnerboerseAdministrationAsync profilVerwaltung = ClientSideSettings.getProfilVerwaltung()
	 * 
	 * 
	 * @return Eindeutige Instanz des Typs PartnerboerseAdministrationAsync
	 */
	public static PartnerboerseAdministrationAsync getProfilVerwaltung() {
		// Gab es bislang noch keine PartnerboerseAdministration-Instanz,
		// dann...
		if (profilverwaltung == null) {
			// Zunächst instantiieren wir PartnerboerseAdministration
			profilverwaltung = GWT.create(PartnerboerseAdministration.class);
		}

		// So, nun brauchen wir die PartnerboerseAdministration nur noch
		// zurückzugeben.
		return profilverwaltung;
	}

	/**
	 * Der Aufruf dieser Methode erfolgt im Client z.B. durch
	 * ReportGeneratorServiceAsync reportGeneratorService = ClientSideSettings.getReportGenerator()
	 * 
	 * @return Eindeutige Instanz des Typs ReportGeneratorServiceAsync
	 */

	public static ReportGeneratorServiceAsync getReportGenerator() {
		// Gab es bislang noch keine ReportGenerator-Instanz, dann...
		if (reportGeneratorService == null) {
			// ZunÃ¤chst instantiieren wir ReportGenerator
			reportGeneratorService = GWT.create(ReportGenerator.class);

			final AsyncCallback<Void> initReportGeneratorCallback = new AsyncCallback<Void>() {
				@Override
				public void onFailure(Throwable caught) {
					ClientSideSettings.getLogger().severe("Der ReportGenerator konnte nicht initialisiert werden!");
				}

				@Override
				public void onSuccess(Void result) {
					ClientSideSettings.getLogger().info("Der ReportGenerator wurde initialisiert.");
				}
			};

			reportGeneratorService.init(initReportGeneratorCallback);
		}

		// So, nun brauchen wir den ReportGenerator nur noch zurÃ¼ckzugeben.
		return reportGeneratorService;
	}

}
