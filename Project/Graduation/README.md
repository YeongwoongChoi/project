# [졸업논문 및 작품]
<hr/>

* 개요
  + 구현 일시
    + Local 코드: 2024. 4. ~ 2024. 6.
    + 웹 사이트: 2024. 7. ~ (진행중)
  + 주제: JSP와 MySQL을 활용한 식당 예약 시스템
  + 인원: 1명 (개인 프로젝트)
 
* 주요 정보
    + 현재 repo에 있는 코드는 localhost에서 동작한 후, 실행하였던 소스 코드입니다.
    + 웹 사이트로 이식 및 구현된 버전은 http://34.83.133.206:8080 에서 확인할 수 있습니다.
    + <b>테스트용 ID: test, password: 1111 </b>
    + 모바일 환경에서 접속할 경우, 레이아웃 등 일부 기능의 동작이 정상적이지 않을 수 있습니다.
    + <b>[수정: 2024. 8. 1. 15:07] delete query issue 수정 완료 </b>(https://github.com/YeongwoongChoi/project/issues/2#issue-2441552050)
    + 현재 버그 fix 및 기능 추가 작업 중입니다.
  + 발표 자료 및 논문은 동일 repo의 PowerPoint 또는 PDF 자료를 통해 확인할 수 있습니다.
  + 테스트용 DB 백업 자료(backup.sql)는 MySQL에 맞는 SQL로 작성되었습니다.

* 구현 기능
  + 초기 화면
    + 고객 로그인 및 계정 ID/Password 찾기
    + 고객 회원가입
  + 메인 화면
    + 간략한 예약 정보 조회
    + 조건부 식당 조회
  + 식당 방문 예약 추가, 취소, 조회
    + 식당 상세 정보 조회
    + 리뷰 작성 및 조회, 삭제
  + 메뉴 예약 추가, 취소, 조회
  + 마이페이지
    + 개인 정보 수정 (비밀번호, 프로필 사진 등)
    + 기존 예약 조회 및 취소
    + 회원 탈퇴, 로그아웃

  + <b> 부가 기능 구현 중 </b>
  
* 이용 스킬
  + 공통
    + Java 8 (JSP, 시스템에 필요한 entity 및 DB 접근용 Class)
    + Apache Tomcat 9
    + JavaScript
    + SQL
  + Local Code
    + Localhost (Windows) 
    + JDBC Driver for MySQL (com.mysql.cj.jdbc.Driver)
  + 웹 사이트
    + Google Cloud Platform에 구축 (Ubuntu 20.04)
    + JDBC Driver for MariaDB (org.mariadb.jdbc.Driver)
<hr/>
