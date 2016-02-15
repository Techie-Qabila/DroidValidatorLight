package com.tmxlr.lib.driodvalidatorlight.helper;

/**
 * Thanks to kazuyamamoto
 * https://github.com/kazuyamamoto/range
 */

enum ComparisonSpec {

	LESS {
		@Override
		boolean isSatisfied(int comparison) {
			return comparison < 0;
		}
	},
	MORE {
		@Override
		boolean isSatisfied(int comparison) {
			return comparison > 0;
		}
	};

	abstract boolean isSatisfied(int comparison);
}

interface Limit<C extends Comparable<C>> {
	boolean includes(C c);
}

public class Range<C extends Comparable<C>> {

	private final Limit<C> low;
	private final Limit<C> high;

	private Range(Limit<C> l, Limit<C> h) {
		low = l;
		high = h;
	}

	public static <LC extends Comparable<LC>> Range<LC> less(LC c) {
		Limit<LC> l = new Infinite<>();
		Limit<LC> h = new Finite<>(c, false, ComparisonSpec.LESS);
		return new Range<>(l, h);
	}

	public static <ELC extends Comparable<ELC>> Range<ELC> equalOrLess(ELC c) {
		Limit<ELC> l = new Infinite<>();
		Limit<ELC> h = new Finite<>(c, true, ComparisonSpec.LESS);
		return new Range<>(l, h);
	}

	public static <MC extends Comparable<MC>> Range<MC> more(MC c) {
		Limit<MC> l = new Finite<>(c, false, ComparisonSpec.MORE);
		Limit<MC> h = new Infinite<>();
		return new Range<>(l, h);
	}

	public static <EMC extends Comparable<EMC>> Range<EMC> equalOrMore(EMC c) {
		Limit<EMC> l = new Finite<>(c, true, ComparisonSpec.MORE);
		Limit<EMC> h = new Infinite<>();
		return new Range<>(l, h);
	}

	public static <EMELC extends Comparable<EMELC>> Range<EMELC> equalsOrMoreAndEqualsOrLess(EMELC lc, EMELC hc) {
		return new Range<>(new Finite<>(lc, true, ComparisonSpec.MORE), new Finite<>(hc, true, ComparisonSpec.LESS));
	}

	public static <EL extends Comparable<EL>> Range<EL> equal(EL c) {
		return new Range<>(new Finite<>(c, true, ComparisonSpec.MORE), new Finite<>(c, true, ComparisonSpec.LESS));
	}

	public boolean includes(C c) {
		return low.includes(c) && high.includes(c);
	}
}

class Finite<C extends Comparable<C>> implements Limit<C> {
	private final C border;
	private final boolean includesBorder;
	private final ComparisonSpec spec;

	Finite(C c, boolean ib, ComparisonSpec s) {
		border = c;
		includesBorder = ib;
		spec = s;
	}

	@Override
	public boolean includes(C c) {
		int comparison = c.compareTo(border);
		return comparison == 0 ? includesBorder : spec.isSatisfied(comparison);
	}
}

class Infinite<C extends Comparable<C>> implements Limit<C> {

	@Override
	public boolean includes(C c) {
		return true;
	}
}
