# Worker Thread Pattern

## Worker Thread Pattern 이란? 

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch9AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
	<tr>
		<td>ClientThread</td>
		<td>업무를 리퀘스트 하는 쓰레드를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>Request</td>
		<td>업무의 리퀘스트를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>Channel</td>
		<td>업무의 리퀘스트를 받아들이고, 워커 쓰레드에게 건네는 클래스</td>
	</tr>
	<tr>
		<td>WorkerThread</td>
		<td>워커 쓰레드를 나타내는 클래스</td>
	</tr>
</table>