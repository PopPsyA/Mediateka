package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.presentation.common.OnCinemaClickListener;

import java.util.ArrayList;
import java.util.List;

public class SmallCinemaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER_TYPE = 0;
    private final int CONTENT_TYPE = 1;

    private final OnCinemaClickListener onCinemaClickListener;
    private final List<Cinema> cinemas;
    private final boolean withOutHeader;

    public SmallCinemaListAdapter(OnCinemaClickListener onCinemaClickListener , boolean withOutHeader) {
        this.onCinemaClickListener = onCinemaClickListener;
        this.withOutHeader = withOutHeader;
        cinemas = new ArrayList<>();
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (withOutHeader){
            view = inflater.inflate(R.layout.item_small_cinema , parent , false);
            return new SmallCinemaViewHolder(view , onCinemaClickListener);
        }
        if (viewType == HEADER_TYPE){
            view = inflater.inflate(R.layout.item_small_cinema_header, parent , false);
            return new SmallCinemaHeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_small_cinema , parent , false);
            return new SmallCinemaViewHolder(view , onCinemaClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HEADER_TYPE : {
                ((SmallCinemaHeaderViewHolder)holder).render(cinemas.size());
                break;
            }
            case CONTENT_TYPE : {
                if (withOutHeader){
                    Cinema cinema = cinemas.get(position);
                    ((SmallCinemaViewHolder)holder).render(cinema , holder.getAdapterPosition());
                } else {
                    Cinema cinema = cinemas.get(position - 1);
                    ((SmallCinemaViewHolder)holder).render(cinema , holder.getAdapterPosition());
                }
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (withOutHeader){
            return cinemas.size();
        }
        return cinemas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (withOutHeader){
            return CONTENT_TYPE;
        }
        return position == 0 ? HEADER_TYPE : CONTENT_TYPE;
    }

    public void addAll(List<Cinema> cinemaList){
        cinemas.clear();
        cinemas.addAll(cinemaList);
        notifyDataSetChanged();
    }

    public void removeCinema(int position){
        cinemas.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreCinema(Cinema cinema , int position){
        cinemas.add(position , cinema);
        notifyItemInserted(position);
    }

    public void clear(){
        cinemas.clear();
    }
}
