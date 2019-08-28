# Kopper
[ Android ] Java에서 Kotlin으로 변환해가는 프로젝트 입니다.


*
 현재는 모두 Kotlin으로 변환되어있는 상태입니다.

 Java와 Kotlin 두 가지 언어로 작성되어있는 상태를 보시려면 Commit한 기록에서 맨 첫 번째 Commit (Commit메세지: Java와 Kotlin 두 가지 언어로 작성되어있는 상태)을 보시면 됩니다.


# Kotlin으로 변환하며 느낀 점:

 프로퍼티를 의미와 목적에 따라 가변/불변으로 나눈 후, 그 밑 구현부 코드가 더 처리 흐름에만 집중되고 간결해진 것을 느꼈습니다.

범위지정함수를 사용하며, 변수가 사용되는 부분이 명확히 구분 지어진게 가장 매력적으로 느껴졌습니다.

 Kotlin으로 변환하기 전보다는 전체적으로 코드 가독성이 좋아져서 코드의 목적이 더 잘 보이게 되었다는 생각이 들었습니다.


# 사용된 버전

gradle버전: 3.2.1
compileSdkVersion: 27
minSdkVersion: 21
targetSdkVersion: 27
Kotlin버전: 1.2.71


# Gradle을 통한 플러그인, 라이브러리 추가

"프로젝트 루트 폴더의 dependencies.gradle 파일"에
ext.kotlin_version = '1.2.71' 를 추가하였습니다.
그리고 dependencies하위에 코틀린 그래들 플러그인을 추가하였습니다.
classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"

"모듈의 빌드 스크립트"에
apply plugin: 'kotlin-android'
를 추가하여 코틀린 플러그인을 적용하였습니다.

그리고 의존성에 코틀린 표준 라이브러리를 추가하였습니다.
implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"

그리고 그외에 사용한 라이브러리들(RecyclerView, Retrofit2, Gson2, OkHttp3, intercept를 하기위한 라이브러리)을 추가하였습니다.
implementation 'com.android.support:recyclerview-v7:27.1.1'
implementation 'com.squareup.retrofit2:retrofit:2.1.0'
implementation 'com.squareup.retrofit2:converter-gson:2.1.0'
implementation 'com.squareup.okhttp3:okhttp:3.0.0-RC1'
implementation 'com.squareup.okhttp3:logging-interceptor:3.0.0-RC1'
