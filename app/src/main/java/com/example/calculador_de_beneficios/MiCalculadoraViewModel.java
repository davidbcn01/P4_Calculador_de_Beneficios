package com.example.calculador_de_beneficios;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MiCalculadoraViewModel extends AndroidViewModel {
    Executor executor;

    SimuladorCalculadora simulador;

    MutableLiveData<Double> beneficio = new MutableLiveData<>();
    MutableLiveData<Double> errorCompra = new MutableLiveData<>();
    MutableLiveData<Double> errorVenta = new MutableLiveData<>();
    MutableLiveData<Double> errorInteres = new MutableLiveData<>();
    MutableLiveData<Boolean> calculando = new MutableLiveData<>();
    public MiCalculadoraViewModel(@NonNull Application application) {
        super(application);

        executor = Executors.newSingleThreadExecutor();
        simulador = new SimuladorCalculadora();
    }

    public void calcular(double compra, double venta, double interes) {

        final SimuladorCalculadora.Solicitud solicitud = new SimuladorCalculadora.Solicitud(compra,venta,interes);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                simulador.calcular(solicitud, new SimuladorCalculadora.Callback() {
                    @Override
                    public void cuandoEsteCalculadoElBeneficio(double b) {
                        errorCompra.postValue(null);
                        errorVenta.postValue(null);
                        errorInteres.postValue(null);
                        beneficio.postValue(b);

                    }

                    @Override
                    public void cuandoHayaErrorDeVentaInferiorAlMinimo(double ventaMinimo) {

                        errorVenta.postValue(ventaMinimo);
                    }
                    @Override
                    public void cuandoEmpieceElCalculo() {
                        calculando.postValue(true);
                    }
                    @Override
                    public void cuandoFinaliceElCalculo() {
                        calculando.postValue(false);
                    }

                    @Override
                    public void cuandoHayaErrorDeCompraInferiorAlMinimo(double compraMinimo) {
                        errorCompra.postValue(compraMinimo);
                    }

                    @Override
                    public void cuandoHayaErrorDeInteresInferiorAlMinimo(double interesMinimo) {

                        errorInteres.postValue(interesMinimo);
                    }

                });
              /*  double resultado = simulador.calcular(solicitud);
                beneficio.postValue(resultado); */
            }
        });
    }
}


