package graphicalDisplay;

import java.awt.EventQueue;
import game.*;

public class Launcher {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			View v1 = new View();
			EventQueue.invokeLater(()->{
				v1.setVisible(true);
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}