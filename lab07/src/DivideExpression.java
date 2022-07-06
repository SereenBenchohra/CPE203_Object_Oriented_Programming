class DivideExpression
    extends BinaryExpression
{
   
    public DivideExpression(final Expression lft, final Expression rht)
    {
         super(lft,rht);
        
    }
	

	@Override 
    protected double applyOperator(double l, double r)
    {
        return l / r;
    }
	
	@Override
	protected String getOperatorName()
	{
		return " / ";
	}
}

