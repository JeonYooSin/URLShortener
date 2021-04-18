# URLShortener
단축 URL - java

# 개발환경
* JAVA8
* spring boot 2.4.4
* JPA
* H2 (file mode - 프로젝트 내장)
* maven

# 기능 
* URL 입력 시 단축 URL 출력 및 호출 수 조회 기능 (화면 제공)
* 부가기능
  - URL 유효성 검증
  - 동일 URL 동일 단축 URL 리턴
* 단축 URL은 Base62로 7자리 Random 값으로 제공

# 설치 및 실행방법
 ## 설치 
 1. git 저장소 zip 파일 및 git clone으로 다운로드 진행 
  > git clone https://github.com/JeonYooSin/URLShortener.git
 2. 설치한 디렉토리에서 ./URLShortener/bin 디렉토리 이동 
  > cd ./URLShortener/bin
 3. bin 디렉토리 밑에 .war 파일 실행
  > java -jar URLShortener-0.0.1-SNAPSHOT.war
  
 ## 단축 URL 페이지 접속
 1. http://localhost:8090/ 으로 페이지 접속 ( 기본 포트 8090 )
