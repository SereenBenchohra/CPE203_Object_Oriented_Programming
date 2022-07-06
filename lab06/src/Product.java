
public class Product
{
    private String productId;
    private int price;

    public Product(String productId, int price)
    {
        this.price = price;
        this.productId = productId;
    }

    public String getId()
    {
        return productId;
    }
    public int getPrice()
    {
        return price;
    }

    @Override
    public String toString()
    {
        return ("productId = " + productId + " price = " + price);
    }

    @Override
    public int hashCode()
    {
        return productId.hashCode()*31 + price;
    }

    @Override
    public boolean equals(Object other)
    {
        if (other instanceof Product)
        {
            Product product = (Product)other;
            return productId.equals(product.productId) && price == product.price;
        }
        else
        {
            return false;
        }

    }
}
