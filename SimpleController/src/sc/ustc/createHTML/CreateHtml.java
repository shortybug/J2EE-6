package sc.ustc.createHTML;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.naming.spi.DirStateFactory.Result;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CreateHtml {
	/** 
	* 测试类 
	* @param args 
	* @throws ParserConfigurationException 
	*/ 
	public void ctreathtml(String path) throws ParserConfigurationException { 
		// 实例化 DocumentBuilderFactory 对象 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
		try { 
			//得到 DocumentBuilder 对象 
				DocumentBuilder db = dbf.newDocumentBuilder(); 
				//加载 test.xml,并将其转换为 Document 对象 
				Document doc = db.parse(path); 
				//实例 DOMSource 对象 
				DOMSource source = new DOMSource(doc); 
				//输出结果,并定义结果输出路径 
				StreamResult result = new StreamResult(new File( path+"success.html")); 
				//加载 xsl 文件 
				StreamSource ss = new StreamSource(new File(path+"success_view.xsl")); 
				//实例化 TransformerFactory 对象 
				TransformerFactory tff = TransformerFactory.newInstance(); 
				Transformer tf = tff.newTransformer(ss); 

				//定义写个字符串 
				StringWriter sw = new StringWriter(); 
				StreamResult resulted = new StreamResult(sw); 
				//设定字符编码方式 
				tf.setOutputProperty("encoding", "UTF-8"); 
				//将定义好的 .xsl 格式 转换 
				tf.transform(source, result); 

				tf.transform(source, resulted); 
				String str = null; 
				//将结果写为一个字符串,然后打印在控制台上 
				str = sw.toString(); 
				System.out.println(str); 
		} catch (ParserConfigurationException e) { 
			e.printStackTrace(); 
		} catch (SAXException e) { 
			e.printStackTrace(); 
		} catch (IOException e) { 
			e.printStackTrace(); 
		} catch (TransformerConfigurationException e) { 
			e.printStackTrace(); 
		} catch (TransformerException e) { 
			e.printStackTrace(); 
		} 
	}
}
