package com.ru.devit.mediateka.presentation.actorlist;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.presentation.common.OnActorClickListener;
import com.ru.devit.mediateka.utils.UrlImagePathCreator;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

class ActorViewHolder extends RecyclerView.ViewHolder{

    private CircleImageView avatar;
    private TextView actorName , actorRole;
    private View rootView;

    private final OnActorClickListener onActorClickListener;

    ActorViewHolder(View itemView , OnActorClickListener onActorClickListener) {
        super(itemView);
        this.onActorClickListener = onActorClickListener;
        rootView = itemView;
        avatar = itemView.findViewById(R.id.iv_actor_avatar);
        actorName = itemView.findViewById(R.id.tv_actor_name);
        actorRole = itemView.findViewById(R.id.tv_actor_role);
    }

    void render(Actor actor , int viewHolderPosition){
        renderAvatar(UrlImagePathCreator.create185pPictureUrl(actor.getProfileUrl()));
        onActorClicked(actor.getActorId() , viewHolderPosition);
        actorName.setText(actor.getName());
        if (actor.getCharacter() == null){
            return;
        }
        actorRole.setText(actor.getCharacter().isEmpty() ? "" : getContext().getString(R.string.role , actor.getCharacter()));
    }

    private void onActorClicked(int actorId , int viewHolderPosition){
        rootView.setOnClickListener(view -> onActorClickListener.onActorClicked(actorId , viewHolderPosition));
    }

    @SuppressWarnings("ConstantConditions")
    private void renderAvatar(String url){
        Picasso.with(getContext())
                .load(url)
                .error(VectorDrawableCompat.create(getContext().getResources() , R.drawable.ic_actor_default_avatar , getContext().getTheme()))
                .into(avatar);
    }

    private Context getContext(){
        return itemView.getContext();
    }
}
