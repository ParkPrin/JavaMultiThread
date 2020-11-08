# Thread-Per-Message Pattern

## Thread-Per-Message Pattern이란? 
thread per message를 직역하면 메시지마다 쓰레드 라는 뜻이다.
어떠한 명령이나 요구마다 새로 한 개의 쓰레드가 할당되고 그 쓰레드가 처리를 실행한다.

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch8AMain</td>
		<td>Host에 문자표시를 요구하는 클래스</td>
	</tr>
	<tr>
		<td>Host</td>
		<td>요구에 대하여 쓰레드를 생성하는 클래스</td>
	</tr>
	<tr>
		<td>Helper</td>
		<td>문자표시라고 하는 기능을 제공하는 수동적인 클래스</td>
	</tr>
</table>