# 개발 과제
프로젝트를 실행하기 전, 아래와 같이 yml 파일을 수정해야합니다.

```yaml
spring:
  datasource:
    driver-class-name: { Your database driver }   # Database Driver
    url: { Your database url }                    # Database URL
    username: { Your database username }          # Database 아이디
    password: { Your database password }          # Database 비밀번호

  jpa:
    properties:
      hibernate:
        dialect: { Your database dialect }        # Database Dialect
```
<p align="center">forecast-domain 모듈의 application-domain.yml</p>

<br>

```yaml
api:
  forecast:
    key: {Your Service Key}                       # Service Key
```
<p align="center">forecast-fetcher 모듈의 application-fetcher.yml</p>

<br><br>




## ⛏ 개발 환경
- **Java 17**

- **Spring Boot 3.2.4**
 
- **Spring Data JPA**

- **MySQL**

<br><br><br>




## 🧩 모듈 구조

```text
root
├── forecast-application
├── forecast-domain
└── forecast-fetcher
```

- **forecast-application** : 어플리케이션 모듈로 단기 예보 조회, 저장 API를 제공합니다.

- **forecast-domain** : 도메인 모듈로 데이터베이스 또는 fetcher 모듈에서 단기 예보 정보를 조회합니다.

- **forecast-fetcher** : 내부 모듈로 기상청 단기 예보 API를 호출합니다.

<br><br><br>




## 📜 API 명세
### 1. 단기 예보 저장 API

- **요청 (Request)**

    요청한 시간을 기준으로 가장 가까운 단기 예보 정보를 조회 후, 데이터베이스에 저장합니다.

    해당 시간의 단기 예보가 저장되어 있는 경우, 데이터베이스에 저장하지 않습니다.

    ```
    POST /forecasts
    ```

    <br><br>


- **응답 (Response)**

    | Name                    | Type    | Description     |
    |:-----------------------:|:-------:|:---------------:|
    | **code**                | Integer | 응답 코드       |
    | **status**              | String  | 응답 상태       |
    | **message**             | String  | 응답 메시지     |
    | **data**                | String  | 응답 정보       |

    <br>

    단기 예보 정보 저장을 성공한 경우,

    ```text
    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    ```

    ```json
    {
        "code": 200,
        "status": "success",
        "message": "단기 예보가 저장되었습니다.",
        "data": null
    }
    ```

    <br>

    단기 예보 정보 저장을 실패한 경우,

    ```json
    {
        "code": 200,
        "status": "failed",
        "message": "단기 예보가 이미 존재합니다.",
        "data": null
    }
    ```

    <br><br>



### 2. 단기 예보 조회 API

- **요청 (Request)**

    단기 예보 정보를 조회합니다.

    ```
    GET /forecasts
    ```
    <br>

    - 쿼리 파라미터

    |    Name   |   Type  | Description | Required |
    |:---------:|:-------:|:-----------:|:--------:|
    |  **page** | Integer | 페이지 번호  |     X    |
    | **count** | Integer | 목록 개수    |     X    |

    <br><br>


- **응답 (Response)**
    
    | Name                    | Type    | Description     |
    |:-----------------------:|:-------:|:---------------:|
    | **code**                | Integer | 응답 코드        |
    | **status**              | String  | 응답 상태        |
    | **message**             | String  | 응답 메시지      |
    | **data**                | String  | 응답 정보        |
    |                         |         |                 |
    | **page**                | Integer | 페이지 번호      |
    | **count**               | Integer | 목록 개수        |
    | **totalCount**          | Integer | 목록 전체 수     |
    | **items**               | List    | 데이터 목록      |
    |                         |         |                 |
    | **id**                  | Integer | 아이디           |
    | **baseAt**              | String  | 발표날짜         |
    | **nx**                  | Integer | 예보지점 X 좌표  |
    | **ny**                  | Integer | 예보지점 Y 좌표  |
    | **category**            | String  | 자료구분문자     |
    | **categoryName**        | String  | 자료구분 이름    |
    | **forecastAt**          | String  | 예보날짜         |
    | **forecastValue**       | String  | 예보 값          |
    | **forecastUnit**        | String  | 예보 값 단위     |
    | **forecastDescription** | String  | 예보 값 설명     |

    <br><br>

    단기예보가 저장되어 있는 경우,
    ```text
    HTTP/1.1 200 OK
    Content-Type: application/json;charset=UTF-8
    ```

    ```json
    {
        "code": 200,
        "status": "success",
        "message": null,
        "data": {
            "page": 1,
            "count": 918,
            "totalCount": 918,
            "items": [
                {
                    "id": 1,
                    "baseAt": "2024-05-02T20:00:00",
                    "nx": 62,
                    "ny": 130,
                    "category": "TMP",
                    "categoryName": "1시간 기온",
                    "forecastAt": "2024-05-02T21:00:00",
                    "forecastValue": "17",
                    "forecastUnit": "℃",
                    "forecastDescription": null
                }
            ]
        }
    }
    ```

    <br>

    단기예보가 저장되어 있지 않은 경우,
    ```text
    HTTP/1.1 204 NO CONTENT
    ```
    
    <br><br><br>



## License
[MIT](./LICENSE) License Copyright (c) 2024 RoseNo
