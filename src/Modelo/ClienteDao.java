/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Alejandro
 */
public class ClienteDao {
    
    Conexion cn = new Conexion(); // Se instancia un objeto de la clase Conexion para establecer la conexión con la base de datos.
    Connection con; // Objeto Connection para la conexión con la base de datos.
    PreparedStatement ps; // Objeto PreparedStatement para ejecutar consultas SQL precompiladas.
    ResultSet rs; // Objeto ResultSet para almacenar los resultados de las consultas SQL.

    // Método para registrar un nuevo cliente en la base de datos.
    public boolean RegistrarCliente(Cliente cl){
        String sql = "INSERT INTO clientes (dni, nombre, telefono, direccion, razon) VALUES (?, ?, ?, ?, ?)";
        try {
            con = cn.getConnection(); // Se obtiene una conexión a la base de datos.
            ps = con.prepareStatement(sql); // Se prepara la consulta SQL.
            // Se establecen los valores de los parámetros en la consulta SQL.
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.execute(); // Se ejecuta la consulta SQL.
            return true;
        }  catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.toString()); // Se muestra un mensaje de error en caso de excepción.
            return false;
        } finally {
            try {
                con.close(); // Se cierra la conexión con la base de datos.
            } catch (SQLException e) {
                System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción al cerrar la conexión.
            }
        }
    }
    
    // Método para listar todos los clientes almacenados en la base de datos.
    public List ListarCliente(){
        List<Cliente> ListaCl = new ArrayList(); // Se crea una lista para almacenar los clientes.
        String sql = "SELECT * FROM clientes"; // Consulta SQL para seleccionar todos los registros de la tabla "clientes".
        try {
            con = cn.getConnection(); // Se obtiene una conexión a la base de datos.
            ps = con.prepareStatement(sql); // Se prepara la consulta SQL.
            rs = ps.executeQuery(); // Se ejecuta la consulta SQL y se obtiene un conjunto de resultados.
            // Se recorre el conjunto de resultados y se van creando objetos Cliente con los datos obtenidos.
            while (rs.next()) {
                Cliente cl = new Cliente();
                cl.setId(rs.getInt("id"));
                cl.setDni(rs.getString("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setRazon(rs.getString("razon"));
                ListaCl.add(cl); // Se agrega el objeto Cliente a la lista.
            }
        } catch (SQLException e) {  
            System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción.
        } finally {
            try {
                con.close(); // Se cierra la conexión con la base de datos.
            } catch (SQLException e) {
                System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción al cerrar la conexión.
            }
        }
        return ListaCl; // Se retorna la lista de clientes.
    }
    
    // Método para eliminar un cliente de la base de datos por su ID.
    public boolean EliminarCliente(int id){
        String sql = "DELETE FROM clientes WHERE id = ?"; // Consulta SQL para eliminar un registro de la tabla "clientes".
        try {
            ps = con.prepareStatement(sql); // Se prepara la consulta SQL.
            ps.setInt(1, id); // Se establece el valor del parámetro en la consulta SQL.
            ps.execute(); // Se ejecuta la consulta SQL.
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción.
            return false;
        } finally {
            try {
                con.close(); // Se cierra la conexión con la base de datos.
            } catch (SQLException ex) {
                System.out.println(ex.toString()); // Se muestra un mensaje de error en caso de excepción al cerrar la conexión.
            }
        }
    }
    
    // Método para modificar los datos de un cliente en la base de datos.
    public boolean ModificarCliente(Cliente cl){
        String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=?, razon=? WHERE id=?"; // Consulta SQL para actualizar un registro de la tabla "clientes".
        try {
            con = cn.getConnection(); // Se obtiene una conexión a la base de datos.
            ps = con.prepareStatement(sql); // Se prepara la consulta SQL.
            // Se establecen los valores de los parámetros en la consulta SQL.
            ps.setString(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setString(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.setInt(6, cl.getId());
            ps.execute(); // Se ejecuta la consulta SQL.
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción.
            return false;
        } finally {
            try {
                con.close(); // Se cierra la conexión con la base de datos.
            } catch (SQLException e) {
                System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción al cerrar la conexión.
            }
        }
    }
    
    // Método para buscar un cliente en la base de datos por su DNI.
    public Cliente Buscarcliente(int dni){
        Cliente cl = new Cliente(); // Se crea un objeto Cliente.
        String sql = "SELECT * FROM clientes WHERE dni = ?"; // Consulta SQL para seleccionar un registro de la tabla "clientes" por su DNI.
        try {
            con = cn.getConnection(); // Se obtiene una conexión a la base de datos.
            ps = con.prepareStatement(sql); // Se prepara la consulta SQL.
            ps.setInt(1, dni); // Se establece el valor del parámetro en la consulta SQL.
            rs = ps.executeQuery(); // Se ejecuta la consulta SQL y se obtiene un conjunto de resultados.
            // Si se encuentra un resultado, se asignan los datos del cliente al objeto Cliente.
            if (rs.next()) {
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getString("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setRazon(rs.getString("razon"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString()); // Se muestra un mensaje de error en caso de excepción.
        }
        return cl; // Se retorna el objeto Cliente.
    }
}


        
    


