abstract public class BinaryExpression implements Expression
{

	private final Expression lft;
    private final Expression rht;
	
	public BinaryExpression(final Expression lft, final Expression rht)
	{
		this.lft = lft;
        this.rht = rht;
	}
	
    public String toString()
	{
		return "(" + lft + getOperatorName() + rht + ")";

	}
     public double evaluate(final Bindings bindings)
	{
		double l = lft.evaluate(bindings);
		double r = rht.evaluate(bindings);
		return applyOperator(l,r);	
	}
	abstract protected double applyOperator(double l, double r);
	abstract protected String getOperatorName();


}
