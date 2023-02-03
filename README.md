# [MultiThread을 이용한 게임 제작]
***
#### Tech Stack
* Glide, MultiThread, MediaPlayer, Sharedpreference

***
### main 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574376-c3e86b3e-63c1-492b-9603-dbb768587061.png" width="40%" height="30%">
</p>

### 게임 난이도 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574384-b2970a41-ab70-4cd6-8eb1-bfadc8368e3c.png" width="40%" height="30%">
</p>
* 게임 난이도 별 선택 가능 easy = 25개, normal = 50개, hard = 100개 버튼이 있습니다.

### Game 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574395-1a117747-c265-48a9-9f7f-1bf9e1b6fbb1.png" width="40%" height="30%">
    <img src="https://user-images.githubusercontent.com/96619472/216574412-23926d41-37ae-49c5-ba5e-2e3acd3a0706.png" width="40%" height="30%">
</p>
* 스타트를 누루면 게임이 시작합니다.
* 버튼을 빨리 선택할수록 얻을수 있는 점수폭이 커집니다.
* 선택을 잘못했을 경우 hp가 사라집니다.
* hp를 모두 잃을시 게임 종료 / 모든 버튼을 선택할시 게임 클리어됩니다.

### Game clear 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574399-b32bbe25-a72f-4e81-8934-22128624af53.png" width="40%" height="30%">
</p>
* 마지막 버튼을 선택후 게임이 클리어 되고 게임 클리어 시간과 점수가 나옵니다.
* 게임의 점수가 최고점일 경우 점수는 저장이 됩니다.

### Game Over 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574417-8a4f26b7-74af-4977-9573-ca2ace92f9af.png" width="40%" height="30%">
</p>
* hp를 모두 잃을시 게임 오버가 되고 게임 시간과 점수가 나옵니다.

### Game 점수판 화면
<p align="center">
    <img src="https://user-images.githubusercontent.com/96619472/216574403-0c271200-dbc1-4c22-aa87-04e76425aacb.png" width="40%" height="30%">
</p>
* 나의 최고 점수와 시간을 확인할 수 있습니다.

***
### 화면을 만들면서 배운것
* 코드를 리펙토링 하는 작업중 숫자가 버튼에 배치가 안되는 오류 발생 -> 실행 순서로 인한 오류 해결 완료
* 중복되는 코드 줄이며 1000줄 넘는 코드 400줄대로 리펙토링 완료
* thread가 바로 종료하는 바람에 마지막 번호 점수를 더하지 않고 끝나는 오류 발생 -> 1초 딜레이를 넣은후 종료하게 만듬
* Sharedpreference의 apply는 비동기적/ commit 은 동기적으로 처리되므로 commit 같은 경우 main 스레드에서 이용을 하면 스레드를 block 시키기 때문에 문제를 일으 킬 수 있다는 것을 알았습니다.
* join()으로 스레드의 종료 순서를 조절할 수 있다는 것을 배웠습니다.

#### 공부해야할 것들
* 타이머를 조금더 쪼개어 밀리초 까지 나타낼 수 있는 방법을 공부하면 좋겠다고 생각핬습니다.
