package ru.itislabs.queueingnetworks;

public class QueueingNetworkUserInputCharacteristics {
	public final int n;
	public final double mu;
	public final double tauSeconds;

	public QueueingNetworkUserInputCharacteristics(int n, double mu, double tauSeconds) {
		this.n = n;
		this.mu = mu;
		this.tauSeconds = tauSeconds;
	}
}
