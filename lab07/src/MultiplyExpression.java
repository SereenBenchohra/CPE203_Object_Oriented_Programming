class MultiplyExpression
    extends BinaryExpression
{

    public MultiplyExpression(final Expression lft, final Expression rht)
    {
         super(lft,rht);
        
    }
	

	@Override 
    protected double applyOperator(double l, double r)
    {
        return l * r;
    }
	


	@Override
	protected String getOperatorName()
	{
		return "*";
	}
}

