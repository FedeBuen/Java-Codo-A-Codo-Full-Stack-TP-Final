package data;

import static data.Conexion.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Auto;

public class AutoDAO {
    
    private static final String SQL_SELECT ="SELECT * FROM deportivos";
        
    public static List<Auto> seleccionar() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Auto auto = null;
        List<Auto> autos = new ArrayList();
        
        try {
            conn = getConexion();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()) {
                int idAuto = rs.getInt("idAuto");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String nacionalidad = rs.getString("nacionalidad");
                String periodo = rs.getString("periodo");
                String potencia = rs.getString("potencia");
                String aceleracion = rs.getString("aceleracion");
                String velocidad = rs.getString("velocidad");
                
                Blob blob = rs.getBlob("imagen");
                byte[] imagenBytes = blob.getBytes(1, (int)blob.length());
                double precio = rs.getDouble("precio");
                   
                auto = new Auto(idAuto, marca, modelo, nacionalidad, periodo, potencia, aceleracion, velocidad, imagenBytes, precio);
                autos.add(auto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                Logger.getLogger(AutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return autos;
    }
}
