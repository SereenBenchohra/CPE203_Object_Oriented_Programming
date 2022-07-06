class SubtractExpression
    extends BinaryExpression
{


    public SubtractExpression(final Expression lft, final Expression rht)
    {
         super(lft,rht);
        
    }
	

	@Override 
    protected double applyOperator(double l, double r)
    {
        return l - r;
    }
	


	protected String getOperatorName()
	{
		return "-";
	}
}

