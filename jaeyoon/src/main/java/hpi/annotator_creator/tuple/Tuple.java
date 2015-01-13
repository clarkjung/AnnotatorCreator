package hpi.annotator_creator.tuple;

public class Tuple {

	private int group_id;
	private String event_text;
	private String group_text;
	
	public Tuple(){
	}

	public int getGroup_id() {
		return group_id;
	}

	public void setGroup_id(int group_id) {
		this.group_id = group_id;
	}

	public String getGroup_text() {
		return group_text;
	}

	public void setGroup_text(String group_text) {
		this.group_text = group_text;
	}

	public String getEvent_text() {
		return event_text;
	}

	public void setEvent_text(String event_text) {
		this.event_text = event_text;
	}	
	
}
