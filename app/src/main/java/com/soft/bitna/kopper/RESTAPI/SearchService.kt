package com.soft.bitna.kopper.RESTAPI

import android.content.Context


import com.soft.bitna.kopper.RESTAPI.VO.TrainInformIsSearched
import com.soft.bitna.kopper.RESTAPI.VO.UserTrainInform

import java.util.ArrayList

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


class SearchService{

    // SignAPI 인터페이스
    interface searchServiceAPI {

        /**
         * 검색된 호차,경로 가져오는 메소드
         *
         * @param userTrainInform
         * @return
         */
        @POST("SearchingTrainInform")
        fun searchingTrainInform(
                @Body userTrainInform: UserTrainInform
        ): Call<TrainInformIsSearched>


        /**
         * 호차좌석 메소드
         *
         * @param hochaSelected
         * @return
         */
        @GET("SearchingSeats")
        fun searchingSeats(
                @Query("hochaSelected") hochaSelected: String
        ): Call<ArrayList<String>>
    }

    companion object {
        /**
         * Retrofit 객체를 가져오는 메소드
         *
         * @param context
         * @return
         */
        fun getRetrofit(context: Context): searchServiceAPI {
            // 현재 서비스객체의 이름으로 Retrofit 객체를 초기화 하고 반환
            return APIAdapter.retrofit(context, searchServiceAPI::class.java) as searchServiceAPI
        }
    }
}