package com.soft.bitna.kopper.RESTAPI;

import android.content.Context;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * APIAdapter 클래스
 */
public class APIAdapter {
    /**
     * Retrofit 객체를 초기화하는 메소드
     *
     * @param context
     * @param serviceName
     * @return
     */
    protected static Object retrofit(Context context, Class<?> serviceName) {
        // 1. OkHttpClient 객체 생성
        // 2. OkHttpClient 빌드
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();


        // 1. Retrofit 객체 생성
        // 2. base(api 서버) url 설정
        // 3. json 형식의 reponse 데이터의 파싱을 위해 Gson 추가
        // 4. 위에서 만든 OkHttpClient 객체를 추가
        // 5. Retrofit 빌드
        // 주의) addConverterFactory를 추가하지 않을 경우 어플리케이션이 종료됨
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("웹서버url")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();


        // 서비스객체의 이름으로 Retrofit 객체 생성 및 반환
        // ex) retrofit.create(SearchService.class);
        return retrofit.create(serviceName);
    }
}
