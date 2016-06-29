//Clase que se encarga de la lógica del método de Newton Raphson Multivariable.
//Esta clase es llamada por NewtonRaphsonMVController.java
/*
*Autores:
*
*
*
*
 */
package Metodos.NewtonRaphsonMV;

import java.util.ArrayList;

public class NewtonRaphsonMV {
    //Resultados de X y Y en cada iteracion.
    Double[] iteracionesX;
    Double[] iteracionesY;
    //Porcentaje de error en cada iteracion.
    Double[] porcentajesError;

    //Converge o diverge.
    boolean converge;

    NewtonRaphsonMV(){}

    public static NewtonRaphsonMV encontrarInterseccion(Double[] coeficientesF1, Double[] coeficientesF2,
                                                        int iteracionesMaximas, Double porcentajeErrorMaximo,
                                                        Double xInicial, Double yInicial){
        int gradoPolinomio = coeficientesF1.length - 1;

        //Donde se guardaran los resultados que se tienen que entregar.
        ArrayList<Double> iteracionesX = new ArrayList<>();
        ArrayList<Double> iteracionesY = new ArrayList<>();
        ArrayList<Double> porcentajesError = new ArrayList<>();

        //Inicializar.
        int numIteraciones = 0;
        Double porcentajeErrorActual = 100.0;
        Double xActual = xInicial;
        Double yActual = yInicial;

        //Valores de F1, F2 y sus derivadas.
        Double f1;
        Double f2;
        Double derivadaF1;
        Double derivadaF2;

        //H y J para Delta
        Double h;
        Double j;

        while ((numIteraciones <= iteracionesMaximas) && porcentajeErrorActual > porcentajeErrorMaximo){
            //Borrar valores de otras iteraciones.
            f1 = 0.0;
            f2 = 0.0;
            derivadaF1 = 0.0;
            derivadaF2 = 0.0;

            //Calculo de derivadas.
            for (int k = gradoPolinomio; k >= 0; k--){
                f1 = (f1 * xActual) + coeficientesF1[k];
                f2 = (f2 * xActual) + coeficientesF2[k];
                if (k > 0){
                    derivadaF1 = (derivadaF1 * xActual) + f1;
                    derivadaF2 = (derivadaF2 * xActual) + f2;
                }
            }

            //Calculo de funciones.
            f1 = f1 - yActual;
            f2 = f2 - yActual;

            //Se calcula F y H.
            h = ((f1 * -1) - (f2 * -1)) / ((derivadaF1 * -1) - (derivadaF2 * -1));
            j = ((f2 * derivadaF1) - (f1 * derivadaF2)) / ((derivadaF1 * -1) - (derivadaF2 * -1));

            //Se calcula el porcentaje de error con H y J.
            porcentajeErrorActual = (Math.pow((Math.pow(h, 2.0) + Math.pow(j, 2.0)), (1.0 / 2.0))) * 100;

            //Se completo una nueva iteracion.
            numIteraciones ++;
            iteracionesX.add(xActual);
            iteracionesY.add(yActual);
            porcentajesError.add(porcentajeErrorActual);

            //Calculo de las nuevas X y Y.
            xActual = xActual - h;
            yActual = yActual - j;
        }

        //Llenar los resultados a ser entregados.
        NewtonRaphsonMV newtonRaphsonMV = new NewtonRaphsonMV();
        newtonRaphsonMV.iteracionesX = new Double[iteracionesX.size()];
        newtonRaphsonMV.iteracionesY = new Double[iteracionesY.size()];
        newtonRaphsonMV.porcentajesError = new Double[porcentajesError.size()];
        newtonRaphsonMV.iteracionesX = iteracionesX.toArray(newtonRaphsonMV.iteracionesX);
        newtonRaphsonMV.iteracionesY = iteracionesY.toArray(newtonRaphsonMV.iteracionesY);
        newtonRaphsonMV.porcentajesError = porcentajesError.toArray(newtonRaphsonMV.porcentajesError);

        newtonRaphsonMV.converge = (numIteraciones <= iteracionesMaximas);
        return newtonRaphsonMV;
    }
}
