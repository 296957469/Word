package com.Mycode.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class WordAnalys {
	    //�ַ�������������¶����Դ�����ַ�
		static char character=' ';
		//�ַ����飬��Ź��ɵ��ʷ��ŵ��ַ���
		static StringBuilder token=new StringBuilder("");
		//��������Դ����
		static String Input;
		//ɨ��ָ���λ��
		static int pos=0;
		//�ļ��ж�ȡ�ĵ���,ȫ����Ŷ������������
		static List<Word> words;
		private static void getchar() {
			if(character!='~') {
			character=Input.charAt(pos);
			pos++;
			}
		}
		//������ļ��з���ĵ���
		static List<Word> wordss=new ArrayList<Word>();
		private static void getbe() {
			while(character==(int)' ') {
				getchar();
			}
		}
		private static void concatenation() {
			token.append(character);
		}
		private static boolean letter(){
			if(('A'<=character&&character<='Z')||('a'<=character&&character<='z')) {
				return true;
			}
			else {
				return false;
			}
		}
		private static boolean digit() {
			if ('0'<=character&&character<='9') {
				return true;
			}
			else {
				return false;
			}
		}
		private static int reserve() {
			for(Word word:wordss) {
				if(token.toString().equals(word.getValue())) {
					return Integer.parseInt(word.getSort());
				}
			}
			return -1;
		
		}
		private static void retract() {
			pos--;
			character=' ';
		}
		private static void error() {
			System.out.println("�Ƿ��ַ���"+token);
			
		}
		public static void main(String[] args) throws IOException {
			//��һ�ַ�ʽ���û�����Դ����
			//Scanner scanner=new Scanner(System.in);
			//Input=scanner.nextLine();
			//�ڶ��ַ�ʽ��ֱ�Ӷ�ȡԴ�����ļ�
			InputStream inputStream=null;
			try {
				inputStream=new FileInputStream("g:/Code/�û�����.txt");
			} catch (FileNotFoundException e) {
				inputStream=new FileInputStream("g:/Code/�û�����.docx");
			}
			//��ȡ�ļ�������
			byte []buffer=new byte [1024];
			int len=0;
			StringBuilder str=new StringBuilder();
			while((len=inputStream.read(buffer))!=-1) {
				str.append(new String(buffer,0,len));
			}
			Input=str.toString();
			
			BufferedReader reader=null;
			try {
				reader=new BufferedReader(new FileReader("g:/Code/word.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("�ļ������ڣ�");
			}
			String string;
			//��ȡ�ļ�������
			while((string=reader.readLine())!=null) {
				Word word=new Word();
				word.setSort(string.substring(0, string.indexOf(" ")));
				word.setValue(string.substring(string.indexOf(" ")+1, string.lastIndexOf("/")));
				wordss.add(word);
			}
			
			//�ʷ���������ʼ
			while(character!='~') {
			getchar();
			getbe();
			if(letter()) {
				while(letter()||digit()) {
					concatenation();
					getchar();
				}
				retract();
				if(reserve()==-1) {
					//����
					System.out.println(token+" ("+56+","+token+")");
				}else {
					//������
					System.out.println(token+" ("+reserve()+","+token+")");
				}
			
			}
			else if(digit()) {
				while(digit()) {
					concatenation();
					getchar();
				}
				retract();
				//������
				System.out.println(token+" ("+57+","+token+")");
			}
			//;
			else if(character==';') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}
			//#
			else if(character=='#') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}
			//+����++
			else if(character=='+') {
				concatenation();
				getchar();
				if(character=='+')
					{
					concatenation();
				    System.out.println(token+" ("+reserve()+","+token+")");
					}
				else {
					System.out.println(token+" ("+reserve()+","+token+")");
					retract();
				}
			}
			//-����--
			else if(character=='-') {
				concatenation();
				getchar();
				if(character=='-')
					{
					concatenation();
				    System.out.println(token+" ("+reserve()+","+token+")");
					}
				else {
					System.out.println(token+" ("+reserve()+","+token+")");
					retract();
				}
			}
			//*
			else if(character=='*') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}
			//(
			else if(character=='(') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}
			//)
			else if(character==')') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}
			//:=
			else if(character==':') {
				concatenation();
				getchar();
				if(character=='=') {
				concatenation();
				System.out.println(token+" ("+reserve()+","+token+")");
			}else {
				error();
				retract();
			}
				}
			
			token=new StringBuilder("");
		}
			
		}
		//�������������ļ��ж�ȡ���ʷ��ϣ�����Ϊ��̬���飬ֻ��ʼ��һ��
		static {
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parser=null;
			try {
				parser = factory.newSAXParser();
			} catch (ParserConfigurationException | SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WordHandler handler=new WordHandler();
			try {
				parser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/Mycode/Util/word.xml"),handler );
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			words=handler.getWords();
		}
		
}
