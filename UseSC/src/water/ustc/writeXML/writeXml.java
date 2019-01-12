package water.ustc.writeXML;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class writeXml {
	public void writeToLog(String name ,String path,String startTime,String result,String endTime){
		System.out.println(name);
		System.out.println(path);
		System.out.println(startTime);
		System.out.println(result);
		System.out.println(endTime);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        File file = new File(path);
		try {

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            Element actionElement = doc.createElement("action");
            Element nameElement = doc.createElement("name");
            Element startElement = doc.createElement("start_time");
            Element endElement = doc.createElement("end_time");
            Element resultElement = doc.createElement("result");
            nameElement.setTextContent(name);
            startElement.setTextContent(startTime);
            endElement.setTextContent(endTime);
            resultElement.setTextContent(result);
            actionElement.appendChild(nameElement);
            actionElement.appendChild(startElement);
            actionElement.appendChild(endElement);
            actionElement.appendChild(resultElement);
            doc.getElementsByTagName("log").item(0).appendChild(actionElement);
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(doc), new StreamResult(file));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
	}
}
