package mechanics2D.shapes;

public interface Orientable extends Locatable {
	
	double angle();
	
	void rotate(double dAngle);
	
}
