# Guarded Suspension Pattern

## Guarded Suspension 이란?
Guarded [보호받고 있다]와 Suspension [일시 정지함]을 친 말로써
복수의 쓰레드가 동시에 작업을 사용할 시 쓰레드를 기다리게 하여 인스턴스의 안전성을 보호한다.

## 예시

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Request</td>
		<td>한 개의 리퀘스트를 표현한 클래스</td>
	</tr>
	<tr>
		<td>RequestQueue</td>
		<td>리퀘스트를 순서대로 비축해 두는 클래스</td>
	</tr>
	<tr>
		<td>ClientThread</td>
		<td>리퀘스트를 제시하는 클래스</td>
	</tr>
	<tr>
		<td>ServerThread</td>
		<td>리퀘스트를 접수하는 클래스</td>
	</tr>
	<tr>
		<td>Ch4AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
</table>
