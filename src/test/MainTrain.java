package test;

import java.util.Random;

public class MainTrain {

	public static void main(String[] args) {
		Random r=new Random();
		int port=r.nextInt(1001)+5000;
		Simulator sim=new Simulator(port); // sim_client on port+1, sim_server on port
		
		int rand=r.nextInt(1000);
		
//		String[] test1={
//				"return "+rand+" * 5 - (8+2)"	
//		};
//		
//		if(MyInterpreter.interpret(test1)!=rand*5-(8+2))
//			System.err.println("failed test1 (-20)");
//
//		String[] test2={
//				"var x",	
//				"x="+rand,	
//				"var y=x+3",	
//				"return y"	
//		};
//		
//		if(MyInterpreter.interpret(test2)!=rand+3)
//			System.err.println("failed test2 (-20)");
//
//		String[] test3={
//				"connect 127.0.0.1 "+port,
//				"var x",
//				"x = bind simX",
//				"var y = bind simX",	
//				"x = "+rand*2,
//				"disconnect",
//				"return y"	
//		};
//		if(MyInterpreter.interpret(test3)!=rand*2)
//			System.err.println("failed test3 (-20)");

		String[] test4={
				"openDataServer "+ (port+1)+" 10",
				"connect 127.0.0.1 "+port,
				"var x = bind simX",
				"var y = bind simY",	
				"var z = bind simZ",	
				"x = "+rand*2,
				"disconnect",
				"return x+y*z"	
		};
		if(MyInterpreter.interpret(test4)!=sim.simX+sim.simY*sim.simZ)
			System.err.println("failed test4 (-20)");
				
//		String[] test5={
//				"var x = 0",
//				"var y = "+rand,
//				"while x < 5 {",
//				"	y = y + 2",
//				"	x = x + 1",
//				"}",
//				"return y"	
//		};
//		
//		if(MyInterpreter.interpret(test5)!=rand+2*5)
//			System.err.println("failed test5 (-20)");
		
		
//		FlySimulator flySim=new FlySimulator(port); // sim_client on port+1, sim_server on port
//		
//		String[] testFly={
//				"openDataServer "+ (port+1)+" 10",
//				"connect 127.0.0.1 "+port,
//				"var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg", 
//				"var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg", 
//				"var rudder = bind /controls/flight/rudder", 
//				"var aileron = bind /controls/flight/aileron", 
//				"var elevator = bind /controls/flight/elevator", 
//				"var alt = bind /instrumentation/altimeter/indicated-altitude-ft", 
//				"var heading = bind /instrumentation/heading-indicator/indicated-heading-deg", 
//				"var h0 = heading", 
//				"while alt < 25 {", 
//					"rudder = (h0 - heading) / 140", 
//					"aileron = (0 - roll) / 70", 
//					"elevator = pitch / 50", 
//					"print alt", 
//					"print rudder", 
//					"print aileron", 
//					"print elevator", 
//					"sleep 2500", 
//				"}", 
//				"disconnect",
//				"return alt"	
//		};
//		if(MyInterpreter.interpret(testFly)< 100)
//			System.err.println("failed testFly (-20)");
//		flySim.close();
		
		
		sim.close();
		System.out.println("done");
	}

}
