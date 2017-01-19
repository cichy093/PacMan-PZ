package pz.pacman.engine.xml;

import java.io.File;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pz.pacman.engine.entities.TopScorerData;

public class TopScoresCreator {

	private String path;
	private LinkedList<TopScorerData> data;
	private Document doc;
	private Element rootElement;

	public TopScoresCreator(String path, LinkedList<TopScorerData> dataSource) {
		this.path = path;
		this.data = dataSource;
	}

	private void preBuild() throws ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.doc = dBuilder.newDocument();
	}

	private void createRootElement() {
		this.rootElement = doc.createElement("class");
		doc.appendChild(rootElement);
	}

	private void createElement(int index, TopScorerData x) {
		Element player = doc.createElement("player");
		rootElement.appendChild(player);

		Attr attr = doc.createAttribute("position");
		attr.setValue(String.valueOf(index));
		player.setAttributeNode(attr);

		// name element
		Element name = doc.createElement("name");
		name.appendChild(doc.createTextNode(x.getNickname()));
		player.appendChild(name);
		
		// points element
		Element points = doc.createElement("points");
		points.appendChild(doc.createTextNode(String.valueOf(x.getPoints())));
		player.appendChild(points);
		
		// time element
		Element time = doc.createElement("time");
		time.appendChild(doc.createTextNode(String.valueOf(x.getTime())));
		player.appendChild(time);
	}
	
	private void postBuild() throws TransformerException {
		// write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(this.path));
        transformer.transform(source, result);
	}

	public void build() throws ParserConfigurationException, TransformerException {
		this.preBuild();
		this.createRootElement();

		for (int i = 0; i < this.data.size(); i++) {
			this.createElement(i + 1, this.data.get(i));
		}
		
		this.postBuild();
	}
}
