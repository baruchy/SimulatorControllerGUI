package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Random;

public class FlySimulator {
	double roll,pitch,rudder,aileron,elevator,alt,heading;
	private int port;
	private volatile boolean stop;
	
	public FlySimulator(int port) {
		this.port=port;
		Random r=new Random();
		pitch=r.nextInt(1000);
		rudder=r.nextInt(1000);
		new Thread(()->runServer()).start();
		new Thread(()->runClient()).start();
	}
	
	private void runClient(){
		while(!stop){
			try {
				Socket interpreter=new Socket("127.0.0.1", port+1);
				PrintWriter out=new PrintWriter(interpreter.getOutputStream());
				while(!stop){
					out.println(roll+","+pitch+","+rudder+","+aileron+","+elevator+","+alt+","+heading);
					out.flush();
					alt+=0.1D;
					try {Thread.sleep(100);} catch (InterruptedException e1) {}
				}
				out.close();
				interpreter.close();
			} catch (IOException e) {
				try {Thread.sleep(1000);} catch (InterruptedException e1) {}
			}
		}
	}
	
	private void runServer(){
		try {
			ServerSocket server=new ServerSocket(port);
			server.setSoTimeout(1000);
			while(!stop){
				try{
					Socket client=server.accept();
					BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));
					String line=null;
					while(!(line=in.readLine()).equals("bye")){
						try{
							if(line.startsWith("set /instrumentation/attitude-indicator/indicated-roll-deg"))
								roll=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /instrumentation/attitude-indicator/internal-pitch-deg"))
								pitch=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /controls/flight/rudder"))
								rudder=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /controls/flight/aileron"))
								aileron=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /controls/flight/elevator"))
								elevator=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /instrumentation/altimeter/indicated-altitude-ft"))
								alt=Double.parseDouble(line.split(" ")[2]);
							if(line.startsWith("set /instrumentation/heading-indicator/indicated-heading-deg"))
								heading=Double.parseDouble(line.split(" ")[2]);
						}catch(NumberFormatException e){}
					}
					in.close();
					client.close();
				}catch(SocketTimeoutException e){}
			}
			server.close();
		} catch (IOException e) {}
	}

	public void close() {
		stop=true;
	}
}
