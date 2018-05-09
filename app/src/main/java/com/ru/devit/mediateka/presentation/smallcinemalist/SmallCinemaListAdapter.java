package com.ru.devit.mediateka.presentation.smallcinemalist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Cinema;

import java.util.ArrayList;
import java.util.List;

public class SmallCinemaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HEADER_TYPE = 0;
    private final int CONTENT_TYPE = 1;

    private final SmallCinemasPresenter presenter;
    private final List<Cinema> cinemas;
    private final boolean inSearchMode;

    public SmallCinemaListAdapter(SmallCinemasPresenter presenter , boolean inSearchMode) {
        this.presenter = presenter;
        this.inSearchMode = inSearchMode;
        cinemas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (inSearchMode){
            view = inflater.inflate(R.layout.item_small_cinema , parent , false);
            return new SmallCinemaViewHolder(view , presenter);
        }
        if (viewType == HEADER_TYPE){
            view = inflater.inflate(R.layout.item_small_cinema_header, parent , false);
            return new SmallCinemaHeaderViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_small_cinema , parent , false);
            return new SmallCinemaViewHolder(view , presenter);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case HEADER_TYPE : {
                ((SmallCinemaHeaderViewHolder)holder).render(cinemas.size());
                break;
            }
            case CONTENT_TYPE : {
                if (inSearchMode){
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
        if (inSearchMode){
            return cinemas.size();
        }
        return cinemas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (inSearchMode){
            return CONTENT_TYPE;
        }
        return position == 0 ? HEADER_TYPE : CONTENT_TYPE;
    }

    public void addAll(List<Cinema> cinemaList){
        cinemas.clear();
        cinemas.addAll(cinemaList);
        notifyDataSetChanged();
    }

}
