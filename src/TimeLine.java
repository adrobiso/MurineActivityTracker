import java.util.Iterator;
import java.util.LinkedList;

public class TimeLine
{
	private LinkedList<Interval> intervals;

	public TimeLine()
	{
		intervals = new LinkedList<Interval>();
	}

	// for now just append (aka assume intervals are being added in order)
	// TODO: insert based on start/end
	public void addInterval(Interval interval)
	{
		intervals.add(interval);
	}

	public static TimeLine merge(TimeLine timeLine1, TimeLine timeLine2)
	{
		Iterator<Interval> iterator1, iterator2;
		TimeLine newTimeLine = new TimeLine();
		Interval int1, int2, newInt;

		iterator1 = timeLine1.intervals.iterator();
		iterator2 = timeLine2.intervals.iterator();

		int1 = iterator1.next();
		int2 = iterator2.next();

		// TODO: very dirty loop, clean up if possible
		do
		{
			newInt = Interval.overlap(int1, int2);

			// make sure there was overlap before adding
			if (newInt != null)
			{
				newTimeLine.addInterval(newInt);
			}

			// if int1 ended first, take next interval from timeline 1
			if (int1.endsBeforeEnd(int2) && iterator1.hasNext())
			{
				int1 = iterator1.next();
			}
			// if int2 ended first, take next interval from timeline 2
			else if (int2.endsBeforeEnd(int1) && iterator2.hasNext())
			{
				int2 = iterator2.next();
			}
			else
			{
				break;
			}
		} 
		while (iterator1.hasNext() || iterator2.hasNext());

		return newTimeLine;
	}

	public String toString()
	{
		String retString = "";

		for (Interval interval : intervals)
		{
			retString += interval.toString() + "\n";
		}

		return retString;
	}
}
