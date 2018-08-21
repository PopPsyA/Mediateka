package com.ru.devit.mediateka.presentation.cinemadetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ru.devit.mediateka.MediatekaApp;
import com.ru.devit.mediateka.R;
import com.ru.devit.mediateka.di.cinema.cinemadetail.CinemaDetailModule;
import com.ru.devit.mediateka.models.model.Cinema;
import com.ru.devit.mediateka.utils.FormatterUtils;

import java.text.NumberFormat;
import java.util.Locale;

import javax.inject.Inject;

public class CinemaDetailContentFragment extends Fragment implements CinemaDetailContentPresenter.View {

    private static final String CINEMA = "cinema";

    private TextView mDescriptionTextView;
    private TextView mReleaseDateTextView;
    private TextView mDirectedByTextView;
    private TextView mBudgetTextView;
    private TextView mRevenueTextView;

    @Inject CinemaDetailContentPresenter presenter;

    public static CinemaDetailContentFragment newInstance(Cinema cinema){
        CinemaDetailContentFragment fragment = new CinemaDetailContentFragment();
        Bundle args = new Bundle();
        args.putParcelable(CINEMA , cinema);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cinema_detail_content , container ,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
        initViews(view);
        initDagger();
        initPresenter();
        Bundle arg = getArguments();
        if (arg != null){
            presenter.setCinema(getArguments().getParcelable(CINEMA));
        }
    }

    @Override
    public void showCinemaContent(Cinema cinema) {
        mDescriptionTextView.setText(cinema.getDescription().isEmpty() ?
                getString(R.string.message_cinema_desc_not_entered) : cinema.getDescription());
        mReleaseDateTextView.setText(FormatterUtils.formatDate(cinema.getReleaseDate()));
        mDirectedByTextView.setText(cinema.getDirectorName());
        mBudgetTextView.setText(getString(R.string.dollar_char , NumberFormat.getInstance(Locale.US).format(cinema.getBudget())));
        mRevenueTextView.setText(getString(R.string.dollar_char , NumberFormat.getInstance(Locale.US).format(cinema.getCinemaRevenue())));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        MediatekaApp.getComponentsManager().clearCinemaComponent();
        super.onDestroy();
    }

    private void initPresenter() {
        presenter.setView(this);
        presenter.initialize();
    }

    private void initDagger() {
        if (presenter == null){
            MediatekaApp.getComponentsManager()
                    .plusCinemaComponent()
                    .plusCinemaDetailComponent(new CinemaDetailModule())
                    .inject(this);
        }
    }

    private void initViews(View view) {
        mDescriptionTextView = view.findViewById(R.id.tv_cinema_detail_desc);
        mReleaseDateTextView = view.findViewById(R.id.tv_cinema_detail_release_date);
        mDirectedByTextView = view.findViewById(R.id.tv_cinema_detail_directed_by);
        mBudgetTextView = view.findViewById(R.id.tv_cinema_detail_budget);
        mRevenueTextView = view.findViewById(R.id.tv_cinema_revenue);
    }
}
