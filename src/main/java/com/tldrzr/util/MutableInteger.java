package com.tldrzr.util;

public class MutableInteger implements Comparable<MutableInteger> {
	private int value = 0;

	public MutableInteger() {
		this(0);
	}

	public MutableInteger(int value) {
		this.value = value;
	}

	public MutableInteger(Integer value) {
		this.value = value == null ? 0 : value.intValue();
	}

	public MutableInteger set(int value) {
		this.value = value;
		return this;
	}

	public MutableInteger set(Integer value) {
		this.value = value == null ? 0 : value.intValue();
		return this;
	}

	public int get() {
		return value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	@Override
	public int hashCode() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o instanceof Integer) {
			return value == ((Integer) o).intValue();
		}
		if (o instanceof MutableInteger) {
			return value == ((MutableInteger) o).value;
		}
		return false;
	}

	@Override
	public int compareTo(MutableInteger o) {
		return value - o.value;
	}

}
