package info.pishen.gameoflife;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class DemoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		//g2.draw(new Line2D.Double(0, 100, 50000, 10));
		for(int i = 0; i < 1000; i++){
			for(int j = 0; j < 1000; j++){
				g2.drawRect(i*30, j*30, 30, 30);
				if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)){
					g2.fillRect(i*30, j*30, 30, 30);
				}
			}
			
			//g2.draw(new Rectangle(10, i * 30, 20, 20));
		}
		/*for(int i = 0; i < 200; i++){
			g2.draw(new Line2D.Double(i * 10, 0, i * 10, 5000));
		}*/
	}

}
