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


Ch8AMain.class

```
public class Ch8AMain {
	public static void main(String[] args) {
		System.out.println("main BEGIN");
		Host host = new Host();
		host.request(10, 'A');
		host.request(20, 'B');
		host.request(30, 'C');
		System.out.println("main END");
	}
}
```

Host.class

```
public class Host {
	private final Helper helper = new Helper();
	public void request(final int count, final char c) {
		System.out.println("      request(" + count + ", " + c + ") BEGIN");
		new Thread() {
			public void run() {
				helper.handle(count, c);
			}
		}.start();
		System.out.println("      request(" + count + ", " + c + ") END");
	}
}
```

Helper.class

```
public class Helper {
	public void handle(int count, char c) {
		System.out.println("      handle(" + count + ", " + c + ") BEGIN ");
		for (int i=0; i < count; i++) {
			slowly();
			System.out.println(c);
		}
		System.out.println("");
		System.out.println("      handle(" + count + ", " + c + ") END");
	}
	private void slowly() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
		}
	}
}
```

main 함수에서 host 객체에서 request를 실행할 때마다 thread 객체가 생성이 되고
주입된 helper 객체의 handle 메소드가 실행이 된다.
특징으로는 request 객체가 실행 후 Helper 객체의 handle 메소드를 호출하고 종료해버린다.
handle 메소드의 호출 종료여부와 관계없이 호출후 종료해버리는 비동기적 방식으로 진행이 된다.

## Thread-Per-Message Pattern의 등장인물

1. Client(의뢰자) 역할: Host객체의 request를 호출하는 역할을 담당한 main함수
2. Host 역할: Client 역할로부터 요구(request)를 받으면 새로 쓰레드를 만들어서 기동한다. 
3. Helper (원조자) 역할: Helper 역할은 요구를 처리(handle)하는 기능을 Host에게 제공한다.


## Thread-Per-Message Pattern의 특징과 사용하는 경우 
1. 응답성을 높이고 지연 시간을 줄인다 
2. 처리 순서를 상관하지 않을 때 사용한다
3. 반환값이 불필요한 경우에 사용한다
4. 서버에 응용
5. 메소드 호출 + 쓰레드 기동 -> 메시지 송신 