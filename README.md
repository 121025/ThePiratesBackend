# 더파이러츠 백엔드 개발자 채용 과제

1. 설치 및 환경설정 가이드  
(1) 압축파일을 다운로드하고 압축해제 후 IntelliJ로 Backend 폴더를 엽니다.  
(2) 의존성과 플러그인들이 설치되는 동안 기다립니다.  
(3) Run -> Run...을 클릭하고 BackendApplication을 선택합니다.  
(3-1) BackendApplication이 없을 경우 Edit Configurations...을 선택하고 Templates에서 Application을 선택합니다. Main class로 BackendApplication을 선택한 후 OK를 클릭합니다.  
(3-2) 포트가 이미 사용 중일 경우 src - main - resources - application.properties 파일에서 server.port를 다른 번호로 지정합니다.  
(4) Spring Boot가 완전히 시작한 후에 http://localhost:8081/h2-console/ 에 접속합니다.  
(5) ![image](https://user-images.githubusercontent.com/12444076/131545281-bd72b56e-35c4-498f-b796-bce00e3f99c4.png)
