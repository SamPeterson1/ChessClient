package Events;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class FileIn {
	
	private HashMap<Integer, Request> lines;
	
	
	public FileIn(String fileName) {
		String file = "bin/cfg/" + fileName;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.lines = new HashMap<Integer, Request>();
		String line;
		if(reader != null) {
			try {
				while((line = reader.readLine()) != null) {
					Request request = Request.parseString(line);
					int id = request.getInt("id");
					this.lines.put(Integer.valueOf(id), request);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Request getLineByID(int ID) {
		return this.lines.get(Integer.valueOf(ID));
	}
	
	public Collection<Request> lines() {
		return this.lines.values();
	}

}
