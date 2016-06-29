//Clase que se encarga de la lógica de Operaciones Matriciales.
//Esta clase es llamada por OperacionesMatricialesController.java
/*
*Autores:
*
*
*
*
 */
package Metodos.OperacionesMatriciales;

public class OperacionesMatriciales {

    public static Double[][] sumar(Double[][] matA, Double[][] matB){
        //La matriz resultante de una sumar es del mismo tamaño de A y B.
        Double[][] matC = new Double[matA.length][matA[0].length];
        for (int i = 0; i<matA.length; i++){
            for (int j = 0; j<matA[i].length; j++){
                matC[i][j] = matA[i][j] + matB[i][j];
            }
        }
        return matC;
    }

    public static Double[][] restar(Double[][] matA, Double[][] matB){
        //La matriz resultante de una sumar es del mismo tamaño de A y B.
        Double[][] matC = new Double[matA.length][matA[0].length];
        for (int i = 0; i<matA.length; i++){
            for (int j = 0; j<matA[i].length; j++){
                matC[i][j] = matA[i][j] - matB[i][j];
            }
        }
        return matC;
    }

    public static Double[][] multiplicar(Double[][] matA, Double[][] matB){
        //La matriz resultante de una multiplicacion es del tamaño de regnlones de A y columnas de B.
        Double[][] matC = new Double[matA.length][matB[0].length];
        matC = OperacionesMatriciales.llenarDeCeros(matC);

        //Algoritmo para implementar la multiplicacion
        for (int i = 0; i<matA.length; i++){
            for (int j = 0; j<matB[0].length; j++){
                for (int k = 0; k<matB.length; k++){
                    Double valorA = matA[i][k];
                    Double valorB = matB[k][j];
                    matC[i][j] = matC[i][j] + (valorA * valorB);
                }
            }
        }
        return matC;
    }

    private static Double[][] llenarDeCeros(Double[][] mat){
        for (int i = 0; i<mat.length; i++){
            for (int j = 0; j < mat[0].length; j++){
                mat[i][j] = 0.0;
            }
        }
        return mat;
    }
}
