import java.util.*;
import java.lang.Object;
// TODO:  Fill this in.  You must provide a constructor that accepts
// two string arguments, one for each part of the Customer ID.
public class CustomerID
{
    private String region;
    private String idInRegion;

    public CustomerID(String region, String idInRegion)
    {
        this.region = region;
        this.idInRegion = idInRegion;
    }
    @Override
    public boolean equals(Object other)
    {

        if(other instanceof CustomerID)
        {
            CustomerID customer = (CustomerID) other;

            return this.region == customer.region && this.idInRegion == customer.idInRegion;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode()
    {
        return region.hashCode()*31 + idInRegion.hashCode()*31 ;
    }

}
