//Clase que se encarga de la lógica del método de Gauss.
//Esta clase es llamada por GaussController.java
/*
*Autores:
*
*
*
*
 */
package Metodos.Gauss;

import Metodos.MetodosMain;
import javafx.scene.control.TextInputDialog;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.DoubleStream;

public class Gauss {

    double[][] matTrianguloInferior;
    double[][] matX;
    boolean multiplesSoluciones = false;
    boolean inconsistente = false;


    private Gauss(){}

    static Gauss resolverSEL(double [][] matA, double [][] matB) throws Exception{
        double [][] matAmpliada = new double[matA.length][matA[0].length + 1];

        //Para facilitar el codigo
        int numColsA = matA[0].length;
        int numFilasAmpliada = matAmpliada.length;
        int numColsAmpliada = matAmpliada[0].length;

        //Juntar matA con matB en una sola matriz. Como la matB es de 1 sola columna, la matAmpliada es de
        //  tamaño filasA x columnasA + 1
        for (int i = 0; i < numFilasAmpliada; i++) {
            for (int j = 0; j < numColsAmpliada; j++){
                //Si es la ultima columna de la matAmpliada, se copia el contenido de matB, si no, se copia loa de matA.
                matAmpliada[i][j] = j == numColsAmpliada - 1 ? matB[i][0] : matA[i][j];
            }
        }

        //La columna que se intenta hacer 0.
        int columnaActual;
        //El renglon con el que se esta trabajando.
        int renglonActual;

        //Se hacen 0's en cada columna. El numero de veces que se hacen 0's depende del numero de renglones que hay,
        //  si hay 4 filas, se hacen 0's en 3 columnas, si hay 5 filas, se hacen 0's en 4 columnas, porque lo que se busca
        //  es un triangulo de 0's en la parte inferior de una matriz (abajo de la diagonal de la matA original).
        for (columnaActual = 0; columnaActual < numColsA; columnaActual ++){

            double pivoteMayor = Math.abs(matAmpliada[columnaActual][columnaActual]);
            int renglonConPivoteMayor = columnaActual;
            //Buscar el mayor elemento en esta columna en renglones en los que no se ha pivoteado
            //  y cambiar el renglon en caso de haber encontrado uno mayor.
            for (int i = columnaActual + 1; i<numFilasAmpliada; i++){
                if (Math.abs(matAmpliada[i][columnaActual]) > pivoteMayor){
                    renglonConPivoteMayor = i;
                }
            }

            //Se encontro un renglon con un pivote mayor. Se debe hacer intercambio de renglon.
            if (columnaActual != renglonConPivoteMayor){
                double temp[] = matAmpliada[columnaActual];
                matAmpliada[columnaActual] = matAmpliada[renglonConPivoteMayor];
                matAmpliada[renglonConPivoteMayor] = temp;
            }


            //Los renglones se convierten a partir del numero de columna + 1.
            for (renglonActual = columnaActual + 1; renglonActual < numFilasAmpliada; renglonActual++){
                //Pivote k,k.
                double pivote = matAmpliada[columnaActual][columnaActual];
                if (pivote == 0){
                    throw new IllegalArgumentException("Division0");
                }

                //El numero por el que se va a multiplicar cada elemento de cierta columna.
                double mulitplicador = ((-1)*matAmpliada[renglonActual][columnaActual])/pivote;

                //Cambiar los elementos del renglon de acuerdo al elemento pivote
                for (int i = 0; i < numColsAmpliada; i++){
                    matAmpliada[renglonActual][i] += mulitplicador * matAmpliada[columnaActual][i];
                }
            }
        }
        return resolverTrianguloSuperior(matAmpliada);
    }

    private static Gauss resolverTrianguloSuperior(double[][] matrTrianguloInferior){
        Gauss gauss = new Gauss();
        gauss.matTrianguloInferior = matrTrianguloInferior;

        /*double[][] matX;
        boolean multiplesSoluciones;*/
        boolean inconsistente;

        int numIncognitas = matrTrianguloInferior.length;

        //Saber si el sistema es inconsistente.
        double sumaUltimoRenglonLadoIzquierdo = DoubleStream.of(matrTrianguloInferior[numIncognitas - 1]).sum()
                - matrTrianguloInferior[numIncognitas - 1][numIncognitas];
        inconsistente = Double.parseDouble(MetodosMain.doubleToString(sumaUltimoRenglonLadoIzquierdo)) == 0
                && (Double.parseDouble(MetodosMain.doubleToString(matrTrianguloInferior[numIncognitas - 1] [numIncognitas])) != 0);

        if (inconsistente){
            gauss.inconsistente = true;
            return gauss;
        }

        //Saber si el SEL tiene multiples soluciones o no.
        boolean mulutiplesSoluciones = false;
        double sumaUltimoRenglon = DoubleStream.of(matrTrianguloInferior[numIncognitas -1]).sum();
        mulutiplesSoluciones = Double.parseDouble(MetodosMain.doubleToString(sumaUltimoRenglon)) == 0;
        gauss.multiplesSoluciones = Double.parseDouble(MetodosMain.doubleToString(sumaUltimoRenglon)) == 0;


        //La matrizX es una matriz columna (1 sola columna).
        double [][] matX = new double[numIncognitas][1];
        //Inicializar la matriz de respuestas con 0 para que las multiplicaciones de variables no calculadas no afecten.
        for (int i = 0; i < matX.length; i++){
            matX[i][0] = 0.0;
        }

        for (int i = numIncognitas - 1; i>= 0; i--){
            //El valor constante de la ecuacion (lado derecho -> Ax = b)
            double valorB = matrTrianguloInferior[i][numIncognitas];

            //El valor por el que se debe dividir el lado derecho.
            //  Ej. si se calcula x2, se debe de dividir sobre el coeficiente de x2 despues.
            double divisor = matrTrianguloInferior[i][i];

            //Pasar al lado derecho (se les invierte el signo) de la ecuacion los valores de las A (en Ax = b)
            for (int colMatrizA = 0; colMatrizA < numIncognitas; colMatrizA++){
                valorB = valorB - matrTrianguloInferior[i][colMatrizA] * matX[colMatrizA][0];
            }

            //Si tiene multiples soluciones, pedir input al usuario y darle ese valor a la ultima variable
            //  por ejemplo, z en un SEL de 3 incognitas.
            if (mulutiplesSoluciones && i == numIncognitas - 1) {
                double input;
                while (true) {
                    try {
                        TextInputDialog dialog = new TextInputDialog("1");
                        dialog.setTitle("Sistema de ecuaciones lineales con múltiples soluciones");
                        dialog.setHeaderText("Has ingresado un sistema de ecuaciones lineales que tiene" +
                                "mútiples soluciones.\nPara continuar, ingresa el valor que se le asignará a " +
                                " la variable \'a" + i + "\'");
                        Optional<String> stringOptional = dialog.showAndWait();
                        input = Double.parseDouble(stringOptional.get());
                        break;
                    } catch (Exception e) {
                        MetodosMain.showErrorDialog();
                    }
                }
                matX[i][0] = input;
            } else {
                matX[i][0] = valorB/divisor;
            }
        }
        gauss.matX = matX;
        return gauss;
    }
}
