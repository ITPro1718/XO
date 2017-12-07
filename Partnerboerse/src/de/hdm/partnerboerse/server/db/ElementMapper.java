package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Element;

public class ElementMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static ElementMapper elementMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ElementMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>ElementMapper</code>-Objekt.
	 */
	public static ElementMapper elementMapper() {
		if (elementMapper == null) {
			elementMapper = new ElementMapper();
		}

		return elementMapper;
	}

	/**
	 * Gibt ein Element-Eintrag aus der Datenbank zurueck
	 * 
	 * @return
	 */
	public Element findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung FROM element" + "WHERE id=" + id);

			if (rs.next()) {
				Element element = new Element();
				element.setId(rs.getInt("id"));
				return element;
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Gibt alle Element-Eintraege zurueck
	 * 
	 * @return
	 */
	public ArrayList<Element> findAll() {
		Connection con = DBConnection.getConnection();

		ArrayList<Element> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, bezeichnung FROM elemente " + "ODER BY id");

			while (rs.next()) {
				Element element = new Element();
				element.setId(rs.getInt("id"));
				element.setBezeichnung(rs.getString("bezeichnung"));

				result.add(element);
			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;

	}

}
