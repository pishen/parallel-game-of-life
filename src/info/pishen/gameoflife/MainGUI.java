package info.pishen.gameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame {
	private static Logger log = Logger.getLogger(MainGUI.class.getName());
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollBar hScrollBar, vScrollBar;
	private int contentWidth = 5000, contentHeight = 5000;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					log.info("starting...");
					MainGUI frame = new MainGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttomPanel = new JPanel();
		contentPane.add(buttomPanel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		buttomPanel.add(btnNewButton);
		
		JPanel customScrollPanel = new JPanel();
		customScrollPanel.setBackground(Color.WHITE);
		contentPane.add(customScrollPanel, BorderLayout.CENTER);
		GridBagLayout gbl_customScrollPanel = new GridBagLayout();
		customScrollPanel.setLayout(gbl_customScrollPanel);
		
		JPanel contentPanel = new ContentPanel();
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.weighty = 1.0;
		gbc_contentPanel.weightx = 1.0;
		gbc_contentPanel.insets = new Insets(0, 0, 0, 0);
		gbc_contentPanel.fill = GridBagConstraints.BOTH;
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 0;
		customScrollPanel.add(contentPanel, gbc_contentPanel);
		
		hScrollBar = new JScrollBar();
		hScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		hScrollBar.setMaximum(contentWidth);
		hScrollBar.addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				log.info("value: " + hScrollBar.getValue() + " visible: " + hScrollBar.getVisibleAmount());
				contentPane.repaint();
			}
		});
		hScrollBar.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				hScrollBar.setVisibleAmount(hScrollBar.getWidth());
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		GridBagConstraints gbc_hScrollBar = new GridBagConstraints();
		gbc_hScrollBar.insets = new Insets(0, 0, 0, 0);
		gbc_hScrollBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_hScrollBar.gridx = 0;
		gbc_hScrollBar.gridy = 1;
		customScrollPanel.add(hScrollBar, gbc_hScrollBar);
		
		vScrollBar = new JScrollBar();
		vScrollBar.setMaximum(contentHeight);
		vScrollBar.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				vScrollBar.setVisibleAmount(vScrollBar.getHeight());
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		GridBagConstraints gbc_vScrollBar = new GridBagConstraints();
		gbc_vScrollBar.fill = GridBagConstraints.VERTICAL;
		gbc_vScrollBar.gridx = 1;
		gbc_vScrollBar.gridy = 0;
		customScrollPanel.add(vScrollBar, gbc_vScrollBar);
	}
	
	private class ContentPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			int xStart = 0 - (hScrollBar.getValue() % 30);
			int yStart = 0 - (vScrollBar.getValue() % 30);
			//Graphics2D g2 = (Graphics2D) g;
			for(int x = xStart, i = 0; x < this.getWidth(); x += 30, i++){
				for(int y = yStart, j = 0; y < this.getHeight(); y += 30, j++){
					g.drawRect(x, y, 30, 30);
					if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)){
						g.fillRect(x, y, 30, 30);
					}
				}
			}
		}
		
		
	}

}
