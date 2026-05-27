package com.example.viewmodellivedatademoenrichi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private CounterViewModel viewModel;
    private TextView tvCount;
    private Button btnIncrement, btnDecrement, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        btnIncrement = findViewById(R.id.btnIncrement);
        btnDecrement = findViewById(R.id.btnDecrement);
        btnReset = findViewById(R.id.btnReset);

        // 1. Récupération (ou création) du ViewModel
        viewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        // 2. Observation du LiveData (Remis en service pour la version finale)
        viewModel.getCount().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer newCount) {
                tvCount.setText(String.valueOf(newCount));  // mise à jour automatique !
            }
        });

        btnIncrement.setOnClickListener(v -> viewModel.increment());
        btnDecrement.setOnClickListener(v -> viewModel.decrement());
        btnReset.setOnClickListener(v -> viewModel.reset());
    }
}
