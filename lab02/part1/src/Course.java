/*
 * This file should remain unchanged.
 */
class Course
{
    private final String name;
    private final int numUnits;

    public Course(final String name, final int numUnits)
    {
        this.name = name;
        this.numUnits = numUnits;
    }

    public String getName()
    {
        return name;
    }

    public int getNumUnits()
    {
        return numUnits;
    }
}
