package com.Mycode.Util;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * ʹ��SAXpaserfactory���ļ���˳���ȡ
 * 1.һ���ļ�ֻ����һ����Ԫ�ء���Ԫ�������õķ���������startElement->charactesr->endElement.
 * 2.�Ǹ�Ԫ�������õķ���������startElement->charactesr->endElement->characters.
 * 3.�ܽ����Ϸ�ʽ��ÿ�ηǸ�Ԫ�ص���endElement��ʱ���ñ��Ϊ�գ�Ȼ��characters������ҩ�����ж�
 * ����Ƿ�Ϊ�յı��ʽ���������ֿ�ָ���쳣
 * �����е�system�Ǹ�Ԫ��
 * �����е�element��sort��value�ǷǸ�Ԫ��
 * �״�㣬�Ƚ��ַ�����ʱ����equals(element)����equals("element")
 * @author SmallRain
 *
 */
public class WordHandler extends DefaultHandler {
    private String tag;
    private Word word;
    private List<Word>words;
    
    public WordHandler() {
		super();
		this.tag =null;
		this.words = new ArrayList<Word>();
	}

	@Override
	public void startDocument() throws SAXException {
       //System.out.println("������ʼ��");
	}
  
	@Override
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
			tag=arg2;
		if(tag.equals("element"))
			word=new Word();
		//System.out.println("��ʼһ��Ԫ�أ�"+tag);
			
	}
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		//System.out.println("Ԫ�ص�ֵΪ��"+new String(arg0,arg1,arg2).trim());
		String element=null;
		element=new String(arg0, arg1, arg2);
           if(tag!=null&&tag.equals("sort"))
           {
        	   word.setSort(element);
           }
           else if(tag!=null&&tag.equals("value"))
           {
        	   word.setValue(element);
           }
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
		
		// System.out.println("����һ��Ԫ�أ�"+arg2);
		if(arg2.equals("element"))
		{
			words.add(word);
		}
		tag=null;
	}

	@Override
	public void endDocument() throws SAXException {
		
		 //System.out.println("����������");
	}

	public List<Word> getWords() {
		return words;
	}

}
