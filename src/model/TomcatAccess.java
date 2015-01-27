package model;

public class TomcatAccess {
	
	private String message;
	private String host;
	private String path;
	private String timestamp;
	private String version;
	private String logFileType;
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}		
	public String getLogFileType() {
		return logFileType;
	}
	public void setLogFileType(String logFileType) {
		this.logFileType = logFileType;
	}
	
	public String toString(){
		return "TomcatAccess---\n"+this.host+",\n"+this.message
				+",\n"+this.path+",\n"+this.timestamp+",\n"+this.version
				+",\n"+this.logFileType+"---";
	}
	
	
	

}
