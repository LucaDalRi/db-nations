import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner s1 = new Scanner(System.in);

        System.out.println("Inserisci il tuo filtro");

        String filtro = s1.next();

        String url = "jdbc:mysql://localhost:3306/db-nations";
        String user = "😊";
        String pswd = "😊";


        try (Connection con = DriverManager.getConnection(url, user, pswd)) {

            String query = """
                    SELECT countries.name nome_nazione , countries.country_id id_nazione , regions.name nome_regione , continents.name nome_continente
                    FROM countries
                    INNER JOIN regions ON countries.region_id = regions.region_id
                    INNER JOIN continents ON continents.continent_id = regions.continent_id
                    WHERE countries.name LIKE ?
                    ORDER BY countries.name ASC;
                    """;

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, "%" + filtro + "%");

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        String nomeNazione = rs.getString(1);
                        int idNazione = rs.getInt(2);
                        String nomeRegione = rs.getString(3);
                        String nomeContinente = rs.getString(4);
                        System.out.println("nome nazione - " + nomeNazione + " / ID Nazione - " + idNazione + " / Nome Regione - " + nomeRegione + " / Nome Continente - " + nomeContinente);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        s1.close();
    }
}
