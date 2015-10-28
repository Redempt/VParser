package viperlordx.parser;

import java.util.HashMap;
import java.util.Map;

public class VParser {
	public static VMap parse(String string) {
		VMap map = new VMap();
		string = string.replaceAll("^\\{","");
		string = string.replaceAll("\\}$","");
		Map<Integer, String> entries = advancedSplit(string, ',');
		for (Integer key : entries.keySet()) {
			Map<Integer, String> entry = advancedSplit(entries.get(key), ':');;
			char[] chars = entry.get(1).toCharArray();
			if (chars[0] == '{') {
				VMap temp = new VMap();
				temp = parse(entry.get(1));
				map.put(entry.get(0), temp);
			} else {
				String object = entry.get(1);
				object = object.replaceAll("(?<!\\\\)\\\\", "");
				object = object.replace("\\\\", "\\");
				map.put(entry.get(0), object);
			}
		}
		return map;
	}
	static Map<Integer, String> advancedSplit(String string, char splitchar) {
		HashMap<Integer, String> entries = new HashMap<Integer, String>();
		char[] split = string.toCharArray();
		int position = 0;
		int depth = 0;
		String combine = "";
		for (char c : split) {
			if (c == '{' && !isEscaped(split, position)) {
				depth++;
			}
			if (c == '}' && !isEscaped(split, position)) {
				depth--;
			}
			if ((c == splitchar && !isEscaped(split, position) && depth == 0) || position == split.length - 1) {
				if (position == split.length - 1) {
					combine = combine + split[position];
				}
				entries.put(entries.size(), combine);
				combine = "";
			} else {
				combine = combine + split[position];
			}
			position++;
		}
		return entries;
	}
	static boolean isEscaped(char[] list, int position) {
		boolean escaped = false;
		if (position > 0 && list[position - 1] == '\\') {
			escaped = true;
			if (position > 1 && list[position - 2] == '\\') {
				escaped = false;
				int start = position - 3;
				while (start > 0 && list[start] == '\\') {
					escaped = !escaped;
					start--;
				}
			}
		}
		return escaped;
	}
}