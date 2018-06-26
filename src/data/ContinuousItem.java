package data;

@SuppressWarnings("serial")
class ContinuousItem extends Item {

	ContinuousItem(ContinuousAttribute attribute, Object value) {
		super(attribute, value);

	}

	@Override
	double distance(Object a) {
		double out = 0;
		out = (Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue(((Number) (getValue())).floatValue())
				- ((ContinuousAttribute) getAttribute()).getScaledValue(((Number) a).floatValue())));
		return out;
	}

}