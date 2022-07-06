import java.util.*;
import java.lang.Object;

// TODO:  Fill this in
public class ProductID
{
    String product;
    int productPrice;

    public ProductID(String product, int productPrice)
    {
        this.product = product;
        this.productPrice = productPrice;
    }

    public int hashCode()
    {
        int hash = 1;
        int hash2 = 1;
        int hash3 = 0;
        hash = hash * 31 + product.hashCode();
        hash2 = hash2 * 31 + productPrice;
        hash3 = hash2 + hash;
        return hash3;
    }
}