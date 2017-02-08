import java.time.LocalDateTime;

public class Interval
{
	private LocalDateTime start;
	private LocalDateTime end;
	private int rotationCount;

	public Interval(LocalDateTime start, LocalDateTime end)
	{
		this.start = start;
		this.end = end;
		this.rotationCount = 0;
	}

	public Interval(LocalDateTime start, LocalDateTime end, int rotationCount)
	{
		assert start.isBefore(end);
		this.start = start;
		this.end = end;
		this.rotationCount = rotationCount;
	}

	public LocalDateTime getStart()
	{
		return start;
	}

	public LocalDateTime getEnd()
	{
		return end;
	}

	public int getCount()
	{
		return rotationCount;
	}

	public String toString()
	{
		return start.toString() + "," + end.toString() + "," + rotationCount;
	}
	
	public boolean endsBeforeStart(Interval other)
	{
		return !this.end.isAfter(other.start);
	}
	
	public boolean startsAfterEnd(Interval other)
	{
		return !this.start.isBefore(other.end);
	}
	
	public boolean endsBeforeEnd(Interval other)
	{
		return !this.end.isAfter(other.end);
	}
	
	public boolean startsBeforeStart(Interval other)
	{
		return !this.start.isAfter(other.start);
	}

	public static Interval overlap(Interval int1, Interval int2)
	{
		LocalDateTime newStart;
		LocalDateTime newEnd;
		int newRotationCount;

		// no overlap
		if (int1.endsBeforeStart(int2) || int1.startsAfterEnd(int2))
		{
			return null;
		}

		// latest start
		if (int1.startsBeforeStart(int2))
		{
			newStart = int2.start;
		}
		else
		{
			newStart = int1.start;
		}
		
		// earliest end
		if (int1.endsBeforeEnd(int2))
		{
			newEnd = int1.end;
		}
		else
		{
			newEnd = int2.end;
		}
		
		// TODO: compensate for partial matches
		if (int1.rotationCount > 0)
		{
			newRotationCount = int1.rotationCount;
		}
		else
		{
			newRotationCount = int2.rotationCount;
		}

		return new Interval(newStart, newEnd, newRotationCount);
	}
}
