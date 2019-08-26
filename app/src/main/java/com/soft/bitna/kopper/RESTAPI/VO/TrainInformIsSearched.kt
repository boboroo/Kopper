package com.soft.bitna.kopper.RESTAPI.VO

import java.util.ArrayList

// 사용자가 탑승한 기차의 경로와 착석 가능한 호차 리스트를 데이터로 가지는 클래스
data class TrainInformIsSearched (internal val routeList: ArrayList<String>?, internal val hochaList: ArrayList<String>?)
