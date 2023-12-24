package ru.itislabs.queueingnetworks;

public final class QueueingNetworkCalculator {
	private QueueingNetworkCalculator() {
	}

	public static QueueingNetworkCharacteristics calculateCharacteristics(
			QueueingNetworkInputCharacteristics queueingNetworkInputCharacteristics) {
		double ro = queueingNetworkInputCharacteristics.lambda / queueingNetworkInputCharacteristics.mu;
		double a = queueingNetworkInputCharacteristics.n / ro;
		double b = queueingNetworkInputCharacteristics.lambda * queueingNetworkInputCharacteristics.tau;
		double c = Math.exp(b * (1 - a));

		double bTilda = ro == queueingNetworkInputCharacteristics.n
			? 1 + b
			: (c - 1) / (1 - a);
		P0Result p0Result = calculateP0(ro, queueingNetworkInputCharacteristics.n, bTilda);
		double pRefuse = p0Result.roPowerNDividedByNFactorial * c * p0Result.p0;

		double dTilda = ro == queueingNetworkInputCharacteristics.n
			? 1 + b / 2
			: (a - c * (a + b * (a - 1))) / (b * (a - 1) * (a - 1));
		double w0 = queueingNetworkInputCharacteristics.tau
			* p0Result.roPowerNDividedByNFactorial
			* dTilda
			* p0Result.p0;

		return new QueueingNetworkCharacteristics(
			queueingNetworkInputCharacteristics.n,
			queueingNetworkInputCharacteristics.mu,
			queueingNetworkInputCharacteristics.lambda,
			queueingNetworkInputCharacteristics.tau,
			ro,
			p0Result.p0,
			pRefuse,
			queueingNetworkInputCharacteristics.lambda * pRefuse,
			w0);
	}

	private static P0Result calculateP0(double ro, int n, double bTilda) {
		double leftTerm = 1; // for k = 0
		double roPower = 1;
		long kFactorial = 1;
		double roPowerNDividedByNFactorial = 0;
		for (int k = 1; k <= n; ++k) {
			roPower *= ro;
			kFactorial *= k;

			double fractionToAdd = roPower / kFactorial;
			leftTerm += fractionToAdd;
			if (k == n)
				roPowerNDividedByNFactorial = fractionToAdd;
		}

		return new P0Result(
			1 / (leftTerm + roPowerNDividedByNFactorial * bTilda),
			roPowerNDividedByNFactorial);
	}

	private static class P0Result {
		public final double p0;
		public final double roPowerNDividedByNFactorial;

		public P0Result(double p0, double roPowerNDividedByNFactorial) {
			this.p0 = p0;
			this.roPowerNDividedByNFactorial = roPowerNDividedByNFactorial;
		}
	}
}
