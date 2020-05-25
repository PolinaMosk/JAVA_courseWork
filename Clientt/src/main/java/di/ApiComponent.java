package di;

import api.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    GoodsApi provideGoodsApi();
    SalesApi provideSalesApi();
    Warehouse1Api provideW1Api();
    Warehouse2Api provideW2Api();
    AuthApi provideAuthApi();
}
