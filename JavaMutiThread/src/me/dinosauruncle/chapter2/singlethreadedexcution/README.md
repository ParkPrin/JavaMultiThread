# Single Threaded Execution 패턴

## Single Threaded Execution 패턴이란?

한 개의 쓰레드에 의한 실행 => 멀티 쓰레드 프로그래밍의 기초
다른 명칭으로는 Critical Section(아슬아슬한 영역, 위험 구역) 혹은 Critical Region

절벽 위 일자 통나무 위에서 건너는 사람을 빗대서 만든 개념

## Single Threaded Execution 을 사용하지 않는 예시

```
프로그램에서는 한 번에 한 사람 밖에 건널 수 없는 문(gate)을 3명이 통과하는 모습을 실험한다.
즉 사람이 문을 통과할 때마다 그 수를 세고, 또 통과하는 사람의 [이름과 출신지]를 기록하게 된다
```


<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Main</td>
		<td>문을 만들고, 3명을 문으로 통과시키는 클래스</td>
	</tr>
	<tr>
		<td>Gate</td>
		<td>문을 표현하는 클래스, 사람이 지나갈 때 이름과 출신지를 기록한다.</td>
	</tr>
	<tr>
		<td>User Thread</td>
		<td>사람을 나타내는 클래스, 문을 통과한다.</td>
	</tr>
</table>

Ch2AMain.java, Gate.java, UserThread.java 코드를 이용해서 만듬
