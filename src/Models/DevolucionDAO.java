package Models;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DevolucionDAO {
    private Connection con;
    private Conexion cn = Conexion.getInstance();
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean devolver(Devolucion devolucion) {
        String sql = "UPDATE prestamos SET unidades = unidades - ? WHERE id = ?";
        con = cn.conectar();

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, devolucion.getUnidades());
            ps.setInt(2, devolucion.getIDPrestamo());
            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                // Sumar las unidades devueltas a las unidades en la tabla "libros"
                String sqlActualizarLibros = "UPDATE libros SET unidades = unidades + ? WHERE id_libro = ?";
                ps = con.prepareStatement(sqlActualizarLibros);
                ps.setInt(1, devolucion.getUnidades());
                ps.setString(2, devolucion.getIdLibro());
                ps.executeUpdate();

                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public DefaultTableModel consultar() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Préstamo");
        modelo.addColumn("ID Libro");
        modelo.addColumn("Número de Control");
        modelo.addColumn("Unidades Prestadas");

        try {
            con = cn.conectar();
            PreparedStatement seleccionar = con.prepareStatement(
                "SELECT prestamos.id, prestamos.id_libro, prestamos.numero_control, prestamos.unidades FROM prestamos"
            );
            ResultSet consulta = seleccionar.executeQuery();

            while (consulta.next()) {
                int idPrestamo = consulta.getInt("id");
                String idLibro = consulta.getString("id_libro");
                String numeroControl = consulta.getString("numero_control");
                int unidadesPrestadas = consulta.getInt("unidades");

                modelo.addRow(new Object[]{idPrestamo, idLibro, numeroControl, unidadesPrestadas});
            }

            cn.cerrarConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.toString());
            }
        }

        return modelo;
    }
}

