import java.util.Collection;
import java.util.HashMap;

public class Cage
{
   private int id;
	private TimeLine wheelTimeLine;
	private HashMap<Integer, Mouse> mice;
	
	public Cage(int id)
	{
	   this.id = id;
		wheelTimeLine = new TimeLine();
		mice = new HashMap<Integer, Mouse>();
	}
	
	public int getId()
	{
	   return id;
	}
	
	public void addMouse(int id)
	{
		mice.put(id, new Mouse(id));
	}
	
	public Mouse getMouse(int id)
	{
		return mice.get(id);
	}
	
	public Collection<Mouse> getMice()
	{
		return mice.values();
	}
	
	public TimeLine getWheelTimeLine()
	{
		return wheelTimeLine;
	}
	
	public String toString()
	{
		return "id: " + id + "\n" + wheelTimeLine + "\n" + mice;
	}
}
