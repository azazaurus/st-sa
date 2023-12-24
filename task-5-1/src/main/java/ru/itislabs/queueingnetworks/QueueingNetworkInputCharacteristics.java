package ru.itislabs.queueingnetworks;

public class QueueingNetworkInputCharacteristics {
	public final int n; // Count of servers
	public final double mu; // Service rate
	public final double lambda; // Arrival rate
	public final double tau; // Maximum time in queue

	public QueueingNetworkInputCharacteristics(int n, double mu, double lambda, double tau) {
		this.n = n;
		this.mu = mu;
		this.lambda = lambda;
		this.tau = tau;
	}
}
