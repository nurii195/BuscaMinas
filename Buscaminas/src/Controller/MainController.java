package Controller;


import java.awt.EventQueue;

import View.MainWindow;

@SuppressWarnings("serial")
public class MainController extends MainWindow {
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try
				{
					MainController frame = new MainController();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainController() {
		super();
	}

}
