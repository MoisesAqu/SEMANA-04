/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class AlumnoArray {
    private ArrayList<Alumno> listaAlumnos;
    private DefaultTableModel modelo;

    public AlumnoArray(DefaultTableModel modelo) {
        this.listaAlumnos = new ArrayList<>();
        this.modelo = modelo;
    }

    public void agregarAlumno(Alumno alumno, Date fechaNacimiento) {
        // Convertimos la fecha a LocalDate
        LocalDate nacimiento = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        alumno.setFechaNacimiento(nacimiento);  
        listaAlumnos.add(alumno);
        actualizarTabla();
    }

    public void actualizarTabla() {
        modelo.setRowCount(0);
        for (Alumno alumno : listaAlumnos) {
            modelo.addRow(new Object[]{
                alumno.getCodigo(),
                alumno.getNombre(),
                alumno.getDireccion(),
                alumno.getTelefono(),
                alumno.getEdad() + " años"
            });
        }
    }

    public void guardarTablaEnArchivo(String rutaArchivo) {
    try {
        File file = new File(rutaArchivo);
        if (!file.exists()) {
            file.getParentFile().mkdirs();  // Crear directorios si no existen
            file.createNewFile();  // Crear el archivo si no existe
        }

        // Escribir los datos de la tabla en el archivo
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String codigo = modelo.getValueAt(i, 0).toString();
                String nombre = modelo.getValueAt(i, 1).toString();
                String direccion = modelo.getValueAt(i, 2).toString();
                String telefono = modelo.getValueAt(i, 3).toString();
                String edad = modelo.getValueAt(i, 4).toString();

                writer.write(codigo + "," + nombre + "," + direccion + "," + telefono + "," + edad);
                writer.newLine();  // Nueva línea después de cada registro
            }
            JOptionPane.showMessageDialog(null, "Datos de la tabla guardados exitosamente en " + rutaArchivo);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar los datos de la tabla: " + e.getMessage());
    }
}


    public void cargarDatosDesdeArchivo(String rutaArchivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            listaAlumnos.clear();  
            modelo.setRowCount(0);  

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");  
                if (datos.length == 5) {
                    Alumno alumno = new Alumno(datos[0], datos[1], LocalDate.now(), datos[2], datos[3]);
                    listaAlumnos.add(alumno);  
                    modelo.addRow(new Object[]{datos[0], datos[1], datos[2], datos[3], datos[4]});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos: " + e.getMessage());
        }
    }
    
    public void guardarDatosEnArchivo(String rutaArchivo) {
    try {
        File file = new File(rutaArchivo);
        if (!file.exists()) {
            file.getParentFile().mkdirs();  // Crear directorios si no existen
            file.createNewFile();  // Crear el archivo si no existe
        }

        // Escribir los datos en el archivo con el formato deseado
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            int contador = 1; // Para enumerar los alumnos
            for (Alumno alumno : listaAlumnos) {
                writer.write("ALUMNO " + contador); // Nombre del alumno con número
                writer.newLine(); // Salto de línea
                writer.write("Nombre: " + alumno.getNombre()); // Nombre del alumno
                writer.newLine(); // Salto de línea
                writer.write("Dirección: " + alumno.getDireccion()); // Dirección del alumno
                writer.newLine(); // Salto de línea
                writer.write("Edad: " + alumno.getEdad() + " años"); // Edad del alumno
                writer.newLine(); // Salto de línea
                writer.write("Teléfono: " + alumno.getTelefono()); // Teléfono del alumno
                writer.newLine(); // Salto de línea
                writer.write("-----------------------------------------"); // Separador entre alumnos
                writer.newLine();  // Nueva línea para separar los datos de cada alumno
                contador++; // Incrementa el número de alumno
            }
            JOptionPane.showMessageDialog(null, "Datos guardados exitosamente en " + rutaArchivo);
        }
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + e.getMessage());
    }
}




    
    public void limpiar(javax.swing.JTextField txtCodigo, 
        javax.swing.JTextField txtNombre, 
        javax.swing.JTextField txtDireccion, 
        javax.swing.JTextField txtTelefono, 
        com.toedter.calendar.JCalendar calendario) {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        calendario.setDate(null);  // Limpiar el calendario
    }

}
