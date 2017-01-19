package pz.pacman.engine.xml;

import java.io.File;

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

import pz.pacman.engine.element.dynamic.Ghost;
import pz.pacman.engine.element.passive.Dot;
import pz.pacman.engine.entities.SaveData;

public class SaveCreator {

	private String path;
	private SaveData data;

	private Document doc;
	private Element rootElement;

	public SaveCreator(String path, SaveData data) {
		this.path = path;
		this.data = data;
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
	
	private void createGeneralInfo() {
		Element general = doc.createElement("state");
		rootElement.appendChild(general);
		
		Attr attr = doc.createAttribute("type");
		attr.setValue("general");
		general.setAttributeNode(attr);
		
		Element level = doc.createElement("level");
		level.appendChild(doc.createTextNode(String.valueOf(this.data.getLevel())));
		general.appendChild(level);
		
		Element dots = doc.createElement("dots");
		dots.appendChild(doc.createTextNode(String.valueOf(this.data.getDots().size())));
		general.appendChild(dots);
		
		Element ghosts = doc.createElement("ghosts");
		ghosts.appendChild(doc.createTextNode(String.valueOf(this.data.getGhosts().size())));
		general.appendChild(ghosts);
		
		Element time = doc.createElement("time");
		time.appendChild(doc.createTextNode(String.valueOf(this.data.getTime())));
		general.appendChild(time);
		
		Element points = doc.createElement("points");
		points.appendChild(doc.createTextNode(String.valueOf(this.data.getPoints())));
		general.appendChild(points);
		
		Element totalTime = doc.createElement("totalTime");
		totalTime.appendChild(doc.createTextNode(String.valueOf(this.data.getTotalTime())));
		general.appendChild(totalTime);
		
		Element totalPoints = doc.createElement("totalPoints");
		totalPoints.appendChild(doc.createTextNode(String.valueOf(this.data.getTotalPoints())));
		general.appendChild(totalPoints);
		
		Element lifes = doc.createElement("lifes");
		lifes.appendChild(doc.createTextNode(String.valueOf(this.data.getLifes())));
		general.appendChild(lifes);
	}
	
	private void createPacman() {
		Element general = doc.createElement("state");
		rootElement.appendChild(general);
		
		Attr attr = doc.createAttribute("type");
		attr.setValue("pacman");
		general.setAttributeNode(attr);
		
		Element x = doc.createElement("x");
		x.appendChild(doc.createTextNode(String.valueOf(this.data.getPacman().getX())));
		general.appendChild(x);
		
		Element y = doc.createElement("y");
		y.appendChild(doc.createTextNode(String.valueOf(this.data.getPacman().getY())));
		general.appendChild(y);
		
		Element direction = doc.createElement("direction");
		direction.appendChild(doc.createTextNode(String.valueOf(this.data.getPacman().getDirection())));
		general.appendChild(direction);
				
		Element requiredDirection = doc.createElement("requiredDirection");
		requiredDirection.appendChild(doc.createTextNode(String.valueOf(this.data.getPacman().getRequiredDirection())));
		general.appendChild(requiredDirection);
	}
	
	private void createGhost(Ghost g) {
		Element general = doc.createElement("state");
		rootElement.appendChild(general);
		
		Attr attr = doc.createAttribute("type");
		attr.setValue("ghost");
		general.setAttributeNode(attr);
		
		Element x = doc.createElement("x");
		x.appendChild(doc.createTextNode(String.valueOf(g.getX())));
		general.appendChild(x);
		
		Element y = doc.createElement("y");
		y.appendChild(doc.createTextNode(String.valueOf(g.getY())));
		general.appendChild(y);
		
		Element direction = doc.createElement("direction");
		direction.appendChild(doc.createTextNode(String.valueOf(g.getDirection())));
		general.appendChild(direction);
				
		Element requiredDirection = doc.createElement("requiredDirection");
		requiredDirection.appendChild(doc.createTextNode(String.valueOf(g.getRequiredDirection())));
		general.appendChild(requiredDirection);
		
		Element colorR = doc.createElement("colorR");
		colorR.appendChild(doc.createTextNode(String.valueOf(g.getColor().getRed())));
		general.appendChild(colorR);
		
		Element colorG = doc.createElement("colorG");
		colorG.appendChild(doc.createTextNode(String.valueOf(g.getColor().getGreen())));
		general.appendChild(colorG);
		
		Element colorB = doc.createElement("colorB");
		colorB.appendChild(doc.createTextNode(String.valueOf(g.getColor().getBlue())));
		general.appendChild(colorB);
	}
	
	private void createGhosts() {
		for (Ghost x : this.data.getGhosts()) {
			createGhost(x);
		}
	}
	
	private void createDot(Dot d) {
		Element general = doc.createElement("state");
		rootElement.appendChild(general);
		
		Attr attr = doc.createAttribute("type");
		attr.setValue("dot");
		general.setAttributeNode(attr);
		
		Element x = doc.createElement("x");
		x.appendChild(doc.createTextNode(String.valueOf(d.getX())));
		general.appendChild(x);
		
		Element y = doc.createElement("y");
		y.appendChild(doc.createTextNode(String.valueOf(d.getY())));
		general.appendChild(y);
	}
	
	private void createDots() {
		for (Dot x : this.data.getDots()) {
			createDot(x);
		}
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

		this.createGeneralInfo();
		this.createPacman();
		this.createGhosts();
		this.createDots();
		
		this.postBuild();
	}
}
