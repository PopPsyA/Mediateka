package com.ru.devit.mediateka.presentation.favouritelistcinema;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.ru.devit.mediateka.R;

public class CinemaSortingDialog extends DialogFragment {

    private String[] arrayCinemaSortingVariants;
    private OnDialogItemClicked onDialogItemClicked;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(requireActivity());
        alertDialogBuilder.setTitle(getString(R.string.sort_favourite_list));
        alertDialogBuilder.setSingleChoiceItems(arrayCinemaSortingVariants, 0, (dialog, which) -> {
            onDialogItemClicked.onDialogItemClicked(which);
            dialog.dismiss();
        });

        return alertDialogBuilder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onDialogItemClicked = (OnDialogItemClicked) context;
        } catch (ClassCastException exception){
            throw new ClassCastException(context.toString()
                    + " must implement OnDialogItemClicked");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayCinemaSortingVariants = getResources().getStringArray(R.array.array_sorting_cinema_variants);
    }

    public interface OnDialogItemClicked{
        void onDialogItemClicked(int position);
    }
}
