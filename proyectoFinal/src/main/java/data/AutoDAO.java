package data;

import static data.Conexion.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Auto;

public class AutoDAO {

    private static final String SQL_SELECT = "SELECT * FROM deportivos";
    private static final String SQL_SELECT_ID = "SELECT * FROM deportivos WHERE idAuto = ?";
    private static final String SQL_INSERT = "INSERT INTO deportivos (marca, modelo, nacionalidad, periodo, potencia, aceleracion, velocidad, imagen, precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM deportivos WHERE idAuto = ?";
    private static final String SQL_UPDATE = "UPDATE deportivos SET marca = ?, modelo = ?, nacionalidad = ?, periodo = ?, potencia = ?, aceleracion = ?, velocidad = ?, precio = ? WHERE idAuto = ?";
    private static final String SQL_UPDATE_IMG = "UPDATE deportivos SET imagen = ? WHERE idAuto = ?";

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
            while (rs.next()) {
                int idAuto = rs.getInt("idAuto");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String nacionalidad = rs.getString("nacionalidad");
                String periodo = rs.getString("periodo");
                String potencia = rs.getString("potencia");
                String aceleracion = rs.getString("aceleracion");
                String velocidad = rs.getString("velocidad");

                Blob blob = rs.getBlob("imagen");
                byte[] imagenBytes = blob.getBytes(1, (int) blob.length());
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

    public static Auto seleccionarId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Auto auto = null;

        try {
            conn = getConexion();
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                int idAuto = rs.getInt("idAuto");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                String nacionalidad = rs.getString("nacionalidad");
                String periodo = rs.getString("periodo");
                String potencia = rs.getString("potencia");
                String aceleracion = rs.getString("aceleracion");
                String velocidad = rs.getString("velocidad");

                Blob blob = rs.getBlob("imagen");
                byte[] imagenBytes = blob.getBytes(1, (int) blob.length());
                double precio = rs.getDouble("precio");

                auto = new Auto(idAuto, marca, modelo, nacionalidad, periodo, potencia, aceleracion, velocidad, imagenBytes, precio);
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
        return auto;
    }

    public static int insertar(Auto auto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConexion();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, auto.getMarca());
            stmt.setString(2, auto.getModelo());
            stmt.setString(3, auto.getNacionalidad());
            stmt.setString(4, auto.getPeriodo());
            stmt.setString(5, auto.getPotencia());
            stmt.setString(6, auto.getAceleracion());
            stmt.setString(7, auto.getVelocidad());

            Blob imagenBlob = conn.createBlob();
            imagenBlob.setBytes(1, auto.getImagen());
            stmt.setBlob(8, imagenBlob);

            stmt.setDouble(9, auto.getPrecio());

            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }

    public static int eliminar(int auto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConexion();

            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, auto);

            registros = stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }

    public static int modificar(Auto auto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConexion();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, auto.getMarca());
            stmt.setString(2, auto.getModelo());
            stmt.setString(3, auto.getNacionalidad());
            stmt.setString(4, auto.getPeriodo());
            stmt.setString(5, auto.getPotencia());
            stmt.setString(6, auto.getAceleracion());
            stmt.setString(7, auto.getVelocidad());
            stmt.setDouble(8, auto.getPrecio());
            stmt.setInt(9, auto.getIdAuto());

            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }

    public static int modificarImagen(Auto auto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            conn = getConexion();
            stmt = conn.prepareStatement(SQL_UPDATE_IMG);
            Blob imagenBlob = conn.createBlob();
            imagenBlob.setBytes(1, auto.getImagen());
            stmt.setBlob(1, imagenBlob);
            stmt.setInt(2, auto.getIdAuto());

            registros = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }
}
