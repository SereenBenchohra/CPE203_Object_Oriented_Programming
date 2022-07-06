import static edu.calpoly.testy.Assert.assertEquals;
import static edu.calpoly.testy.Assert.assertTrue;
import static edu.calpoly.testy.Assert.assertNotNull;
import static edu.calpoly.testy.Assert.fail;

import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import edu.calpoly.testy.Testy;

public class TestCases
{
    private static final Song[] songs = new Song[] {
            new Song("Lene Lovich", "New Toy", 1981),
            new Song("Kate Bush", "The Wedding List", 1980),
            new Song("Lene Lovich", "Lucky Number", 1979),
            new Song("Pizzicato 5", "Twiggy Twiggy", 1991),
            new Song("Lene Lovich", "It's You, Only You (Mein Schmerz)", 1982),
            new Song("Kate Bush", "Babooshka", 1980),
            new Song("Devo", "Beautiful World", 1981),
            new Song("Devo", "Be Stiff", 1978),
            new Song("Lene Lovich", "Be Stiff", 1978),
            new Song("Devo", "Freedom of Choice", 1980),
            new Song("Anonymous", "Sumer is Icuumen In", 1250),
            new Song("Thomas Dolby", "She Blinded Me with Science", 1982),
            new Song("Kate Bush", "Them Heavy People", 1978)
    };

    public void testArtistComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.artistComparator);
        assertTrue(Comparators.artistComparator.compare(songs[0], songs[1]) > 0);
        assertTrue(Comparators.artistComparator.compare(songs[5], songs[6]) > 0);
    }

    public void testLambdaTitleComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.lambdaTitleComparator);
        assertTrue(Comparators.lambdaTitleComparator.compare(songs[0], songs[1]) < 0);
        assertTrue(Comparators.lambdaTitleComparator.compare(songs[1], songs[2]) > 0);
    }


    public void testKeyExtractorYearComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.keyExtractorYearComparator);
        assertTrue(Comparators.keyExtractorYearComparator.compare(songs[0], songs[1]) > 0);
        assertTrue(Comparators.keyExtractorYearComparator.compare(songs[1], songs[2]) > 0);
    }

    public void testTwoFieldComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.twoFieldComparator);
        assertTrue(Comparators.twoFieldComparator.compare(songs[6], songs[7]) > 0);
        assertTrue(Comparators.twoFieldComparator.compare(songs[0], songs[1]) > 0);
    }

    public void testComposedComparator()
    {
        // Add your tests after the assertNotNull()
        assertNotNull(Comparators.composedComparator);
        assertTrue(Comparators.composedComparator.compare(songs[6], songs[7]) > 0);
        assertTrue(Comparators.composedComparator.compare(songs[0], songs[1]) > 0);
    }

    public void testSort()
    {
        // This method is already complete.
        List<Song> songList = new ArrayList<>(Arrays.asList(songs));
        List<Song> expectedList = Arrays.asList(
                new Song("Anonymous", "Sumer is Icuumen In", 1250),
                new Song("Devo", "Be Stiff", 1978),
                new Song("Devo", "Beautiful World", 1981),
                new Song("Devo", "Freedom of Choice", 1980),
                new Song("Kate Bush", "Babooshka", 1980),
                new Song("Kate Bush", "The Wedding List", 1980),
                new Song("Kate Bush", "Them Heavy People", 1978),
                new Song("Lene Lovich", "Be Stiff", 1978),
                new Song("Lene Lovich", "It's You, Only You (Mein Schmerz)", 1982),
                new Song("Lene Lovich", "Lucky Number", 1979),
                new Song("Lene Lovich", "New Toy", 1981),
                new Song("Pizzicato 5", "Twiggy Twiggy", 1991),
                new Song("Thomas Dolby", "She Blinded Me with Science", 1982)
        );

        assertNotNull(Comparators.sortByArtistThenTitleThenYearComparator);
        songList.sort(Comparators.sortByArtistThenTitleThenYearComparator);

        assertEquals(songList, expectedList);
    }

    public void runTests()
    {
        Testy.run(
                () -> testArtistComparator(),
                () -> testLambdaTitleComparator(),
                () -> testKeyExtractorYearComparator(),
                () -> testTwoFieldComparator(),
                () -> testComposedComparator(),
                () -> testSort()
        );
    }
}
