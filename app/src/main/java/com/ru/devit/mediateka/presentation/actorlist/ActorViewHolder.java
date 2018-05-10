package com.ru.devit.mediateka.presentation.actorlist;

import android.content.Context;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.models.model.Actor;
import com.ru.devit.mediateka.utils.Constants;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

class ActorViewHolder extends RecyclerView.ViewHolder{

    private CircleImageView avatar;
    private TextView actorName , actorRole;
    private View rootView;

    private final ActorsPresenter presenter;

    ActorViewHolder(View itemView , ActorsPresenter presenter) {
        super(itemView);
        this.presenter = presenter;
        rootView = itemView;
        avatar = itemView.findViewById(R.id.iv_actor_avatar);
        actorName = itemView.findViewById(R.id.tv_actor_name);
        actorRole = itemView.findViewById(R.id.tv_actor_role);
    }

    void render(Actor actor , int viewHolderPosition){
        renderAvatar(actor);
        onActorClicked(actor.getActorId() , viewHolderPosition);
        actorName.setText(actor.getName());
        if (actor.getCharacter() == null){
            return;
        }
        actorRole.setText(actor.getCharacter().isEmpty() ? "" : getContext().getString(R.string.role , actor.getCharacter()));
    }

    private void onActorClicked(int actorId , int viewHolderPosition){
        rootView.setOnClickListener(view -> presenter.onActorClicked(actorId , viewHolderPosition));
    }

    @SuppressWarnings("ConstantConditions")
    private void renderAvatar(Actor actor){
        Picasso.with(getContext())
                .load(Constants.IMG_PATH_W185 + actor.getProfilePath())
                .error(VectorDrawableCompat.create(getContext().getResources() , R.drawable.ic_actor_default_avatar , getContext().getTheme()))
                .into(avatar);
    }

    private Context getContext(){
        return itemView.getContext();
    }
}
