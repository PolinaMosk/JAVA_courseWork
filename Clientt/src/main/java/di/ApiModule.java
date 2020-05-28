package di;

import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class ApiModule {
    private static final String BASE_URL = "http://localhost:8080/wholesalecomp/";

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    public Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    @Provides
    @Singleton
    public AuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(AuthApi.class);
    }

    @Provides
    @Singleton
    public GoodsApi provideGoodsApi(Retrofit retrofit) {
        return retrofit.create(GoodsApi.class);
    }

    @Provides
    @Singleton
    public SalesApi provideSalesApi(Retrofit retrofit) {
        return retrofit.create(SalesApi.class);
    }

    @Provides
    @Singleton
    public Warehouse1Api provideW1Api(Retrofit retrofit) {
        return retrofit.create(Warehouse1Api.class);
    }

    @Provides
    @Singleton
    public Warehouse2Api provideW2Api(Retrofit retrofit) {
        return retrofit.create(Warehouse2Api.class);
    }
}
