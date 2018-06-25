package data;

@SuppressWarnings("serial")
public class ContinuousItem extends Item {

	public ContinuousItem(Attribute attribute, Object value) {
		super(attribute, value);

	}

	@Override
	public double distance(Object a) {
		double out = 0;
//		if (a instanceof Double) {
//			if (getValue() instanceof Double) {
				out = (Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Double) (getValue()))
						- ((ContinuousAttribute) getAttribute()).getScaledValue((Double) a)));
//			}
//		}
//		else if (a instanceof Float) {
//			out = (Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Float) (getValue()))
//					- ((ContinuousAttribute) getAttribute()).getScaledValue((Float) a)));
//		}
//		else {
//			out = (Math.abs(((ContinuousAttribute) getAttribute()).getScaledValue((Integer) (getValue()))
//					- ((ContinuousAttribute) getAttribute()).getScaledValue((Integer) a)));
//		}
		return out;

	}

}