package com.soft.bitna.kopper.Activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.soft.bitna.kopper.ForRecyclerViews.HochaRecyclerViewAdapter

import com.soft.bitna.kopper.R
import com.soft.bitna.kopper.ForRecyclerViews.SeatGreedItem
import com.soft.bitna.kopper.ForRecyclerViews.SeatRecyclerViewAdapter
import com.soft.bitna.kopper.RESTAPI.SearchService

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {
    private val getDataIntent by lazy { intent } // MainActivity로 부터 넘어오는 Intent
    private val hochaList: ArrayList<String> by lazy { getDataIntent.getStringArrayListExtra("hochaList") }
    private val routeList: ArrayList<String> by lazy { getDataIntent.getStringArrayListExtra("routeList") }
    private var seatList: ArrayList<SeatGreedItem> = ArrayList()
    private var responseSeatList: ArrayList<String>? = null

    private val selectingHocha by lazy { findViewById<View>(R.id.selectingHocha) as Spinner } // lazy 프로퍼티를 사용하여, 최초로 사용하는 시점에 초기화
    private val hochaSpinnerAdapter: ArrayAdapter<String> by lazy { ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, hochaList) }

    private val seatGridRecyclerView by lazy { findViewById<View>(R.id.seatGridRecyclerview) as RecyclerView }
    private lateinit var seatGridAdapter: SeatRecyclerViewAdapter //onCreate()에서 초기화
    val seatGridLayoutManager by lazy { GridLayoutManager(applicationContext, 1) }

    private val hochaLinearRecyclerView by lazy { findViewById<View>(R.id.hochaRecyclerView) as RecyclerView }
    private val hochaLinearAdapter by lazy { HochaRecyclerViewAdapter(applicationContext, hochaList) }
    val hochaListLayoutManager by lazy { LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false) }

    private var beforeHochaPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_activity)


        // Spinner 셋팅
        with(selectingHocha) {
            adapter = hochaSpinnerAdapter
            setSelection(0)
        }


        // 좌석 그리드 리사이클러뷰 셋팅 1 (리싸이클러뷰 형태 설정)
        seatGridRecyclerView.layoutManager = seatGridLayoutManager

        // 좌석 그리드 리사이클러뷰 셋팅 2 (리싸이클러뷰 어댑터 초기화)
        seatGridAdapter = SeatRecyclerViewAdapter(applicationContext, seatList)
        seatGridRecyclerView.adapter = seatGridAdapter


        ////////////////////////////////////////////////////////////////////////


        /* 1. 호차 RecyclerView의 선택된 호차를 표시하는 네모상자표시를 최근에 선택한 호차에 표시한다.
         * 2. 서버와 통신하여 선택된 호차에 따른 좌석정보(response.body())를 가져오고
         *    좌석 RecyclerView의 좌석list(seatList)를 선택된 호차에 따른 좌석정보로 변경한다.
         */
        selectingHocha.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.d("Kopper", hochaList[position] + " 선택") //hochaList[position]의 형식: n 호차 (n = 정수)

                // 호차 RecyclerView에서 선택되어있던 위치의 아이템에서 네모표시를 없애고,
                // 새로 선택된 위치의 아이템에 네모표시를 합니다.
                with(hochaLinearRecyclerView) {
                    getChildAt(beforeHochaPosition).setBackgroundColor(Color.rgb(191, 222, 212))
                    getChildAt(position).setBackgroundResource(R.drawable.touch_hocha_border)
                }
                beforeHochaPosition = position


                /* 서버와 통신하여 선택된 호차에 따른 좌석정보(response.body())를 가져오고
                 * 좌석 RecyclerView의 좌석list(seatList)를 선택된 호차에 따른 좌석정보로 변경
                 */
                SearchService.getRetrofit(applicationContext)
                        .searchingSeats(hochaList[position].trim().split(" ")[0]) //hochaList[position] 형식: n 호차 (n = 정수 (Data type: String))
                        .enqueue(object : Callback<ArrayList<String>> {
                            var routeListIdx = 0

                            override fun onResponse(call: Call<ArrayList<String>>, response: Response<ArrayList<String>>) {
                                Log.d("Kopper", "(searchingSeats) Request 성공  ??")

                                if (response.isSuccessful) { //요청성공
                                    seatList.let {
                                        it.clear()
                                        responseSeatList = response.body()

                                        Log.d("Kopper", "(searchingSeats) Request 성공  !!:")
                                        Log.d("kopper", "${responseSeatList
                                                ?: "responseSeatList==null"}")

                                        //101~120 lines, 선택한 호차로 가져온 좌석 정보로 좌석 RecyclerView를 변경
                                        if (responseSeatList?.isEmpty()!!) {
                                            it.add(SeatGreedItem(routeList[0], "좌석이 없습니다."))
                                            routeListIdx = 1
                                            for (i in routeList) {
                                                it.add(SeatGreedItem(routeList[routeListIdx++], ""))
                                            }
                                        } else { //responseSeatList?.isNotEmpty() 일 경우 해당
                                            routeListIdx = 0
                                            for (seat in response.body()) {
                                                it.add(SeatGreedItem(routeList[routeListIdx++], seat))
                                            }
                                        }

                                        seatGridAdapter = SeatRecyclerViewAdapter(applicationContext, it)
                                        seatGridRecyclerView.adapter = seatGridAdapter
                                    }
                                }
                                else {
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

                            override fun onFailure(call: Call<ArrayList<String>>, t: Throwable) {
                                Log.d("Kopper", "(searchingSeats) 서버 통신 실패")
                                Log.d("Kopper", "(searchingSeats) 메세지 : " + t.message)

                                // 선택한 호차에 대해서 좌석 RecyclerView의 좌석 정보 변경
                                seatList.let{
                                    it.add(SeatGreedItem(routeList[0], "죄송합니다. 서버와의 통신에 실패하여 사용자님의 요청에 대한 응답을 받지 못하였습니다."))
                                    routeListIdx = 1
                                    it.clear()
                                    for (i in routeList) {
                                        it.add(SeatGreedItem(routeList[routeListIdx++], ""))
                                    }

                                    seatGridAdapter = SeatRecyclerViewAdapter(applicationContext, it)
                                    seatGridRecyclerView.adapter = seatGridAdapter
                                }
                            }

                        })
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                Log.d("Kopper", "selectingHocha onNothingSelected()")
            }
        }


        //////////////////////////////////////////////////////////////////////////


        // 호차 RecyclerView 셋팅
        with(hochaLinearRecyclerView) {
            adapter = hochaLinearAdapter
            layoutManager = hochaListLayoutManager
        }


        ///////////////////////////////////////////////////////////////////////////////


        val gestureDetector = GestureDetector(this@ResultActivity, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }
        })


        /* 호차 RecyclerView에서 사용자가 호차를 선택했을시, 선택한 호차를 Spinner 리스너로 넘겨서 Spinner의 선택되어있는 호차도 함께 변경되도록 합니다.
         * Spinner 리스너에서 좌석 RecyclerView가 보여주는 좌석 정보가 변경되도록 하였습니다.
         */
        hochaLinearRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val child = rv.findChildViewUnder(e.x, e.y)

                if (child != null && gestureDetector.onTouchEvent(e)) {
                    val position = rv.getChildAdapterPosition(child)

                    Log.d("Kopper", "[hochaPosition: $position]")
                    Log.d("Kopper", "[beforeHochaPosition: $beforeHochaPosition]")

                    val tv = rv.getChildViewHolder(child).itemView.findViewById<View>(R.id.hochaTv) as TextView

                    Log.d("Kopper", "[" + tv.text.toString() + "]")

                    selectingHocha.setSelection(position) //호차 스피너 position 변경
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })

    }

}
