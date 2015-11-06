package de.uni_mannheim.informatik.wdi.utils;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.joda.time.DateTime;

public class ProgressReporter {

	private int done = 0;
	private long lastTime = 0;
	private int total = 0;
	private long start = 0;
	private String message;
	
	public ProgressReporter(int totalElements, String message) {
		total = totalElements;
		start = System.currentTimeMillis();
		lastTime = start;
		this.message = message;
	}
	
	public void incrementProgress() {
		done++;
	}
	
	public void report() {
		// report status every second
		long now = System.currentTimeMillis();
		long durationSoFar = now - start;
		if ((now - lastTime) > 1000) {
			System.out
					.println(String
							.format("[%s] %s: %,d / %,d elements completed (%.2f%%) after %s",
									new DateTime(now).toString(),
									message,
									done,
									total,
									(double) done
											/ (double) total
											* 100,
									DurationFormatUtils
											.formatDurationHMS(durationSoFar)));
			lastTime = now;
		}
	}
	
}
