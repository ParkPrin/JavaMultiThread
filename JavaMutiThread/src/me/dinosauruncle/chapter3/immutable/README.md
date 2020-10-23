# Immutable 패턴

## Immutable 패턴이란?

Immutable은 [불편의], [변하는 것이 없는] 이라는 뜻을 가짐<br>
Immutable 패턴에는 인스턴스의 상태가 절대 변하지 않는 클래스가 등장한다.<br>
그 인스턴스의 액세스에는 시간이 걸리는 배타제어가 불필요하기 때문에 잘 사용하면 수행능력을 높일 수 있다<br>

## 예시

<table>
	<tr>
		<td>이름</td>
		<td>해설</td>
	</tr>
	<tr>
		<td>Person</td>
		<td>사람을 나타내는 클래스</td>
	</tr>
	<tr>
		<td>Ch3AMain</td>
		<td>동작 테스트용 클래스</td>
	</tr>
	<tr>
		<td>PrintPersonThread</td>
		<td>Person 인스턴스를 표시하는 thread를 나타내는 클래스</td>
	</tr>
</table>

### 예시 코드

Person.java

```
public final class Person {
	private final String name;
	private final String address;
	public Person(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String toString() {
		return "[ Person: name = " + name + ", address = " + address + " ]";
	}

}
```

Ch3AMain.java

```
	public static void main(String[] args) {
		// 불변의 객체를 공유자원으로 사용함
		Person alice = new Person("Alice", "Alaska");
		
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();
		new PrintPersonThread(alice).start();

	}
```

PrintPersonThread.java

```
public class PrintPersonThread extends Thread {
	private Person person;
	// 객체 자체가 final로 정의된 불편 객체이기 때문에 synchronized를 하지 않아도 데이터 간섭이 발생하지 않는다
	// synchronized가 불필요함
	public PrintPersonThread(Person person) {
		this.person = person;
	}
	
	public void run() {
		while (true) {
			// Thread.currentThread().getName은 자기 thread의 이름을 구할 때 사용한다
			// Thread.currentThread()는 현재의 thread 구하는 메소드이다.
			System.out.println(Thread.currentThread().getName() + "prints" + person);
		}
	}
}
```

## single threaded execution과 immutable 패턴과의 비교

single threaded execution과 immutable 두 패턴과의 공통점 thread와 공용자원을 사용하는 것이다
그러나 가장 큰 차이점은 single threaded execution은 공용자원이 복수의 thread가 사용할 때마다
공용자원객체 내부의 변경이 일어나지만, immutable은 공용자원객체의 변경이 없다는 것이다.
그래서 공용자원객체의 내부 변경이 일어날 때 데이터 간섭의 의한 데이터 무결성이 깨지지만, 객체 변경이 불가하다면
아무리 많은 양의 thread가 불변의 객체를 공용자원으로 사용하더라도 데이터 간섭이 발생하지 않으므로
배타제어 처리를 하지 않아도 된다. 이로써 안전성과 생존성을 잃어버리지 않고도 수행 능력을 높일 수 있다.


## 어떨 때 사용할까? [적용 가능성]

1. 인스턴스 생성 후 상태가 변하지 않을 때
2. 인스턴스가 공유되어 빈번하게 액세스 될 때

## 표준 클래스 라이브러리에서 사용되는 Immutable 패턴

1. 문자열을 나타내는 java.lnag.String 클래스
2. 큰 수를 나타내는 java.math.BigInteger 클래스
3. 정규 표현의 패턴을 나타내는 java.util.regex.Pattern 클래스
4. 색을 표현하는 java.awt.Color
5. java.lang.Integer

<table>
	<tr>
		<td>기본형</td>
		<td>랩퍼 클래스</td>
	</tr>
	<tr>
		<td>boolean</td>
		<td>java.lang.Boolean</td>
	</tr>
	<tr>
		<td>char</td>
		<td>java.lang.Char</td>
	</tr>
	<tr>
		<td>float</td>
		<td>java.lang.Float</td>
	</tr>
	<tr>
		<td>long</td>
		<td>java.lang.Long</td>
	</tr>
	<tr>
		<td>void</td>
		<td>java.lang.Void</td>
	</tr>
	<tr>
		<td>byte</td>
		<td>java.lang.Byte</td>
	</tr>
	<tr>
		<td>double</td>
		<td>java.lang.Double</td>
	</tr>
	<tr>
		<td>Int</td>
		<td>java.lang.Integer</td>
	</tr>
	<tr>
		<td>short</td>
		<td>java.lang.Short</td>
	</tr>
</table>


## final

### final의 의미

#### final 클래스
클래스에 final이 선언된 경우 그 클래스는 확장할 수 없고 결국 final한 클래스의 서브 클래스는 만들 수 없다.

#### final 메소드
인스턴스 메소드에 final이 선언된 경우 그 메소드는 서브 클래스의 메소드로 오버라이드 될 수 없다.

#### final 필드
final한 필드에는 한 번 밖에 대입할 수 없다.
대입에는 두가지 방법이 있다

```
// 선언과 동시에 대입하는 방법
final int value = 123;
```

```
class Something {
	// 선언을 하고 대입은 생성자에서 하는 방법
	final int value;
	public Something(int value){
		this.value = value;
	}
}


```

## 컬렉션 클래스와 multi thread
컬렉션이란 복수의 인스턴스를 관리하는 인터페이스나 클래스에 대한 총칭
java의 컬렉션은 대부분 thread safe가 아니다.
=> multi thread 사용하고자 하는 클래스나 인터페이스가 thread safe인지 확인하는 것이 중요


