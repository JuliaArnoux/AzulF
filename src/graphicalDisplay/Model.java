package graphicalDisplay;
import game.*;

public class Model {
	
	private Plateau plateau;
	
	public Model(Plateau p) {
		this.plateau = p;
	}

	public Plateau getPlateau() {
		return plateau;
	}

}
