package pz.pacman.engine.xml;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import pz.pacman.engine.entities.TopScorerData;

public class TopScoresParser {

	private String filename;
	private List<Element> playersList;
	private LinkedList<TopScorerData> topScorers;
	
	public TopScoresParser(String filename) {
		this.filename = filename;
		this.topScorers = new LinkedList<TopScorerData>();
	}

	private void preParse() throws JDOMException, IOException {
		File inputFile = new File(this.filename);
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(inputFile);
		Element classElement = document.getRootElement();

		this.playersList = classElement.getChildren();
	}

	private TopScorerData parseSingleElement(Element elem) throws NumberFormatException {
		TopScorerData data = null;
		try {
			data = new TopScorerData(elem.getChild("name").getText(), Integer.parseInt(elem.getChild("points").getText()), Integer.parseInt(elem.getChild("time").getText()));
		} catch (NumberFormatException e) {
			throw e;
		}
		
		return data;
	}
	
	public void parse() throws JDOMException, IOException, NumberFormatException {
		this.preParse();

		for (Element x : this.playersList) {
			topScorers.add(parseSingleElement(x));
		}
	}
	
	public LinkedList<TopScorerData> getTopScorers() {
		return this.topScorers;
	}
}
