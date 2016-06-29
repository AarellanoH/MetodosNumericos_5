/*Clase que se encarga de "renderizar" el contenido en la clase NewtonRaphson.fxml,
* además invoca a la clase que contiene la lógica de NewtonRaphson: NewtonRaphson.java
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
package Metodos.NewtonRaphson;

import Metodos.MetodosMain;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NewtonRaphsonController {
    //Campos para tomar los coeficientes de la función.
    @FXML private TextField tfx0;
    private Double x0 = 0.0;
    @FXML private TextField tfx1;
    private Double x1 = 0.0;
    @FXML private TextField tfx2;
    private Double x2 = 0.0;
    @FXML private TextField tfx3;
    private Double x3 = 0.0;
    @FXML private TextField tfx4;
    private Double x4 = 0.0;
    @FXML private TextField tfx5;
    private Double x5 = 0.0;

    @FXML private TextField tfXInicial;
    private Double xInicial = 1.0;
    @FXML private TextField tfIteracionesMaximas;
    private int iteracionesMaximas = 20;
    @FXML private TextField tfPorcentajeErrorMaximo;
    private Double porcentajeErrorMaximo = .05;

    //Para mostrar resultados.
    @FXML private VBox vbIteraciones;
    @FXML private GridPane gridIteraciones;
    @FXML private Text txtConclusiones;

    @FXML public void btnExecute(){
        try {
            this.validarYLlenarDatos();
            this.resetResults();

            Double [] coeficientesOriginales = {x0, x1, x2, x3, x4, x5};
            NewtonRaphson newtonRaphson = new NewtonRaphson(coeficientesOriginales, xInicial, iteracionesMaximas, porcentajeErrorMaximo);

            newtonRaphson = newtonRaphson.aproximarRaiz();

            int numIteraciones = newtonRaphson.iteracionesX.length;

            //Crear la tabla
            gridIteraciones.getRowConstraints().add(new RowConstraints(50));
            for (int i = 0; i<numIteraciones; i++){
                RowConstraints rowConstraints = new RowConstraints(15);
                rowConstraints.setPrefHeight(15);
                gridIteraciones.getRowConstraints().add(rowConstraints);
            }

            //Titulos de cada columna
            Text txtIteracionTitle = new Text("Iteración");
            Text txtXTitle = new Text("X");
            Text txtErrorTitle = new Text("Error (%)");
            HBox hbIteracionTitle = new HBox(txtIteracionTitle);
            HBox hbXTitle = new HBox(txtXTitle);
            HBox hbErrorTitle = new HBox(txtErrorTitle);
            hbIteracionTitle.setAlignment(Pos.CENTER);
            hbXTitle.setAlignment(Pos.CENTER);
            hbErrorTitle.setAlignment(Pos.CENTER);
            gridIteraciones.add(hbIteracionTitle, 0, 0);
            gridIteraciones.add(hbXTitle, 1, 0);
            gridIteraciones.add(hbErrorTitle, 2, 0);

            //Desplegar resultados
            for (int i = 0; i < numIteraciones; i++){
                Text txtIteracion = new Text(Integer.toString(i));
                Text txtX = new Text(MetodosMain.doubleToString(newtonRaphson.iteracionesX[i]));
                Text txtError = new Text(MetodosMain.doubleToString(newtonRaphson.porcentajesError[i]));

                HBox hbIteracion = new HBox(txtIteracion);
                HBox hbX = new HBox(txtX);
                HBox hbError = new HBox(txtError);
                hbIteracion.setAlignment(Pos.CENTER);
                hbX.setAlignment(Pos.CENTER);
                hbError.setAlignment(Pos.CENTER);

                gridIteraciones.add(hbIteracion, 0, i + 1);
                gridIteraciones.add(hbX, 1, i + 1);
                gridIteraciones.add(hbError, 2, i + 1);
            }

            //Conclusiones
            String conclusion;
            if (newtonRaphson.encontroRaiz){
                if (newtonRaphson.raizMultiple){
                    conclusion = "Se encontró una raíz múltiple en x = ";
                }
                else {
                    conclusion = "Se encontró una raíz sencilla en x = ";
                }
                conclusion = conclusion + MetodosMain.doubleToString(newtonRaphson.iteracionesX[newtonRaphson.iteracionesX.length - 1]);
            }
            else {
                conclusion = "No se encontró raíz en " + iteracionesMaximas + " iteraciones.";
            }
            txtConclusiones.setText(conclusion);
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    private void validarYLlenarDatos(){
        //Llenar los campos de los coeficientes de la ecuacion.
        if (tfx0.getText().equals("")){
            x0 = 0.0;
        }
        else {
            x0 = Double.parseDouble(tfx0.getText());
        }

        if (tfx1.getText().equals("")){
            x1 = 0.0;
        }
        else {
            x1 = Double.parseDouble(tfx1.getText());
        }

        if (tfx2.getText().equals("")){
            x2 = 0.0;
        }
        else {
            x2 = Double.parseDouble(tfx2.getText());
        }

        if (tfx3.getText().equals("")){
            x3 = 0.0;
        }
        else {
            x3 = Double.parseDouble(tfx3.getText());
        }

        if (tfx4.getText().equals("")){
            x4 = 0.0;
        }
        else {
            x4 = Double.parseDouble(tfx4.getText());
        }

        if (tfx5.getText().equals("")){
            x5 = 0.0;
        }
        else {
            x5 = Double.parseDouble(tfx5.getText());
        }


        //Otros campos
        if (tfXInicial.getText().equals("")){
            xInicial = 1.0;
        }
        else {
            xInicial = Double.parseDouble(tfXInicial.getText());
        }

        if (tfIteracionesMaximas.getText().equals("")){
            iteracionesMaximas = 20;
        }
        else {
            iteracionesMaximas = Integer.parseInt(tfIteracionesMaximas.getText());
            if (iteracionesMaximas < 1 || iteracionesMaximas > 30){
                iteracionesMaximas = 30;
                tfIteracionesMaximas.setText("30");
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

    private void resetResults(){
        gridIteraciones.getChildren().removeAll(gridIteraciones.getChildren());
        gridIteraciones.getRowConstraints().removeAll(gridIteraciones.getRowConstraints());
    }
}
