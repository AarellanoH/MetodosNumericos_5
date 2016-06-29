/*Clase que se encarga de "renderizar" el contenido en la clase Horner.fxml,
* además invoca a la clase que contiene la lógica de Horner: Horner.java
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
package Metodos.Horner;

import Metodos.MetodosMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.LinkedList;

public class HornerController {
    /*------------------------------------START OF THE VARIABLES FROM THE FXML FILE-----------------------------------*/

    //Para saber a partir de que renglon estaran los resultados.
    private static final int NUMBER_OF_ROWS_USED = 5;

    //Para desplegar los resultados.
    @FXML private GridPane gridPane;

    //Para poder limpiar los resultados
    private LinkedList<Node> insertedNodes = new LinkedList<Node>();

    //Campos de input para los coeficientes de la ecuacion. Ademas, se declaran las variables en que se asignaran los
    //valores sacados de estos campos de input.
    @FXML private TextField tfx0;
    private Double x0;
    @FXML private TextField tfx1;
    private Double x1;
    @FXML private TextField tfx2;
    private Double x2;
    @FXML private TextField tfx3;
    private Double x3;
    @FXML private TextField tfx4;
    private Double x4;
    @FXML private TextField tfx5;
    private Double x5;

    //Campos para calcular los intervalos. Tambien se declaran las variables en que se asignaran los valores sacados
    //de estos campos de input.
    @FXML private TextField tfRangoMenor;
    private Double rangoMenor;
    @FXML private TextField tfRangoMayor;
    private Double rangoMayor;
    @FXML private TextField tfNIntervalos;
    private int nIntervalos;


    //Buttons.
    @FXML private Button btnExecute;

    /*------------------------------------END OF THE VARIABLES FROM THE FXML FILE-------------------------------------*/


    //Test action.
    @FXML protected void btnExecuteAction(ActionEvent event){
        resetResults();
        try{
            this.validarYLlenarDatos();

            //Inicializar con los valores sacados de los campos de input.
            Horner horner = new Horner(x0, x1, x2, x3, x4, x5, rangoMenor, rangoMayor, nIntervalos);

            //Poner los títulos de cada columna
            Text txtXValueTitle = new Text("x");
            Text txtfEvaluadaEnXTitle = new Text("f(x)");
            Text txtPrimerDerivadaEvaluadaEnXTitle = new Text("f'(x)");
            Text txtSegundaDerivadaEvaluadaEnXTitle = new Text("f''(x)");
            Text txtConclusionTitle = new Text("Conclusión");

            HBox hbXValueTitle = new HBox(txtXValueTitle);
            HBox hbfEvaluadaEnXTitle = new HBox(txtfEvaluadaEnXTitle);
            HBox hbPrimerDerivadaEvaluadaEnXTitle = new HBox(txtPrimerDerivadaEvaluadaEnXTitle);
            HBox hbSegundaDerivadaEvaluadaEnXTitle = new HBox(txtSegundaDerivadaEvaluadaEnXTitle);
            HBox hbConclusionTitle = new HBox(txtConclusionTitle);

            hbXValueTitle.setAlignment(Pos.CENTER);
            hbfEvaluadaEnXTitle.setAlignment(Pos.CENTER);
            hbPrimerDerivadaEvaluadaEnXTitle.setAlignment(Pos.CENTER);
            hbSegundaDerivadaEvaluadaEnXTitle.setAlignment(Pos.CENTER);
            hbConclusionTitle.setAlignment(Pos.CENTER);

            gridPane.add(hbXValueTitle, 0, NUMBER_OF_ROWS_USED - 1);
            gridPane.add(hbfEvaluadaEnXTitle, 1, NUMBER_OF_ROWS_USED - 1);
            gridPane.add(hbPrimerDerivadaEvaluadaEnXTitle, 2, NUMBER_OF_ROWS_USED - 1);
            gridPane.add(hbSegundaDerivadaEvaluadaEnXTitle, 3, NUMBER_OF_ROWS_USED - 1);
            gridPane.add(hbConclusionTitle, 4, NUMBER_OF_ROWS_USED - 1);

            insertedNodes.add(hbXValueTitle);
            insertedNodes.add(hbfEvaluadaEnXTitle);
            insertedNodes.add(hbPrimerDerivadaEvaluadaEnXTitle);
            insertedNodes.add(hbSegundaDerivadaEvaluadaEnXTitle);
            insertedNodes.add(hbConclusionTitle);

            //Mandar a llamar al metodo que se encarga de obtener resultados.
            LinkedList<Horner> results = horner.obtenerResultados();
            for (int i = 0; i < results.size(); i ++){
                //Sacar los resultados de cada linea de resultados.
                Horner result = results.get(i);

                Text txtXValue = new Text(MetodosMain.doubleToString(result.getxValue()));
                Text txtFEvaluadaEnX = new Text(MetodosMain.doubleToString(result.getfEvaluadaEnX()));
                Text txtPrimerDerivadaEvaluadaEnX = new Text(MetodosMain.doubleToString(result.getPrimerDerivadaEvaluadaEnX()));
                Text txtSegundaDerivadaEvaluadaEnX = new Text(MetodosMain.doubleToString(result.getSegundaDerivadaEvaluadaEnX()));
                Text txtConclusion = new Text(result.getConclusion());

                HBox hbXValue = new HBox(txtXValue);
                HBox hbFEvaluadaEnX = new HBox(txtFEvaluadaEnX);
                HBox hbPrimerDerivadaEvaluadaEnX = new HBox(txtPrimerDerivadaEvaluadaEnX);
                HBox hbSegundaDerivadaEvaluadaEnX = new HBox(txtSegundaDerivadaEvaluadaEnX);
                HBox hbConclusion = new HBox(txtConclusion);

                hbXValue.setAlignment(Pos.CENTER);
                hbFEvaluadaEnX.setAlignment(Pos.CENTER);
                hbPrimerDerivadaEvaluadaEnX.setAlignment(Pos.CENTER);
                hbSegundaDerivadaEvaluadaEnX.setAlignment(Pos.CENTER);
                hbConclusion.setAlignment(Pos.CENTER);

                gridPane.add(hbXValue, 0, i + HornerController.NUMBER_OF_ROWS_USED);
                gridPane.add(hbFEvaluadaEnX, 1, i + HornerController.NUMBER_OF_ROWS_USED);
                gridPane.add(hbPrimerDerivadaEvaluadaEnX, 2, i + HornerController.NUMBER_OF_ROWS_USED);
                gridPane.add(hbSegundaDerivadaEvaluadaEnX, 3, i + HornerController.NUMBER_OF_ROWS_USED);
                gridPane.add(hbConclusion, 4, i + HornerController.NUMBER_OF_ROWS_USED);

                //Para llevar cuenta de qué es lo que se insertó y poder borrarlo después.
                insertedNodes.add(hbXValue);
                insertedNodes.add(hbFEvaluadaEnX);
                insertedNodes.add(hbPrimerDerivadaEvaluadaEnX);
                insertedNodes.add(hbSegundaDerivadaEvaluadaEnX);
                insertedNodes.add(hbConclusion);
            }
        }
        catch (IllegalArgumentException e){
            MetodosMain.showErrorDialog("El número de intervalos que ingresaste es inválido.\nEl número de " +
                    "intervalos tiene que ser 1> n > 11");
        }
        catch (Exception e){
            MetodosMain.showErrorDialog();
        }
    }

    //Asignar el valor default a los campos que esten vacios (0 para los coeficientes de las ecuaciones, y el intervalo
    //de [0,10] con 10 intervalos) si el usuario no ingreso nada. Si ingreso algo invalido, el programa se detendra.
    private void validarYLlenarDatos() throws IllegalArgumentException {

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


        //Llenar los campos de los intervalos
        if (tfRangoMenor.getText().equals("")){
            rangoMenor = 0.0;
        }
        else {
            rangoMenor = Double.parseDouble(tfRangoMenor.getText());
        }

        if (tfRangoMayor.getText().equals("")){
            rangoMayor = 10.0;
        }
        else {
            rangoMayor = Double.parseDouble(tfRangoMayor.getText());
        }

        if (tfNIntervalos.getText().equals("")){
            nIntervalos = 10;
        }
        else {
            nIntervalos = Integer.parseInt(tfNIntervalos.getText());
            if ((nIntervalos < 1) || (nIntervalos > 10)){
                throw new IllegalArgumentException();
            }
        }
    }

    private void resetFields(){
        tfx0.setText("");
        tfx1.setText("");
        tfx2.setText("");
        tfx3.setText("");
        tfx4.setText("");
        tfx5.setText("");
        tfRangoMenor.setText("");
        tfRangoMayor.setText("");
        tfNIntervalos.setText("");
    }

    private void resetResults(){
        gridPane.getChildren().removeAll(insertedNodes);
    }

}
