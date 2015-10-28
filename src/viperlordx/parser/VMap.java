package viperlordx.parser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class VMap implements Iterable<Object> {
	HashMap<String, Object> map = new HashMap<String, Object>();
	public VMap() {
	}
	public void put(String string, Object object) {
		map.put(string, object);
	}
	public void put(String string, VMap vmap) {
		map.put(string, vmap);
	}
	public Object get(String key) {
		Object object = map.get(key);
		return object;
	}
	@Override
	public Iterator<Object> iterator() {
		return new SIterator(map);
	}
	@Override
	public String toString() {
		String combine = "{";
		for (String obj : map.keySet()) {
			Object object = map.get(obj);
			if (map.get(obj) instanceof String) {
				object = ((String) object).replace("\\","\\\\");
				object = ((String) object).replace("}", "\\}");
				object = ((String) object).replace("{", "\\{");
				object = ((String) object).replace(",", "\\,");
				object = ((String) object).replace(":", "\\:");
			}
			combine = combine + obj + ":" + object.toString() + ",";
		}
		combine = combine.replaceAll(",$", "");
		combine = combine + "}";
		return combine;
	}
	public void remove(String key) {
		map.remove(key);
	}
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}
	public boolean containsValue(String value) {
		return map.containsValue(value);
	}
	public Set<String> keySet() {
		return map.keySet();
	}
}
class SIterator implements Iterator<Object> {
	HashMap<String, Object> map;
	Object[] keys;
	int position;
	public SIterator(HashMap<String, Object> map) {
		this.map = map;
		keys = map.keySet().toArray();
		position = 0;
	}
	@Override
	public boolean hasNext() {
		boolean bool = false;
		bool = position >= keys.length;
		return !bool;
	}
	@Override
	public Object next() {
		position++;
		return map.get(keys[position - 1]);
	}
	
}