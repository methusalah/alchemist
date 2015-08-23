package util.entity;

import java.util.ArrayList;
import java.util.List;


public class CompMask extends ArrayList<Class<? extends Comp>> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompMask(Class<? extends Comp>... compClasses) {
		for(Class<? extends Comp> c : compClasses)
			add(c);
	}

	public CompMask(Iterable<Class<? extends Comp>> compClassList) {
		for(Class<? extends Comp> c : compClassList)
			add(c);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(CompMask.class.getSimpleName() + " (");
		for(Class<? extends Comp> compClass : this){
			sb.append(compClass.getSimpleName());
			if(compClass != get(size()-1))
				sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
	
}
