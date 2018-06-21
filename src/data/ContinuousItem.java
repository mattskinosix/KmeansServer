package data;

public class ContinuousItem extends Item {

	public ContinuousItem(Attribute attribute, Object value) {
		super(attribute, value);

	}

	@Override
	public double distance(Object a) {
		double out = 0;
		out = (Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) (getValue()))
				- ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a)));
		return out;
	}

}