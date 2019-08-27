package com.soft.bitna.kopper.Activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast

import com.soft.bitna.kopper.R
import com.soft.bitna.kopper.RESTAPI.SearchService
import com.soft.bitna.kopper.RESTAPI.VO.TrainInformIsSearched
import com.soft.bitna.kopper.RESTAPI.VO.UserTrainInform

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val departStation by lazy { findViewById<View>(R.id.departStation) as EditText }
    private var departStationStr :String? = null
    private val arriveStation by lazy { findViewById<View>(R.id.arriveStation) as EditText }
    private var arriveStationStr :String? = null
    private var trainInform :TrainInformIsSearched? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById<View>(R.id.trainInformSubmit) as Button).setOnClickListener {
            departStationStr = departStation.text.toString()
            arriveStationStr = arriveStation.text.toString()

            if(departStationStr.isNullOrEmpty() or arriveStationStr.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"출발역과 도착역을 모두 입력해주세요.",Toast.LENGTH_LONG).show()
                Log.d("Kopper", "(searchingTrainInform) isNullOrEmpty()")
            }
            else {
                /* 사용자가 입력한 출발역과 도착역을 서버로 전송하여,
                 * 서버로 부터 사용자가 탑승한 기차의 경로와 착석 가능한 호차 리스트를 가져옵니다.
                 */
                SearchService.getRetrofit(applicationContext)
                        .searchingTrainInform(UserTrainInform(departStationStr!!, arriveStationStr!!))
                        .enqueue(object : Callback<TrainInformIsSearched> {
                            override fun onResponse(call: Call<TrainInformIsSearched>, response: Response<TrainInformIsSearched>) {
                                Log.d("Kopper", "(searchingTrainInform) Request 성공  ??")

                                if (response.isSuccessful) { //요청 성공
                                    trainInform = response.body()
                                    Log.d("Kopper", "(searchingTrainInform) Request 성공  !! :")
                                    Log.d("Kopper", "${trainInform?:"trainInform == null"}")

                                    if ((trainInform!!.hochaList != null) and (trainInform!!.routeList != null)) {
                                        val intent = Intent(applicationContext, ResultActivity::class.java).apply {
                                            putStringArrayListExtra("routeList", trainInform!!.routeList)
                                            putStringArrayListExtra("hochaList", trainInform!!.hochaList)
                                            putStringArrayListExtra("hochaList", trainInform!!.hochaList)
                                        }

                                        startActivity(intent)
                                    }
                                    else if (trainInform!!.hochaList == null){
                                        Toast.makeText(applicationContext, "미구매된 좌석이 없습니다.", Toast.LENGTH_LONG).show()
                                    }
                                    else if (trainInform!!.routeList == null){
                                        Toast.makeText(applicationContext, "죄송합니다. 서비스상에 오류가 발생하였습니다. 다시 시도해주십시오.", Toast.LENGTH_LONG).show()
                                    }
                                } else {
                                    if (response.code() == 500) {
                                        Log.d("Kopper", "500에러")
                                        Toast.makeText(applicationContext, "Error Number: 500", Toast.LENGTH_LONG).show()
                                    } else if (response.code() == 503) {
                                        Log.d("Kopper", "503에러")
                                        Toast.makeText(applicationContext, "Error Number: 503", Toast.LENGTH_LONG).show()
                                    } else if (response.code() == 401) {
                                        Log.d("Kopper", "401에러")
                                        Toast.makeText(applicationContext, "Error Number: 401", Toast.LENGTH_LONG).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<TrainInformIsSearched>, t: Throwable) {
                                Log.d("Kopper", "(searchingTrainInform) 서버 통신 실패")
                                Log.d("Kopper", "(searchingTrainInform) 메세지 : " + t.message)

                                Toast.makeText(applicationContext, "죄송합니다. 서버와의 통신에 실패하여 사용자님의 요청에 대한 응답을 받지 못하였습니다.", Toast.LENGTH_LONG).show()
                            }
                        })
            }
        }


        /* 사용자가 EditText를 터치하여 키보드가 다른 View를 가려서, 사용자가 불편함을 겪을 수도 있으니,
         * 사용자가 현재 Activity의 Layout을 터치하면 키보드가 사라지도록 하였습니다.
         */
        (findViewById<View>(R.id.mainLayout) as RelativeLayout).setOnTouchListener { v, event ->
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).run {
                hideSoftInputFromWindow(departStation.windowToken, 0)
                hideSoftInputFromWindow(arriveStation.windowToken, 0)
            }

            false
        }
    }
}