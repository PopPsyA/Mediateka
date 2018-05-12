package com.ru.devit.mediateka.presentation.actorlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.common.OnActorClickListener;

import java.util.ArrayList;
import java.util.List;

public class ActorListAdapter extends RecyclerView.Adapter<ActorViewHolder> {

    private final List<Actor> actors;
    private final OnActorClickListener onActorClickListener;

    public ActorListAdapter(OnActorClickListener onActorClickListener) {
        actors = new ArrayList<>();
        this.onActorClickListener = onActorClickListener;
    }

    @Override
    public ActorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor , parent , false);
        return new ActorViewHolder(view , onActorClickListener);
    }

    @Override
    public void onBindViewHolder(ActorViewHolder holder, int position) {
        Actor actor = actors.get(position);
        holder.render(actor , holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return actors.size();
    }

    public void addAll(List<Actor> actors){
        clear();
        this.actors.addAll(actors);
        notifyDataSetChanged();
    }

    private void clear(){
        actors.clear();
    }
}
