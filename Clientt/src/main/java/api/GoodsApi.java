package api;

import io.reactivex.Observable;
import model.Goods;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

public interface GoodsApi {
    @GET("goods/goods")
    Observable<Response<List<Goods>>> getGoods(@Header("Authorization") String token);

    @GET("goods/getGood/{id}")
    Observable<Response<Goods>> getGoodById(@Header("Authorization") String token, @Path("id") Long id);
}
