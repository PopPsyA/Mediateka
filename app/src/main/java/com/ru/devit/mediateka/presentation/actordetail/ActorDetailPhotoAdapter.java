package com.ru.devit.mediateka.presentation.actordetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.presentation.common.OnActorClickListener;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.ru.devit.mediateka.utils.UrlImagePathCreator.Quality;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ActorDetailPhotoAdapter extends RecyclerView.Adapter<ActorDetailPhotoAdapter.ActorPhotoViewHolder>{

    private final List<String> photoUrls;
    private final OnActorClickListener onActorClickListener;

    public ActorDetailPhotoAdapter(OnActorClickListener onActorClickListener) {
        this.onActorClickListener = onActorClickListener;
        photoUrls = new ArrayList<>();
    }

    @NonNull
    @Override
    public ActorPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actor_photo , parent , false);
        return new ActorPhotoViewHolder(view, onActorClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorPhotoViewHolder holder, int position) {
        String photoUrl = photoUrls.get(position);

        holder.render(position , photoUrl);
    }

    @Override
    public int getItemCount() {
        return photoUrls.size();
    }

    public void addAll(List<String> photoUrls){
        if (photoUrls != null){
            this.photoUrls.addAll(photoUrls);
        }
    }

    static class ActorPhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewActorPhoto;
        private final Random random = new Random();
        private final OnActorClickListener onActorClickListener;

        ActorPhotoViewHolder(View itemView, OnActorClickListener onActorClickListener) {
            super(itemView);
            mImageViewActorPhoto = itemView.findViewById(R.id.iv_actor_photo);
            this.onActorClickListener = onActorClickListener;
        }

        void render(int position , String photoUrl){
            onActorClicked(position);
            loadImage(photoUrl);
            generateRandomHeight();
        }

        private void generateRandomHeight(){
            mImageViewActorPhoto.getLayoutParams().height = getRandomIntInRange(600 , 500);
        }

        private void loadImage(String imgUrl){
            Picasso.with(itemView.getContext())
                    .load(UrlImagePathCreator.INSTANCE.createPictureUrlFromQuality(Quality.Quality185 , imgUrl))
                    .into(mImageViewActorPhoto);
        }

        private int getRandomIntInRange(int max, int min){
            int randomInt = random.nextInt(max) + min;
            if (randomInt >= max){
                randomInt = max;
            }
            return randomInt;
        }

        private void onActorClicked(int position){
            itemView.setOnClickListener(v -> onActorClickListener.onActorClicked(position , getAdapterPosition()));
        }
    }
}
