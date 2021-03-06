package zirix.zxccmock;


public interface ZXCCWorkState {

	
	// a work state has a STATUS 
	int status();
	
	// a work state is able to evolve to a new state
	void evolve();	
	
	// a work state has attributes
	ZXCCWorkStateDataDAO data();
}



