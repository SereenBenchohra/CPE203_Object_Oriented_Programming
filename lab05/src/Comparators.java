
import java.util.Comparator;

//
//  Provide initializers for the static fields, as instructed.
//
public class Comparators 
{

    public final static Comparator<Song> 
    artistComparator = new ArtistComparator();

    public final static Comparator<Song>
    lambdaTitleComparator = (Song a, Song b) -> a.getTitle().compareTo(b.getTitle());

    public final static Comparator<Song> 
    keyExtractorYearComparator = Comparator.comparingInt(Song::getYear);

    public final static Comparator<Song> 
    twoFieldComparator = (Song a, Song b) ->
    {
        int i = a.getArtist().compareTo(b.getArtist());
        if( i == 0)
            {
                return Comparators.keyExtractorYearComparator.compare(a, b);
            }
        else
            { return i;}
    };
    public final static Comparator<Song> 
    composedComparator = new ComposedComparator(Comparators.artistComparator, Comparators.lambdaTitleComparator);

    public final static Comparator<Song> 
    sortByArtistThenTitleThenYearComparator = new ComposedComparator(Comparators.composedComparator, Comparators.keyExtractorYearComparator);
}
