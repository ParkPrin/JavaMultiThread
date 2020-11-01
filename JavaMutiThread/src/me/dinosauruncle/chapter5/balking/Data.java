package me.dinosauruncle.chapter5.balking;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Data {
	private final String filename;	// �����ϴ� ���ϸ�
	private String content;
	private boolean changed;
	
	public Data(String filename, String content) {
		this.filename = filename;
		this.content = content;
		this.changed = true;
	}
	
	// �������� ������ �����Ѵ�
	public synchronized void change(String newContent) {
		content = newContent;
		changed = true;
	}
	
	
	// �������� ������ ����Ǿ����� ���Ͽ� �����Ѵ�
	public synchronized void save() throws IOException {
		if (!changed) {
			return;
		}
		doSave();
		changed = false;
		
	}
	
	// �������� ������ ������ ���Ͽ� �����Ѵ�
	private void doSave() throws IOException {
		System.out.println(Thread.currentThread().getName() 
				+ " calls doSave, content = " + content);
		Writer writer = new FileWriter(filename);
		writer.write(content);
		writer.close();
	}
}
