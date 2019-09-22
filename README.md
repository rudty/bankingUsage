# bankingUsage

인터넷 이용현황과 이용 정보를 제공하는 api 서버

## 개발 환경
- git
- java 8 이상
  - kotlin
  - spring framework 2.1.8
  - gradle

## 실행
```
git clone https://github.com/rudty/bankingUsage.git

cd bankingUsage

./gradlew bootRun
```

## 문제 해결 전략
  1. 로그인, 회원가입을 제외한 모든 API들은 인증이 필요함. 
    - 공통 인증 기능을 [interceptor](https://github.com/rudty/bankingUsage/blob/master/src/main/kotlin/com/rudtyz/bank/interceptor/JwtInterceptor.kt)로 제작, [등록](https://github.com/rudty/bankingUsage/blob/master/src/main/kotlin/com/rudtyz/bank/config/AuthInterceptorConfig.kt)
  2. 데이터 csv는 고정 값으로 변경이 없기에 어떠한 이유로 서버가 종료되어 재시작 하여도 데이터 유실이 없음.
     서버 시작 시 db에는 [등록](#%EB%8D%B0%EC%9D%B4%ED%84%B0-%ED%8C%8C%EC%9D%BC%EC%97%90%EC%84%9C-%EA%B0%81-%EB%A0%88%EC%BD%94%EB%93%9C%EB%A5%BC-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%98%EB%8A%94-%EC%BD%94%EB%93%9C)하지만(요구사항) 실제로 사용은 메모리에 저장된 값을 사용
    문법은 JPA interface로 만들수 있는것과 유사하게 하여 이후 쉽게 변경할 수 있도록 함.
  3. 반드시 최신의 데이터를 로드해야만 하는 유저 계정 정보는 db를 사용
    - 디바이스 목록은 자주 사용될 수 있지만 캐싱하여 사용
  4. 요구사항의 모든 리스트 형태 반환값은 `{"devices":...` 로 시작하고 모든 1개 객체의 반환 값은 `{"result":...` 이므로
     반환값을 공통적으로 [래핑하는 클래스](https://github.com/rudty/bankingUsage/blob/master/src/main/kotlin/com/rudtyz/bank/aop/ResponseWrapper.kt) 제작
  5. 요구 사항에는 없지만 예외 발생 시 `{"error":...` 형태의 에러 로그를 출력
    - 이후 예외 로그를 저장. db에 저장하고 있으므로 이후 db의 부하가 예상 될 시 로그 전용 db를 연결 필요 

## 지원 API 
- 회원가입/로그인을 제외한 모든 API는 헤더에 인증이 필요로 한다
  형식은 다음과 같다.

```Authorization: Bearer 토큰```

- 올바른 응답일 시 http status는 200으로 반환된다. 
- 올바르지 않은 응답일 시 http status는 200이 아니고 response body에 에러에 대한 응답을 전송한다. 

  ```{"error":"에러에 대한 이유"}```

---

### 회원가입 
/auth/signup

계정생성 API: ID, PW를 입력 받아 내부 DB에 계정을 저장하고 토큰을 생성하여 출력한다. 

토큰은 Authorization 헤더에 넣어 전달한다.

|param|info|
|-|-|
|id|등록할 아이디|
|pw|등록할 비밀번호|

|response header|response body|
|-|-|
|Authorization: Bearer 토큰|{"result": "OK"}|

---

### 로그인
/auth/signin

로그인 API: 입력으로 생성된 계정 (ID, PW)으로 로그인 요청하면 토큰을 발급한다. 

|param|info|
|-|-|
|id|등록할 아이디|
|pw|등록할 비밀번호|

|response header|response body|
|-|-|
|Authorization: Bearer 토큰|{"result": "OK"}|

---

### 새로고침
/auth/refresh

refresh 토큰 재발급 API: 기존에 발급받은 토큰을 Authorization 헤더에 “Bearer Token”으로 입력 요청을 하면 토큰을 재발급한다.  

|response header|response body|
|-|-|
|Authorization: Bearer 토큰|{"result": "OK"}|

---

### 인터넷뱅킹 서비스 접속 기기 목록을 출력
/device/all

|response body|
|-|
|{"devices":[{"device_id":"DIS40dff29631","device_name":"스마트폰"},{"device_id":"DIS40e0047","device_name":"데스크탑 컴퓨터"},{"device_id":"DIS40e005272","device_name":"노트북 컴퓨터"},{"device_id":"DIS40e0029406","device_name":"기타"},{"device_id":"DIS40e0027515","device_name":"스마트패드"}]}|

---

### 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 출력
/device/rank/year/{연도}

|response body|
|-|
|{"result":{"year":2017,"device_name":"스마트폰","rate":90.6}}|

---

### 디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해
/device/rank/id/{디바이스 아이디}

|response body|
|-|
|{"result":{"year":2011,"device_name":"데스크탑 컴퓨터","rate":95.1}}|

---

### 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 코드
[연결](https://github.com/rudty/bankingUsage/blob/master/src/main/kotlin/com/rudtyz/bank/loader/TableBankingUsageLoader.kt#L42)

---

###  데이터 파일을 로컬 파일 시스템에서 로딩하여 적재하는 API
/device/reload

|response body|
|-|
|{"result":"OK"}|

