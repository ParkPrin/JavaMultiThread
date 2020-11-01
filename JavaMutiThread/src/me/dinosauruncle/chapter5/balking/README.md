# Balking Pattern

## Balking Pattern이란?
특정 처리를 실행하면 곤란하다거나 혹은 당장 실행할 필요가 없는 경우
처리 직전에 실행을 중단하고 돌아 가는 패턴

## 예제

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Data</td>
		<td>변경·저장이 가능한 데이터를 나타내는 클래스</td>
	</tr>
	<tr>
		<td>SaverThread</td>
		<td>데이터의 내용을 정기적으로 저장하는 클래스</td>
	</tr>
	<tr>
		<td>ChangerThread</td>
		<td>데이터의 내용을 변경·저장하는 클래스</td>
	</tr>
	<tr>
		<td>Ch5AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
</table>