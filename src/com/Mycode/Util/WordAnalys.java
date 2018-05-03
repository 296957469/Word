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
	    //字符变量，存放最新读入的源程序字符
		static char character=' ';
		//字符数组，存放构成单词符号的字符串
		static StringBuilder token=new StringBuilder("");
		//存放输入的源程序
		static String Input;
		//扫描指针的位置
		static int pos=0;
		//文件中读取的单词,全部存放都这个容器里面
		static List<Word> words;
		private static void getchar() {
			if(character!='~') {
			character=Input.charAt(pos);
			pos++;
			}
		}
		//存放中文件中服务的单词
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
			System.out.println("非法字符："+token);
			
		}
		public static void main(String[] args) throws IOException {
			//第一种方式，用户输入源程序
			//Scanner scanner=new Scanner(System.in);
			//Input=scanner.nextLine();
			//第二种方式，直接读取源程序文件
			InputStream inputStream=null;
			try {
				inputStream=new FileInputStream("g:/Code/用户程序.txt");
			} catch (FileNotFoundException e) {
				inputStream=new FileInputStream("g:/Code/用户程序.docx");
			}
			//读取文件的内容
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
				System.out.println("文件不存在！");
			}
			String string;
			//读取文件的内容
			while((string=reader.readLine())!=null) {
				Word word=new Word();
				word.setSort(string.substring(0, string.indexOf(" ")));
				word.setValue(string.substring(string.indexOf(" ")+1, string.lastIndexOf("/")));
				wordss.add(word);
			}
			
			//词法分析器开始
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
					//变量
					System.out.println(token+" ("+56+","+token+")");
				}else {
					//保留字
					System.out.println(token+" ("+reserve()+","+token+")");
				}
			
			}
			else if(digit()) {
				while(digit()) {
					concatenation();
					getchar();
				}
				retract();
				//整常量
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
			//+或者++
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
			//-或者--
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
		//解析工厂，从文件中读取单词符合，设置为静态语句块，只初始化一次
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
