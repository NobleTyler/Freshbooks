package bean;
/*
 * This category bean is used for the categories
 * you can call it in the dao whenever you wanna search by categories i guess
 */
public class CategoryBean {

	private String category; 
	private int bid;

	public CategoryBean(String category, int bid) {
		super();
		this.category = category;
		this.bid = bid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}
	
}
