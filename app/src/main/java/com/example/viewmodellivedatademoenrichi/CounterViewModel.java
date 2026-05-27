package com.example.viewmodellivedatademoenrichi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class CounterViewModel extends ViewModel {

    private static final String COUNT_KEY = "count_key";
    private final SavedStateHandle handle;

    // Le SavedStateHandle est injecté automatiquement par le ViewModelProvider
    public CounterViewModel(SavedStateHandle handle) {
        this.handle = handle;
    }

    public void increment() {
        int current = getSafeCount();
        handle.set(COUNT_KEY, current + 1);
    }

    public void decrement() {
        int current = getSafeCount();
        handle.set(COUNT_KEY, current - 1);
    }

    public void reset() {
        handle.set(COUNT_KEY, 0);
    }

    // Retourne un LiveData lié au SavedStateHandle
    // Toute modification via handle.set() notifiera les observateurs de ce LiveData
    public LiveData<Integer> getCount() {
        return handle.getLiveData(COUNT_KEY, 0); // 0 est la valeur par défaut
    }

    private int getSafeCount() {
        Integer current = handle.get(COUNT_KEY);
        return (current != null) ? current : 0;
    }
}
