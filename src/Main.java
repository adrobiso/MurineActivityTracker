import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class Main
{
	public static void main(String[] args)
	{
		TimeLine cageTimeLine, mouseTimeLine, mergedTimeLine;
		HashMap<Integer, Cage> cages;
		String cagesFilename, miceFilename;
		
		// TODO: get filenames w/ full path from UI
		cagesFilename = "maddi9.26.16.csv";
		miceFilename = "beta.csv";

		try
		{
			cages = new CageBuilder().buildCagesFromCSV(cagesFilename, miceFilename);

			// for each mouse in each cage
			for (Cage cage : cages.values())
			{
				for (Mouse mouse : cage.getMice())
				{
					// get TimeLines and merge
					cageTimeLine = cage.getWheelTimeLine();
					mouseTimeLine = mouse.getPresence();
					mergedTimeLine = TimeLine.merge(cageTimeLine, mouseTimeLine);

					// write merged TimeLine to csv
					System.out.println("cage: " + cage.getId() + " mouse: "
							+ mouse.getId() + "\n" + mergedTimeLine);
					
					try{
					    PrintWriter writer = new PrintWriter("Mouse" + mouse.getId() + ".csv", "UTF-8");
					    writer.println("cage," + cage.getId());
					    writer.println("mouse," + mouse.getId());
					    writer.print(mergedTimeLine);
					    writer.close();
					}
					catch (IOException e) {
					   System.out.println(e.getMessage());
					}
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
