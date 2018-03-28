package learn.nlp.nlplibrary.search;

public class SearchControl {
	public enum SearchAction {
		RUNNING, PAUSE, STOP
	}
	public  SearchControl(){}
	public  SearchControl(SearchAction action){
		this.status = action;
	}
	public void setSearchAction(SearchAction searchAction) {
		this.status = searchAction;
	}
	
	public SearchAction getSearchAction() {
		return this.status;
	}
	
	private SearchAction status;
}
