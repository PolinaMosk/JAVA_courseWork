package api;

import io.reactivex.Observable;
import model.Goods;
import model.Sales;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface SalesApi {
    @GET("sales/sales")
    Observable<Response<List<Sales>>> getSales(@Header("Authorization") String token);

    @GET("sales/getGoodBySaleId/{id}")
    Observable<Response<Goods>> getGoodsBySaleId(@Header("Authorization") String token, @Path("id") Long id);

    @GET("sales/getSale/{id}")
    Observable<Response<Sales>> getGroupById(@Header("Authorization") String token, @Path("id") Long id);

    @GET("sales/listGoodsInSales")
    Observable<Response<List<Goods>>> listGoodsInSales(@Header("Authorization") String token);

    @POST("sales/addSale")
    Observable<Response<Void>> addSale(@Header("Authorization") String token, @Body Sales sale);
}
