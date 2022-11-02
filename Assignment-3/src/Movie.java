public class Movie {
	private String id;
	private String title;
	private float rating;

	public Movie(String[] fields) {
		this.id = fields[0];
		this.title = fields[1];
		this.rating = Float.parseFloat(fields[2]);
	}

	public String getId() {return id;}
	public String getTitle() {return title;}
	public float getRating() {return rating;}
}
