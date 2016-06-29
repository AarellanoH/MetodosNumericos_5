/*Clase que se encarga de "renderizar" el contenido en la clase OperacionesMatriciales.fxml,
* además invoca a la clase que contiene la lógica de Operaciones Matriciales: OperacionesMatriciales.java
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
package Metodos.OperacionesMatriciales;

import Metodos.MetodosMain;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.DoubleStream;

public class OperacionesMatricialesController implements Initializable{
    //Elemento graficos.
    @FXML TilePane tilePane;
    @FXML ComboBox cmbFilasA;
    @FXML ComboBox cmbColsA;
    @FXML ComboBox cmbFilasB;
    @FXML ComboBox cmbColsB;
    @FXML VBox graphicMatA;
    @FXML VBox graphicMatB;
    @FXML VBox graphicMatC;
    @FXML TextField txtEscalarA;
    @FXML TextField txtEscalarB;
    @FXML Button btnSumar;
    @FXML Button btnRestar;
    @FXML Button btnMultiplicar;


    //Numero de filas y columnas seleccionadas por el usuario en cada matriz.
    private int filasA = 3;
    private int colsA = 3;
    private int filasB = 3;
    private int colsB = 3;

    //Los arreglos que representan a las dos matrices.
    private Double[][] matA;
    private Double[][] matB;

    //Las escalares que deberan multiplicar a cada matriz.
    private Double escalarA = 1.0;
    private Double escalarB = 1.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.mostrarMatriz(graphicMatA, this.filasA, this.colsA);
        this.mostrarMatriz(graphicMatB, this.filasB, this.colsB);
    }

    //Este metodo se activa cada vez que un comboBox (Con el numero de filas y columnas de cada matriz) es cambaido.
    @FXML public void actualizarMatrices(){
        //Obtener el numero seleccionado de filas y columnas de cada matriz.
        //Solo se genera la nueva matriz si el numero de filas o columnas cambio.
        if (cmbFilasA.getSelectionModel().getSelectedItem() != null){
            if (this.filasA != Integer.parseInt((String)cmbFilasA.getSelectionModel().getSelectedItem())){
                this.filasA = Integer.parseInt((String)cmbFilasA.getSelectionModel().getSelectedItem());
                this.mostrarMatriz(this.graphicMatA, this.filasA, this.colsA);
                this.habilitarBotones();
            }
        }
        if (cmbColsA.getSelectionModel().getSelectedItem() != null){
            if (this.colsA != Integer.parseInt((String)cmbColsA.getSelectionModel().getSelectedItem())){
                this.colsA = Integer.parseInt((String)cmbColsA.getSelectionModel().getSelectedItem());
                this.mostrarMatriz(this.graphicMatA, this.filasA, this.colsA);
                this.habilitarBotones();
            }
        }
        if (cmbFilasB.getSelectionModel().getSelectedItem() != null){
            if (this.filasB != Integer.parseInt((String)cmbFilasB.getSelectionModel().getSelectedItem())){
                this.filasB = Integer.parseInt((String)cmbFilasB.getSelectionModel().getSelectedItem());
                this.mostrarMatriz(this.graphicMatB, this.filasB, this.colsB);
                this.habilitarBotones();
            }
        }
        if (cmbColsB.getSelectionModel().getSelectedItem() != null){
            if (this.colsB != Integer.parseInt((String)cmbColsB.getSelectionModel().getSelectedItem())){
                this.colsB = Integer.parseInt((String)cmbColsB.getSelectionModel().getSelectedItem());
                this.mostrarMatriz(this.graphicMatB, this.filasB, this.colsB);
                this.habilitarBotones();
            }
        }
    }

    private HashMap<String, HBox> rows = new HashMap<>();
    private int rowNumber = 5;
    private int columnNumber = 5;

    @FXML public void doSomething(){
        leerMatrices();
    }

    //Mostrar graficamente una matriz de n filas y n columnas en cierto espacio.
    private void mostrarMatriz(VBox mat, int filas, int cols){
        mat.getChildren().removeAll(mat.getChildren());
        //Crear filas
        for (int i = 0; i < filas; i++){
            HBox row = new HBox(20);
            row.setAlignment(Pos.TOP_CENTER);
            row.setPrefSize(200, 100);
            //Crear columnas
            for (int j = 0; j<cols; j++){
                TextField textField = new TextField();
                textField.setPromptText("1");
                textField.setPrefWidth(60);
                textField.setAlignment(Pos.CENTER);
                row.getChildren().add(textField);
            }
            mat.getChildren().add(row);
        }
    }

    private void mostrarMatriz(VBox graphicMat, Double[][]matValues){
        graphicMat.getChildren().removeAll(graphicMat.getChildren());
        //Crear filas
        for (int i = 0; i < matValues.length; i++){
            HBox row = new HBox(20);
            row.setAlignment(Pos.TOP_CENTER);
            row.setPrefSize(200, 100);
            //Crear columnas
            for (int j = 0; j<matValues[0].length; j++){
                Double value = matValues[i][j];
                TextField textField = new TextField(MetodosMain.doubleToString(value));
                textField.setPrefWidth(60);
                textField.setAlignment(Pos.CENTER);
                textField.setEditable(false);
                row.getChildren().add(textField);
            }
            graphicMat.getChildren().add(row);
        }
    }

    //Leer los datos de la parte grafica
    private void leerMatrices(){
        this.matA = new Double[this.filasA][this.colsA];
        this.matB = new Double[this.filasB][this.colsB];

        //Leer la matriz A.
        for (int i = 0; i<this.filasA; i++) {
            HBox row = (HBox)graphicMatA.getChildren().get(i);
            for (int j = 0; j<this.colsA; j++) {
                if (((TextField)row.getChildren().get(j)).getText().equals("")){
                    this.matA[i][j] = 1.0;
                }
                else {
                    this.matA[i][j] = Double.parseDouble(((TextField)row.getChildren().get(j)).getText());
                }
            }
        }

        //Leer la matriz B.
        for (int i = 0; i<this.filasB; i++) {
            HBox row = (HBox)graphicMatB.getChildren().get(i);
            for (int j = 0; j<this.colsB; j++) {
                if (((TextField)row.getChildren().get(j)).getText().equals("")){
                    this.matB[i][j] = 1.0;
                }
                else {
                    this.matB[i][j] = Double.parseDouble(((TextField)row.getChildren().get(j)).getText());
                }
            }
        }
    }

    //Leer los datos de los escalares.
    private void leerEscalares(){
        if (this.txtEscalarA.getText().equals("")){
            this.escalarA = 1.0;
        }
        else {
            this.escalarA = Double.parseDouble(this.txtEscalarA.getText());
        }
        if (this.txtEscalarB.getText().equals("")){
            this.escalarB = 1.0;
        }
        else {
            this.escalarB = Double.parseDouble(this.txtEscalarB.getText());
        }
    }

    //Habilitar los botones de las operaciones posibles de acuerdo a los tamaños de filas y columnas de ambas matrices.
    private void habilitarBotones(){
        //Suma y resta son posibles cuando filas y columnas de ambas matrices son iguales.
        if ((this.filasA == this.filasB) && (this.colsA == this.colsB)){
            this.btnSumar.setDisable(false);
            this.btnRestar.setDisable(false);
        }
        else {
            this.btnSumar.setDisable(true);
            this.btnRestar.setDisable(true);
        }

        //Multiplicacion es posible cuando el numero de columnas en A es igual a renglones en B.
        if (this.colsA == this.filasB){
            this.btnMultiplicar.setDisable(false);
        }
        else {
            this.btnMultiplicar.setDisable(true);
        }
    }

    @FXML public void sumar(){
        try {
            this.leerMatrices();
            this.leerEscalares();
            this.matA = this.multiplicarPorEscalar(this.matA, this.escalarA);
            this.matB = this.multiplicarPorEscalar(this.matB, this.escalarB);
            Double matC[][] = OperacionesMatriciales.sumar(matA, matB);
            this.mostrarMatriz(graphicMatC, matC);
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    @FXML public void restar(){
        try {
            this.leerMatrices();
            this.leerEscalares();
            this.matA = this.multiplicarPorEscalar(this.matA, this.escalarA);
            this.matB = this.multiplicarPorEscalar(this.matB, this.escalarB);
            Double matC[][] = OperacionesMatriciales.restar(matA, matB);
            this.mostrarMatriz(graphicMatC, matC);
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    @FXML public void multiplicar(){
        try {
            this.leerMatrices();
            this.leerEscalares();
            this.matA = this.multiplicarPorEscalar(this.matA, this.escalarA);
            this.matB = this.multiplicarPorEscalar(this.matB, this.escalarB);
            Double matC[][] = OperacionesMatriciales.multiplicar(matA, matB);
            this.mostrarMatriz(graphicMatC, matC);
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    private Double[][] multiplicarPorEscalar(Double [][] mat, Double escalar){
        Double[][] matEscalar = mat;
        for (int i = 0; i < matEscalar.length; i++) {
            for (int j = 0; j < matEscalar[0].length; j++) {
                matEscalar[i][j] *= escalar;
            }
        }
        return matEscalar;
    }


}
