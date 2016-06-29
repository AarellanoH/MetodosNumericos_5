//Clase que se encarga de la lógica del método de  Gauss.
//Esta clase es llamada por HornerController.java
/*
*Autores:
*
*
*
*
 */
package Metodos.Horner;

import java.util.LinkedList;

public class Horner {

    private static final int EQUATION_NUMBER = 5;

    //Textos usados como conclusion cuando se encuentra algo.
    private static final String conclusionMaximo = "Máximo";
    private static final String conclusionMinimo = "Mínimo";
    private static final String conclusionInflexion = "Punto de inflexión";
    private static final String conclusionCreciente = "Creciente";
    private static final String conclusionDecreciente = "Decreciente";

    //Los datos relevantes que se tienen que obtener para cada punto de X.
    private Double xValue;
    private Double fEvaluadaEnX;
    private Double primerDerivadaEvaluadaEnX;
    private Double segundaDerivadaEvaluadaEnX;
    private String conclusion;

    //Los coeficientes de cada grado de x en la ecuacion.
    private Double x0;
    private Double x1;
    private Double x2;
    private Double x3;
    private Double x4;
    private Double x5;

    //Para los intervalos [a,b] con n particiones.
    private Double rangoMenor;
    private Double rangoMayor;
    private int intervalNumber;

    //El tamaño que tendra cada intervalo.
    private Double intervaloCalculado;

    //Arreglo que representa una funcion polinomial de grado 5. Contiene los coeficientes de cada grado de x.
    //coeficientesOriginales[0] contiene el coeficiente de la constante (x^0),
    // coeficientesOriginales[2] = coeficiente de x^2.
    private Double[] coeficientesOriginales = new Double[EQUATION_NUMBER + 1];

    public Horner(Double x0, Double x1, Double x2, Double x3, Double x4, Double x5, Double rangoMenor, Double rangoMayor, int intervalNumber) {
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.x4 = x4;
        this.x5 = x5;
        this.rangoMenor = rangoMenor;
        this.rangoMayor = rangoMayor;
        this.intervalNumber = intervalNumber;

        this.intervaloCalculado = (this.rangoMayor - this.rangoMenor) / this.intervalNumber;
    }

    private Horner(Double xValue, Double fEvaluadaEnX, Double primerDerivadaEvaluadaEnX,
                   Double segundaDerivadaEvaluadaEnX, String conclusion){
        this.xValue = xValue;
        this.fEvaluadaEnX = fEvaluadaEnX;
        this.primerDerivadaEvaluadaEnX = primerDerivadaEvaluadaEnX;
        this.segundaDerivadaEvaluadaEnX = segundaDerivadaEvaluadaEnX;
        this.conclusion = conclusion;
    }

    //Llenar el arreglo que representa los coeficientes de la funcion polinomial con los datos originales.
    private void fillOriginalArray(){
        coeficientesOriginales[0] = x0;
        coeficientesOriginales[1] = x1;
        coeficientesOriginales[2] = x2;
        coeficientesOriginales[3] = x3;
        coeficientesOriginales[4] = x4;
        coeficientesOriginales[5] = x5;
    }

    //Llenar los coeficientes de la primera y segunda derivada.
    public LinkedList<Horner> obtenerResultados(){
        fillOriginalArray();

        //La x con la que se esta trabajando, en la que se evalua f(x), f'(x), f''(x) en cada renglon
        Double xActual = this.rangoMenor;

        //Arreglos que serviran para evaluar metodo de Horner e ir acumulando valores.
        //Cada vez que se deriva, se pierde una constante, es por eso que los arreglos van decreciendo por 1 cada vez.
        Double[] evaluada = new Double[EQUATION_NUMBER + 1];
        Double[] primaEvaluada = new Double[EQUATION_NUMBER];
        Double[] biPrimaEvaluada = new Double[EQUATION_NUMBER - 1];

        LinkedList<Horner> results = new LinkedList<Horner>();

        //Generar el numero de intervalos requeridos.
        for (int i = 0; i< intervalNumber + 1; i++){

            //Llenar las evaluadas. Empieza con la x de mayor potencia y acaba con la de menor potencia.
            for (int j = coeficientesOriginales.length - 1; j>=0; j--){
                if(j == coeficientesOriginales.length - 1){
                    evaluada[j] = coeficientesOriginales[j];
                }
                else{
                    evaluada[j] = coeficientesOriginales[j] + evaluada[j + 1] * xActual;
                }
            }

            //Llenar las primas evaluadas[f'(x)]. De mayor a menor potencia de x.
            for (int j = primaEvaluada.length - 1; j>=0; j--){
                if(j == primaEvaluada.length - 1){
                    primaEvaluada[j] = evaluada[j + 1];
                }
                else{
                    primaEvaluada[j] = evaluada[j + 1] + primaEvaluada[j + 1] * xActual;
                }
            }

            //Llenar las biPrimas evaluadas[f''(x)]. De mayor a menor potencia de x.
            for (int j = biPrimaEvaluada.length - 1; j>=0; j--){
                if(j == biPrimaEvaluada.length - 1){
                    biPrimaEvaluada[j] = primaEvaluada[j + 1];
                }
                else{
                    biPrimaEvaluada[j] = primaEvaluada[j + 1] + biPrimaEvaluada[j + 1] * xActual;
                }
            }


            Double fEvaluadaEnX = evaluada[0];
            Double primerDerivadaEvaluadaEnX = primaEvaluada[0];
            Double segundaDerivadaEvaluadaEnX = biPrimaEvaluada[0] * 2;

            String conclusion;
            //Obtener las conclusiones de acuerdo a primer y segunda derivada.
            if (primerDerivadaEvaluadaEnX == 0){
                if (segundaDerivadaEvaluadaEnX == 0){
                    conclusion = Horner.conclusionInflexion;
                }
                else if (segundaDerivadaEvaluadaEnX > 0){
                    conclusion = Horner.conclusionMinimo;
                }
                else {
                    conclusion = Horner.conclusionMaximo;
                }
            }
            else {
                if (segundaDerivadaEvaluadaEnX > 0){
                    conclusion = Horner.conclusionCreciente;
                }
                else {
                    conclusion = Horner.conclusionDecreciente;
                }
            }

            //Generar un renglon de resultados y añadirlo a la lista de resultados.
            Horner horner = new Horner(xActual, fEvaluadaEnX, primerDerivadaEvaluadaEnX,
                    segundaDerivadaEvaluadaEnX, conclusion);
            results.add(horner);

            //Aumentar la xActual
            xActual = xActual + intervaloCalculado;
        }

        return results;
    }

    public Double getxValue() {
        return xValue;
    }

    public Double getfEvaluadaEnX() {
        return fEvaluadaEnX;
    }

    public Double getPrimerDerivadaEvaluadaEnX() {
        return primerDerivadaEvaluadaEnX;
    }

    public Double getSegundaDerivadaEvaluadaEnX() {
        return segundaDerivadaEvaluadaEnX;
    }

    public String getConclusion() {
        return conclusion;
    }
}
