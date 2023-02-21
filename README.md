# miniProject
### 제목학원
- 이미지에 맞는 재미있는 제목을 작성하여 공유하는 커뮤니티 입니다.

## API & 팀 노션
https://www.notion.so/5-SA-94973c1774cf4462ab310684aa6cfd13?pvs=4

## ERD
![제목학원 (2)](https://user-images.githubusercontent.com/95573777/220406942-0fa478fd-ccd3-4f8f-a0df-144209ae1b9a.png)

## 기술 스택
### **Application**
- JAVA 11
- Spring Boot _2.7.0
- Spring Security _0.11.2
- JPA


### **Data**
- AWS S3
- H2

### **DevOps**
- AWS EC2


# 트러블 슈팅

## 1. (AWS 설정 오류)
문제 상황 : git bash로 배포까지 문제없이 완료됬지만 요청을 보내면 timeout 에러가 뜨는 상황
문제 원인 : AWS EC2 배포시 인스턴스의 인바운드 규칙 중 8080포트를 열어주지 않아 생긴 문제
해결 방법 : 인바운드 규칙에서 8080포트를 열어주고 다시 배포하여 해결

## 2. (Response 값 추가)
문제 상황 : 프론트에서 좋아요 여부 불러오기 시 그 유저가 좋아요를 눌렀는지 바로 확인이 어려운 문제
문제 원인 :
해결 방법 : 게시글 상세보기 와 좋아요 업데이트 시 해당 유저가 좋아요를 눌렀는지에 대한 여부를 추가해서 response에 담아서 보냄

## 3. (단순 오타)
문제 상황 : 프론트에서 AccessToken과 RefeshToken을 받아오지 못 하는 상황
문제 원인 : cors 설정 시 addExposedHeader 메서드에 AccessToken, RefreshToken 오타
해결 방법 : cors 설정 시 AccessToken -> Access_Token, RefreshToken -> Refresh_Token 변경 후 해결

## 4. (Bulid 오류)
문제 상황 : 배포를 하기위해 프로젝트 Build를 하였는데 "Execution failed for task ':test'." 이러한 오류가 뜨면서 Build 에 실패함
문제 원인 : 프로젝트 경로에 한글이 들어있어서 빌드하는데 실패함
문제 해결 : 프로젝트 경로를 바탕화면에서 C 드라이브로 옮겨서 다시 Build 하니 해결 

## 5. (로직 오류)
문제 상황 : 게시글 수정, 삭제 기능을 테스트 중 nullpointerexception에러 발생
문제 원인 : postman에 AccessToken과 RefreshToken값을 넣지 않음
해결 방법 : 게시글 작성할 때 AccessToken과 RefreshToken값을 넣고 로그인 했을 때 작성하게 수정

## 6. (로직 오류)
문제 상황 : 게시글 상세 조회 기능 테스트 중 response값 중 comment에 값이 들어가지 않음
문제 원인 : commentresdto값이 들어있는 commentresdtolist 필드를 PostResponsedto.builer에 추가하지 않음
해결 방법 : List로 만들었던 commentresdto를 필드에 추가

## 7. (연관관계 설정오류) 
문제 상황 : 댓글이 게시글을 삭제해도 남음
문제 원인 : 댓글과 게시글의 연관관계가 설정이 안되어 있었음
문제 해결 : 댓글과 게시글의 다대일 연관관계를 설정하여 해결

## 8. (JPA 오류)
문제 상황 : 댓글 전체 조회를 했는데 모든 게시글의 댓글이 한번에 나오는 문제
문제 원인 : 댓글을 불러올 때 게시글의 id 값을 조건으로 주어 그 값만 가져오는 구문이 빠져있었음
해결 방법 : 댓글을 불러올 때 해당 게시글의 id 값을 조건으로 주어 일치하는 값만 뽑아오는 로직을 추가함.

## 9. (Response 값 추가)
문제 상황 : 프론트에서 댓글을 등록, 수정, 삭제 할 때 바로 값을 업데이트 하지 못하고 새로고침을 눌러야만 업데이트 됨.
문제 원인 : 댓글 등록, 수정, 삭제 시 Response 값이 없기 때문에 바로 업데이트가 어려움
해결 방법 : 댓글 수정, 등록, 삭제 시 Response 값에 댓글을 담아 return 해줌
