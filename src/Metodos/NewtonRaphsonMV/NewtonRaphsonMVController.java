/*Clase que se encarga de "renderizar" el contenido en la clase NewtonRaphsonMV.fxml,
* además invoca a la clase que contiene la lógica de Newton Raphson MV: NewtonRaphsonMV.java
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
package Metodos.NewtonRaphsonMV;

import Metodos.MetodosMain;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Arrays;

public class NewtonRaphsonMVController {

    //Campos que definen coeficientes de la Funcion 1.
    @FXML private TextField tfF1x0;
    @FXML private TextField tfF1x1;
    @FXML private TextField tfF1x2;
    @FXML private TextField tfF1x3;
    @FXML private TextField tfF1x4;
    @FXML private TextField tfF1x5;
    private Double f1x0;
    private Double f1x1;
    private Double f1x2;
    private Double f1x3;
    private Double f1x4;
    private Double f1x5;

    //Campos que definen coeficientes de la Funcion 2.
    @FXML private TextField tfF2x0;
    @FXML private TextField tfF2x1;
    @FXML private TextField tfF2x2;
    @FXML private TextField tfF2x3;
    @FXML private TextField tfF2x4;
    @FXML private TextField tfF2x5;
    private Double f2x0;
    private Double f2x1;
    private Double f2x2;
    private Double f2x3;
    private Double f2x4;
    private Double f2x5;

    //Campos de X y Y iniciales.
    @FXML private TextField tfXInicial;
    @FXML private TextField tfYInicial;
    private Double xInicial;
    private Double yInicial;

    //Campos que definen chasta cuándo se va a detener el metodo.
    @FXML private TextField tfIteracionesMaximas;
    @FXML private TextField tfPorcentajeErrorMaximo;
    private int iteracionesMaximas;
    private Double porcentajeErrorMaximo;

    //Outputs donde se colocarán los resultados.
    @FXML private VBox vbIteraciones;
    @FXML private Text txtConclusiones;
    @FXML private GridPane gridIteraciones;


    @FXML public void btnExecute(){
        try{
            this.validarYLlenarDatos();
            this.clearResultados();

            Double[] coeficientesF1 = {f1x0, f1x1, f1x2, f1x3, f1x4, f1x5};
            Double[] coeficientesF2 = {f2x0, f2x1, f2x2, f2x3, f2x4, f2x5};

            NewtonRaphsonMV newtonRaphsonMV = NewtonRaphsonMV.encontrarInterseccion(coeficientesF1, coeficientesF2,
                    this.iteracionesMaximas, this.porcentajeErrorMaximo, this.xInicial, this.yInicial);

            int numIteraciones = newtonRaphsonMV.iteracionesX.length;

            //Crear la tabla
            gridIteraciones.getRowConstraints().add(new RowConstraints(50));
            for (int i = 0; i<numIteraciones; i++){
                RowConstraints rowConstraints = new RowConstraints(15);
                gridIteraciones.getRowConstraints().add(rowConstraints);
            }

            //Titulos de cada columna
            Text txtIteracionTitle = new Text("Iteración");
            Text txtXTitle = new Text("X");
            Text txtYTitle = new Text("Y");
            Text txtErrorTitle = new Text("Error (%)");
            HBox hbIteracionTitle = new HBox(txtIteracionTitle);
            HBox hbXTitle = new HBox(txtXTitle);
            HBox hbYTitle = new HBox(txtYTitle);
            HBox hbErrorTitle = new HBox(txtErrorTitle);
            hbIteracionTitle.setAlignment(Pos.CENTER);
            hbXTitle.setAlignment(Pos.CENTER);
            hbYTitle.setAlignment(Pos.CENTER);
            hbErrorTitle.setAlignment(Pos.CENTER);
            gridIteraciones.add(hbIteracionTitle, 0, 0);
            gridIteraciones.add(hbXTitle, 1, 0);
            gridIteraciones.add(hbYTitle, 2, 0);
            gridIteraciones.add(hbErrorTitle, 3, 0);

            //Desplegar resultados
            for (int i = 0; i < numIteraciones; i++){
                Text txtIteracion = new Text(Integer.toString(i));
                Text txtX = new Text(MetodosMain.doubleToString(newtonRaphsonMV.iteracionesX[i]));
                Text txtY = new Text(MetodosMain.doubleToString(newtonRaphsonMV.iteracionesY[i]));
                Text txtError = new Text(MetodosMain.doubleToString(newtonRaphsonMV.porcentajesError[i]));

                HBox hbIteracion = new HBox(txtIteracion);
                HBox hbX = new HBox(txtX);
                HBox hbY = new HBox(txtY);
                HBox hbError = new HBox(txtError);
                hbIteracion.setAlignment(Pos.CENTER);
                hbX.setAlignment(Pos.CENTER);
                hbY.setAlignment(Pos.CENTER);
                hbError.setAlignment(Pos.CENTER);

                gridIteraciones.add(hbIteracion, 0, i + 1);
                gridIteraciones.add(hbX, 1, i + 1);
                gridIteraciones.add(hbY, 2, i + 1);
                gridIteraciones.add(hbError, 3, i + 1);
            }
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    private void validarYLlenarDatos(){
        //Llenar los campos de los coeficientes de la primera ecuacion.
        if (tfF1x0.getText().equals("")){
            f1x0 = 0.0;
        }
        else {
            f1x0 = Double.parseDouble(tfF1x0.getText());
        }

        if (tfF1x1.getText().equals("")){
            f1x1 = 0.0;
        }
        else {
            f1x1 = Double.parseDouble(tfF1x1.getText());
        }

        if (tfF1x2.getText().equals("")){
            f1x2 = 0.0;
        }
        else {
            f1x2 = Double.parseDouble(tfF1x2.getText());
        }

        if (tfF1x3.getText().equals("")){
            f1x3 = 0.0;
        }
        else {
            f1x3 = Double.parseDouble(tfF1x3.getText());
        }

        if (tfF1x4.getText().equals("")){
            f1x4 = 0.0;
        }
        else {
            f1x4 = Double.parseDouble(tfF1x4.getText());
        }

        if (tfF1x5.getText().equals("")){
            f1x5 = 0.0;
        }
        else {
            f1x5 = Double.parseDouble(tfF1x5.getText());
        }

        //Llenar los campos de los coeficientes de la segunda ecuacion.
        if (tfF2x0.getText().equals("")){
            f2x0 = 0.0;
        }
        else {
            f2x0 = Double.parseDouble(tfF2x0.getText());
        }

        if (tfF2x1.getText().equals("")){
            f2x1 = 0.0;
        }
        else {
            f2x1 = Double.parseDouble(tfF2x1.getText());
        }

        if (tfF2x2.getText().equals("")){
            f2x2 = 0.0;
        }
        else {
            f2x2 = Double.parseDouble(tfF2x2.getText());
        }

        if (tfF2x3.getText().equals("")){
            f2x3 = 0.0;
        }
        else {
            f2x3 = Double.parseDouble(tfF2x3.getText());
        }

        if (tfF2x4.getText().equals("")){
            f2x4 = 0.0;
        }
        else {
            f2x4 = Double.parseDouble(tfF2x4.getText());
        }

        if (tfF2x5.getText().equals("")){
            f2x5 = 0.0;
        }
        else {
            f2x5 = Double.parseDouble(tfF2x5.getText());
        }

        //Otros campos
        if (tfXInicial.getText().equals("")){
            xInicial = 1.0;
        }
        else {
            xInicial = Double.parseDouble(tfXInicial.getText());
        }
        if (tfYInicial.getText().equals("")){
            yInicial = 1.0;
        }
        else {
            yInicial = Double.parseDouble(tfYInicial.getText());
        }

        if (tfIteracionesMaximas.getText().equals("")){
            iteracionesMaximas = 20;
        }
        else {
            iteracionesMaximas = Integer.parseInt(tfIteracionesMaximas.getText());
            if (iteracionesMaximas < 1 || iteracionesMaximas > 25){
                iteracionesMaximas = 25;
                tfIteracionesMaximas.setText("25");
            }
        }

        if (tfPorcentajeErrorMaximo.getText().equals("")){
            porcentajeErrorMaximo = 0.05;
        }
        else {
            porcentajeErrorMaximo = Double.parseDouble(tfPorcentajeErrorMaximo.getText());
            if (porcentajeErrorMaximo < 0 || porcentajeErrorMaximo > 99){
                porcentajeErrorMaximo = .05;
                tfPorcentajeErrorMaximo.setText(".05");
            }
        }
    }

    private void clearResultados(){
        gridIteraciones.getChildren().removeAll(gridIteraciones.getChildren());
        gridIteraciones.getRowConstraints().removeAll(gridIteraciones.getRowConstraints());
    }
}