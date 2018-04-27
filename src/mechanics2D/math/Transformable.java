package mechanics2D.math;

import mechanics2D.shapes.Orientable;
import tensor.DVector2;

public interface Transformable {
	
	Orientable transform(DVector2 direction, double orientation);
	
}
