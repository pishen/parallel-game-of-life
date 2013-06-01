package info.pishen.gameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class MainGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("New button");
		panel.add(btnNewButton);
		
		DemoPanel demoPanel = new DemoPanel();
		//demoPanel.setPreferredSize(new Dimension(10000, 10000));
		
		JPanel gridPanel = new JPanel();
		
		gridPanel.setLayout(new GridLayout(0, 190, 0, 0));
		gridPanel.setPreferredSize(new Dimension(2000, 2000));
		
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
		for(int i = 0; i < 190; i++){
			for(int j = 0; j < 190; j++){
				JPanel cellPanel = new JPanel();
				if((i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0)){
					cellPanel.setBackground(Color.DARK_GRAY);
				}
				cellPanel.setBorder(border);
				gridPanel.add(cellPanel);
			}
		}
		
		JScrollPane scrollPane = new JScrollPane(gridPanel);
		//scrollPane.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

}
