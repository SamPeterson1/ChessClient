package Net;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket clientSocket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private InputStream inputStream;
	private OutputStream outputStream;
	private String ID;

	public void connect(String hostname, int hostport) {
		try {
			this.clientSocket = new Socket(hostname, hostport);
			this.inputStream = this.clientSocket.getInputStream();
			this.outputStream = this.clientSocket.getOutputStream();
			this.bufferedReader = new BufferedReader(new InputStreamReader(this.inputStream));
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.outputStream));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setID(String ID) {
		this.ID = ID;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public void disconnect() {
		if (this.clientSocket != null) {
			try {
				this.clientSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void write(String data) {
		try {
			this.bufferedWriter.write(data);
			this.bufferedWriter.newLine();
			this.bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String nonBlockRead() {
		try {
			if(this.inputStream.available() > 0) {
				return this.bufferedReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "nothing";
	}
	
	public String read() {
		try {
			return this.bufferedReader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}