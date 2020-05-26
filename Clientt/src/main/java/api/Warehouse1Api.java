package api;

import io.reactivex.Observable;
import model.Goods;
import model.Warehouse1;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface Warehouse1Api {
    @GET("warehouse1/listWare")
    Observable<Response<List<Warehouse1>>> listWare(@Header("Authorization") String token);

    @GET("warehouse1/listGoodsInWare")
    Observable<Response<List<Goods>>> listGoods(@Header("Authorization") String token);

    @GET("warehouse1/getGoodInWare/{id}")
    Observable<Response<Warehouse1>> getGoodInWare(@Header("Authorization") String token, @Path("id") Long id);

    @GET("warehouse1/getGoodByWareId/{id}")
    Observable<Response<Goods>> getGoodByWareId(@Header("Authorization") String token, @Path("id") Long id);

    @POST("warehouse1/addWare")
    Observable<Response<Void>> addGoodToWare(@Header("Authorization") String token, @Body Warehouse1 warehouse1);

    @DELETE("warehouse1/removeByGoodId/{id}")
    Observable<Response<Void>> removeGoodById(@Header("Authorization") String token, @Path("id") Long id);

    @DELETE("warehouse1/removeByBatchId/{id}")
    Observable<Response<Void>> removeGoodByWare(@Header("Authorization") String token, @Path("id") Long id);

}
