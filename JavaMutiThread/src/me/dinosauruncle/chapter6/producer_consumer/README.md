# Producer-Consumer Pattern

## Producer-Consumer Pattern 이란?

producer은 생산자, 데이터 작성하는 thread를
consumer는 소비자, 데이터를 이용하는 thread를 의미한다.

생산자가 소비자에게 안전하게 데이터를 전달하는 패턴이다.

다음과 같은 문제의 상태이 있다고 하자
다만 생산자와 소비자가 각각 별도의 thread로서 동작할 때
양쪽 처리속도에 차이가 있으면 문제가 발생한다.
소비자가 데이터를 받으려 하는데 데이터가 미완성 상태거나,
생산자가 데이터를 건네려 하는데 소비자가 데이터를 받을 수 없는
상태의 문제를 말한다.

Producer-Consumer Pattern은 생산자와 소비자 사이에
중개 역할로 존재하며, 이 중개 역할이 양쪽 thread 간 처리 속도의
차이를 메우게 된다


## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Ch6AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
	<tr>
		<td>MakerThread</td>
		<td>요리사를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>EaterTrhead</td>
		<td>손님을 나타내는 클래스</td>
	</tr>
	<tr>
		<td>Table</td>
		<td>테이블을 나타내는 클래스</td>
	</tr>
</table>