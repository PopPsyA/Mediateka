package com.ru.devit.mediateka.presentation.cinemalist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CinemaListAdapter extends RecyclerView.Adapter<CinemaViewHolder> {

    private final CinemaListPresenter presenter;
    private final List<Cinema> cinemas;
    private final Set<Cinema> cinemaSet;

    public CinemaListAdapter(CinemaListPresenter presenter) {
        this.presenter = presenter;
        cinemas = new ArrayList<>();
        cinemaSet = new LinkedHashSet<>();
    }

    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cinema , parent , false);
        return new CinemaViewHolder(view , presenter);
    }

    @Override
    public void onBindViewHolder(CinemaViewHolder holder, int position) {
        Cinema cinema = cinemas.get(position);
        holder.render(cinema , holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return cinemas.size();
    }

    public void addAll(List<Cinema> cinemaEntities){
        cinemaSet.addAll(cinemaEntities);
        cinemas.clear();
        cinemas.addAll(cinemaSet);
        notifyItemInserted(getItemCount());
    }
}
