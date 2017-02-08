import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Scanner;

public class CageBuilder
{
	private static String csvDateTimeFormat = "MM/dd/yy HH:mm:ss";

	private File csvFile;
	private Scanner scanner;
	private DateTimeFormatter csvDateTimeFormatter;

	public CageBuilder()
	{
		csvDateTimeFormatter = DateTimeFormatter.ofPattern(csvDateTimeFormat);
	}

	public HashMap<Integer, Cage> buildCagesFromCSV(String cageFile,
			String miceFile) throws FileNotFoundException
	{
		HashMap<Integer, Cage> cages;

		cages = buildCages(cageFile);
		cages = addMiceToCages(miceFile, cages);

		return cages;
	}

	private HashMap<Integer, Cage> buildCages(String filename)
			throws FileNotFoundException
	{
		HashMap<Integer, Cage> cages = new HashMap<Integer, Cage>();
		String line, rowElements[];
		int cageId, rotationCount;
		LocalDateTime startTime, endTime;
		Cage cage;
		Interval interval;

		csvFile = new File(filename);
		scanner = new Scanner(csvFile);

		// scanner seek to start
		while (scanner.hasNextLine() && !scanner.nextLine().split(",")[0].equals("Int"))
		{
			;
		}

		// parse
		while (scanner.hasNextLine())
		{
			line = scanner.nextLine();
			rowElements = line.split(",");

			cageId = Integer.parseInt(rowElements[1]);
			startTime = LocalDateTime.parse(rowElements[2], csvDateTimeFormatter);
			endTime = startTime.plusSeconds(1);
			rotationCount = Integer.parseInt(rowElements[3]);

			cage = cages.get(cageId);

			if (cage == null)
			{
				cage = new Cage(cageId);
				cages.put(cageId, cage);
			}

			interval = new Interval(startTime, endTime, rotationCount);
			cage.getWheelTimeLine().addInterval(interval);
		}

		return cages;
	}

	private HashMap<Integer, Cage> addMiceToCages(String filename,
			HashMap<Integer, Cage> cages) throws FileNotFoundException
	{
		String line, rowElements[];
		int cageId, mouseId;
		LocalDateTime startTime, endTime;
		Cage cage;
		Mouse mouse;
		Interval interval;
		
		csvFile = new File(filename);
		scanner = new Scanner(csvFile);

		// parse
		while (scanner.hasNextLine())
		{
			line = scanner.nextLine();
			rowElements = line.split(",");

			cageId = Integer.parseInt(rowElements[0]);
			mouseId = Integer.parseInt(rowElements[1], 16);
			startTime = LocalDateTime.parse(rowElements[2]);
			endTime = startTime.plusSeconds(1);
			
			cage = cages.get(cageId);
			if (cage == null)
			{
				cage = new Cage(cageId);
				cages.put(cageId, cage);
			}
			
			mouse = cage.getMouse(mouseId);
			if (mouse == null)
			{
				mouse = new Mouse(mouseId);
				cage.addMouse(mouseId);
			}
			
			interval = new Interval(startTime, endTime);
			mouse.getPresence().addInterval(interval);
		}
		
		return cages;
	}
}
