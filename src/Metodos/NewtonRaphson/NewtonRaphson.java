//Clase que se encarga de la lógica del método de Newton Raphson.
//Esta clase es llamada por NewtonRaphsonController.java
/*
*Autores:
*
*
*
*
 */
package Metodos.NewtonRaphson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NewtonRaphson {
    //Los coeficientes de la función original. coeficientesOriginales[0] = constante; coefOrig[1] = x; coefOrig[5] = x^5
    private Double[] coeficientesOriginales;

    private int iteracionesMaximas;
    private Double porcentajeErrorMaximo;
    private Double xInicial;


    //Resultados de x en cada iteracion.
    Double[] iteracionesX;
    Double[] porcentajesError;

    boolean encontroRaiz;
    boolean raizMultiple;

    private static int GRADO_POLINOMIO = 5;

    NewtonRaphson(Double[] coeficientesOriginales, Double xInicial, int iteracionesMaximas, Double porcentajeErrorMaximo){
        this.coeficientesOriginales = coeficientesOriginales;
        this.xInicial = xInicial;
        this.iteracionesMaximas = iteracionesMaximas;
        this.porcentajeErrorMaximo = porcentajeErrorMaximo;
    }

    NewtonRaphson aproximarRaiz(){
        //La funcion de derivada tiene un valor menos porque la constante desaparece.
        Double [] coeficientesDerivadas = new Double[this.coeficientesOriginales.length - 1];
        //Derivar (Obtener coeficientes de la funcion derivada)
        coeficientesDerivadas[0] = this.coeficientesOriginales[1];
        coeficientesDerivadas[1] = this.coeficientesOriginales[2] * 2;
        coeficientesDerivadas[2] = this.coeficientesOriginales[3] * 3;
        coeficientesDerivadas[3] = this.coeficientesOriginales[4] * 4;
        coeficientesDerivadas[4] = this.coeficientesOriginales[5] * 5;

        Double porcentajeErrorActual = 100.0;
        ArrayList<Double> iteracionesX = new ArrayList<>();
        ArrayList<Double> porcentajesError = new ArrayList<>();
        int numIteraciones = 0;

        //Funcion evaluada en x.
        Double fDeX = 0.0;
        //Funcion derivada evaluada en x.
        Double fPrimaDeX = 0.0;

        //La iteracion 0 es = lo original, con 100% de error.
        iteracionesX.add(xInicial);
        porcentajesError.add(100.0);

        //x en el numero de iteracion actual.
        Double xActual = this.xInicial;
        //x en iteracion + 1.
        Double xSiguiente;



        while ((porcentajeErrorActual > porcentajeErrorMaximo) && (numIteraciones <= this.iteracionesMaximas)){
            //Evaluar la funcion en f(x).
            for (Integer i =0; i<this.coeficientesOriginales.length - 1; i++){
                fDeX = fDeX + this.coeficientesOriginales[i] * Math.pow(xActual, i.doubleValue());
            }
            //Evaluar la funcion en f'(x).
            for (Integer i =0; i<coeficientesDerivadas.length - 1; i++){
                fPrimaDeX = fPrimaDeX + coeficientesDerivadas[i] * Math.pow(xActual, i.doubleValue());
            }

            //Aplicar la formula iterativa de Newton-Raphson.
            xSiguiente = xActual - (fDeX/fPrimaDeX);
            porcentajeErrorActual = Math.abs((xSiguiente - xActual)/xSiguiente * 100);
            xActual = xSiguiente;

            //Agregar los resultados de la iteracion a la lista.
            iteracionesX.add(xActual);
            porcentajesError.add(porcentajeErrorActual);

            //Reiniciar los valores de las evaluaciones de f(x) y f'(x).
            fDeX = 0.0;
            fPrimaDeX = 0.0;

            numIteraciones++;
        }

        //Asignar valores.
        this.iteracionesX = new Double[iteracionesX.size()];
        this.iteracionesX = iteracionesX.toArray(this.iteracionesX);
        this.porcentajesError = new Double[porcentajesError.size()];
        this.porcentajesError = porcentajesError.toArray(this.porcentajesError);

        this.encontroRaiz = (numIteraciones <= iteracionesMaximas);
        if (this.encontroRaiz){
            //Evaluar la funcion en f(x).
            for (Integer i =0; i<this.coeficientesOriginales.length - 1; i++){
                fDeX = fDeX + this.coeficientesOriginales[i] * Math.pow(xActual, i.doubleValue());
            }
            //Evaluar la funcion en f'(x).
            for (Integer i =0; i<coeficientesDerivadas.length - 1; i++){
                fPrimaDeX = fPrimaDeX + coeficientesDerivadas[i] * Math.pow(xActual, i.doubleValue());
            }

            DecimalFormat f2 = new DecimalFormat(".##");
            Double fDeXCon2Decimales = Double.parseDouble(f2.format(fDeX));
            Double fPrimaDeXCon2Decimales = Double.parseDouble(f2.format(fPrimaDeX));

            if ((fDeXCon2Decimales == 0) && (fPrimaDeXCon2Decimales == 0)){
                this.raizMultiple = true;
            }
            else {
                this.raizMultiple = false;
            }
        }
        return this;
    }

}
