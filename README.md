# GD Health
![image](README/headoffice.png)
>헬스클럽 운영/관리 플랫폼입니다. <br>
팀원들과 프로젝트 주제에 대해 상의하던 중, 헬스 프랜차이즈 지점별로 고객들을 관리하고 본사에서 총괄하는
웹사이트를 만들면 좋을 것 같다는 의견이 있어 회의를 거쳐 제작하였습니다.

⌨️ **Demo Link**<br>
[로그인 페이지](http://52.78.98.70)<br>
<br>

## 📍 역할
본사(Headoffice) 페이지, 채팅<br>
<br>


## 📍 참여 인원 & 제작 기간
- 참여 인원 : 5명 
- 제작 기간 : 2023.12.21 — 2024.01.31
  
<br>

## 📍 사용 기술
Backend
>Java 17<br>
>Spring Boot 3.2.0<br>
>MySQL 8.0.43<br>
>MyBatis
  
Frontend
>JavaScript(JQuery)
  
Tool
>Gradle<br>
>Git<br>
>AWS

<br>

## 📍 담당 기능
- 공통 기능
  - 본사 페이지네이션 [📌 코드 확인](https://github.com/kwanho12/GDHealth/blob/develop/src/main/java/com/tree/gdhealth/utils/pagination/HeadofficePagination.java) 
  - 본사 페이지 파일 저장 [📌 코드 확인](https://github.com/kwanho12/GDHealth/blob/develop/src/main/java/com/tree/gdhealth/utils/imagesave/HeadofficeImageSaver.java)
<br><br>

- 직원(본사) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/headoffice/emp)
  - 직원 추가
      - ID 중복 검사
      - Bean Validation API를 이용한 유효성 검사(정규식 이용)
  - 직원 목록
      - 검색 기능
      - Ajax로 페이지네이션 처리(검색 후 결과에 대해서도 적용)
<br><br>

- 회원(본사) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/headoffice/customer)
  - 회원 목록
    - 검색 기능
    - Ajax로 페이지네이션 처리(검색 후 결과에 대해서도 적용)
<br><br>

- 프로그램(본사) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/headoffice/program)
    - 프로그램 추가
        - 달력 API를 이용해 개설 날짜 추가 가능
        - Bean Validation API를 이용한 유효성 검사
        - 개설 날짜들의 중복 검사
        - Custom Validator API로 프로그램 파일을 첨부하였는지 검증
        - Service Layer에서 첨부한 파일의 type이 이미지인지 검증
    - 프로그램 목록
        - 검색 기능
        - Ajax로 페이지네이션 처리(검색 후 결과에 대해서도 적용)
    - 프로그램 상세
        - 비활성화/활성화 버튼 클릭시 active 변경
    - 프로그램 수정
        - 이미지 파일 첨부시 Service Layer에서 첨부한 파일의 type이 이미지인지 검증
        - Custom Validator와 Bean Validation으로 서버 사이드에서 검증
<br><br>

- 물품(본사) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/headoffice/sportsEquipment)
    - 물품 추가
        - Bean Validation API를 이용한 유효성 검사
        - Custom Validator API로 프로그램 파일을 첨부하였는지 검증
    - 물품 목록
        - 검색 기능
        - Ajax로 페이지네이션 처리(검색 후 결과에 대해서도 적용)
        - 비동기 통신으로 활성화/비활성화 버튼 구현
    - 물품 수정
        - 이미지 파일 첨부시 Service Layer에서 첨부한 파일의 type이 이미지인지 검증
<br><br>

- 채팅(본사) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/headoffice/chat)
    - 채팅방 목록
        - 채팅으로 문의한 고객들의 채팅방 조회 가능
        - 각각의 고객에 대한 채팅 방을 구분하고 특정한 고객과 1대1로만 채팅을 할 수 있게 구현
    - 채팅방
        - 웹소켓으로 채팅 기능 구현
        - 각각의 고객의 방으로 입장하면 고객과 본사 직원이 보낸 이전 채팅 기록을 확인 가능
        - 고객과 실시간으로 채팅이 가능하도록 구현
<br><br>

- 채팅(고객) [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/customer/chat)
    - 채팅방
        - 웹소켓으로 채팅 기능 구현
        - 처음으로 채팅 방에 입장한다면 DB에 방에 대한 정보 추가
        - 고객과 본사 직원이 보낸 이전 채팅 기록을 확인 가능
        - 본사 직원과 실시간으로 채팅이 가능하도록 구현
<br><br>

## 📍 개선 사항
   - 프로그램을 추가할 때 날짜의 형식과 오늘 이후의 날짜인지, 날짜의 형식이 올바른지에 대해 검증해야 하는데 스프링이 제공하는 기본 validation API로는 검증에 한계가 있었습니다.<br>
    → Custom Validator API를 만들어 List type에 대해 validation을 하여 선택한 프로그램 날짜들의 형식이 올바른지, 오늘 이후의 날짜인지 검증하였습니다. [📌 코드 확인](https://github.com/kwanho12/GDHealth/tree/develop/src/main/java/com/tree/gdhealth/utils/customvalidation)
    <br><br>

   - SocketHandler에서 채팅방 번호와 session에 대한 정보를 저장하는 roomSessionMap 변수는 여러 스레드에서 공유되는 자원이므로 여러 사용자의 웹 소켓 세션들이 동시에 읽고 쓰려고 할 때 동시성 문제가 발생할 수 있습니다.<br>
    ![image](README/roomSessionMap1.png)
    → roomSessionMap의 객체를 HashMap에서 동시성을 위해 설계된 컬렉션인 ConcurrentHashMap으로 변경하였습니다.<br>
    ![image](README/roomSessionMap2.png)
    <br><br>

   - 한 번에 DB의 program_date table에 여러 개의 프로그램 날짜들을 추가하려고 할 때 Service Layer에서 프로그램 날짜 1개를 추가할 때마다 insert 작업을 한 번 하려고 하였으나 DB 성능 저하가 우려되었습니다.
    <br>
    → ProgramMapper.xml에서 for each문을 사용하여 List&lt;Domain&gt; type으로 한 번의 insert 작업으로 여러 개의 프로그램 날짜들을 추가하였습니다. [📌 코드 확인](https://github.com/kwanho12/GDHealth/blob/develop/src/main/java/com/tree/gdhealth/headoffice/program/ProgramMapper.xml#L144)

<br>


