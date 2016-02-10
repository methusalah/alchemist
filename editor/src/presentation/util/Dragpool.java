package presentation.util;

public class Dragpool {
	
	private static Object content; 

	private Dragpool(){
		
	}
	
	public static boolean isEmpty(){
		return content == null;
	}

	public static <T> boolean containsType(Class<T> clazz){
		return content != null && content.getClass() == clazz;
	}
	
	public static void setContent(Object object){
		content = object;
	}
	
	
	/**
	 * Get the content and empty the pool.
	 * @param T
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T grabContent(Class<T> clazz){
		T res = (T)content; 
		content = null;
		return res;
	}
}
