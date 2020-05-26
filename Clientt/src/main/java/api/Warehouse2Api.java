package api;

import io.reactivex.Observable;
import model.Goods;
import model.Warehouse2;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface Warehouse2Api {
    @GET("warehouse2/listWare")
    Observable<Response<List<Warehouse2>>> listWare(@Header("Authorization") String token);

    @GET("warehouse2/listGoodsInWare")
    Observable<Response<List<Goods>>> listGoods(@Header("Authorization") String token);

    @GET("warehouse2/getGoodInWare/{id}")
    Observable<Response<Warehouse2>> getGoodInWare(@Header("Authorization") String token, @Path("id") Long id);

    @GET("warehouse2/getGoodByWareId/{id}")
    Observable<Response<Goods>> getGoodByWareId(@Header("Authorization") String token, @Path("id") Long id);

    @POST("warehouse2/addWare")
    Observable<Response<Void>> addGoodToWare(@Header("Authorization") String token, @Body Warehouse2 warehouse2);

    @DELETE("warehouse2/removeByGoodId/{id}")
    Observable<Response<Void>> removeGoodById(@Header("Authorization") String token, @Path("id") Long id);

    @DELETE("warehouse2/removeByBatchId/{id}")
    Observable<Response<Void>> removeGoodByWare(@Header("Authorization") String token, @Path("id") Long id);
}
