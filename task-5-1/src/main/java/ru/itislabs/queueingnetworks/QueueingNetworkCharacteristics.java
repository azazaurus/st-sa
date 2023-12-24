package ru.itislabs.queueingnetworks;

public class QueueingNetworkCharacteristics {
	public final int n; // Count of servers
	public final double mu; // Service rate
	public final double lambda; // Arrival rate
	public final double tau; // Maximum time in queue

	public final double ro; // Network utilization
	public final double p0; // Fraction of time when all servers are free
	public final double pRefuse; // Probability that request will be refused
	public final double lambdaRefuse; // Fraction of refused requests
	public final double w0; // Average time in queue

	public QueueingNetworkCharacteristics(
			int n,
			double mu,
			double lambda,
			double tau,
			double ro,
			double p0,
			double pRefuse,
			double lambdaRefuse,
			double w0) {
		this.n = n;
		this.mu = mu;
		this.lambda = lambda;
		this.tau = tau;
		this.ro = ro;
		this.p0 = p0;
		this.pRefuse = pRefuse;
		this.lambdaRefuse = lambdaRefuse;
		this.w0 = w0;
	}
}
