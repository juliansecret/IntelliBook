package Models;

import Util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PrestamoDAO {
    Connection con;
    Conexion cn = Conexion.getInstance();
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrar(Prestamo prestamo) {
    String sql = "INSERT INTO prestamos (id_libro, numero_control, fecha_entrega, fecha_salida, unidades) VALUES (?,?,?,?,?)";
    con = cn.conectar();
    try {
        // Verificar existencia del número de control en la tabla alumnos
        String sqlAlumno = "SELECT * FROM alumnos WHERE numeroControl = ?";
        ps = con.prepareStatement(sqlAlumno);
        ps.setString(1, prestamo.getNumeroControl());
        rs = ps.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(null, "El número de control no existe en la base de datos de alumnos.");
            return false;
        }

        // Verificar existencia y disponibilidad del libro en la tabla libros
        String sqlLibro = "SELECT * FROM libros WHERE id_libro = ?";
        ps = con.prepareStatement(sqlLibro);
        ps.setString(1, prestamo.getIdLibro());
        rs = ps.executeQuery();

        if (rs.next()) {
            int unidadesDisponibles = rs.getInt("unidades");
            int unidadesSolicitadas = prestamo.getUnidades();
            if (unidadesDisponibles < unidadesSolicitadas) {
                JOptionPane.showMessageDialog(null, "El ID del libro no tiene suficientes unidades disponibles.");
                return false;
            } else {
                // Reducir el número de unidades disponibles en la tabla libros
                int unidadesRestantes = unidadesDisponibles - unidadesSolicitadas;
                String sqlUpdateLibro = "UPDATE libros SET unidades = ? WHERE id_libro = ?";
                ps = con.prepareStatement(sqlUpdateLibro);
                ps.setInt(1, unidadesRestantes);
                ps.setString(2, prestamo.getIdLibro());
                ps.executeUpdate();
            }
        } else {
            JOptionPane.showMessageDialog(null, "El ID del libro no existe en la base de datos de libros.");
            return false;
        }

        // Registrar el préstamo en la base de datos
        ps = con.prepareStatement(sql);
        ps.setString(1, prestamo.getIdLibro());
        ps.setString(2, prestamo.getNumeroControl());
        ps.setDate(3, java.sql.Date.valueOf(prestamo.getFechaEntrega()));
        ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
        ps.setInt(5, prestamo.getUnidades());
        ps.executeUpdate();

        return true;
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


    public boolean modificar(Prestamo prestamo) {
    String sql = "UPDATE prestamos SET id_libro=?, numero_control=?, fecha_entrega=?, fecha_salida=?, unidades=? WHERE id=?";
    try {
        con = cn.conectar();
        ps = con.prepareStatement(sql);
        ps.setString(1, prestamo.getIdLibro());
        ps.setString(2, prestamo.getNumeroControl());
        ps.setDate(3, java.sql.Date.valueOf(prestamo.getFechaEntrega()));
        ps.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
        ps.setInt(5, prestamo.getUnidades());
        ps.setInt(6, prestamo.getId());
        
        int filasAfectadas = ps.executeUpdate();
        
        if (filasAfectadas > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el préstamo con el ID especificado");
            return false;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return false;
    } finally {
        try {
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
}


    public DefaultTableModel consultar() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID Préstamo");
    modelo.addColumn("ID Libro");
    modelo.addColumn("Número de Control");
    modelo.addColumn("Fecha de Entrega");
    modelo.addColumn("Fecha de Salida");
    modelo.addColumn("Unidades");

    try {
        con = cn.conectar();
        PreparedStatement seleccionar = con.prepareStatement("SELECT * FROM prestamos");
        ResultSet consulta = seleccionar.executeQuery();

        while (consulta.next()) {
            int id_prestamo = consulta.getInt("id");
            String id_libro = consulta.getString("id_libro");
            String numero_control = consulta.getString("numero_control");
            String fecha_entrega = consulta.getString("fecha_entrega");
            String fecha_salida = consulta.getString("fecha_salida");
            String unidades = consulta.getString("unidades");

            modelo.addRow(new Object[]{id_prestamo, id_libro, numero_control, fecha_entrega, fecha_salida, unidades});
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


    public Prestamo buscar(String numeroControl) {
    Prestamo prestamo = null;
    String sql = "SELECT * FROM prestamos WHERE numero_control=?";
    con = cn.conectar();
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1, numeroControl);
        rs = ps.executeQuery();

        if (rs.next()) {
            prestamo = new Prestamo();
            prestamo.setId(rs.getInt("id"));
            prestamo.setIdLibro(rs.getString("id_libro"));
            prestamo.setNumeroControl(rs.getString("numero_control"));
            prestamo.setFechaEntrega(LocalDate.parse(rs.getString("fecha_entrega")));
            prestamo.setFechaSalida(LocalDate.parse(rs.getString("fecha_salida")));
            prestamo.setUnidades(rs.getInt("unidades"));
        }

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
    return prestamo;
}
public boolean eliminar(int id) {
    String sqlObtenerUnidades = "SELECT unidades FROM prestamos WHERE id=?";
    String sqlActualizarUnidades = "UPDATE libros SET unidades = unidades + ? WHERE id_libro = ?";

    try {
        con = cn.conectar();

        // Obtener las unidades del préstamo que se va a eliminar
        int unidadesPrestamo = 0;
        ps = con.prepareStatement(sqlObtenerUnidades);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            unidadesPrestamo = rs.getInt("unidades");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el préstamo con el ID especificado");
            return false;
        }

        // Obtener el ID del libro asociado al préstamo
        String sqlObtenerIdLibro = "SELECT id_libro FROM prestamos WHERE id=?";
        String idLibro = "";
        ps = con.prepareStatement(sqlObtenerIdLibro);
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next()) {
            idLibro = rs.getString("id_libro");
        }

        // Sumar las unidades del préstamo eliminado a las unidades actuales del libro
        ps = con.prepareStatement(sqlActualizarUnidades);
        ps.setInt(1, unidadesPrestamo);
        ps.setString(2, idLibro);
        ps.executeUpdate();

        // Eliminar el préstamo de la tabla "prestamos"
        String sqlEliminarPrestamo = "DELETE FROM prestamos WHERE id=?";
        ps = con.prepareStatement(sqlEliminarPrestamo);
        ps.setInt(1, id);
        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas > 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el préstamo con el ID especificado");
            return false;
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.toString());
        return false;
    } finally {
        // Cerrar recursos y conexiones
        // ...
    }
}




}
