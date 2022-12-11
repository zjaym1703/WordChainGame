## 끄투(온라인 끝말잇기 게임) 클론 코딩

### 프로젝트
- 게임방에 입장 후 끝말 잇기 게임을 진행하며 프로그램 내부에 저장된 단어를 맞추는 경우 점수를 획득하는 게임 
- 끄투의 기능을 클론하여 socket API로 미니프로젝트를 진행

### 개발환경과 개발 언어
<img src="https://img.shields.io/badge/Java-007396.svg?style=for-the-badge&logo=Java&logoColor=white"/> <img src="https://img.shields.io/badge/Eclipse-2C2255.svg?style=for-the-badge&logo=Eclipse&logoColor=white"/>

### 게임 시나리오
- 게임 참여 유저는 로그인 후 대기 방에 접속. 
- 게임방을 생성하거나 입장할 수 있으며, 게임방을 생성하게 되면 자동으로 방장이 되고 게임시작. 게임방 입장은 최대 5명까지 가능, 최대 인원을 초과한 경우 게임 시작 전에만 관전모드로 입장 가능
- 게임방에 입장한 순서대로 턴이 돌며 한명씩 돌아가면서 게임 진행
- 게임이 종료되면 다이얼로그로 게임 결과를 알려줌

### 실행 화면
1. 대기 방

게임에 입장한 후 play 버튼을 눌러 게임방 생성, 다른 유저들은 게임방 조회 가능<br>
<img width="287" alt="image" src="https://user-images.githubusercontent.com/41943129/206894484-c9cd4169-4ca9-4497-9240-e0921cf5b237.png"> <img width="296" alt="image" src="https://user-images.githubusercontent.com/41943129/206894500-e0b3e9bd-7d59-49f9-bbf0-2cd085952ff5.png"><br>

입장하려는 방에 인원이 찬 경우 참가자에게 알리고, 관전모드로 입장
<img width="650" alt="image" src="https://user-images.githubusercontent.com/41943129/206894551-720ffb86-421c-4319-9b29-ce4f544753a9.png">

관전모드인 경우 채팅을 게임과 채팅을 진행하지 못하며 관전모드로 입장한 유저에게 관전모드를 알려줌
<img width="397" alt="image" src="https://user-images.githubusercontent.com/41943129/206894654-5844180f-9f94-4115-8d09-c9c503a4aa84.png">

2. 게임 방

게임 참여 유저와 채팅 진행 가능, 특정 유저에게 귓속말을 보내는 경우 사용자를 클릭하여 귓속말 전송
<img width="650" alt="image" src="https://user-images.githubusercontent.com/41943129/206895336-3415194a-21dd-4049-8cd9-ef2810e2aaec.png"><br>

이모티콘으로 자신의 상태 표현<br>
<img width="650" alt="image" src="https://user-images.githubusercontent.com/41943129/206895361-2aa2b392-171c-4ca7-8656-19a5a8e35b1b.png">

게임 시작 시 wordlist.txt 파일에서 단어를 불러와 게임 참여 유저에게 전달, 차례대로 답을 입력하는 경우 10점 추가, 오답을 입력하는 경우 10점 감점, 20초의 입력시간 동안 입력하지 않는 경우도 10점 감점
![ezgif com-gif-maker](https://user-images.githubusercontent.com/41943129/206896436-0cf3be59-08db-411e-9d39-824df509f618.gif)

게임 종료 시 게임결과를 다이얼로그로 알림<br>
<img width="400" alt="image" src="https://user-images.githubusercontent.com/41943129/206895492-9c15de86-8517-42b9-85c5-ce0f16e618b0.png">


