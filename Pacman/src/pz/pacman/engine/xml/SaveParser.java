package pz.pacman.engine.xml;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import pz.pacman.constants.Direction;
import pz.pacman.constants.PacmanConstants;
import pz.pacman.engine.element.dynamic.Ghost;
import pz.pacman.engine.element.dynamic.Pacman;
import pz.pacman.engine.element.passive.Dot;
import pz.pacman.engine.entities.SaveData;

public class SaveParser {

	private SaveData data;
	private List<Element> stateList;
	private String path;

	public SaveParser(String path) {
		this.data = new SaveData();
		this.path = path;
		this.stateList = new LinkedList<Element>();
	}

	
	private void preParse() throws JDOMException, IOException {
		File inputFile = new File(this.path);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(inputFile);
		Element classElement = document.getRootElement();

		this.stateList = classElement.getChildren();
	}
	
	private void parseGeneral(Element x) throws NumberFormatException {
		this.data.setLevel(Integer.parseInt(x.getChild("level").getText()));
		this.data.setTotalTime(Integer.parseInt(x.getChild("totalTime").getText()));
		this.data.setTotalPoints(Integer.parseInt(x.getChild("totalPoints").getText()));
		this.data.setTime(Integer.parseInt(x.getChild("time").getText()));
		this.data.setPoints(Integer.parseInt(x.getChild("points").getText()));
		this.data.setLifes(Integer.parseInt(x.getChild("lifes").getText()));
	}
	
	private void parsePacman(Element x) {
		Pacman pacman = new Pacman(0, 0, PacmanConstants.pacmanRadius*2, PacmanConstants.pacmanRadius*2);
		
		pacman.setX(Integer.parseInt(x.getChild("x").getText()));
		pacman.setY(Integer.parseInt(x.getChild("y").getText()));
		pacman.setDirection(Direction.valueOf(x.getChild("direction").getText()));
		pacman.setRequiredDirection(Direction.valueOf(x.getChild("requiredDirection").getText()));
		
		this.data.setPacman(pacman);
	}
	
	private void parseGhost(Element x) {
		Ghost ghost = new Ghost(0, 0, PacmanConstants.ghostRadiusWidth*2, PacmanConstants.ghostRadiusHeight*2);
		
		ghost.setX(Integer.parseInt(x.getChild("x").getText()));
		ghost.setY(Integer.parseInt(x.getChild("y").getText()));
		ghost.setDirection(Direction.valueOf(x.getChild("direction").getText()));
		ghost.setRequiredDirection(Direction.valueOf(x.getChild("requiredDirection").getText()));
		ghost.setColor(
				new Color(
						Integer.valueOf(x.getChild("colorR").getText()), 
						Integer.valueOf(x.getChild("colorG").getText()), 
						Integer.valueOf(x.getChild("colorB").getText())
						)
				);
		
		this.data.addGhost(ghost);
	}
	
	private void parseDot(Element x) {
		Dot dot = new Dot(0, 0, PacmanConstants.dotRadius, PacmanConstants.dotRadius);
		
		dot.setX(Integer.parseInt(x.getChild("x").getText()));
		dot.setY(Integer.parseInt(x.getChild("y").getText()));
		
		this.data.addDot(dot);
	}
	
	public void parse() throws JDOMException, IOException, NumberFormatException {
		this.preParse();

		for (Element x : this.stateList) {
			if (x.getAttributeValue("type").toString().equals("general")) {
				this.parseGeneral(x);
			} else if (x.getAttributeValue("type").toString().equals("pacman")) {
				this.parsePacman(x);
			} else if (x.getAttributeValue("type").toString().equals("ghost")) {
				this.parseGhost(x);
			} else if (x.getAttributeValue("type").toString().equals("dot")) {
				this.parseDot(x);
			}
		}
	}
	
	public SaveData getSaveData() {
		return this.data;
	}
}
