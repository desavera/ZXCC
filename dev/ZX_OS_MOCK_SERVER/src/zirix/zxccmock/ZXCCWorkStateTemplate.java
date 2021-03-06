package zirix.zxccmock;

public class ZXCCWorkStateTemplate implements ZXCCWorkState{

	private ZXCCWorkState state_ = null;
	
	
	public ZXCCWorkStateTemplate(ZXCCWorkState state) {
		
		state_ = state;
	}
	
	@Override
	public int status() {
		
		return state_.status();
	}

	@Override
	public void evolve() {
		
		state_.evolve();
	}
	
	@Override
	public ZXCCWorkStateDataDAO data() {
		
		return state_.data();
		
	}	
}
