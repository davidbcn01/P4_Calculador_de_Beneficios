package com.example.calculador_de_beneficios;

public class SimuladorCalculadora {
    public static class Solicitud {
        public double compra;
        public double venta;
        public double interes;

        public Solicitud(double compra, double venta, double interes) {
            this.compra = compra;
            this.venta = venta;
            this.interes = interes;
        }
    }
    interface Callback {
        void cuandoEsteCalculadoElBeneficio(double beneficio);
        void cuandoHayaErrorDeVentaInferiorAlMinimo(double ventaMinimo);
        void cuandoHayaErrorDeCompraInferiorAlMinimo(double compraMinimo);
        void cuandoHayaErrorDeInteresInferiorAlMinimo(double interesMinimo);
        void cuandoEmpieceElCalculo();
        void cuandoFinaliceElCalculo();


    }
    public void calcular(Solicitud solicitud, Callback callback) {
        callback.cuandoEmpieceElCalculo();
        double interesMinimo = 0;
        double ventaMinimo = 0;
        double compraMinimo = 0;
        try {
            Thread.sleep(6000);   // simular operacion de larga duracion (10s)
            interesMinimo = 1;
            ventaMinimo = 150;
            compraMinimo = 150;
        } catch (InterruptedException e) {}
            boolean error = false;
        if (solicitud.compra < compraMinimo){
            callback.cuandoHayaErrorDeCompraInferiorAlMinimo(compraMinimo);
            error = true;
        }
        if (solicitud.venta < ventaMinimo){
            callback.cuandoHayaErrorDeVentaInferiorAlMinimo(ventaMinimo);
            error = true;
        }
        if (solicitud.interes < interesMinimo){
            callback.cuandoHayaErrorDeInteresInferiorAlMinimo(interesMinimo);
            error = true;
        }
        if(!error) {
            callback.cuandoEsteCalculadoElBeneficio(solicitud.venta - (solicitud.venta * (solicitud.interes / 100)) - solicitud.compra);
        }
        callback.cuandoFinaliceElCalculo();
    }
}
