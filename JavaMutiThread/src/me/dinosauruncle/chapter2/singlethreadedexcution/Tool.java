package me.dinosauruncle.chapter2.singlethreadedexcution;

public class Tool {
	private final String name;
	public Tool(String name) {
		this.name = name;
	}
	public String toString() {
		return "[ " + name + " ]";
	}
}
