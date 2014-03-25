package Elements;

import java.util.HashMap;
import java.util.List;

import jm.JMC;

public class MapNotevsSound {
	public HashMap NvsS = new HashMap();
	
	public MapNotevsSound(){
		NvsS.put("c4",JMC.C4);
		NvsS.put("cs4",JMC.CS4);
		NvsS.put("d4",JMC.D4);
		NvsS.put("ds4",JMC.DS4);
		NvsS.put("e4",JMC.E4);
		NvsS.put("f4",JMC.F4);
	}
}

