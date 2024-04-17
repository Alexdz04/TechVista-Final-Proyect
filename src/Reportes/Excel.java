package Reportes;
        
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import Modelo.Conexion;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class Excel {
    public static void reporte() {

        Workbook book = new XSSFWorkbook(); // Se crea un nuevo libro de Excel en formato .xlsx.
        Sheet sheet = book.createSheet("Productos"); // Se crea una nueva hoja en el libro y se le asigna un nombre.

        try {
            // Estilo para el título del reporte.
            CellStyle tituloEstilo = book.createCellStyle();
            tituloEstilo.setAlignment(HorizontalAlignment.CENTER);
            tituloEstilo.setVerticalAlignment(VerticalAlignment.CENTER);
            Font fuenteTitulo = book.createFont();
            fuenteTitulo.setFontName("Arial");
            fuenteTitulo.setBold(true);
            fuenteTitulo.setFontHeightInPoints((short) 14);
            tituloEstilo.setFont(fuenteTitulo);

            // Se crea la fila para el título y se establece el estilo.
            Row filaTitulo = sheet.createRow(1);
            Cell celdaTitulo = filaTitulo.createCell(1);
            celdaTitulo.setCellStyle(tituloEstilo);
            celdaTitulo.setCellValue("Reporte de los productos!");

            // Se fusionan las celdas para el título.
            sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 3));

            // Cabecera del reporte.
            String[] cabecera = new String[]{"Código", "Nombre", "Precio", "Existencia"};

            // Estilo para la cabecera.
            CellStyle headerStyle = book.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderBottom(BorderStyle.THIN);

            Font font = book.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            font.setColor(IndexedColors.WHITE.getIndex());
            font.setFontHeightInPoints((short) 12);
            headerStyle.setFont(font);

            // Se crea la fila para los encabezados y se establece el estilo.
            Row filaEncabezados = sheet.createRow(4);

            // Se agregan los encabezados a la fila con el estilo.
            for (int i = 0; i < cabecera.length; i++) {
                Cell celdaEnzabezado = filaEncabezados.createCell(i);
                celdaEnzabezado.setCellStyle(headerStyle);
                celdaEnzabezado.setCellValue(cabecera[i]);
            }

            // Se realiza la conexión a la base de datos.
            Conexion con = new Conexion();
            PreparedStatement ps;
            ResultSet rs;
            Connection conn = con.getConnection();

            int numFilaDatos = 5; // Fila donde se comenzarán a escribir los datos.

            // Estilo para los datos.
            CellStyle datosEstilo = book.createCellStyle();
            datosEstilo.setBorderBottom(BorderStyle.THIN);
            datosEstilo.setBorderLeft(BorderStyle.THIN);
            datosEstilo.setBorderRight(BorderStyle.THIN);
            datosEstilo.setBorderBottom(BorderStyle.THIN);

            // Se ejecuta la consulta SQL para obtener los productos.
            ps = conn.prepareStatement("SELECT codigo, nombre, precio, stock FROM productos");
            rs = ps.executeQuery();

            int numCol = rs.getMetaData().getColumnCount(); // Número de columnas en el resultado.

            // Se recorren los resultados y se escriben en el archivo Excel.
            while (rs.next()) {
                Row filaDatos = sheet.createRow(numFilaDatos);

                // Se agregan los datos a la fila con el estilo.
                for (int a = 0; a < numCol; a++) {
                    Cell CeldaDatos = filaDatos.createCell(a);
                    CeldaDatos.setCellStyle(datosEstilo);
                    CeldaDatos.setCellValue(rs.getString(a + 1));
                }

                numFilaDatos++;
            }

            // Se ajusta el tamaño de las columnas automáticamente.
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);

            sheet.setZoom(150); // Se establece el zoom de la hoja.

            String fileName = "productos"; // Nombre del archivo Excel.
            String home = System.getProperty("user.home"); // Directorio del usuario.
            File file = new File(home + "/Downloads/" + fileName + ".xlsx"); // Se crea el archivo en la carpeta "Descargas".
            FileOutputStream fileOut = new FileOutputStream(file);
            book.write(fileOut); // Se escribe el libro en el archivo.
            fileOut.close(); // Se cierra el flujo de salida.
            Desktop.getDesktop().open(file); // Se abre el archivo con la aplicación predeterminada.
            JOptionPane.showMessageDialog(null, "Reporte Generado correctamente!!"); // Se muestra un mensaje de éxito.

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | SQLException ex) {
            Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}