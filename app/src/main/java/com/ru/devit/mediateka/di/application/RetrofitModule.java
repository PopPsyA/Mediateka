package com.ru.devit.mediateka.di.application;

import com.ru.devit.mediateka.BuildConfig;
import com.ru.devit.mediateka.data.datasource.network.CinemaApiService;

import java.io.IOException;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private final String LOCAL_LANGUAGE = Locale.getDefault().getLanguage();

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalUrl = original.url();

            HttpUrl url = originalUrl.newBuilder()
                    .addQueryParameter("api_key" , BuildConfig.THE_MOVIE_DB_API_KEY)
                    .addQueryParameter("language" , LOCAL_LANGUAGE)
                    .build();

            Request.Builder request = original.newBuilder()
                    .url(url);

            return chain.proceed(request.build());
        });
        return client.build();
    }

    @Provides
    @Singleton
    CinemaApiService provideCinemaApiService(Retrofit retrofit){
        return retrofit.create(CinemaApiService.class);
    }

}
