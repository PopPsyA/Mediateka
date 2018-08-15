package com.ru.devit.mediateka.presentation.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ru.devit.mediateka.models.model.Cinema;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/*
Must extends all adapters which related with Cinema.
 */

public abstract class AbstractCinemaListAdapter<HOLDER_RENDERER extends HolderRenderer<Cinema> , VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final OnCinemaClickListener onCinemaClickListener;
    protected final List<Cinema> cinemas;
    private final Set<Cinema> cinemaSet;

    public AbstractCinemaListAdapter(OnCinemaClickListener onCinemaClickListener) {
        this.onCinemaClickListener = onCinemaClickListener;
        cinemas = new ArrayList<>();
        cinemaSet = new LinkedHashSet<>();
    }

    @Override
    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Cinema cinema = cinemas.get(position);
        HOLDER_RENDERER typedHolder = (HOLDER_RENDERER) holder;
        typedHolder.render(cinema , holder.getAdapterPosition());
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

    public void notifyRemoveEach() {
        for (int i = 0; i < cinemas.size(); i++) {
            notifyItemRemoved(i);
        }
    }

    public void notifyAddEach() {
        for (int i = 0; i < cinemas.size(); i++) {
            notifyItemInserted(i);
        }
    }
}
