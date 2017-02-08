
public class Mouse
{
   private int id;
	private TimeLine presenceTimeLine;
	
	public Mouse(int id)
	{
	   this.id = id;
		presenceTimeLine = new TimeLine();
	}
	
	public int getId()
	{
	   return id;
	}
	
	public TimeLine getPresence()
	{
		return presenceTimeLine;
	}
}
