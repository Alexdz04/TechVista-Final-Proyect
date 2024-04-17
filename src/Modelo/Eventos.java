package Modelo;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class Eventos {

    // Método para permitir únicamente la entrada de caracteres alfabéticos y espacios en un campo de texto.
    public void textKeyPress(KeyEvent evt) {
        char car = evt.getKeyChar(); // Se obtiene el carácter ingresado.
        // Se verifica si el carácter ingresado no es una letra, un espacio o la tecla de retroceso.
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')
                && (car != (char) KeyEvent.VK_BACK_SPACE) && (car != (char) KeyEvent.VK_SPACE)) {
            evt.consume(); // Si no es un carácter válido, se consume el evento para evitar que sea ingresado.
        }
    }

    // Método para permitir únicamente la entrada de caracteres numéricos en un campo de texto.
    public void numberKeyPress(KeyEvent evt) {
        char car = evt.getKeyChar(); // Se obtiene el carácter ingresado.
        // Se verifica si el carácter ingresado no es un número o la tecla de retroceso.
        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // Si no es un carácter válido, se consume el evento para evitar que sea ingresado.
        }
    }

    // Método para permitir únicamente la entrada de números decimales en un campo de texto.
    public void numberDecimalKeyPress(KeyEvent evt, JTextField textField) {
        char car = evt.getKeyChar(); // Se obtiene el carácter ingresado.
        // Se verifica si el carácter ingresado no es un número, si ya hay un punto decimal y si no es la tecla de retroceso.
        if ((car < '0' || car > '9') && textField.getText().contains(".") && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // Si no es un carácter válido, se consume el evento para evitar que sea ingresado.
        } 
        // Se verifica si el carácter ingresado no es un número, ni un punto decimal, ni la tecla de retroceso.
        else if ((car < '0' || car > '9') && (car != '.') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume(); // Si no es un carácter válido, se consume el evento para evitar que sea ingresado.
        }
    }
}
