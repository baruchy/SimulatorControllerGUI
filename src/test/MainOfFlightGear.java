package test;

public class MainOfFlightGear {
	public static void main(String[] args) {
//		MyInterpreter.interpret(test1());
//		MyInterpreter.interpret(test2());
		MyInterpreter.interpret(test3TomScript());
		System.exit(0);
	}
   
   private static String[] test1() {
	   return new String[]{
			"openDataServer 5400 10", 
	    	"connect 127.0.0.1 5402",
			"var breaks = bind /controls/flight/speedbrake" ,
			"var throttle = bind /controls/engines/current-engine/throttle", 
			"var heading = bind /instrumentation/heading-indicator/indicated-heading-deg", 
			"var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt", 
			"var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg", 
			"var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg", 
			"var rudder = bind /controls/flight/rudder", 
			"var aileron = bind /controls/flight/aileron", 
			"var elevator = bind /controls/flight/elevator", 
			"var alt = bind /instrumentation/altimeter/indicated-altitude-ft", 
			"sleep 60000", 
			"breaks = 0", 
			"throttle = 1", 
			"var h0 = heading", 
			"while alt < 100 {", 
				"print h0 heading rudder roll aileron pitch elevator",
				"rudder = (h0 - heading) / 140", 
				"aileron = (0 - roll) / 70", 
				"elevator = pitch / 50", 
				"print alt", 
				"sleep 2500", 
			"}", 
			"print done"   
	   };
   }
      
   private static String[] test2() {
	   return new String[]{
	    		  "openDataServer 6401 10", 
	    		  "connect 127.0.0.1 5402", 
	    		  "var breaks = bind \"/controls/flight/speedbrake\"", 
	    		  "var throttle = bind \"/controls/engines/current-engine/throttle\"", 
	    		  "var heading = bind \"/instrumentation/heading-indicator/offset-deg\"", 
	    		  "var airspeed = bind \"/instrumentation/airspeed-indicator/indicated-speed-kt\"", 
	    		  "var roll = bind \"/instrumentation/attitude-indicator/indicated-roll-deg\"", 
	    		  "var pitch = bind \"/instrumentation/attitude-indicator/internal-pitch-deg\"", 
	    		  "var rudder = bind \"/controls/flight/rudder\"", 
	    		  "var aileron = bind \"/controls/flight/aileron\"", 
	    		  "var elevator = bind \"/controls/flight/elevator\"", 
	    		  "var alt = bind \"/instrumentation/altimeter/indicated-altitude-ft\"", 
	    		  "sleep 80000", 
	    		  "breaks = 0", 
	    		  "throttle = 1", 
	    		  "var desiredPitch = 10", 
	    		  "var h0 = heading", 
	    		  "sleep 40000", 
	    		  "while alt < 1000 {", 
	    		  		"rudder = (h0 - heading)/20", 
	    		  		"aileron = (0 - roll) / 70 - 0.1", 
	    		  		"elevator = pitch / 50 + 0.1", 
	    		  		"print alt", 
	    		  		"sleep 250", 
	    		  "}", 
	    		  "disconnect", 
	    		  "print done"
	    		  };
   }
   
   private static String[] test3TomScript() {
	   return new String[] {
				"openDataServer 5400 10", 
		    	"connect 127.0.0.1 5402",
	    		  "sleep 40000", 
			   "var breaks = bind /controls/flight/speedbrake",
					   "var throttle = bind /controls/engines/current-engine/throttle",
					   "var heading = bind /instrumentation/heading-indicator/indicated-heading-deg",
					   "var airspeed = bind /instrumentation/airspeed-indicator/indicated-speed-kt",
					   "var roll = bind /instrumentation/attitude-indicator/indicated-roll-deg",
					   "var pitch = bind /instrumentation/attitude-indicator/internal-pitch-deg",
					   "var rudder = bind /controls/flight/rudder",
					   "var aileron = bind /controls/flight/aileron",
					   "var elevator = bind /controls/flight/elevator",
					   "var alt = bind /instrumentation/altimeter/indicated-altitude-ft",
			    		  "sleep 40000", 
					   "breaks = 0",
					   "throttle = 1",
					   "var desiredPitch = 10",
					   "var h0 = heading",
					   "while airspeed < 50 {",
					   		"rudder = ((h0 - heading)/20) + 0.1",
					   		"aileron = ((0-roll) / 70) + 0.1",
					   		"elevator = 0.03",
					   		"print airspeed",
					   "}",
					   "while alt < -200 {",
					   		"rudder = ((h0 - heading)/20) + 0.1",
					   		"aileron = ((0-roll) / 70) + 0.1",
					   		"elevator = (pitch - desiredPitch) / 50",
					   "print alt",
					   "}",
					   "aileron = 0",
					   "elevator = 0",
					   "rudder = 0",
					   "throttle = 1",
	   };
   }
}
