import java.util.HashMap;
import javax.lang.model.type.NullType;

public class Set {
	private HashMap<Integer, NullType> map = new HashMap<Integer, NullType>();

	public boolean contains(int x) {return map.containsKey(x);}
	public void insert(int x) {map.put(x, null);}
	public void remove(int x) {map.remove(x);}
	public int size() {return map.size();}
}
