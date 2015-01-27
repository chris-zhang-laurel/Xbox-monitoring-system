package parser;

import java.util.HashMap;

public interface ILogParser {
	public String getIP(String _log, int _typeid);
	
	public HashMap<String, Object> getLogObj(String _log, String _prefix);
}