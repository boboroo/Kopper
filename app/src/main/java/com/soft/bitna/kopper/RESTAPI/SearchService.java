package com.soft.bitna.kopper.RESTAPI;

import android.content.Context;


import com.soft.bitna.kopper.RESTAPI.VO.TrainInformIsSearched;
import com.soft.bitna.kopper.RESTAPI.VO.UserTrainInform;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public class SearchService extends APIAdapter {
    /**
     * Retrofit 객체를 가져오는 메소드
     *
     * @param context
     * @return
     */
    public static searchServiceAPI getRetrofit(Context context) {
        // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
        return (searchServiceAPI) retrofit(context, searchServiceAPI.class);
    }

    // SignAPI 인터페이스
    public interface searchServiceAPI {

        /**
         * 검색된 호차,경로 가져오는 메소드
         *
         * @param userTrainInform
         * @return
         */
        @POST("SearchingTrainInform")
        Call<TrainInformIsSearched> searchingTrainInform(
                @Body UserTrainInform userTrainInform
        );


        /**
         * 호차좌석 메소드
         *
         * @param hochaSelected
         * @return
         */
        @GET("SearchingSeats")
        Call<ArrayList<String>> searchingSeats(
                @Query("hochaSelected") String hochaSelected
        );
    }
}
