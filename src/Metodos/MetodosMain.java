//Clase que se encarga de inicializar el programa. También implementa lógica que es igual para todos
//      los métodos en general, como el despliegue de mensajes de error o redondeo de números a 5 decimales.
//Esta clase es llamada por NewtonRaphsonMVController.java
/*
*Autores:
*
*
*
*
 */
package Metodos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MetodosMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Métodos Numéricos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showErrorDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Has llenado los campos incorrectamente.\nFavor de revisar e intentar de nuevo.");
        alert.showAndWait();
    }

    public static void showErrorDialog(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }


    //Convertir a String y cortar los puntos decimales en caso de que sea integer
    public static String doubleToString(Double num){
        BigDecimal bigDecimal = num > 0 ? new BigDecimal(num).setScale(5, BigDecimal.ROUND_FLOOR)
                : new BigDecimal(num).setScale(5, BigDecimal.ROUND_CEILING) ;

        DecimalFormat df5 = new DecimalFormat(".####");

        BigDecimal bigDecimal1 = MetodosMain.truncateDecimal(num, 5);
        return new BigDecimal(bigDecimal.intValue()).compareTo(bigDecimal) == 0 ? Integer.toString(bigDecimal.intValue())
                : df5.format(bigDecimal);
    }

    private static BigDecimal truncateDecimal(Double num, int numberOfDecimals){
        return num > 0 ? new BigDecimal(String.valueOf(num)).setScale(numberOfDecimals, BigDecimal.ROUND_FLOOR)
                : new BigDecimal(String.valueOf(num)).setScale(numberOfDecimals, BigDecimal.ROUND_CEILING);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
