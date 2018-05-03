package com.Mycode.Util;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 使用SAXpaserfactory按文件流顺序读取
 * 1.一个文件只能有一个根元素。根元素所调用的方法依次是startElement->charactesr->endElement.
 * 2.非根元素所调用的方法依次是startElement->charactesr->endElement->characters.
 * 3.总结以上方式，每次非根元素到达endElement的时候，置标记为空，然后characters方法中药加入判断
 * 标记是否为空的表达式。否则会出现空指针异常
 * 本例中的system是根元素
 * 本例中的element，sort，value是非根元素
 * 易错点，比较字符串的时候不是equals(element)而是equals("element")
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
       //System.out.println("解析开始！");
	}
  
	@Override
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
			tag=arg2;
		if(tag.equals("element"))
			word=new Word();
		//System.out.println("开始一个元素！"+tag);
			
	}
	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		//System.out.println("元素的值为："+new String(arg0,arg1,arg2).trim());
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
		
		// System.out.println("结束一个元素！"+arg2);
		if(arg2.equals("element"))
		{
			words.add(word);
		}
		tag=null;
	}

	@Override
	public void endDocument() throws SAXException {
		
		 //System.out.println("解析结束！");
	}

	public List<Word> getWords() {
		return words;
	}

}
