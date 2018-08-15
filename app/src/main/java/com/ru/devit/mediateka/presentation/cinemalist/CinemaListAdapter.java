package com.ru.devit.mediateka.presentation.cinemalist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.AbstractCinemaListAdapter;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CinemaListAdapter extends AbstractCinemaListAdapter<CinemaViewHolder , CinemaViewHolder> {

    CinemaListAdapter(OnCinemaClickListener onCinemaClickListener) {
        super(onCinemaClickListener);
    }

    @Override
    @NonNull
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema , parent , false);
        return new CinemaViewHolder(view , onCinemaClickListener);
    }
}
