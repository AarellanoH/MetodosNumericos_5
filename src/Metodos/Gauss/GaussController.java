/*Clase que se encarga de "renderizar" el contenido en la clase Gauss.fxml,
* además invoca a la clase que contiene la lógica de Gauss: Gauss.java
* Esta clase también se encarga de la validación de datos introducidos por el usuario.
* Conexión entre lo gráfico y la lógica.
.*/
/*
*Autores:
*
*
*
*
 */
package Metodos.Gauss;

import Metodos.MetodosMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.ResourceBundle;

public class GaussController implements Initializable{

    //Input del numero de incognitas.
    @FXML private ComboBox<String> cmbIncognitas;

    //Matriz A (en Ax = b) (Input)
    @FXML private VBox vbMatA;
    //Matriz B (en Ax = b) (Input)
    @FXML private VBox vbMatB;

    //Matriz X(En Ax = b)(Output)
    @FXML private VBox vbMatX;

    @FXML private Text txtConclusiones;

    //Matrices en donde van los signos de igual o nombres de incógnitas.
    @FXML private VBox vbMatSignoIgual;
    @FXML private VBox vbMatXNames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarMatrices(new ActionEvent());
    }

    @FXML void actualizarMatrices(ActionEvent event) {
        int incognitas = 3;
        try {
            incognitas = Integer.parseInt(cmbIncognitas.getSelectionModel().getSelectedItem());
        }
        catch (Exception e){
            incognitas = 3;
        }

        //Los titulos de cada matriz. Algunos se tienen que crear vacíos para que las matrices no se desfacen.
        HBox hbMatATitle = GaussController.titleRow("a", incognitas, false);
        HBox hbMatSignoIgualTitle = GaussController.titleRow(" ", 1, false);
        HBox hbMatBTitle = GaussController.titleRow("b", 1, false);

        //Mostrar Ax = b
        GaussController.mostrarMatrizDeTextFields(vbMatA, hbMatATitle, incognitas, incognitas, true);
        GaussController.mostrarMatrizDeTextFields(vbMatX, null, incognitas, 1, false);
        GaussController.mostrarMatrizDeTexts(vbMatSignoIgual, hbMatSignoIgualTitle, incognitas, 1, "=", false);
        GaussController.mostrarMatrizDeTextFields(vbMatB, hbMatBTitle, incognitas, 1, true);
        GaussController.mostrarMatrizDeTexts(vbMatXNames, null, incognitas, 1, "a", true);
    }

    @FXML void btnExecute(){
        try {
            double[][] matA = GaussController.leerMatriz(vbMatA, true);

            double[][] matB = GaussController.leerMatriz(vbMatB, true);

            Gauss gauss = Gauss.resolverSEL(matA, matB);
            //Si es inconsistente, mostrar la inconsistencia.
            if (gauss.inconsistente){
                txtConclusiones.setText("El sistema de ecuaciones que ingresaste es inconsisteente. La matriz ampliada" +
                        " con la inconsistencia se muestra a continuación:");
                vbMatXNames.getChildren().removeAll(vbMatXNames.getChildren());
                HBox titleRow = GaussController.titleRow("a", gauss.matTrianguloInferior[0].length, true);
                titleRow.setSpacing(65);
                HBox wrappingRow = new HBox(titleRow);
                Pane paddingPane = new Pane();
                paddingPane.setPrefSize(30,0);
                wrappingRow.getChildren().add(0,paddingPane);

                GaussController.mostrarMatrizConTitulo(vbMatX, wrappingRow, gauss.matTrianguloInferior);
            }
            else {
                if (gauss.multiplesSoluciones){
                    txtConclusiones.setText("El sistema de ecuaciones que ingresaste tiene múltiples soluciones." +
                            " De acuerdo al valor que ingresaste, la solución es la siguiente:");
                }
                else {
                    txtConclusiones.setText("El sistema de ecuaciones que ingresaste tiene solución única:");
                }
                GaussController.mostrarMatriz(vbMatX, gauss.matX);
            }
        }
        catch (IllegalArgumentException e){
            MetodosMain.showErrorDialog("Has ingresado un SEL que no pudo ser acomodado de tal manera" +
                    " que todos los pivotes fueran diferentes a 0.\nEsto provoca un error al intentar generar" +
                    " la matriz inferior (con solo 0's), pues se intenta hacer una división entre 0,\n" +
                    " evitando que se pueda realizar con éxito el método de Gauss con sustitución\n" +
                    " regresiva. Favor de revisar los datos e intentar de nuevo.");
        }
        catch (Exception e){
            System.out.println(e.toString());
            MetodosMain.showErrorDialog();
        }
    }

    //Mostrar graficamente una matriz de textFields(editables o no editables) de n filas y n columnas en cierto espacio.
    private static void mostrarMatrizDeTextFields(VBox mat, HBox titleRow, int filas, int cols, boolean editable){
        mat.getChildren().removeAll(mat.getChildren());
        if (titleRow != null){
            mat.getChildren().add(titleRow);
        }

        //Crear filas
        for (int i = 0; i < filas; i++){
            HBox row = new HBox(10);
            row.setAlignment(Pos.TOP_LEFT);
            //Crear columnas
            for (int j = 0; j<cols; j++){
                TextField textField = new TextField();
                textField.setPromptText("1");
                textField.setPrefWidth(60);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(editable);
                row.getChildren().add(textField);
            }
            mat.getChildren().add(row);
        }
    }

    //Mostrar graficamente una matriz de texts(recibe el texto a usar) de n filas y n columnas en cierto espacio
    private static void mostrarMatrizDeTexts(VBox mat, HBox titleRow, int filas, int cols, String strDefault, boolean incrementable){
        mat.getChildren().removeAll(mat.getChildren());
        if (titleRow != null){
            mat.getChildren().add(titleRow);
        }
        //Crear filas
        for (int i = 0; i < filas; i++){
            HBox row = new HBox(10);
            row.setPrefHeight(26);
            row.setAlignment(Pos.TOP_LEFT);
            //Crear columnas
            for (int j = 0; j<cols; j++){
                Text text = new Text();
                if (incrementable){
                    if (filas == 1) {
                        text.setText(strDefault + "    =    ");
                    }
                    else {
                        text.setText(strDefault + i + "    =    ");
                    }
                }
                else {
                    text.setText(strDefault);
                }
                row.getChildren().add(text);
            }
            mat.getChildren().add(row);
        }
    }

    private static HBox titleRow(String strDefault, int cols, boolean matrizMixta){
        if (cols == 1){
            HBox hbRowTitle = new HBox(new Text(strDefault));
            hbRowTitle.setAlignment(Pos.CENTER);
            return hbRowTitle;
        }
        else {
            HBox hbRowTitle = new HBox(60);
            for (Integer i = 0; i<cols; i++){
                if (matrizMixta){
                    if (i == cols - 1){
                        hbRowTitle.getChildren().add(new Text("b"));
                    }
                    else {
                        hbRowTitle.getChildren().add(new Text(strDefault + i.toString()));
                    }
                }
                else {
                    hbRowTitle.getChildren().add(new Text(strDefault + i.toString()));
                }
            }
            return hbRowTitle;
        }
    }

    private static void mostrarMatriz(VBox graphicMat, double[][]matValues){
        graphicMat.getChildren().removeAll(graphicMat.getChildren());
        //Crear filas
        for (int i = 0; i < matValues.length; i++){
            HBox row = new HBox(20);
            row.setAlignment(Pos.TOP_LEFT);
            //Crear columnas
            for (int j = 0; j<matValues[0].length; j++){
                double value = matValues[i][j];
                TextField textField = new TextField(MetodosMain.doubleToString(value));
                textField.setPrefWidth(60);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false);
                row.getChildren().add(textField);
            }
            graphicMat.getChildren().add(row);
        }
    }

    private static void mostrarMatrizConTitulo(VBox graphicMat, HBox titleRow, double[][]matValues){
        System.out.println("title" + titleRow);
        graphicMat.getChildren().removeAll(graphicMat.getChildren());
        graphicMat.getChildren().add(titleRow);
        //Crear filas
        for (int i = 0; i < matValues.length; i++){
            HBox row = new HBox(20);
            row.setAlignment(Pos.TOP_LEFT);
            //Crear columnas
            for (int j = 0; j<matValues[0].length; j++){
                double value = matValues[i][j];
                TextField textField = new TextField(MetodosMain.doubleToString(value));
                textField.setPrefWidth(60);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false);
                row.getChildren().add(textField);
            }
            graphicMat.getChildren().add(row);
        }
    }


    private static double[][] leerMatriz(VBox mat, boolean hasTitleRow){


        int numFilas = hasTitleRow ? mat.getChildren().size() - 1 : mat.getChildren().size();
        int numCols = ((HBox)mat.getChildren().get(0)).getChildren().size();
        double [][] matArr = new double[numFilas][numCols];

        for (int i = 0; i < numFilas; i++){
            HBox row = hasTitleRow ? (HBox)mat.getChildren().get(i + 1) : (HBox)mat.getChildren().get(i);
            for (int j = 0; j < numCols; j++) {
                TextField textField = (TextField)row.getChildren().get(j);
                matArr[i][j] = textField.getText().equals("") ? 1.0 : Double.parseDouble(textField.getText());
            }
        }
        return matArr;
    }

}
