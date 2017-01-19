package pz.pacman.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import pz.pacman.constants.PacmanConstants;
import pz.pacman.mvc.model.MainMenuModel;

public class MainMenuComp extends JComponent {
	private static final long serialVersionUID = -8678115443009583291L;

	private MainMenuModel model;
	
	public MainMenuComp() {

	}
	
	public Dimension getPreferredSize() {
		return new Dimension(PacmanConstants.windowWidth, PacmanConstants.windowHeight);
	}

	private void setAntialiasing(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
	}
	
	private void drawBackground(Graphics graphics) {
		Image background = new ImageIcon(this.getClass().getResource("/img/background.jpg")).getImage();
		graphics.drawImage(background, 0, 0, null);
	}
	
	private TextLayout createTextLayout(String textToDraw, FontRenderContext context) {
		return new TextLayout(textToDraw, PacmanConstants.defaultFont, context);
	}
	
	private void drawSingleMenuEntry(final Graphics2D graphics2D, String textToDraw, int x, int y, FontRenderContext context, BasicStroke strokeStyle, Color colorInside, Color colorOutside) {
		TextLayout textLayout = createTextLayout(textToDraw, context);
		double textWidth = PacmanConstants.defaultFont.getStringBounds(textToDraw, context).getWidth();
		
		graphics2D.setStroke(strokeStyle);
		
		Shape shape = textLayout.getOutline(null);
		graphics2D.translate((x - textWidth)/2, y + PacmanConstants.spaceBetweenTexts/2);
		
		graphics2D.setColor(colorInside);
		graphics2D.fill(shape);
		
		graphics2D.setColor(colorOutside);
		graphics2D.draw(shape);
		
		graphics2D.translate(-(x - textWidth)/2, - (y + PacmanConstants.spaceBetweenTexts/2));	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		int width = getWidth();
		int height = (getHeight() - PacmanConstants.spaceBetweenTexts * PacmanConstants.mainMenuCnt) / 2;

		FontRenderContext context = g2d.getFontRenderContext();
		
		setAntialiasing(g2d);
		drawBackground(g2d);
		
		int i = 0;
		for (String x : PacmanConstants.mainMenuTexts) {
			if (this.model.getSelectedMenuPosition() == i) {
				drawSingleMenuEntry(g2d, x, width, height, context, PacmanConstants.activeTextStroke, PacmanConstants.activeField, PacmanConstants.activeField);
			} else {
				drawSingleMenuEntry(g2d, x, width, height, context, PacmanConstants.inactiveTextStroke, PacmanConstants.inactiveFieldInside, PacmanConstants.inactiveFieldOutside);
			}
		
			height += PacmanConstants.spaceBetweenTexts;
			i++;
		}
	}
	
	public void setModel(MainMenuModel model) {
		if (this.model == null) {
			this.model = model;
		}
	}
}