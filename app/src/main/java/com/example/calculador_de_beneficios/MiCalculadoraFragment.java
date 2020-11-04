package com.example.calculador_de_beneficios;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculador_de_beneficios.databinding.FragmentMiCalculadoraBinding;
public class MiCalculadoraFragment extends Fragment {
    private FragmentMiCalculadoraBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentMiCalculadoraBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MiCalculadoraViewModel miCalculadoraViewModel = new ViewModelProvider(this).get(MiCalculadoraViewModel.class);

        binding.calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double compra = Double.parseDouble(binding.compra.getText().toString());
                double venta = Double.parseDouble(binding.venta.getText().toString());
                double interes = Double.parseDouble(binding.interes.getText().toString());

                miCalculadoraViewModel.calcular(compra,venta,interes);
            }
        });

        miCalculadoraViewModel.beneficio.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double beneficio) {
                binding.beneficio.setText(String.format("%.2f",beneficio));
            }
        });
        miCalculadoraViewModel.errorVenta.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double ventaMinimo) {
                if (ventaMinimo != null) {
                    binding.venta.setError("El precio de venta no puede ser inferor a " + ventaMinimo);
                } else {
                    binding.venta.setError(null);
                }
            }
        });

        miCalculadoraViewModel.errorCompra.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double compraMinimo) {
                if (compraMinimo != null) {
                    binding.compra.setError("El precio de compra no puede ser inferior a " + compraMinimo );
                } else {
                    binding.compra.setError(null);
                }
            }
        });
        miCalculadoraViewModel.errorInteres.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double interesMinimo) {
                if (interesMinimo != null) {
                    binding.compra.setError("El interes no puede ser inferior a " + interesMinimo);
                } else {
                    binding.compra.setError(null);
                }
            }
        });
        miCalculadoraViewModel.calculando.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean calculando) {
                if (calculando) {
                    binding.calculando.setVisibility(View.VISIBLE);
                    binding.beneficio.setVisibility(View.GONE);
                } else {
                    binding.calculando.setVisibility(View.GONE);
                    binding.beneficio.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}